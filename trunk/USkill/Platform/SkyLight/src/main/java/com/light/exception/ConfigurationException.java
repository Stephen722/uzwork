package com.light.exception;


/**
 * 该异常类主要用在处理配置时候遇到的异常，包括验证任何组件的配置情况。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ConfigurationException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;
	// 该异常的错误信息。
    public static final String CONFIGURATION_ERROR = "applicationException.configuration.error";

    public ConfigurationException(String debugMessage) {
        super(CONFIGURATION_ERROR, debugMessage);
    }

    public ConfigurationException(String debugMessage, Throwable cause) {
        super(CONFIGURATION_ERROR, debugMessage, cause);
    }
}
