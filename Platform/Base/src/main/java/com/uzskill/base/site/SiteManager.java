package com.uzskill.base.site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.light.record.Record;
import com.light.record.ResultList;

/**
 * 网站非主体业务管理类
 * 
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-04-05
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public interface SiteManager {
	
	/**
	 * 取得所有反馈信息
	 * 
	 * @param record
	 * 
	 * @return 反馈信息列表
	 */
	public ResultList<Map> getAllFeedback(Record record);
	
	/**
	 * 根据查询信息取得某个反馈信息
	 * 
	 * @param record
	 * 
	 * @return 某个反馈
	 */
	public Record getFeedback(Record record);
	
	/**
	 * 根据城市名搜索省份和城市
	 * @param city
	 * @return
	 */
	public List<HashMap> getProvinceAndCity(String city);
	
	/**
	 * 根据城市名称取得信息
	 * 
	 * @param city
	 * @return
	 */
	public Record getCity(String city);
	
	/**
	 * 是否可以继续提交反馈
	 * 为了避免同一IP连续多次提交，系统限制同一个IP同一天最多只能提交10次反馈
	 * 
	 * @param ip
	 * 
	 * @return true/false
	 */
	public boolean canPostFeedback(String ip);
	
	/**
	 * 删除反馈信息
	 * 
	 * @param feedbackId
	 * 
	 * @return 影响行数
	 */
	public int deleteFeedback(int feedbackId);
	
	/**
	 * 插入新的反馈信息
	 * 
	 * @param record
	 * @return 影响行数
	 */
	public int insertFeedback(Record record);
	
	/**
	 * 取得最新版本，同时会根据当前Version Id和APP类型来判断是否需要强制更新
	 * 
	 * @param record
	 * @return
	 */
	public Record getLatestVersion(Record record);
}
