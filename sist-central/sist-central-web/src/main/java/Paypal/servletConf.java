package Paypal;


import datos.entidades.Departamento;
import logica.negocios.ReservaDomicilioBean;
import logica.servicios.local.ReservaDomicilioBeanServiceLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/Paypal")
public class servletConf extends HttpServlet {

    @EJB
    ReservaDomicilioBeanServiceLocal reservaDomicilioBeanServiceLocal;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        HttpSession session = request.getSession();

        String direccion = (String) session.getAttribute("direccion");
        String localidad = (String) session.getAttribute("localidad");
        Departamento departamento = (Departamento) session.getAttribute("departamento");
        LocalDate fecha = (LocalDate) session.getAttribute("fecha");
        String enfermedad = (String) session.getAttribute("enfermedad");
        int ci = (int) session.getAttribute("ci");

        reservaDomicilioBeanServiceLocal.efectuarReservaDomicilio(ci,departamento,direccion,localidad,enfermedad,fecha);

        response.sendRedirect("/Home");
    }

}
