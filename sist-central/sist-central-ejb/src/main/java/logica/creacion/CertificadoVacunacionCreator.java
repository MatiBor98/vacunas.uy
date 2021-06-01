package logica.creacion;

import datos.dtos.DosisDadaDTO;
import datos.dtos.certificado.CertificadoVacunacionDTO;
import datos.entidades.*;
import datos.repositorios.CiudadanoRepositoryLocal;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CertificadoVacunacionCreator {

    @EJB
    CiudadanoRepositoryLocal ciudadanoRepositoryLocal;

    public CertificadoVacunacionCreator() {
    }

    public CertificadoVacunacionDTO create(int ci){
        Ciudadano ciudadanoConReservas = ciudadanoRepositoryLocal.getCiudadanoConReservas(ci);

        String nombre = ciudadanoConReservas.getNombre();
        List< DosisDadaDTO > dosisDadas = new ArrayList<>();

        Collection<Reserva> reservas = ciudadanoConReservas.getReservas().values();

        for (Reserva reserva : reservas){
            if (reserva.getEstado() == Estado.VACUNADO){
                
                Vacuna vacuna = reserva.getIntervalo().getAgenda().getEtapa().getVacuna();

                List<String> enfermedades = vacuna.getEnfermedades().stream().map(Enfermedad::getNombre).collect(Collectors.toList());

                LocalDate fechaini = null;
                LocalDate fechafin = null;

                if( vacuna.getCantDosis() == reserva.getParaDosis()){
                    fechaini = reserva.getIntervalo().getFechayHora().toLocalDate();
                    fechafin = fechaini.plusMonths(vacuna.getInmunidadMeses());
                }


                dosisDadas.add(new DosisDadaDTO( reserva.getCodigo(), enfermedades, 
                        reserva.getIntervalo().getFechayHora().toLocalDate().toString(), reserva.getParaDosis(), fechafin.toString(), fechaini.toString()));
            }
        }


        return new CertificadoVacunacionDTO( String.valueOf(ci),  nombre, LocalDate.now().toString(), dosisDadas);
    }
}
