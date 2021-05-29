package autentiacionBackOffice;

import java.io.IOException;
import java.util.Base64;

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

/**
 * Servlet Filter implementation class autenticacionBackOfficeFilter
 */
@WebFilter(	urlPatterns = "/backoffice/*",
			filterName = "autenticacionBackOfficeFilter",
			description = "Verifica el jwt para acceder a las funcionalidades backoffice")
public class autenticacionBackOfficeFilter implements Filter {

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
		Cookie[] cookie = req.getCookies();
		int iter = 0;
		while(iter < cookie.length && !cookie[iter].equals("JWT")) {
			iter++;
		}
		if(iter < cookie.length) {
			//lo mandas a login
		}
		else {
			//aca encontramos la cookie de la token y resta verificarla
			String token = getAtributeFromJWTString(cookie[iter].getValue(), "id_token");
			String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(chunks[1]));
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
   String getAtributeFromJWTString(String payload, String param){
        String[] user = payload.split(param+"\":\"");
        user = user[1].split("\"");
        return user[0];
    }

}
