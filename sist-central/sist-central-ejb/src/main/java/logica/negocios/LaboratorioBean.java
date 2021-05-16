package logica.negocios;

import datos.entidades.Laboratorio;
import datos.entidades.Vacuna;
import datos.repositorios.LaboratorioRepositoryLocal;
import logica.servicios.local.LaboratorioServiceLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class LaboratorioBean implements LaboratorioServiceLocal {
    @EJB
    private LaboratorioRepositoryLocal laboratorioRepository;

    public LaboratorioBean() {
    }

    @Override
    public List<Laboratorio> find() {
        return laboratorioRepository.find();
    }

    @Override
    public List<Laboratorio> findByNombreLaboratorio(String nombre){
        return laboratorioRepository.findByNombreLaboratorio(nombre);
    }

    public void save(String nombre, List<Vacuna> vacs) {
    	laboratorioRepository.save(nombre, vacs);
    }
    
    public void eliminar(String nombre) {
    	laboratorioRepository.eliminar(nombre);
    }
}