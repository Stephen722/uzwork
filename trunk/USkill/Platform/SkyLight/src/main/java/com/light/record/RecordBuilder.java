package com.light.record;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.light.record.Record;
import com.light.record.RecordSet;

/**
 * Record Builder.
 * 将从数据库返回的Map或是List转换为Record和RecordSet.
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class RecordBuilder {

	public static final String RETURN_INT_VALUE = "rtnInt";
	public static final String RETURN_VALUE = "rtnString";
	
	/**
	 * 将返回的对象转换为Record。
	 * 如果是一个Map对象，取其Key/Value存入Record。
	 * 如果是一个String对象，以RETURN_VALUE将其值存入Record。
	 * 如果是一个Integer对象，以RETURN_INT_VALUE将其值存入Record。
	 * 
	 * @param obj
	 * @return Record
	 */
	public static Record buildRecord(Object obj){
		Record record = new Record();
		if(obj == null){
			return record;
		}
		if(obj instanceof Map){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;
			if(map.size() > 0){
				Iterator<Entry<String, Object>> itr = map.entrySet().iterator();
				while(itr.hasNext()){
					Entry<String, Object> en = itr.next();
					String key = en.getKey();
					Object value = en.getValue(); 
					record.setValue(key, value);
				}
			}
		}
		else if(obj instanceof String){
			record.setValue(RETURN_VALUE, obj);
		}
		else if(obj instanceof Integer){
			record.setValue(RETURN_INT_VALUE, obj);
		}
		return record;
	}
	
	/**
	 * 将返回的List对象转换为RecordSet。
	 * 
	 * @param results
	 * @return RecordSet
	 */
	public static RecordSet buildRecordSet(List<Object> results){
		RecordSet rs = new RecordSet();
		if(results != null && results.size() > 0){
			for(Object obj: results){
				Record record = buildRecord(obj);
				rs.addRecord(record);
			}
		}
		return rs;
	}
	
}

