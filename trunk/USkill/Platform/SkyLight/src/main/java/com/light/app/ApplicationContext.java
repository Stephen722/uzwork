package com.light.app;

import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import com.light.app.impl.PropertyProvider;
import com.light.exception.ApplicationException;
import com.light.exception.ConfigurationException;
import com.light.utils.StringUtils;

/**
 * 该类主要是用在Action，servlet之外的类中获取Bean。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ApplicationContext {
	private static final Logger logger = LogManager.getLogger(ApplicationContext.class);
	private static ApplicationContext applicationContext;
	private WebApplicationContext webApplicationContext;
	private ApplicationContext(){

	}

	/**
	 * 取得ApplicationContext实例。
	 * 
	 * @return ApplicationContext
	 */
    public static ApplicationContext getInstance() {
    	if(applicationContext == null){
    		synchronized (ApplicationContext.class) {
    			if(applicationContext == null){
    				applicationContext = new ApplicationContext();
    				logger.info("Initialized an instance of ApplicationContext.");
    			}
    		}
    	}
        return applicationContext;
    }
    
    /**
     * 设置WebApplicationContext。
     * 
     * @param applicationContext
     */
    public void setWebApplicationContext(WebApplicationContext applicationContext){
		webApplicationContext = applicationContext;
	} 
	
	/**
	 * 取得一个WebApplicationContext。
	 * 
	 * @return WebApplicationContext
	 */
    public WebApplicationContext getWebApplicationContext(){
		return webApplicationContext;
	} 

	/**
	 * 判断指定的Bean是否存在。
	 * 
	 * @param beanName
	 * @return boolean
	 */
    public boolean hasBean(String beanName) {
        return webApplicationContext.containsBean(beanName);
    }
    
    /**
     * 取得一个Bean对象。
     * 
     * @param beanName
     * @return Object
     */
    public Object getBean(String beanName) {
    	return webApplicationContext.getBean(beanName);
    }
    

	/**
	 * 判断指定的属性是否存在。
	 * 
	 * @param key
	 * @return true 表示该属性存在
	 * @throws ConfigurationException
	 */
    public boolean hasProperty(String key) throws ConfigurationException {
        boolean hasProperty = false;
        try {
            String value = getProperty(key);
            hasProperty = !StringUtils.isBlank(value);
        } catch (Exception e) {
            hasProperty = false;
            logger.warn("System doesn't have the property named:'"+key+"'");
        }

        return hasProperty;
    }

    /**
     * 取得指定属性值，如果该属性不存在则返回给定默认值。
     * 
     * @param key
     * @param defaultValue
     * @return String
     */
    public String getProperty(String key, String defaultValue) {
        String value = null;
        String errorMessage = "System fails to locate the property named '" + key + "' from the configured properties.";
        try {
        	value = StringUtils.trimTail(PropertyProvider.getInstance().getProperty(key));
        } catch (Exception e) {
        	logger.error(errorMessage);
        	ConfigurationException ex = new ConfigurationException(errorMessage);
            throw ex;
        }

        value = StringUtils.isBlank(value) ? defaultValue : value;
        if (value == null) {
        	logger.error(errorMessage);
            ConfigurationException ex = new ConfigurationException(errorMessage);
            throw ex;
        }

        return value;
    }
    
    /**
     * 取得指定属性的值。
     *
     * @param key 属性Key
     * @return String
     */
    public String getProperty(String key) {
        return getProperty(key, null);
    }

    /**
     * 取得所有属性。
     *
     * @throws ConfigurationException
     */
    public Properties getProperties() throws ConfigurationException {
        return PropertyProvider.getInstance().getProperties();
    }

    /**
     * 取得所有属性名称。
     * 
     * @return 所有属性名称
     */
    public Iterator<?> getPropertyNames() {
    	return PropertyProvider.getInstance().getPropertyNames();
    }
    
    /**
     * 根据Message Key 取得 message
     * 
     * @param key
     * @return message
     */
    public String getMessage(String key) {
    	return getMessage(key, null);
    }
    
    /**
     * 根据Message Key和相关参数取得 message
     * 
     * @param key
     * @param args
     * @return message
     */
    public String getMessage(String key, String[] args) {
    	try{
    		return getWebApplicationContext().getMessage(key, args, Locale.getDefault());
	    }
	    catch(Exception e){
	    	String errorMessage = "The message key: " + key + " doesn't exist.";
	    	logger.error(errorMessage + "\n Exception:" + e.toString());
	    	throw new ApplicationException("applicationException.noMessage", errorMessage, e);
	    }
    }
}
