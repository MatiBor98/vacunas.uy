package beans;


import com.google.gson.Gson;
import datos.entidades.Enfermedad;
import datos.entidades.Etapa;
import datos.entidades.Vacuna;
import logica.negocios.ReservaBean;
import logica.servicios.local.EnfermedadServiceLocal;
import logica.servicios.local.VacunaServiceLocal;
import lombok.Data;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("MonitorBean")
@ViewScoped
@Data
public class MonitorBean implements Serializable {

    @EJB
    EnfermedadServiceLocal enfermedadServiceLocal;

    @EJB
    ReservaBean reservaBean;

    @EJB
    VacunaServiceLocal vacunaServiceLocal;



    private String selEnfermedad;



    private String selVacuna;
    private String selEtapa;

    private List<String> enfermedades, vacunas;
    private List<Integer>  etapas;


    //private Integer rn,pa,fs,ar,sj,co,ro,mo,ma,la,fd,ca,tt,ta,rv,du,cl,so,sa;
    private Map<String, Integer> dosisDadasDepartamento;

    private Integer VacunadosHoy, DosisAdministradas, AgendadosProximos;

    public String getSelEnfermedad() {
        return selEnfermedad;
    }

    public void setSelEnfermedad(String selEnfermedad) {
        this.selEnfermedad = selEnfermedad;
        this.selVacuna = null;
        this.selEtapa = null;

        initVacunas();
        initEtapas();
    }
    public String getSelVacuna() {
        return selVacuna;
    }

    public void setSelVacuna(String selVacuna) {
        this.selVacuna = selVacuna;
        this.selEtapa = null;
        initEtapas();

    }



    public MonitorBean() {
        this.dosisDadasDepartamento = new HashMap<>();
        this.selEnfermedad = "";
        this.selVacuna = "";
        this.selEtapa = "";
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

    public void initEtapas(){
        if (selVacuna == null || selVacuna.equals("")) {
            this.vacunas = new ArrayList<>();
        }
        else {
            List<Etapa> etapasList = vacunaServiceLocal.getEtapas(selVacuna);
            this.etapas = etapasList.stream().map(Etapa::getId)
                    .collect(Collectors.toList());
        }
    }

    @PostConstruct
    private void initEnfermedades(){
        this.enfermedades = enfermedadServiceLocal.find().stream().map(Enfermedad::getNombre).collect(Collectors.toList());

        loadData();
    }




    public void loadData(){

        Integer etapaInt = -1;
        if (!(selEtapa == null || selEtapa.equals(""))){ etapaInt = Integer.parseInt(selEtapa);}

        if(selEnfermedad == null){ selEnfermedad = "";}
        if(selVacuna == null){ selVacuna = "";}

        dosisDadasDepartamento = reservaBean.getDosisPorDepartamentos(selEnfermedad,selVacuna,etapaInt);
        String dosisDadasDepartamentoJSON = new Gson().toJson(dosisDadasDepartamento).toString();
        String scriptUpdateMapa = String.format("updateMapa('%s');", dosisDadasDepartamentoJSON);
        PrimeFaces.current().executeScript(scriptUpdateMapa);


        VacunadosHoy = reservaBean.findVacunadosHoy(selEnfermedad,selVacuna,etapaInt);
        String scriptVacunadosHoy = String.format("updateVacunadosHoy('%s');", VacunadosHoy);
        PrimeFaces.current().executeScript(scriptVacunadosHoy);

        DosisAdministradas = dosisDadasDepartamento.values().parallelStream().reduce(0, Integer::sum);
        String scriptDosisAdministradas = String.format("updateDosisAdministradas('%s');", DosisAdministradas);
        PrimeFaces.current().executeScript(scriptDosisAdministradas);

        AgendadosProximos = reservaBean.findAgendadosProximos(selEnfermedad,selVacuna,etapaInt);
        String scriptAgendadosProximos = String.format("updateAgendadosProximos('%s');", AgendadosProximos);
        PrimeFaces.current().executeScript(scriptAgendadosProximos);


    }
}
