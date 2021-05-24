package com.baeldung.oauth2.client;




import java.io.IOException;

        import javax.json.JsonObject;
        import javax.servlet.RequestDispatcher;
        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;

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


        if(request.getSession().getAttribute("tokenResponse") != null){
            String end_session_endpoint = "https://auth-testing.iduruguay.gub.uy/oidc/v1/logout";
            String redirect_uri = "http://localhost:8080/logout";
            String token = request.getSession().getAttribute("id_token").toString();
            String logoutURL = "https://auth-testing.iduruguay.gub.uy/oidc/v1/logout?id_token_hint="
                    + token + "&post_logout_redirect_uri=" + redirect_uri;

            request.getSession().removeAttribute("tokenResponse");
            request.getSession().removeAttribute("user");
            request.getSession().removeAttribute("id_token");


            response.sendRedirect(logoutURL);
        }

        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/index.xhtml");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
