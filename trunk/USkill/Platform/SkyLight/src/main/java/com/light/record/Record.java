package com.light.record;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.light.utils.StringUtils;

/**
 * 记录类。
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-31
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class Record implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> fieldMap;
    
	/**
	 * 构造方法。
	 *
	 */
	public Record() {
        fieldMap = new HashMap<String, Object>();
    }
	
	/**
	 * 用Map构造Record。
	 * 
	 * @param inputMap
	 */
	public Record(Map<String, Object> inputMap) {
        fieldMap = new HashMap<String, Object>();
        fieldMap.putAll(inputMap);
    }

	/**
	 * 构造方法，定义fieldMap 初始化大小。
	 * 
	 * @param size
	 */
    public Record(int size) {
        fieldMap = new HashMap<String, Object>((int) Math.ceil(1.34 * size));
    }

    /**
     * 判断指定的属性是否存在。
     * 
     * @param name 
     * @return true, false
     */
    public boolean hasValue(String name) {
        return fieldMap.containsKey(name);
    }

    /**
     * 判断指定的属性是否有字符串值。
     * 
     * @param name
     * @return true, false
     */
    public boolean hasStringValue(String name) {
        return hasValue(name) && getStringValue(name).trim().length() > 0;
    }

    /**
     * 取值，不存在该值则返回null。
     * 
     * @param name
     * @return Object
     */
    @SuppressWarnings("unchecked")
	public <T> T getValue(String name) {
        return (T) fieldMap.get(name);
    }

    /**
     * 赋值，默认覆盖存在的值。
     * 
     * @param name
     * @param value
     */
    public void setValue(String name, Object value) {
    	setValue(name, value, true);
    }

    /**
     * 赋值，根据overwriteIfExists判断是否覆盖存在的值。
     * 
     * @param name
     * @param value
     * @param overwriteIfExists
     */
    public void setValue(String name, Object value, boolean overwriteIfExists) {
    	if(!StringUtils.isBlank(name)){
    		if(overwriteIfExists || !hasValue(name)){
    			fieldMap.put(name, value);
            }
    	}
    }
    
    /**
     * 增加Record到当前Record，默认覆盖存在的值。
     * 
     * @param newRecord
     */
    public void setRecord(Record newRecord){
    	setRecord(newRecord, true);
    }
    
    /**
     * 增加Record到当前Record，根据指定判断是否覆盖已有的值。
     * 
     * @param newRecord
     * @param overwriteIfExists
     */
    public void setRecord(Record newRecord, boolean overwriteIfExists){
    	if(newRecord != null && newRecord.getSize() > 0){
            Iterator<Entry<String, Object>> iter = newRecord.getRecordMap().entrySet().iterator();
            while(iter.hasNext()) {
            	Entry<String, Object> en = iter.next();
            	setValue(en.getKey(), en.getValue(), overwriteIfExists);
            }
    	}
    }
    
    /**
     * 取得所有属性值。
     * 
     * @return Iterator
     */
    public Iterator<Object> getValues() {
        return fieldMap.values().iterator();
    }

    /**
     * 取得记录里的所存在属性总数。
     * 
     * @return int
     */
    public int getSize() {
        return fieldMap.size();
    }

    /**
     * 以Map形式返回所有记录。
     * 
     * @return Map
     */
    public Map<String, Object> getRecordMap(){
    	return fieldMap;
    }
    
    /**
     * 以Map形式返回所有记录。
     * 
     * @param inputMap
     */
    public void setRecordMap(Map<String, Object> inputMap){
    	fieldMap.putAll(inputMap);
    }
    
    /**
     * 取得该属性的字符值。
     * 
     * @param name
     * @return String
     */
    public String getStringValue(String name) {
        return getStringValue(name, "");
    }

    /**
     * 取得该属性的字符值，并指定一个默认值。
     * 
     * @param name
     * @param nullValue
     * @return String
     */
    public String getStringValue(String name, String nullValue) {
    	Object rtnValue;
    	Object value = getDetailValue(name, nullValue);
		
		if (value == null) {
			rtnValue = nullValue;
		} 
		else if (value instanceof String) {
			rtnValue = value;
		}
		else {
			rtnValue = String.valueOf(value.toString());
		}
		
    	return (String) rtnValue;
    }

    /**
     * 取得Double类型数据。
     * 
     * @param name
     * @return Double
     */
    public Double getDoubleValue(String name){
    	return getDoubleValue(name, 0d);
    }

    /**
     * 取得Double类型数据，并指定一个默认值。
     * 
     * @param name
     * @param nullValue
     * @return Double
     */
    public Double getDoubleValue(String name, Double nullValue) {
    	Object rtnValue;
    	Object value = getDetailValue(name, nullValue);
		if (value instanceof Double) {
			rtnValue = value;
		} 
		else if (value instanceof Number) {
			rtnValue = new Double(((Number) value).floatValue());
		} 
		else if (value == null || value.toString().equalsIgnoreCase("")) {
			rtnValue = nullValue;
		} 
		else {
			rtnValue = Double.valueOf(value.toString());
		}
        return (Double) rtnValue;
    }

    
    /**
     * 取得BigDecimal类型数据。
     * 
     * @param name
     * @return BigDecimal
     */
    public BigDecimal getBigDecimalValue(String name){
    	return getBigDecimalValue(name, null);
    }

    /**
     * 取得BigDecimal类型数据，并指定一个默认值。
     * 
     * @param name
     * @param nullValue
     * @return BigDecimal
     */
    public BigDecimal getBigDecimalValue(String name, BigDecimal nullValue) {
    	Object rtnValue;
    	Object value = getDetailValue(name, nullValue);
		if (value instanceof BigDecimal) {
			rtnValue = value;
		} 
		else if (value instanceof Number) {
			rtnValue = new BigDecimal(((Number) value).doubleValue());
		} 
		else if (value == null || value.toString().equalsIgnoreCase("")) {
			rtnValue = nullValue;
		} 
		else {
			rtnValue = new BigDecimal(value.toString());
		}
        return (BigDecimal) rtnValue;
    }
    
    /**
     * 取得Float类型数据。
     * 
     * @param name
     * @return Float
     */
    public Float getFloatValue(String name){
    	return getFloatValue(name, 0f);
    }

    /**
     * 取得Float类型数据，并指定一个默认值。
     * 
     * @param name
     * @param nullValue
     * @return Float
     */
    public Float getFloatValue(String name, Float nullValue) {
    	Object rtnValue;
    	Object value = getDetailValue(name, nullValue);
		if (value instanceof Float) {
			rtnValue = value;
		} 
		else if (value instanceof Number) {
			rtnValue = new Float(((Number) value).floatValue());
		} 
		else if (value == null || value.toString().equalsIgnoreCase("")) {
			rtnValue = nullValue;
		} 
		else {
			rtnValue = Float.valueOf(value.toString());
		}
        return (Float) rtnValue;
    }

    /**
     * 取得Long类型数据。
     * 
     * @param name
     * @return Long
     */
    public Long getLongValue(String name){
    	return getLongValue(name, 0l);
    }

    /**
     * 取得Long类型数据，并指定一个默认值。
     * 
     * @param name
     * @param nullValue
     * @return Long
     */
    public Long getLongValue(String name, Long nullValue) {
    	Object rtnValue;
    	Object value = getDetailValue(name, nullValue);
		if (value instanceof Long) {
			rtnValue = value;
		} 
		else if (value instanceof Number) {
			rtnValue = new Long(((Number) value).longValue());
		} 
		else if (value == null || value.toString().equalsIgnoreCase("")) {
			rtnValue = nullValue;
		} 
		else {
			rtnValue = Long.valueOf(value.toString());
		}
        return (Long) rtnValue;
    }

    /**
     * 取得Integer类型数据。
     * 
     * @param name
     * @return Integer
     */
    public Integer getIntegerValue(String name){
    	return getIntegerValue(name, 0);
    }

    /**
     * 取得Integer类型数据，并指定一个默认值。
     * 
     * @param name
     * @param nullValue
     * @return Integer
     */
    public Integer getIntegerValue(String name, Integer nullValue) {
    	Object rtnValue;
    	Object value = getDetailValue(name, nullValue);
		if (value instanceof Integer) {
			rtnValue = value;
		} 
		else if (value instanceof Number) {
			rtnValue = new Integer(((Number) value).intValue());
		} 
		else if (value == null || value.toString().equalsIgnoreCase("")) {
			rtnValue = nullValue;
		} 
		else {
			rtnValue = Integer.valueOf(value.toString());
		}
        return (Integer) rtnValue;
    }

    /**
     * 取得Short类型数据。
     * 
     * @param name
     * @return Short
     */
    public Short getShortValue(String name){
    	return getShortValue(name, null);
    }

    /**
     * 取得Short类型数据，并指定一个默认值。
     * 
     * @param name
     * @param nullValue
     * @return Short
     */
    public Short getShortValue(String name, Short nullValue) {
    	Object rtnValue;
    	Object value = getDetailValue(name, nullValue);
		if (value instanceof Short) {
			rtnValue = value;
		} 
		else if (value instanceof Number) {
			rtnValue = new Short(((Number) value).shortValue());
		} 
		else if (value == null || value.toString().equalsIgnoreCase("")) {
			rtnValue = nullValue;
		} 
		else {
			rtnValue = Short.valueOf(value.toString());
		}
        return (Short) rtnValue;
    }

    /**
     * 取得Boolean类型数据。
     * 
     * @param name
     * @return Boolean
     */
    public Boolean getBooleanValue(String name){
    	return getBooleanValue(name, false);
    }

    /**
     * 取得Boolean类型数据，并指定一个默认值。
     * 
     * @param name
     * @param nullValue
     * @return Boolean
     */
    public Boolean getBooleanValue(String name, Boolean nullValue) {
    	Object rtnValue;
    	Object value = getDetailValue(name, nullValue);
		if (value instanceof Boolean) {
			rtnValue = value;
		} 
		else if (value == null || value.toString().equalsIgnoreCase("")) {
			rtnValue = nullValue;
		} 
		else {
			String valueStr = value.toString();
			if(valueStr.equalsIgnoreCase("YES") || valueStr.equalsIgnoreCase("Y")) {
				rtnValue = true;
			}
			else if(valueStr.equalsIgnoreCase("NO") || valueStr.equalsIgnoreCase("N")) {
				rtnValue = false;
			}
			else {
				rtnValue = Boolean.valueOf(valueStr);
			}
		}
        return (Boolean) rtnValue;
    }
    
    private <T> T getDetailValue(String name, T nullValue) {
    	T t = nullValue;
    	if(hasValue(name)) {
    		t = this.getValue(name);
    	}
		
    	return t;
    }
    
    public void remove(String name) {
        if (hasValue(name)) {
            fieldMap.remove(name);
        }
    }
    
    public void clear() {
        fieldMap.clear();
    }
}
