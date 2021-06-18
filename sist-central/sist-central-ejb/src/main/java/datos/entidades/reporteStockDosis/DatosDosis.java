package datos.entidades.reporteStockDosis;

import datos.entidades.Enfermedad;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@IdClass(diaVacunatorioVacuna.class)
public class DatosDosis {
    @Id
    private LocalDate fecha;
    @Id
    private String nombreVacunatorio;
    @Id
    private String nombreVacuna;

    private Integer cantidad;

    public DatosDosis() {
    }

    public DatosDosis(LocalDate fecha, String nombreVacunatorio, String nombreVacuna, Integer cantidad) {
        this.fecha = fecha;
        this.nombreVacunatorio = nombreVacunatorio;
        this.nombreVacuna = nombreVacuna;
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombreVacunatorio() {
        return nombreVacunatorio;
    }

    public void setNombreVacunatorio(String nombreVacunatorio) {
        this.nombreVacunatorio = nombreVacunatorio;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

