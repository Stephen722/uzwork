package com.light.record;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 记录集类。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class RecordSet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 第一条记录索引值。
	 */
    public static final int FIRST_ROW_INDEX = 0;

    /**
     * 取得第一条记录。
     * @return Record
     */
    public Record getFirstRecord() {
        return (Record) records.get(FIRST_ROW_INDEX);
    }
    
    /**
     * 取得最后一条记录。
     * @return Record
     */
    public Record getLastRecord() {
    	int pos = records.size() > 0 ? records.size() - 1 : 0;
        return (Record) records.get(pos);
    }

    /**
     * 按索引取得记录。
     * @param index
     * @return Record
     */
    public Record getRecord(int index) {
        return (Record) records.get(index);
    }
    
    /**
     * 设置一个记录集到当前记录集。
     * @param records
     */
    public void addRecords(RecordSet records) {
        if (records == null || records.getSize() == 0){ 
        	return;
        }
        Iterator<Record> recIter = records.getRecords().iterator();
        while (recIter.hasNext()) {
            addRecord((Record) recIter.next());
        }
    }

    /**
     * 增加一条记录，默认不设置 RecordNumber。
     * 
     * @param record
     */
    public void addRecord(Record record) {
        records.add(record);
    }

    /**
     * 根据索引来替换一条记录。
     * 
     * @param index
     * @param record
     */
    public void replaceRecord(int index, Record record) {
        records.set(index, record);
    }

    /**
     * 往所有的记录里插入一个属性，默认覆盖存在的属性。
     * 
     * @param name
     * @param value
     */
    public void setValueOnAll(String name, Object value) {
        setValueOnAll(name, value, true);
    }

    /**
     * 往所有的记录里插入一个属性，并根据overwriteIfExists 判断是否覆盖存在的属性。
     * 
     * @param name
     * @param value
     * @param overwriteIfExists
     */
    public void setValueOnAll(String name, Object value, boolean overwriteIfExists) {
        Iterator<Record> iter = getRecords().iterator();
        while (iter.hasNext()) {
            Record record = (Record) iter.next();
            if (overwriteIfExists || !record.hasValue(name)) {
                record.setValue(name, value);
            }
        }
        addName(name);
    }

    /**
     * 取得所有记录。
     * 
     * @return List
     */
    public List<Record> getRecords() {
        return records;
    }

    /**
     * 取得记录集摘要。
     * 
     * @return Record
     */
    public Record getSummaryRecord() {
        return summaryRecord;
    }

    /**
     * 设置记录集摘要。
     * 
     * @param summaryRecord
     */
    public void setSummaryRecord(Record summaryRecord) {
        this.summaryRecord = summaryRecord;
    }

    /**
     * 取得记录集里的记录总数。
     * 
     * @return int
     */
    public int getSize() {
        return records.size();
    }

    public void clear() {
        records.clear();
        summaryRecord.clear();
        fieldNamesMap.clear();
    }

    /**
     * 增加一个属性。
     * 
     * @param name
     */
    protected void addName(String name) {
        if (!fieldNamesMap.containsKey(name)) {
            fieldNamesMap.put(name, name);
        }
    }

    /**
     * 增加属性。
     * 
     * @param names
     */
    public void addNameCollection(List<String> names) {
        addNameCollection(names.iterator());
    }
    
    /**
     * 增加属性。
     * 
     * @param namesIter
     */
    public void addNameCollection(Iterator<String> namesIter) {
        while (namesIter.hasNext()) {
            String fieldName = namesIter.next();
            addName(fieldName);
        }
    }
    
    /**
     * 删除一个属性。
     * 
     * @param name
     */
    public void removeName(String name) {
        if (fieldNamesMap.containsKey(name)) {
            fieldNamesMap.remove(name);
        }
    } 

    private List<Record> records = new ArrayList<Record>();
    private Record summaryRecord = new Record();
    private Map<String, String> fieldNamesMap = new HashMap<String, String>();
}
