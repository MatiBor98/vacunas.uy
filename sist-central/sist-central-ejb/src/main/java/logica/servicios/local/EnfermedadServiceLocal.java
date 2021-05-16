package logica.servicios.local;

import datos.entidades.Enfermedad;
import datos.entidades.PlanVacunacion;
import datos.entidades.Vacuna;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EnfermedadServiceLocal {
    List<Enfermedad> find();
    void save(String nombre, String descripcion, List<Vacuna> vacunas, List<PlanVacunacion> planesVacunacion);
    List<Enfermedad> findByNombreEnfermedad(String nombre);
    void eliminar(String nombre);
}
