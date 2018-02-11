package com.uzskill.base.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.app.ApplicationContext;

public class SynchonizeScheduleService{
	private static final Logger logger = LogManager.getLogger(SynchonizeScheduleService.class);
	
	private static final int POOL_SIZE = 15;
	private static SynchonizeScheduleService instance;
	private static ScheduledExecutorService scheduledServive;
	private static final String BEAN_NAME = "SynchonizeScheduleService";
	
	private SynchonizeScheduleService() {
		
	}
	
	/**
	 * Return a SynchonizeScheduleService instance
	 * 
	 * @return SynchonizeScheduleService
	 */
	public static final SynchonizeScheduleService getInstance() {
		if (instance == null) {
			synchronized(SynchonizeScheduleService.class) {
				if (instance == null) {
					if (ApplicationContext.getInstance().hasBean(BEAN_NAME)) {
						instance = (SynchonizeScheduleService) ApplicationContext.getInstance().getBean(BEAN_NAME);
					}
					else {
						instance = new SynchonizeScheduleService();
					}
					scheduledServive = Executors.newScheduledThreadPool(POOL_SIZE);
					logger.info("Initialized an instance of UserSessionManager.");
				}
			}
		}
		return instance;
	}
	
	public void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		scheduledServive.scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	/**
	 * "Shut down" rather than "shut down now" to stop the schedule service
	 */
	public void shutdown() {
		if(scheduledServive != null && !scheduledServive.isShutdown()) {
			scheduledServive.shutdown();
		}
	}

}
