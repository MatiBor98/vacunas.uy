package logica.servicios.filter;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.SecretKey;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.json.JsonObject;

import datos.exceptions.PermissionException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

public class BackOfficeInterceptor {
	
	String password = "secretKeyforJwt.secretKeyForJwt.secretKeyforJwt";
	SecretKey key = Keys.hmacShaKeyFor(password.getBytes(StandardCharsets.UTF_8));
	
	public BackOfficeInterceptor() {
		
	}
	
	@AroundInvoke
	public Object autenticarToken(InvocationContext ctx) throws PermissionException {
		Object[] params = ctx.getParameters();
		if(params[0] instanceof String) {
			String jwtoken = (String) params[0];
			try {
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtoken).getBody();
			}
			catch(JwtException e) {
				throw new PermissionException();
			}
			
		}
		throw new PermissionException();
	}
	
	
}


	