package datos.repositorios;

import datos.entidades.Mensaje;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MensajeRepositoryLocal {

    public void addMensaje(Mensaje msg);
    public List<Mensaje> findMensaje();
}
