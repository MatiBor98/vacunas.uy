package logica.negocios;


import datos.entidades.*;
import datos.repositorios.*;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Singleton
@LocalBean
public class ReservaDomicilioBean implements logica.servicios.local.ReservaDomicilioBeanServiceLocal {

    @EJB
    ReservaDomicilioRepository reservaRepository;

    @EJB
    CiudadanoRepositoryLocal ciudadanoRepository;

    @EJB
    EnfermedadRepositoryLocal enfermedadRepository;

    @Override
    public List<ReservaDomicilio> findReservasDomicilioCiudadano(int offset, int limit, int ci){
        return reservaRepository.findReservasADomicilioCiudadano(offset, limit, ci);
    }

    @Override
    public List<ReservaDomicilio> findReservasDomicilio(int offset, int limit){
        return reservaRepository.findReservasADomicilio(offset, limit);
    }
    private void saveReservaDomicilio(int ci, Departamento dep, String direccion, String localidad, int paraDosis, Vacuna vacuna, LocalDate fecha) {
        ReservaDomicilio nuevaReserva = new ReservaDomicilio();
        Ciudadano ciudadano = ciudadanoRepository.find(ci).orElseThrow(RuntimeException::new);
        nuevaReserva.setCiudadano(ciudadano);
        nuevaReserva.setVacuna(vacuna);
        nuevaReserva.setDepartamento(dep);
        nuevaReserva.setDireccion(direccion);
        nuevaReserva.setLocalidad(localidad);
        nuevaReserva.setEstadoAprobacion(Estado.PENDIENTE);
        nuevaReserva.setEstadoVacunacion(Estado.PENDIENTE);
        nuevaReserva.setParaDosis(paraDosis);
        nuevaReserva.setFecha(fecha);
        reservaRepository.saveReservaDomicilio(nuevaReserva);
    }
    @Override
    public void efectuarReservaDomicilio(int ci, Departamento dep, String direccion, String localidad, String enfermedad, LocalDate fecha) {


        LocalDate primeraDosis = fecha;

        Vacuna vac = enfermedadRepository.find(enfermedad).get().getVacunas().get(0);

        this.saveReservaDomicilio(ci, dep, direccion, localidad, 1, vac, primeraDosis);
        LocalDate siguienteDosis = primeraDosis;
        for(int dosis = 2; dosis <= vac.getCantDosis(); dosis++) {
            siguienteDosis = siguienteDosis.plusDays(vac.getDosisSeparacionDias());
            if(siguienteDosis.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                siguienteDosis = siguienteDosis.plusDays(2);
            }
            else if (siguienteDosis.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                siguienteDosis = siguienteDosis.plusDays(1);
            }
            this.saveReservaDomicilio(ci, dep, direccion, localidad, dosis, vac, siguienteDosis);
        }

    }


    @Override
    public void aceptarReservaDomicilio(int codigo) {
        ReservaDomicilio reserva = reservaRepository.findReservaADomicilio(codigo).orElseThrow();

        if(reserva.getEstadoAprobacion() == Estado.PENDIENTE) {
            reserva.setEstadoAprobacion(Estado.VACUNADO);
        }

    }

    @Override
    public void rechazarReservaDomicilio(int codigo) {
        ReservaDomicilio reserva = reservaRepository.findReservaADomicilio(codigo).orElseThrow();

        if(reserva.getEstadoAprobacion() == Estado.PENDIENTE) {
            reserva.setEstadoAprobacion(Estado.CANCELADA);
        }

    }


    @Override
    public void aceptarReservaDomicilioVacunacion(int codigo) {
        ReservaDomicilio reserva = reservaRepository.findReservaADomicilio(codigo).orElseThrow();

        if(reserva.getEstadoVacunacion() == Estado.PENDIENTE) {
            reserva.setEstadoVacunacion(Estado.VACUNADO);
        }

    }

    @Override
    public void rechazarReservaDomicilioVacunacion(int codigo) {
        ReservaDomicilio reserva = reservaRepository.findReservaADomicilio(codigo).orElseThrow();

        if(reserva.getEstadoVacunacion() == Estado.PENDIENTE) {
            reserva.setEstadoVacunacion(Estado.CANCELADA);
        }

    }


}
