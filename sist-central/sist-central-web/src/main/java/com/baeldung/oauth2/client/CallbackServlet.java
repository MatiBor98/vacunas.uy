package com.baeldung.oauth2.client;

import org.eclipse.microprofile.config.Config;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Base64;

@WebServlet(urlPatterns = "/")
public class CallbackServlet extends AbstractServlet {

    @Inject
    private Config config;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	if (request.getParameter("code") == null) {
            response.sendRedirect("/Home");
            return;
    	}
    	
        String clientId = config.getValue("client.clientId", String.class);
        String clientSecret = config.getValue("client.clientSecret", String.class);

        //Error:
        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", error);
            dispatch("/", request, response);
            return;
        }
        String localState = (String) request.getSession().getAttribute("CLIENT_LOCAL_STATE");
        if (!localState.equals(request.getParameter("state"))) {
            request.setAttribute("error", "The state attribute doesn't match !!");
            dispatch("/", request, response);
            return;
        }

        String code = request.getParameter("code");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(config.getValue("provider.tokenUri", String.class));

        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("code", code);
        form.param("redirect_uri", config.getValue("client.redirectUri", String.class));

        try {
            JsonObject tokenResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue(clientId, clientSecret))
                    .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), JsonObject.class);
            /////////
            String a =tokenResponse.getString("id_token");
            String[] chunks = a.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            String[] user = payload.split("nombre_completo\":\"");
            user = user[1].split("\"");
            request.getSession().setAttribute("user", user[0]);

            request.getSession().setAttribute("tokenResponse", tokenResponse);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            request.setAttribute("error", ex.getMessage());
        }
        dispatch("/index.xhtml", request, response);
    }
}
