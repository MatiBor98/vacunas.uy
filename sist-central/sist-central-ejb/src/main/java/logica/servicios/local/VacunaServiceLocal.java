package logica.servicios.local;

import datos.entidades.Enfermedad;
import datos.entidades.Etapa;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

import javax.ejb.Local;
import java.util.List;

@Local
public interface VacunaServiceLocal {
    List<Vacuna> find();
    void save(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs);
    List<Vacuna> findByNombreVacuna(String nombre);
    void eliminar(String nombre);
    void modificarVacuna(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs );
	Vacuna find(String nomVacuna);
	List<Vacuna> findByEnfermedad(String enfermedad);
	List<Etapa> getEtapas(String nombreVacuna);
}
