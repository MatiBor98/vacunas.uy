package beans;


import com.google.gson.Gson;
import datos.entidades.Enfermedad;
import datos.entidades.Etapa;
import datos.entidades.Reserva;
import datos.entidades.Vacuna;
import logica.negocios.ReservaBean;
import logica.servicios.local.EnfermedadServiceLocal;
import logica.servicios.local.VacunaServiceLocal;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.primefaces.component.piechart.PieChart;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import plataformainteroperabilidad.Sexo;
import plataformainteroperabilidad.Trabajo;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Named("ReporteVacunacionesBean")
@SessionScoped
public class ReporteVacunacionesBean extends MonitorBean implements Serializable {

    private BarChartModel barModelDosisTiempo;

    private PieChartModel pieChartSexo;
    private PieChartModel pieChartTrabajos;

    private String fechaInicio, fechaFin;
    private LocalDate dateFechainicio, dateFechaFin;

    private Map<String, Number> datosTrabajos;

    @PostConstruct
    public void init() {
        barModelDosisTiempo = new BarChartModel();
        pieChartSexo = new PieChartModel();
        pieChartTrabajos = new PieChartModel();

        loadReporteData();
    }

    public void loadReporteData() {
        Integer etapaInt = -1;
        if (!(selEtapa == null || selEtapa.equals(""))) {
            etapaInt = Integer.parseInt(selEtapa);
        }

        if (selEnfermedad == null) {
            selEnfermedad = "";
        }
        if (selVacuna == null) {
            selVacuna = "";
        }

        dosisDadasDepartamento = reservaBean.getDosisPorDepartamentos(selEnfermedad, selVacuna, etapaInt, dateFechainicio, dateFechaFin);
        String dosisDadasDepartamentoJSON = new Gson().toJson(dosisDadasDepartamento).toString();
        String scriptUpdateMapa = String.format("updateMapa('%s');", dosisDadasDepartamentoJSON);
        PrimeFaces.current().executeScript(scriptUpdateMapa);


        procesarDatosVacunaciones(etapaInt);
    }


    public void procesarDatosVacunaciones(Integer etapaInt) {
        if (dateFechainicio == null || dateFechaFin == null) {
            dateFechainicio = LocalDate.of(2019, 1, 1);
            dateFechaFin = LocalDate.of(2022, 1, 1);


        }


        List<Reserva> allDosisDadas = reservaBean.findAllDosisDadas(selEnfermedad, selVacuna, etapaInt, dateFechainicio, dateFechaFin);

        pieChartSexo = new PieChartModel();
        pieChartTrabajos = new PieChartModel();


        List<Number> sexoValues = new ArrayList<>(Arrays.asList(0, 0, 0)); // HOMBRES, MUJERES, INDEFINIDO POR PDI
        datosTrabajos = new LinkedHashMap<>();


        long diasVisualizables = dateFechainicio.until(dateFechaFin, ChronoUnit.DAYS);
        String modoVisualizacionBarChart = diasVisualizables < 14 ? "dias" : diasVisualizables > 98 ? "meses" : "semanas";

        Map<String, Number> datosBarChart = new LinkedHashMap<>();


        if (modoVisualizacionBarChart.equals("dias")){
            LocalDate iterator = dateFechainicio;
            while (iterator.isBefore(dateFechaFin)){
                datosBarChart.put(iterator.toString(), 0);
                iterator = iterator.plus(1, ChronoUnit.DAYS);
            }
        }
        if (modoVisualizacionBarChart.equals("semanas")){
            LocalDate iterator = dateFechainicio;
            while (iterator.isBefore(dateFechaFin)){
                LocalDate aux = iterator.withDayOfMonth(((iterator.getDayOfMonth() / 7)*7) + 1);
                datosBarChart.put(aux.toString(), 0);
                iterator = iterator.plus(7, ChronoUnit.DAYS);
            }
        }
        if (modoVisualizacionBarChart.equals("meses")){
            LocalDate iterator = dateFechainicio;
            while (iterator.isBefore(dateFechaFin)){
                LocalDate aux = iterator.withDayOfMonth(1);
                datosBarChart.put(aux.toString(), 0);
                iterator = iterator.plus(1, ChronoUnit.MONTHS);
            }
        }


        for (Reserva reserva : allDosisDadas) {
            //sexo
            if (reserva.getCiudadano().getSexo() != null) {
                if (reserva.getCiudadano().getSexo() == Sexo.HOMBRE) {
                    sexoValues.set(0, sexoValues.get(0).intValue() + 1);
                } else {
                    sexoValues.set(1, sexoValues.get(1).intValue() + 1);
                }
            }
            // Trabajo
            Trabajo trabajo = reserva.getCiudadano().getTrabajo();
            if (trabajo != null) {
                if (datosTrabajos.get(trabajo.toString()) == null) {
                    datosTrabajos.put(trabajo.toString(), 1);
                } else {
                    datosTrabajos.put(trabajo.toString(), datosTrabajos.get(trabajo.toString()).intValue() + 1);
                }
            }
            // Fechas
            LocalDate fecha = reserva.getIntervalo().getFechayHora().toLocalDate();
            if (modoVisualizacionBarChart.equals("dias")) {

            }
            if (modoVisualizacionBarChart.equals("semanas")) {
                fecha = fecha.withDayOfMonth(((fecha.getDayOfMonth() / 7)*7) + 1); // primer dia de
            }
            if (modoVisualizacionBarChart.equals("meses")) {
                fecha = fecha.withDayOfMonth(1);
            }

            // insertar
            if (datosBarChart.get(fecha.toString()) == null) {
                datosBarChart.put(fecha.toString(), 1);
            } else {
                datosBarChart.put(fecha.toString(), datosBarChart.get(fecha.toString()).intValue() + 1);
            }

        }


        createSexoChart(sexoValues);
        createTrabajoChart(datosTrabajos);
        createBarChart(datosBarChart);
    }

