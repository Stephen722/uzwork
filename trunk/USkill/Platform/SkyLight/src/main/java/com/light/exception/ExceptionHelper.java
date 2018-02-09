package com.light.exception;

import com.light.app.ApplicationContext;

/**
 * 该类用来处理系统ApplicationException。
 * 如果提供的Exception是ApplicationException，handleException只直接返回之，否则转换为ApplicationException返回。
 * <p/>
 * 下面是该类的使用方法。
 * <code>
 * <br> try {
 * <br>&nbsp;&nbsp; ...
 * <br> } catch (Exception e) {
 * <br>&nbsp;&nbsp; ApplicationException ae = ExceptionHelper.getInstance().handleException("Failed to ...", e);
 * <br>&nbsp;&nbsp; logger.error(getClass().getName(), "methodName", ae);
 * <br>&nbsp;&nbsp; throw ae;
 * <br> }
 * </code>
 * <p/>
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ExceptionHelper {

    public static final String BEAN_NAME = "ExceptionHelper";

    private static ExceptionHelper c_instance;
    
    public synchronized static final ExceptionHelper getInstance() {
        if (c_instance == null) {
            if (ApplicationContext.getInstance().hasBean(BEAN_NAME)) {
                c_instance = (ExceptionHelper) ApplicationContext.getInstance().getBean(BEAN_NAME);
            } else {
                c_instance = new ExceptionHelper();
            }
        }
        return c_instance;
    }

    /**
     * 无论指定的Exception是什么类型的，该方法都将返回ApplicationException，以ApplicationException.UNEXPECTED_ERROR为默认消息Key。
     * 如果给定的Exception属于ApplicationException，message key将不会被替换。
     *
     * @param describeMessage
     * @param e the exception
     * @return AppException 
     */
    public ApplicationException handleException(String describeMessage, Exception e) {
    	String messageKey = ApplicationException.UNEXPECTED_ERROR;
    	if (e instanceof ApplicationException) {
            messageKey = ((ApplicationException) e).getMessageKey();
        }
        return handleException(messageKey, describeMessage, e);
    }

    /**
     * 无论指定的Exception是什么类型的，该方法都将返回ApplicationException，以ApplicationException.UNEXPECTED_ERROR为默认消息Key。
     * 如果给定的Exception属于ApplicationException，将用该Exception的messageKey替换传入的messagekey。
     *
     * @param messageKey
     * @param describeMessage
     * @param e the exception
     * @return AppException
     */
    public ApplicationException handleException(String messageKey, String describeMessage, Exception e) {
    	ApplicationException ae = null;
        if (e instanceof ApplicationException) {
            ae = (ApplicationException) e;
            ae.pushDescribeMessage(describeMessage);
        } 
        else {
            ae = new ApplicationException(messageKey, describeMessage, e);
        }
        return ae;
    }
}
