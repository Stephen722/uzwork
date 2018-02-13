package com.uzskill.base.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.app.ApplicationContext;

/**
 * ApplicationContext cannot be used to get the instance because this class will be created before ApplicationContext
 * 
 * @author Stephen
 *
 */
public class SynchronizeScheduleService{
	private static final Logger logger = LogManager.getLogger(SynchronizeScheduleService.class);
	
	private static final int POOL_SIZE = 14;
	private static SynchronizeScheduleService instance;
	private static ScheduledExecutorService scheduledServive;
	private static final String BEAN_NAME = "SynchonizeScheduleService";
	
	private SynchronizeScheduleService() {
		
	}
	
	/**
	 * Return a SynchronizeScheduleService instance
	 * 
	 * @return SynchronizeScheduleService
	 */
	public static final SynchronizeScheduleService getInstance() {
		if (instance == null) {
			synchronized(SynchronizeScheduleService.class) {
				if (instance == null) {
					if (ApplicationContext.getInstance().hasBean(BEAN_NAME)) {
						instance = (SynchronizeScheduleService) ApplicationContext.getInstance().getBean(BEAN_NAME);
					}
					else {
						instance = new SynchronizeScheduleService();
					}
					scheduledServive = Executors.newScheduledThreadPool(POOL_SIZE);
					logger.info("Initialized an instance of SynchronizeScheduleService.");
				}
			}
		}
		return instance;
	}
	
	public void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		logger.info("Starting monitor command at fixed rate");
		scheduledServive.scheduleWithFixedDelay(command, initialDelay, period, unit);
	}
	
	/**
	 * "Shut down" rather than "shut down now" to stop the schedule service
	 */
	public void shutdown() {
		if(scheduledServive != null && !scheduledServive.isShutdown()) {
			scheduledServive.shutdown();
		}
		logger.info("Synchronize schedule service shutdown");
	}

}
