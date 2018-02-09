package com.uzskill.base.configuration;

import java.util.List;

import com.light.record.Record;
/**
 * 系统配置管理类。
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-03-02
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public interface ConfigurationManager {
	
	/**
	 * 取得所有配置参数
	 * 
	 * @return 配置参数列表
	 */
	public List<String> getAllConfiguration();
	
	/**
	 * 根据参数名取得其对应的值列表
	 * 
	 * @param name 参数名
	 * @return 值列表
	 */
	public List<String> getListConfiguration(String name);
	
	/**
	 * 根据参数名取得其对应的值列表
	 * 
	 * @param name 参数名
	 * @return 值列表
	 */
	public String getConfiguration(String name);
	
	/**
	 * 插入新的参数
	 * 
	 * @param record
	 * @return 影响行数
	 */
	public int insertConfiguration(Record record);
	
	/**
	 * 更新参数
	 * 
	 * @param record
	 * @return 影响行数
	 */
	public int updateConfiguration(Record record);
	
	/**
	 * 删除参数
	 * 
	 * @param id
	 * @return 影响行数
	 */
	public int deleteConfiguration(int id);

}