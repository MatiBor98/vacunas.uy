package beans;

import logica.scheduled.InitSchedule;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("ReporteStockDosisBean")
@RequestScoped
public class ReporteStockDosisBean {

    @EJB
    InitSchedule initSchedule;

    public void generarDatosHoy(){
        initSchedule.guardarDatosStockDosis();
    }

    public ReporteStockDosisBean() {
    }
}
