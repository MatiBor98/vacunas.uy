package datos.repositorios;

import java.util.List;

import javax.ejb.Local;

import datos.entidades.Mensaje;

@Local
public interface MensajeRepositoryLocal {

    public void addMensaje(Mensaje msg);
    public List<Mensaje> findMensaje();
}
