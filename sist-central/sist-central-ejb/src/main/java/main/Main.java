package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import datos.entidades.Agenda;
import datos.repositorios.AgendaRepositoryLocal;

public class Main {
	
	/*@EJB
	static
	controllers.controllerVacunaRemote contVac;*/
	
	public static void main(String[] args) throws NamingException, IOException {
		
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		props.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
		
		Context ctx = new InitialContext(props);
		String jndiName = "ejb:sist-central/sist-central-ejb/AgendaRepository!datos.repositorios.AgendaRepositoryLocal";
		AgendaRepositoryLocal agendaRep = (AgendaRepositoryLocal)ctx.lookup(jndiName);
		
		List<Agenda> agendas = agendaRep.find();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Scanner in = new Scanner(System.in);
		int eleccion = 0;
		
		while (eleccion != 5) {
			System.out.println("Bienvenido, seleccione una opcion");
			System.out.println("1. Gestionar...");
			System.out.println("2. Gestionar...");
			System.out.println("3. Gestionar...");
			System.out.println("4. Gestionar...");
			System.out.println("5. Salir");
			System.out.println("");
			eleccion = in.nextInt();
			System.out.println("");
			
			
			switch (eleccion) {
			}
		}
		
	}
	

}
