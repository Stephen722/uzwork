package com.light.record;
/**
 * JSON记录对象
 *
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-06-29
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class JSONRecord {
	
	private int code;
	private String message;
	private Object value;
	
	public JSONRecord(int code, String message, Object value) {
		this.code = code;
		this.message = message;
		this.value = value;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
