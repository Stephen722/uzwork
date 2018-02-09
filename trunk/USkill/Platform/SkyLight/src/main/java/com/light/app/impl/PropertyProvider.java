package com.light.app.impl;

import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.collections.iterators.EnumerationIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.light.app.ApplicationContext;

/**
 * This class exposes a getProperty method to retrieve the property's value from the configured property file(s).
 * This class extends the org.springframework.beans.factory.config.PropertyProvider so that
 * all properties who's value is another property placeholder will be resolved.
 * <p/>
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-31
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class PropertyProvider extends PropertyPlaceholderConfigurer {
	private static final Logger logger = LogManager.getLogger(ApplicationContext.class);
	public static final String BEAN_NAME = "PropertyProvider";
	
	private static PropertyProvider instance;
	
	private PropertyProvider(){
		
	}
    
    public static final PropertyProvider getInstance() {
        if (instance == null) {
        	synchronized (PropertyProvider.class) {
        		 if (instance == null) {
        			 if (ApplicationContext.getInstance().hasBean(BEAN_NAME)) {
        	                instance = (PropertyProvider) ApplicationContext.getInstance().getBean(BEAN_NAME);
        			 }
        			 else {
    	                instance = new PropertyProvider();
        			 }
    	            logger.info("Initialized an instance of PropertyProvider.");
        		 }
        	}
        }
        return instance;
    }
    
    public String getProperty(String key) {
        return resolvePlaceholder(key, getProperties());
    }

    public Iterator<?> getPropertyNames() {
        return new EnumerationIterator(getProperties().propertyNames());
    }

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        properties = props;
    }

    public Properties getProperties(){
        return properties;    
    }

    private Properties properties;
}
