package datos.repositorios;

import datos.entidades.Agenda;
import datos.entidades.Departamento;
import datos.entidades.Etapa;
import datos.entidades.Trabajos;

import java.util.List;
import java.util.Optional;

import javax.ejb.Remote;

@Remote
public interface AgendaRepositoryRemote {
    List<Agenda> find();
    Optional<Agenda> find(int id);
    List<Agenda> findAgendasParaCiudadanoPorDepartamento(String nombreEnfermedad, int edadCiudadano, Trabajos trabajos,
                                                         Departamento departamento);
    void save(Agenda agenda);
    List<Agenda> findByNombrePlan(String criterio);
	Agenda find(String nombre, Etapa etapa, String nombre2);
	void drop();
}
