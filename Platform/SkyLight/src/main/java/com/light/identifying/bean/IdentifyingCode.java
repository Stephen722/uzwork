package com.light.identifying.bean;

/**
 * IdentifyingCode 验证码实现类
 * 如果cellPhone不为空则说明是短信验证码，否则为图片验证码
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-31
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class IdentifyingCode {
	
	private String code;
	private String cellPhone;
	private int length;
	private long generatedTime;
	private int times;
	private long duration = 10 * 60 * 1000;     // 验证码有效时间为十分钟
	
	/**
	 * 取得验证码
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置验证码
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 取得验证码生成的时间
	 * 
	 * @return
	 */
	public long getGeneratedTime() {
		return generatedTime;
	}
	/**
	 * 设置验证码生成的时间
	 * 
	 * @param generatedTime
	 */
	public void setGeneratedTime(long generatedTime) {
		this.generatedTime = generatedTime;
	}
	/**
	 * 取得验证码长度
	 * 
	 * @return
	 */
	public int getLength() {
		return length;
	}
	/**
	 * 设置验证码长度
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * 取得接收短信验证码的手机号码
	 * 
	 * @return
	 */
	public String getCellPhone() {
		return cellPhone;
	}
	/**
	 * 设置接受短信验证码的手机号码
	 * 
	 * @param cellPhone
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	/**
	 * 取得该验证码的有效时间
	 * 
	 * @return
	 */
	public long getDuration() {
		return duration;
	}
	/**
	 * 设置该验证码的有效时间
	 * 
	 * @param duration
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
	/**
	 * 取得生成验证码的次数
	 * 
	 * @return
	 */
	public int getTimes() {
		return times;
	}
	/**
	 * 设置生成验证码的次数
	 * 
	 * @param times
	 */
	
	public void setTimes(int times) {
		this.times = times;
	}
	
	/**
	 * 判断验证码是否有效
	 * 
	 * @return
	 */
	public boolean isValid() {
		boolean valid = true;
		if(System.currentTimeMillis() > generatedTime + duration) {
			valid = false;
		}
		return valid;
	}
	
	/**
	 * 判断验证码生成时间是否超过指定分钟
	 * great than minutes
	 * 
	 * @param minutes
	 * @return true/false
	 */
	public boolean gtMinutes(int minutes) {
		boolean isMore = false;
		if(System.currentTimeMillis() > generatedTime + minutes * 60 * 1000) {
			isMore = true;
		}
		return isMore;
	}
}
