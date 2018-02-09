package com.light.exception;


/**
 * 该异常类主要用在处理数据库操作时候遇到的异常。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class MybatisException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;
	// 该异常的错误信息。
    public static final String MYBATIS_ERROR = "applicationException.mybatis.error";

    public MybatisException(String debugMessage) {
        super(MYBATIS_ERROR, debugMessage);
    }

    public MybatisException(String debugMessage, Throwable cause) {
        super(MYBATIS_ERROR, debugMessage, cause);
    }
}
