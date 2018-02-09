package com.light.exception;

import com.light.record.Record;

/**
 * 验证异常。
 *
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ValidationException extends ApplicationException {
 
	private static final long serialVersionUID = 1L;
	// 默认验证异常的信息。
    public static final String VALIDATION_ERROR = "applicationException.validation.error";

    /**
     * 用空的信息来构造一个验证异常类。VALIDATION_ERROR作为一个消息Key。
     */
    public ValidationException() {
        super(VALIDATION_ERROR, "");
    }
    
    /**
     * 用空的信息来构造一个验证异常类。VALIDATION_ERROR作为一个消息Key。
     * 用一个 message = "messageKey : debugMessage"的超类RuntimeException。
     *
     * @param describeMessage
     */
    public ValidationException(String describeMessage) {
    	super(VALIDATION_ERROR, describeMessage);
    }

    /**
     * 返回验证异常对象的Record。
     *
     *  @return Record
     */
    public Record getValidFields() {
        return m_validFields;
    }

    /**
     * 设置Record到一个validationException对象。
     *
     * @param validFields
     */
    public void setValidFields(Record validFields) {
        m_validFields.setRecord(validFields);
    }

    /**
     * 设置Key/Value到一个validationException对象。
     *
     * @param fieldName
     * @param value 
     */
    public void setValidFieldValue(String fieldName, Object value) {
        m_validFields.setValue(fieldName, value);
    }

    /**
     * 一个验证异常类可以包含一个Record(fieldName-fieldValue)，可以从中取得异常原因。
     * 如：在manager里设置一个值后在Action可以取得该值。
     */
    private Record m_validFields = new Record();
}
