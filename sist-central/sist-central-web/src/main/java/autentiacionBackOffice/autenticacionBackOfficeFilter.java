package autentiacionBackOffice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.undertow.util.Cookies;

/**
 * Servlet Filter implementation class autenticacionBackOfficeFilter
 */
@WebFilter(	urlPatterns = "/backoffice/*",
			filterName = "autenticacionBackOfficeFilter",
			description = "Verifica el jwt para acceder a las funcionalidades backoffice")
public class autenticacionBackOfficeFilter implements Filter {

	String password;
	SecretKey key;

	
    /**
     * Default constructor. 
     */
    public autenticacionBackOfficeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Cookie[] cookie = req.getCookies();
		
		int iter = 0;
		while(iter < cookie.length && !cookie[iter].getName().equals("JWTBO")) {
			iter++;
		}
		//si no tiene la cookie es q no inicio sesion en backoffice y lo mandamos a login
		if(iter >= cookie.length) {
            httpResponse.sendRedirect(req.getContextPath() + "/Login.xhtml");
            return;
		}
		String accessToken = cookie[iter].getValue();
		try {
			//aca encontramos la cookie de la token y resta verificarlalink a viewscoped bean with pages
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
			String rol = claims.get("rol", String.class);
			
		    //ahora vemos a q pagina esta intentando acceder para comprobar que tiene permiso
		    String requri = ((HttpServletRequest) request).getRequestURI();
		    String[] direcciones = requri.split("/");
		    if(direcciones.length > 3 && direcciones[1].equals("backoffice")) {
		    	//si accede a uno q no corresponde a su rol lo mandamos a la pagina de acceso denegado
		    	if(!rol.equals(direcciones[2])) {
		    		if(rol.equals("administrador")) {
		    			httpResponse.sendRedirect(req.getContextPath() + "/backoffice/administrador/Administrador.xhtml?accessDenied=true");
		 	            return;
		    		}
		    		if(rol.equals("autoridad")) {
		    			httpResponse.sendRedirect(req.getContextPath() + "/backoffice/autoridad/Autoridad.xhtml?accessDenied=true");
		 	            return;
		    		}
		           
		    	}
		    	// si accede correcto continuamos la ejecucion normalmente
			    chain.doFilter(request, response);
		    }
		    
		    
		}
		catch(ExpiredJwtException e) {
			iter = 0;
			while(iter < cookie.length && !cookie[iter].getName().equals("JWTBORefresh")) {
				iter++;
			}
			if(iter >= cookie.length) {
				httpResponse.sendRedirect(req.getContextPath() + "/Login.xhtml");
		        return;
			}
			try {
				refreshAccesToken(cookie[iter].getValue(), httpResponse);
				chain.doFilter(request, response);
			}
			catch(JwtException f) {
				httpResponse.sendRedirect(req.getContextPath() + "/Login.xhtml?sessionExpired=true");
		        return;
			}
			
		}
		catch(JwtException e) {
	    	//token invalida
			//se borra la cookie y redirigimos al login
			httpResponse.sendRedirect(req.getContextPath() + "/Login.xhtml");
	        return;
	    }
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		password = "secretKeyforJwt.secretKeyForJwt.secretKeyforJwt";
		key = Keys.hmacShaKeyFor(password.getBytes(StandardCharsets.UTF_8));
	}

	
	protected void refreshAccesToken(String refreshToken, HttpServletResponse httpResponse ) throws JwtException{
		Claims refreshClaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).getBody();
    	long timestamp = System.currentTimeMillis();
		String newAccessToken = Jwts.builder()
    			.setIssuedAt(new Date(timestamp))
    			.setExpiration(new Date(timestamp+1000 * 60 * 10))
    			.claim("email", refreshClaims.get("email", String.class))
    			.claim("rol", refreshClaims.get("rol", String.class))
    			.signWith(key).compact();
		Cookie accessCookie = new Cookie("JWTBO", newAccessToken);
		accessCookie.setMaxAge(60 * 60); //expire could be 60 (seconds)
		accessCookie.setHttpOnly(true);
		accessCookie.setPath("/");
        httpResponse.addCookie(accessCookie);
	}
}
