package logica.servicios.local;

import javax.crypto.SecretKey;
import javax.ejb.Local;

@Local
public interface KeyStoreLocal {

	public SecretKey getKey();
	
}
