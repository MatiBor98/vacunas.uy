package prueba;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import datos.entidades.Departamento;
import datos.entidades.PuestoVacunacion;
import datos.entidades.Vacunatorio;
import logica.servicios.local.PuestoVacunacionBeanLocal;
import logica.servicios.local.VacunatorioControllerLocal;


/**
 * Servlet implementation class prueba
 */
@WebServlet("/prueba")
public class prueba extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	VacunatorioControllerLocal vacunatorioControllerLocal;
	
	@EJB
	PuestoVacunacionBeanLocal puestoVacunacionBeanLocal;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public prueba() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Transactional
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*
		String nombreVacPrueba = "VacunatorioPrueba";
        vacunatorioControllerLocal.addVacunatorio(nombreVacPrueba, "Mdeo", "Calle Facultad 3027", Departamento.Artigas);
        @SuppressWarnings("unused")
		Vacunatorio vac = vacunatorioControllerLocal.find(nombreVacPrueba).get();
        
        long idPuesto = puestoVacunacionBeanLocal.addPuestoVacunacion("Puesto 1", nombreVacPrueba);
        
        vacunatorioControllerLocal.addPuestoAlVacunatorio(nombreVacPrueba, idPuesto);
        
		Vacunatorio vac2 = vacunatorioControllerLocal.find(nombreVacPrueba).get();
		@SuppressWarnings("unused")
		PuestoVacunacion puesto = vac2.getPuestosVacunacion().get(0);
		response.getWriter().append(puesto.getNombrePuesto()).append(request.getContextPath());
		*/
    	
    	String nombreVacPrueba = "VacunatorioPrueba";
        vacunatorioControllerLocal.addVacunatorio(nombreVacPrueba, "Mdeo", "Calle Facultad 3027", Departamento.Artigas);
        Vacunatorio vac = vacunatorioControllerLocal.find(nombreVacPrueba).get();
		response.getWriter().append(vac.getDepartamento().toString()).append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
