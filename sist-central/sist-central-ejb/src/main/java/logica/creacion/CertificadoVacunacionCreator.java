package logica.creacion;

import datos.dtos.DosisDadaDTO;
import datos.dtos.CertificadoVacunacionDTO;
import datos.entidades.*;
import datos.repositorios.CiudadanoRepositoryLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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

                String fechaini = "N/A";
                String fechafin = "N/A";

                if( vacuna.getCantDosis() == reserva.getParaDosis()){
                    LocalDate fechainiDate = reserva.getIntervalo().getFechayHora().toLocalDate();

                    fechaini = fechainiDate.toString();
                    fechafin = fechainiDate.plusMonths(vacuna.getInmunidadMeses()).toString();


                }


                int codigo = reserva.getCodigo();

                String fechaEmitido = reserva.getIntervalo().getFechayHora().toLocalDate().toString();
                String nombreVacuna = vacuna.getNombre();
                DosisDadaDTO dosis = new DosisDadaDTO(codigo, enfermedades,
                        nombreVacuna, fechaEmitido, reserva.getParaDosis(), fechaini, fechafin);
                
                dosisDadas.add(dosis);
            }
        }


        return new CertificadoVacunacionDTO( String.valueOf(ci),  nombre, LocalDate.now().toString(), dosisDadas);
    }
}
