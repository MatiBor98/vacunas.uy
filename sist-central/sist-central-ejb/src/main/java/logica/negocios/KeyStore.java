package logica.negocios;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import logica.servicios.local.KeyStoreLocal;

/**
 * Session Bean implementation class KeyStore
 */
@Singleton
@LocalBean
@Startup
public class KeyStore implements KeyStoreLocal {

    /**
     * Default constructor. 
     */
	private SecretKey key;
	
    /**
     * Default constructor. 
     */
    public KeyStore() {
        
    }
    
    @PostConstruct
    public void init() {
    	
    	key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    	
    }

	public SecretKey getKey() {
		return key;
	}

}
