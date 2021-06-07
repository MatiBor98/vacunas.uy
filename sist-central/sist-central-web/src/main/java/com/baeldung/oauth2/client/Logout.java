package com.baeldung.oauth2.client;




import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Optional<String> cookie =  readCookie("JWT", request);
        if(cookie != null){
            String end_session_endpoint = "https://auth-testing.iduruguay.gub.uy/oidc/v1/logout";
            String redirect_uri = "http://localhost:8080/logout";
            String token = getAtributeFromCookie(cookie,"id_token");
            String logoutURL = "https://auth-testing.iduruguay.gub.uy/oidc/v1/logout?id_token_hint="
                    + token + "&post_logout_redirect_uri=" + redirect_uri;

            //Hay que hacerlo asi porque servlet no probe una manera directa de eliminar cookies
            Cookie newCookie = new Cookie("JWT", "");
            newCookie.setMaxAge(0);
            response.addCookie(newCookie);

            response.sendRedirect(logoutURL);
        }

        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/frontofficeIndex.xhtml");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    public Optional<String> readCookie(String key,HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    String getAtributeFromCookie(Optional<String>  cookie, String param){
        String[] user = cookie.get().split(param+"\":\"");
        user = user[1].split("\"");
        return user[0];
    }

}
