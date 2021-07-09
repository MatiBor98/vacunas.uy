package datos.entidades;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class ReservaDomicilio {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="reservaDOMId")
    @SequenceGenerator(name="reservaDOMId",sequenceName="reservaDOMId", allocationSize=1)
    private int codigo;

    @ManyToOne
    private Ciudadano ciudadano;

    private Estado estadoVacunacion;

    private Estado estadoAprobacion;

    private String direccion, localidad;

    private Departamento departamento;

    private int paraDosis;

    private LocalDate fecha;

    @ManyToOne
    private Vacuna vacuna;


    public ReservaDomicilio() {
    }

    public ReservaDomicilio(int codigo, Ciudadano ciudadano, Estado estado, Estado estadoAprobacion, String direccion, String localidad, Departamento departamento, int paraDosis, LocalDate fecha, Vacuna vacuna) {
        this.codigo = codigo;
        this.ciudadano = ciudadano;
        this.estadoVacunacion = estado;
        this.estadoAprobacion = estadoAprobacion;
        this.direccion = direccion;
        this.localidad = localidad;
        this.departamento = departamento;
        this.paraDosis = paraDosis;
        this.fecha = fecha;
        this.vacuna = vacuna;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    public Estado getEstadoVacunacion() {
        return estadoVacunacion;
    }

    public void setEstadoVacunacion(Estado estado) {
        this.estadoVacunacion = estado;
    }

    public Estado getEstadoAprobacion() {
        return estadoAprobacion;
    }

    public void setEstadoAprobacion(Estado estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public int getParaDosis() {
        return paraDosis;
    }

    public void setParaDosis(int paraDosis) {
        this.paraDosis = paraDosis;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }


}
