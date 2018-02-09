package com.uzskill.base.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.redis.CRUDEnum;
import com.uzskill.base.manager.BaseManager;

public class BaseMonitor<T> implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(BaseMonitor.class);
	
	private CRUDEnum operation; // CRUD operation
	private String statement;   // DB statement
	private String category; 	// It could be user, topic, skill, topic/skill:comment, topic/skill:praise
	private Class<T> objClass;  // The class refers to category
	private BaseManager baseManager;
	
	public BaseMonitor(String category, CRUDEnum operation, String statement, Class<T> objClass, BaseManager baseManager) {
		this.operation = operation;
		this.statement = statement;
		this.category = category;
		this.objClass = objClass;
		this.baseManager = baseManager;
	}
	
	@Override
	public void run() {
		try {
			logger.info("Starting to sync data: objClass={}, category={}, operation={}, statement={}, ", objClass.getName(), category, operation, statement);
			redisSync(category, operation, statement, objClass);
		}
		catch(Exception e) {
			logger.error("Failed to synchronize data for category={}, operation={}, statement={}, objClass={}. Error:{}", category, operation, statement, objClass.getName(), e);
		}
	}
	
	/**
	 * Synchronize data from Redis to database.
	 * 
	 * @param category It could be user, topic, skill, topic/skill:comment, topic/skill:praise
	 * @param operation CRUD operation
	 * @param statement DB statement
	 * @param clazz the class refers to category
	 */
	public void redisSync(String category, CRUDEnum operation, String statement, Class<T> clazz) {
		String listKey = category + ":" + operation.name().toLowerCase();
		int length = baseManager.getRedis().lLen(listKey);
		logger.debug("The size of {} is {}", listKey, length);
		if(length <= 0) {
			return;
		}
		
		int fetchSize = 1000;
		List<T> unSavedList = null; // INSERT and UPDATE: the list of objects which have been persisted into database failed
		List<String> unSavedIdList = null; // DELETE: the list of id which have been persisted into database faield
		List<T> cachedList = null;  // the list of objects which cached in Redis
		// retrieve one thousand objects at most
		if(length <= fetchSize) {
			// the list may be changed, MUST make sure the start value is correct
			int start = 0 - length;
			int end = -1;
			
			switch (operation) {
				case INSERT: 
					cachedList = baseManager.getRedis().lGet(listKey, start, end, clazz);
					unSavedList = redisBatchInsert(statement, cachedList); break;
				case UPDATE: 
					cachedList = baseManager.getRedis().lGet(listKey, start, end, clazz);
					unSavedList = redisBatchUpdate(statement, cachedList); break;
				case DELETE: 
					List<String> cachedIdList = baseManager.getRedis().lGet(listKey, start, end);
					unSavedIdList = redisBatchDelete(statement, cachedIdList); break;
				default: break;
			}
		}
		else {
			int count = length / fetchSize;
			int remainder = length % fetchSize;
			
			if(remainder > 0) {
				count ++;
			}
			int minusFetchSize = 0 - fetchSize;
			for(int i = 0; i < count; i++) {
				int start = minusFetchSize * (i + 1);
				int end = minusFetchSize * i - 1;
				
				switch (operation) {
					case INSERT: 
						cachedList = baseManager.getRedis().lGet(listKey, start, end, clazz);
						unSavedList = redisBatchInsert(statement, cachedList); break;
					case UPDATE: 
						cachedList = baseManager.getRedis().lGet(listKey, start, end, clazz);
						unSavedList = redisBatchUpdate(statement, cachedList); break;
					case DELETE:
						List<String> cachedIdList =  baseManager.getRedis().lGet(listKey, start, end);
						unSavedIdList = redisBatchDelete(statement, cachedIdList); break;
					default: break;
				}
			}
		}
		
		// remove the saved data  
		// the set may be changed, MUST get the latest length again
		int newEnd = 0;
		int newLength = baseManager.getRedis().lLen(listKey);
		if(newLength > length) {
			newEnd = newLength - length;
		}
		logger.debug("The new size is {}", newLength);
		logger.debug("trim the saved key");
		baseManager.getRedis().lTrim(listKey, 0, newEnd);
		
		// the unsaved data should be written back into Redis 
		if(operation.equals(CRUDEnum.DELETE)) {
			if(unSavedIdList != null && !unSavedIdList.isEmpty()) {
				logger.debug("There are {} object have not been saved", unSavedList.size());
				baseManager.getRedis().lSet(listKey, unSavedIdList.toArray());
			}
		}
		else if(unSavedList != null && !unSavedList.isEmpty()) {
			logger.debug("There are {} object have not been saved", unSavedList.size());
			baseManager.getRedis().lSet(listKey, unSavedList);
		}
	}
	
	/**
	 * Batch insert for 
	 * 
	 * @param statement
	 * @param cachedList
	 * @return
	 */
	private List<T> redisBatchInsert(String statement, List<T> cachedList) {
		List<T> unSavedList = new ArrayList<T>();
		// batch insert
		logger.debug("Starting to batch insert");
		int rows = baseManager.insertBatch(statement, cachedList);
		// if batch insert failed, then insert one by one to find which one cannot be persisted.
		if(rows <= 0) {
			for(T t : cachedList) {
				int insRows = baseManager.insert(statement, t);
				if(insRows <= 0) {
					unSavedList.add(t);
				}
			}
		}
		logger.debug("Batch insert end");
		return unSavedList;
	}
	
	private List<T> redisBatchUpdate(String statement, List<T> cachedList) {
		List<T> unSavedList = new ArrayList<T>();
		// batch update
		int rows = baseManager.updateBatch(statement, cachedList);
		// if batch update failed, then update one by one to find which one cannot be persisted.
		if(rows <= 0) {
			for(T t : cachedList) {
				int row = baseManager.update(statement, t);
				if(row <= 0) {
					unSavedList.add(t);
				}
			}
		}
		return unSavedList;
	}
	
	private List<String> redisBatchDelete(String statement, List<String> cachedIdList) {
		List<String> unSavedList = new ArrayList<String>();
		// batch delete
		int rows = baseManager.deleteBatch(statement, cachedIdList);
		// if batch delete failed, then delete one by one to find which one cannot be persisted.
		if(rows <= 0) {
			for(String str : cachedIdList) {
				int row = baseManager.delete(statement, str);
				if(row <= 0) {
					unSavedList.add(str);
				}
			}
		}
		return unSavedList;
	}
}
