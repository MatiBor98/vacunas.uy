package logica.servicios.local;

import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;

import javax.ejb.Local;
import java.util.List;

@Local
public interface LaboratorioServiceLocal {
    List<Laboratorio> find();
    void save(String nombre, List<Vacuna> vacs);
    List<Laboratorio> findByNombreLaboratorio(String nombre);
    void eliminar(String nombre);
}
