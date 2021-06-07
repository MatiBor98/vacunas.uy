package datos.dtos;

import java.io.Serializable;
import java.util.List;


public class DosisDadaDTO implements Serializable {

    private int codigo;

    private List<String> enfermedades;

    private String nombreVacuna;

    private String fecha;


    private Integer paraDosis;

    private String periodoInmunidadInicio, periodoInmunidadFin;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public List<String> getEnfermedad() {
        return enfermedades;
    }

    public void setEnfermedad(List<String> enfermedad) {
        this.enfermedades = enfermedad;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getParaDosis() {
        return paraDosis;
    }

    public void setParaDosis(Integer paraDosis) {
        this.paraDosis = paraDosis;
    }

    public List<String> getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(List<String> enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getPeriodoInmunidadInicio() {
        return periodoInmunidadInicio;
    }

    public void setPeriodoInmunidadInicio(String periodoInmunidadInicio) {
        this.periodoInmunidadInicio = periodoInmunidadInicio.toString();
    }

    public String getPeriodoInmunidadFin() {
        return periodoInmunidadFin;
    }

    public void setPeriodoInmunidadFin(String periodoInmunidadFin) {
        this.periodoInmunidadFin = periodoInmunidadFin.toString();
    }

    public DosisDadaDTO(int codigo, List<String> enfermedades, String nombreVacuna, String fecha, Integer paraDosis, String periodoInmunidadInicio, String periodoInmunidadFin) {
        this.codigo = codigo;
        this.enfermedades = enfermedades;
        this.nombreVacuna = nombreVacuna;
        this.fecha = fecha;
        this.paraDosis = paraDosis;
        this.periodoInmunidadInicio = periodoInmunidadInicio.toString();
        this.periodoInmunidadFin = periodoInmunidadFin.toString();
    }

    public DosisDadaDTO() {
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }
}
