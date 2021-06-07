package logica.negocios;

import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import logica.servicios.local.KeyStoreLocal;

/**
 * Session Bean implementation class KeyStore
 */
@Singleton
@Startup
public class KeyStore implements KeyStoreLocal {

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
