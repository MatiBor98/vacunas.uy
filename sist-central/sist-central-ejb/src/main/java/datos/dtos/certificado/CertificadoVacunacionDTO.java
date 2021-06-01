package datos.dtos.certificado;


import datos.dtos.DosisDadaDTO;

import java.io.Serializable;
import java.util.List;

public class CertificadoVacunacionDTO implements Serializable {
    private String ci, nombre;
    private String fechaExpedido;
    private List<DosisDadaDTO> reservas;

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaExpedido() {
        return fechaExpedido;
    }

    public void setFechaExpedido(String fechaExpedido) {
        this.fechaExpedido = fechaExpedido.toString();
    }

    public List<DosisDadaDTO> getReservas() {
        return reservas;
    }

    public void setReservas(List<DosisDadaDTO> reservas) {
        this.reservas = reservas;
    }

    public CertificadoVacunacionDTO() {
    }

    public CertificadoVacunacionDTO(String ci, String nombre, String fechaExpedido, List<DosisDadaDTO> reservas) {
        this.ci = ci;
        this.nombre = nombre;
        this.fechaExpedido = fechaExpedido.toString();
        this.reservas = reservas;
    }
}
