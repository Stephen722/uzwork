package com.uzskill.base.configuration.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.light.record.Record;
import com.light.utils.StringUtils;
import com.uzskill.base.configuration.ConfigurationManager;
import com.uzskill.base.configuration.ConfigurationQuery;
import com.uzskill.base.manager.BaseManager;

/**
 * 系统配置管理类。
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-03-02
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ConfigurationManagerImpl extends BaseManager implements ConfigurationManager {

	private static final String SPLITER = ";"; 
	
	@Override
	public List<String> getAllConfiguration() {
		return selectList(ConfigurationQuery.GET_ALL_CONFIGURATION);
	}

	@Override
	public List<String> getListConfiguration(String name) {
		List<String> rtn = new ArrayList<String>();
		String value = getConfiguration(name);
		if(!StringUtils.isBlank(value)) {
			if(value.indexOf(SPLITER) > 0) {
				String[] valueArray = value.split(SPLITER);
				rtn = Arrays.asList(valueArray);
			}
			else {
				rtn.add(value);
			}
		}
		
		return rtn;
	}

	@Override
	public String getConfiguration(String name) {
		return select(ConfigurationQuery.GET_CONFIGURATION, name);
	}
	
	@Override
	public int insertConfiguration(Record record) {
		return insert(ConfigurationQuery.INSERT_CONFIGURATION, record);
	}

	@Override
	public int updateConfiguration(Record record) {
		return update(ConfigurationQuery.UPDATE_CONFIGURATION, record);
	}

	@Override
	public int deleteConfiguration(int id) {
		return delete(ConfigurationQuery.DELETE_CONFIGURATION, id);
	}
}
