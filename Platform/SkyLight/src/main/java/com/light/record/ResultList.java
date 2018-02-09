package com.light.record;

import java.util.List;

/**
 * 结果列表类，包含结果对象的集合和对象总数
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-27
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ResultList<T> {
	
	private int total;
	private List<T> results;
	
	/**
	 * 取得总数
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * 设置总数
	 * 
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * 取得结果列表
	 * 
	 * @return
	 */
	public List<T> getResults() {
		return results;
	}
	
	/**
	 * 设置结果列表
	 * 
	 * @param projects
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}
}
