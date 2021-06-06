package logica.schedule;

import java.util.Timer;

public class SchedulerMain {

	public static void main(String[] args) {

        Timer time = new Timer(); 
        DatosVacunatorioSchedule datosVac = new DatosVacunatorioSchedule(); 
        time.schedule(datosVac, 0, 10000); // 10 segundos

	}

}