    private void createBarChart(Map<String, Number> datosBarChart) {
        /*barModelDosisTiempo = new BarChartModel();
        ChartData data = new ChartData();
        BarChartDataSet dataSet = new BarChartDataSet ();
        dataSet.setLabel("Evolución");
        dataSet.setData(new ArrayList<Number>(datosBarChart.values()));

        List<String> bgColors = new ArrayList<>();
        bgColors.add("#2196F3");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>(datosBarChart.keySet());
        data.setLabels(labels);

        barModelDosisTiempo.setData(data);*/

        Number accum = 0;

        for (Map.Entry<String, Number> entry : datosBarChart.entrySet()){
            accum = accum.intValue() + entry.getValue().intValue();
            entry.setValue(accum);
        }


        String datosJson = new Gson().toJson(datosBarChart).toString();
        PrimeFaces.current().executeScript(String.format("evolucionChart('%s');", datosJson));

    }

    private void createSexoChart(List<Number> sexoValues) {

        ChartData data = new ChartData();
        PieChartDataSet dataSet = new PieChartDataSet();
        dataSet.setData(sexoValues);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Hombre");
        labels.add("Mujer");
        data.setLabels(labels);

        pieChartSexo.setData(data);
    }

    private void createTrabajoChart(Map<String, Number> trabajoValues) {

        ChartData data = new ChartData();
        PieChartDataSet dataSet = new PieChartDataSet();
        dataSet.setData(new ArrayList<Number>(trabajoValues.values()));

        List<String> bgColors = new ArrayList<>();
        bgColors.add("#D64933");
        bgColors.add("#2B303A");
        bgColors.add("#0C7C59");
        bgColors.add("#58A4B0");
        bgColors.add("#BAC1B8");
        bgColors.add("#600047");
        bgColors.add("#44001A");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>(trabajoValues.keySet());
        data.setLabels(labels);

        pieChartTrabajos.setData(data);
    }


    public void onDateSelectInicio(SelectEvent<LocalDate> event) {
        dateFechainicio = event.getObject();
    }

    public void onDateSelectFin(SelectEvent<LocalDate> event) {
        dateFechaFin = event.getObject();
    }


    public BarChartModel getBarModelDosisTiempo() {
        return barModelDosisTiempo;
    }

    public void setBarModelDosisTiempo(BarChartModel barModelDosisTiempo) {
        this.barModelDosisTiempo = barModelDosisTiempo;
    }

    public PieChartModel getPieChartSexo() {
        return pieChartSexo;
    }

    public void setPieChartSexo(PieChartModel pieChartSexo) {
        this.pieChartSexo = pieChartSexo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getDateFechainicio() {
        return dateFechainicio;
    }

    public void setDateFechainicio(LocalDate dateFechainicio) {
        this.dateFechainicio = dateFechainicio;
    }

    public LocalDate getDateFechaFin() {
        return dateFechaFin;
    }

    public void setDateFechaFin(LocalDate dateFechaFin) {
        this.dateFechaFin = dateFechaFin;
    }

    public ReporteVacunacionesBean() {
    }

    public PieChartModel getPieChartTrabajos() {
        return pieChartTrabajos;
    }

    public void setPieChartTrabajos(PieChartModel pieChartTrabajos) {
        this.pieChartTrabajos = pieChartTrabajos;
    }
}
