package com.uzskill.base.schedule;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.redis.CRUDEnum;
import com.uzskill.base.manager.BaseManager;

public class MonitorCommand<T> implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(MonitorCommand.class);
	
	private CRUDEnum operation; // CRUD operation
	private String statement;   // DB statement
	private String category; 	// It could be user, topic, skill, topic/skill:comment, topic/skill:praise
	private Class<T> objClass;  // The class refers to category
	private BaseManager baseManager;
	
	public MonitorCommand(String category, CRUDEnum operation, String statement, Class<T> objClass, BaseManager baseManager) {
		this.operation = operation;
		this.statement = statement;
		this.category = category;
		this.objClass = objClass;
		this.baseManager = baseManager;
	}
	
	@Override
	public void run() {
		try {
			redisSync(category, operation, statement, objClass);
		}
		catch(Exception e) {
			logger.error("Failed to synchronize data beteen Redis and database: category={}, operation={}. Error:{}", category, operation, e);
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
		if(length <= 0) {
			return;
		}
		
		logger.debug("Synchronization starting: {}", statement);
		int fetchSize = 600;
		List<T> unSavedList = null; // INSERT and UPDATE: the list of objects which have been persisted into database failed
		List<String> unSavedIdList = null; // DELETE: the list of id which have been persisted into database faield
		List<T> cachedList = null;  // the list of objects which cached in Redis
		// retrieve one thousand objects at most
		if(length <= fetchSize) {
			// the list may be changed, MUST make sure the start value is correct
			int start = 0;
			int end = length;
			
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
			for(int i = 0; i < count; i++) {
				int start = fetchSize * i;
				int end = fetchSize * (i + 1) - 1;
				
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
		int newLength = baseManager.getRedis().lLen(listKey);
		baseManager.getRedis().lTrim(listKey, length + 1, newLength);
		
		// the unsaved data should be written back into Redis. 
		// DELETE list only contains ID, INSERT/UPDATE list contains object.
		if(operation.equals(CRUDEnum.DELETE)) {
			if(unSavedIdList != null && !unSavedIdList.isEmpty()) {
				baseManager.getRedis().lSet(listKey, unSavedIdList.toArray());
			}
		}
		else if(unSavedList != null && !unSavedList.isEmpty()) {
			baseManager.getRedis().lSet(listKey, unSavedList);
		}
		
		logger.debug("Synchronization ending: {}", statement);
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
		int rows = baseManager.insertBatch(statement, cachedList);
		// if batch insert failed, then insert one by one to find which one cannot be persisted.
		if(rows <= 0) {
			logger.debug("{}, batch insert failed, try to insert one by one", statement);
			for(T t : cachedList) {
				int insRows = baseManager.insert(statement, t);
				if(insRows <= 0) {
					unSavedList.add(t);
				}
			}
			int unSavedSize = unSavedList.size();
			if(unSavedSize > 0) {
				logger.debug("{} object(s) still inserted failed", unSavedSize);
			}
		}
		else {
			logger.debug("{}, batch insert successfully", statement);
		}
		return unSavedList;
	}
	
	private List<T> redisBatchUpdate(String statement, List<T> cachedList) {
		List<T> unSavedList = new ArrayList<T>();
		// batch update
		int rows = baseManager.updateBatch(statement, cachedList);
		// if batch update failed, then update one by one to find which one cannot be persisted.
		if(rows <= 0) {
			logger.debug("{}, batch update failed, try to update one by one", statement);
			for(T t : cachedList) {
				int row = baseManager.update(statement, t);
				if(row <= 0) {
					unSavedList.add(t);
				}
			}
			int unSavedSize = unSavedList.size();
			if(unSavedSize > 0) {
				logger.debug("{} object(s) still updated failed", unSavedSize);
			}
		}
		else {
			logger.debug("{}, batch update successfully", statement);
		}
		return unSavedList;
	}
	
	private List<String> redisBatchDelete(String statement, List<String> cachedIdList) {
		List<String> unSavedList = new ArrayList<String>();
		// batch delete
		int rows = baseManager.deleteBatch(statement, cachedIdList);
		// if batch delete failed, then delete one by one to find which one cannot be persisted.
		if(rows <= 0) {
			logger.debug("{}, batch delete failed, try to delete one by one", statement);
			for(String str : cachedIdList) {
				int id = Integer.parseInt(str);
				int row = baseManager.delete(statement, id);
				if(row <= 0) {
					unSavedList.add(str);
				}
			}
			int unSavedSize = unSavedList.size();
			if(unSavedSize > 0) {
				logger.debug("{} object(s) still delete failed", unSavedSize);
			}
		}
		else {
			logger.debug("{}, batch delete successfully", statement);
		}
		return unSavedList;
	}
}