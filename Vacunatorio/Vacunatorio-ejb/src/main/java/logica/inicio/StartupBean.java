package logica.inicio;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import logica.schedule.SchedulerMain;

@Singleton
@Lock(LockType.WRITE)
@Startup
@LocalBean
public class StartupBean {
	
	@PostConstruct
	void init() throws InterruptedException{
		/*SchedulerMain schedulere = new SchedulerMain();
		TimeUnit.SECONDS.sleep(10);
		schedulere.mainCaller();*/
	}
	
}