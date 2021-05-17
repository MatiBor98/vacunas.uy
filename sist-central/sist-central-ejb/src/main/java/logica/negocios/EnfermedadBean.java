package logica.negocios;

import datos.entidades.Enfermedad;


import datos.entidades.PlanVacunacion;
import datos.entidades.Vacuna;
import datos.repositorios.EnfermedadRepositoryLocal;

import logica.servicios.local.EnfermedadServiceLocal;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class EnfermedadBean implements EnfermedadServiceLocal {
    @EJB
    private EnfermedadRepositoryLocal enfermedadRepository;

    public EnfermedadBean() {
    }

    @Override
    public List<Enfermedad> find() {
        return enfermedadRepository.find();
    }

    @Override
    public List<Enfermedad> findByNombreEnfermedad(String nombre){
        return enfermedadRepository.findByNombreEnfermedad(nombre);
    }

    public void save(String nombre, String descripcion, List<Vacuna> vacunas, List<PlanVacunacion> planesVacunacion) {
    	enfermedadRepository.save(nombre, descripcion, vacunas, planesVacunacion);
    }

    public void eliminar(String nombre) {
    	enfermedadRepository.eliminar(nombre);
    }

    public List<Enfermedad> findPage(int primerResultado, int limiteResultados) {
        return enfermedadRepository.find(primerResultado, limiteResultados);
    }
}
