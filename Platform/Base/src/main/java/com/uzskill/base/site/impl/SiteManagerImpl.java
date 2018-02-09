package com.uzskill.base.site.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.light.common.Constants;
import com.light.record.Record;
import com.light.record.ResultList;
import com.light.utils.StringUtils;
import com.uzskill.base.manager.BaseManager;
import com.uzskill.base.site.SiteManager;
import com.uzskill.base.site.SiteQuery;

/**
 * 网站非主体业务管理类
 * 
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-04-05
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class SiteManagerImpl extends BaseManager implements SiteManager {

	@Override
	public ResultList<Map> getAllFeedback(Record record) {
		ResultList rsList = new ResultList();
		// 分页信息
		int page = record.getIntegerValue(Constants.PAGE, 0);
		int pageSize = record.getIntegerValue(Constants.PAGE_SIZE, 10);
		int total = select(SiteQuery.GET_ALL_FEEDBACK_TOTAL, record);
		List list = selectPageList(SiteQuery.GET_ALL_FEEDBACK, record, page, pageSize);
		
		rsList.setTotal(total);
		rsList.setResults(list);
		return rsList;
	}

	@Override
	public Record getFeedback(Record record) {
		return selectRecord(SiteQuery.GET_FEEDBACK, record.getRecordMap());
	}
	
	@Override
	public List<HashMap> getProvinceAndCity(String city) {
		return selectList(SiteQuery.GET_PROVINCE_AND_CITY, city);
	}
	
	@Override
	public Record getCity(String city) {
		return selectRecord(SiteQuery.GET_CITY, city);
	}
	
	@Override
	public boolean canPostFeedback(String ip) {
		int number = select(SiteQuery.GET_NUMBER_OF_FEEDBACK_TODAY, ip);
		return number < 10;
	}
	
	@Override
	public int deleteFeedback(int feedbackId) {
		return delete(SiteQuery.DELETE_FEEDBACK, feedbackId);
	}

	@Override
	public int insertFeedback(Record record) {
		return insert(SiteQuery.INSERT_FEEDBACK, record.getRecordMap());
	}

	@Override
	public Record getLatestVersion(Record record) {
		int latestVersionId = 0;
//		int latestForceUpdate = 0;
		Record latestVersion = new Record();
//		int versionId = record.getIntegerValue(ShareFields.VERSION_ID, 0);
//		if(versionId > 0) {
//			// default APP type as "android" 
//			if(!record.hasStringValue(ShareFields.APP)) {
//				record.setValue(ShareFields.APP, "android");
//			}
//			
//			latestVersion = selectRecord(SiteQuery.GET_LATEST_VERSION, record);
//			if(latestVersion != null) {
//				// 如果当前Version Id小于最新Version Id，说明有新版本。
//				latestVersionId = latestVersion.getIntegerValue(ShareFields.VERSION_ID, 0);
//				latestForceUpdate = latestVersion.getIntegerValue(ShareFields.FORCE_UPDATE, 0);
//				if(versionId < latestVersionId) {
//					// 如果最新版本没有强制更新，则判断在当前版本和最新版本之间是否有强制更新的版本，如果有则需要强制更新到最新版本无论最新版本是否为强制更新版本。
//					if(latestForceUpdate == 0) {
//						int hasForceUpdateVersion = StringUtils.getInt(select(SiteQuery.CHECK_FORCE_UPDATE_BY_VERSION_ID, record));
//						if(hasForceUpdateVersion > 0) {
//							latestForceUpdate = 1;
//						}
//					}
//				}
//			}
//		}
//		latestVersion.setValue(ShareFields.VERSION_ID, latestVersionId);
//		latestVersion.setValue(ShareFields.FORCE_UPDATE, latestForceUpdate);

		return latestVersion;
	}

}
