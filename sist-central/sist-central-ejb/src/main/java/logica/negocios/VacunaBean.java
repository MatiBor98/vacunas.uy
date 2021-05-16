package logica.negocios;

import datos.entidades.Enfermedad;
import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;
import datos.repositorios.VacunaRepositoryLocal;
import logica.servicios.local.VacunaServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class VacunaBean implements VacunaServiceLocal {
    @EJB
    private VacunaRepositoryLocal vacunaRepository;

    public VacunaBean() {
    }

    @Override
    public List<Vacuna> find() {
        return vacunaRepository.find();
    }

    @Override
    public List<Vacuna> findByNombreVacuna(String nombre){
        return vacunaRepository.findByNombreVacuna(nombre);
    }

    public void save(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs) {
        vacunaRepository.save(nombre, cantDosis, inmunidadMeses, dosisSeparacion, labs, enfs);
    }
    
    public void eliminar(String nombre) {
    	vacunaRepository.eliminar(nombre);
    }
    
    public void modificarVacuna(String nombre, int cantDosis, int inmunidadMeses, int dosisSeparacion, List<Laboratorio>labs, List<Enfermedad>enfs) {
    	vacunaRepository.modificarVacuna(nombre, cantDosis, inmunidadMeses, dosisSeparacion, labs, enfs);
    }
}