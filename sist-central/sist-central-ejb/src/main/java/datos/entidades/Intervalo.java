package datos.entidades;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import datos.entidades.Laboratorio;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Intervalo implements Serializable{
	
	@Id @GeneratedValue
	private Long id;
	private LocalDate fecha;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private int capacidad;
}