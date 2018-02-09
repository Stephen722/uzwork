package com.light.exception;

import com.light.utils.StringUtils;

/**
 * 程序异常类。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public static final String UNEXPECTED_ERROR = "applicationException.unexpected.error";
    public static final String DESCRIBE_MESSAGE_SEPARATOR = " :: ";

    private String m_messageKey;
    private StringBuffer m_describeMessage = new StringBuffer();
    
    /**
     * 用给定的描述消息来构造一个程序异常类，使用默认UNEXPECTED_ERROR为Message Key。
     *
     * @param describeMessage
     */
    public ApplicationException(String describeMessage) {
        this(UNEXPECTED_ERROR, describeMessage);
    }

    /**
     * 用给定的Message Key和相关的描述信息来构造一个程序异常类。
     *
     * @param messageKey
     * @param describeMessage
     */
    public ApplicationException(String messageKey, String describeMessage) {
        super("");
        m_messageKey = messageKey;
        pushDescribeMessage(describeMessage);
    }

    /**
     * 用给定的描述消息来构造一个程序异常类，使用默认UNEXPECTED_ERROR为Message Key。
     *
     * @param describeMessage
     * @param cause
     */
    public ApplicationException(String describeMessage, Throwable cause) {
        this(UNEXPECTED_ERROR, describeMessage, cause);
    }

    /**
     * 用给定的Message Key和相关描述来构造一个程序异常类。
     *
     * @param messageKey
     * @param describeMessage a describe message
     * @param cause
     */
    public ApplicationException(String messageKey, String describeMessage, Throwable cause) {
        super("", cause);
        m_messageKey = messageKey;
        pushDescribeMessage(describeMessage);
    }

    /**
     * 返回该异常的详细信息（Message Key代表的Message加上Describe Message）。
     * 
     * @return String
     */
    public String getMessage() {
        return getDescribeMessage();
    }

    /**
     * 返回该异常的Message Key。
     * 
     * @return String
     */
    public String getMessageKey() {
        return m_messageKey;
    }

    /**
     * 设置Message Key。
     * 
     * @param messageKey
     */
    public void setMessageKey(String messageKey) {
        m_messageKey = messageKey;
    }

    /**
     * Push a describe message to the front of the Describe Message Stack for this ApplicationException
     */
    
    /**
     * 将给定描述置于当前ApplicationException中。
     * 
     * @param describeMessage
     */
    public void pushDescribeMessage(String describeMessage) {
        if (!StringUtils.isBlank(describeMessage))
            m_describeMessage.insert(0, DESCRIBE_MESSAGE_SEPARATOR + describeMessage);
    }

    /**
     * 返回该异常的详细信息（Message Key代表的Message加上Describe Message）。
     * 
     * @return String
     */
    public String getDescribeMessage() {
        return m_messageKey + m_describeMessage.toString();
    }
}