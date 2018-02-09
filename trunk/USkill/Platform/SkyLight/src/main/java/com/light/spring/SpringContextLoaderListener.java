package com.light.spring;

import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.light.exception.ApplicationException;

/**
 * 该类继承自ContextLoaderListener，当一个Web应用上下文初始化成功后，系统将取得该上下文并将其设置到ApplicationContext，
 * 方便以后在Action，servlet之外的类中可以使用该上下文获取Bean。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class SpringContextLoaderListener extends ContextLoaderListener {
	
	private static final Logger logger = LogManager.getLogger(SpringContextLoaderListener.class);
	/**
	 * 初始化web应用上下文。
	 */
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		// web应用上下文被成功初始化后，将其设置到ApplicationContext。
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		if(springContext == null){
			logger.info("Failed to initialize Spring Web Application Context.");
			throw new ApplicationException("Failed to initialize Spring Web Application Context.");
		}
		logger.info("Set the initialized Spring WebApplicationContext to Light ApplicationContext.");
		com.light.app.ApplicationContext.getInstance().setWebApplicationContext(springContext);
	}

	/**
	 * 注销web应用上下文。
	 */
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		com.light.app.ApplicationContext.getInstance().setWebApplicationContext(null);
		logger.info("Close Light Web Application Context.");		
	}
}
