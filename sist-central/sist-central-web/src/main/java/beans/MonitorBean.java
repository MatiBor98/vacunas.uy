package beans;


import datos.entidades.Departamento;
import datos.entidades.Enfermedad;
import jdk.jfr.DataAmount;
import logica.negocios.ReservaBean;
import logica.servicios.local.EnfermedadServiceLocal;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("MonitorBean")
@RequestScoped
@Data
public class MonitorBean {

    @EJB
    EnfermedadServiceLocal enfermedadServiceLocal;

    @EJB
    ReservaBean reservaBean;

    private String selEnfermedad, selVacuna, selEtapa;

    private List<String> enfermedades, vacunas, etapas;


    private Integer rn,pa,fs,ar,sj,co,ro,mo,ma,la,fd,ca,tt,ta,rv,du,cl,so,sa;
    private Map<Departamento, Integer> dosisDadasDepartamento;


    public MonitorBean() {
        this.dosisDadasDepartamento = new HashMap<>();

    }

    @PostConstruct
    private void initEnfermedades(){
        this.enfermedades = enfermedadServiceLocal.find().stream().map(Enfermedad::getNombre).collect(Collectors.toList());
    }

    public void loadData(){
        dosisDadasDepartamento = reservaBean.getDosisPorDepartamentos();
    }
}
