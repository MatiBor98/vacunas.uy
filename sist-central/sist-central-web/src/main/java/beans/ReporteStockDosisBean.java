package beans;

import com.google.gson.Gson;
import datos.entidades.*;
import datos.entidades.reporteStockDosis.DatosDosis;
import datos.repositorios.DosisReporteReporteBean;
import logica.negocios.ReservaBean;
import logica.scheduled.InitSchedule;
import logica.servicios.local.EnfermedadServiceLocal;
import logica.servicios.local.VacunaServiceLocal;
import logica.servicios.local.VacunatorioControllerLocal;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Named("ReporteStockDosisBean")
@ViewScoped
public class ReporteStockDosisBean implements Serializable {

    @EJB
    InitSchedule initSchedule;

    public void generarDatosHoy(){
        initSchedule.guardarDatosStockDosis();
    }

    public ReporteStockDosisBean() {
        this.selEnfermedad = "";
        this.selVacunaaa = "";
        this.selDepartamento = null;
        this.selVacunatorio = "";
    }

    @EJB
    EnfermedadServiceLocal enfermedadServiceLocal;

    @EJB
    VacunaServiceLocal vacunaServiceLocal;

    @EJB
    VacunatorioControllerLocal vacunatorioControllerLocal;

    @EJB
    DosisReporteReporteBean dosisReporteReporteBean;

    protected String selEnfermedad;

    protected String selVacunaaa;
    protected Departamento selDepartamento;
    protected String selVacunatorio;

    protected List<String> enfermedades, vacunas;
    protected List<String>  vacunatorios;
    private List<Departamento> departamentos;

    private LocalDate dateFechainicio, dateFechaFin;

    private Integer dosisAdministradas;

    public Integer getDosisAdministradas() {
        return dosisAdministradas;
    }

    public void setDosisAdministradas(Integer dosisAdministradas) {
        this.dosisAdministradas = dosisAdministradas;
    }

    public void onDateSelectInicio(SelectEvent<LocalDate> event) {
        dateFechainicio = event.getObject();
    }

    public void onDateSelectFin(SelectEvent<LocalDate> event) {
        dateFechaFin = event.getObject();
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

    public String getSelEnfermedad() {
        return selEnfermedad;
    }

    public void setSelEnfermedad(String selEnfermedad) {
        this.selEnfermedad = selEnfermedad;
        this.selVacunaaa = null;
        initVacunas();
    }

    public String getSelVacunaaa() {
        return selVacunaaa;
    }

    public void setSelVacunaaa(String selVacunaaa) {
        this.selVacunaaa = selVacunaaa;
    }

    public void averVacuna(SelectEvent<String> event){
        this.selVacunaaa = event.getObject();
    }
    public void averVTorio(SelectEvent<String> event){
        this.selVacunatorio = event.getObject();
    }


    public void initVacunas(){
        if (selEnfermedad == null || selEnfermedad.equals("")) {
            this.vacunas = new ArrayList<>();
        }
        else {
            this.vacunas = vacunaServiceLocal.findByEnfermedad(selEnfermedad).stream().map(Vacuna::getNombre)
                    .collect(Collectors.toList());
        }
    }


    public void initVacunatorios(){
        if (selDepartamento == null || selDepartamento.equals("")) {
            this.vacunatorios = new ArrayList<>();
        }
        else {
            this.vacunatorios = vacunatorioControllerLocal.findByDepartamento(selDepartamento).stream().map(Vacunatorio::getNombre).collect(Collectors.toList());
        }
    }


    @PostConstruct
    protected void initEnfermedades(){
        this.enfermedades = enfermedadServiceLocal.find().stream().map(Enfermedad::getNombre).collect(Collectors.toList());

        this.departamentos = Arrays.asList(Departamento.values());
        loadData();
    }




    public void loadData(){

        if (dateFechainicio == null || dateFechaFin == null) {
            dateFechainicio = LocalDate.of(2021, 1, 1);
            dateFechaFin = LocalDate.of(2021, 7, 1);


        }


        if(selEnfermedad == null){ selEnfermedad = "";}
        if(selVacunaaa == null){ selVacunaaa = "";}
        //if(selDepartamento == null){ selDepartamento = "";}
        if(selVacunatorio == null){ selVacunatorio = "";}


        List<DatosDosis> datosDosisList = dosisReporteReporteBean.findDatos(selEnfermedad, selVacunaaa, selVacunatorio, dateFechainicio, dateFechaFin);
        Collections.sort(datosDosisList);


        Map<String, Integer> datosMap = new LinkedHashMap<>();
        for (DatosDosis dato : datosDosisList){
            String fechaClave = dato.getFecha().toString();
            if (datosMap.get(fechaClave) == null){
                datosMap.put(fechaClave, dato.getCantidad());
            }
            else {
                datosMap.put(fechaClave, datosMap.get(fechaClave) + dato.getCantidad());
            }
        }


        Integer dosishoy = datosMap.get(LocalDate.now().toString());

        dosisAdministradas = (dosishoy == null) ?  0 :  dosishoy;

        PrimeFaces.current().executeScript(String.format("updateDosisHoy('%s');", dosisAdministradas.toString()));

        String datosJson = new Gson().toJson(datosMap).toString();
        PrimeFaces.current().executeScript(String.format("dosisChart('%s');", datosJson));
    }

    public Departamento getSelDepartamento() {
        return selDepartamento;
    }

    public void setSelDepartamento(Departamento selDepartamento) {
        this.selDepartamento = selDepartamento;
        this.selVacunatorio = null;
        initVacunatorios();
    }

    public String getSelVacunatorio() {
        return selVacunatorio;
    }

    public void setSelVacunatorio(String selVacunatorio) {
        this.selVacunatorio = selVacunatorio;
    }

    public List<String> getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(List<String> enfermedades) {
        this.enfermedades = enfermedades;
    }

    public List<String> getVacunas() {
        return vacunas;
    }

    public void setVacunas(List<String> vacunas) {
        this.vacunas = vacunas;
    }

    public List<String> getVacunatorios() {
        return vacunatorios;
    }

    public void setVacunatorios(List<String> vacunatorios) {
        this.vacunatorios = vacunatorios;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
}
