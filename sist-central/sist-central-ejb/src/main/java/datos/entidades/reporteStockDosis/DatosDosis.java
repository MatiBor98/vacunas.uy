package datos.entidades.reporteStockDosis;

import datos.entidades.Departamento;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@IdClass(diaVacunatorioVacuna.class)
public class DatosDosis implements Comparable<DatosDosis> {
    @Id
    private LocalDate fecha;
    @Id
    private String nombreVacunatorio;
    @Id
    private String nombreVacuna;

    private Departamento departamento;

    private Integer cantidad;

    public DatosDosis() {
    }

    public DatosDosis(LocalDate fecha, String nombreVacunatorio, String nombreVacuna, Departamento departamento, Integer cantidad) {
        this.fecha = fecha;
        this.nombreVacunatorio = nombreVacunatorio;
        this.nombreVacuna = nombreVacuna;
        this.departamento = departamento;
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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public int compareTo(DatosDosis o) {
        return this.getFecha().toString().compareTo(o.getFecha().toString());
    }
}

