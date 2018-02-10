package com.uzskill.base.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.manager.LightManager;
import com.light.redis.CRUDEnum;
import com.light.redis.RedisDao;

/**
 * Base manager
 *
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-25
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class BaseManager extends LightManager {
	
	private static final Logger logger = LogManager.getLogger(BaseManager.class);
	
	protected static ScheduledExecutorService scheduledServive = Executors.newScheduledThreadPool(15);
	
	private RedisDao redis;

	public RedisDao getRedis() {
		return redis;
	}

	public void setRedis(RedisDao redis) {
		this.redis = redis;
	}
	
	/**
	 * Insert key/object into Redis.
	 * 
	 * @param category it could be user, topic, skill
	 * @param key
	 * @param obj
     * @param hasSet whether the category has the sorted set "category:set"
	 * @return affect rows
	 */
	public int redisInsert(String category, String key, Object obj, boolean hasSet) {
		List<Object> transRows = getRedis().transactionExec(CRUDEnum.INSERT, category, key, obj, hasSet);
		return transRows.size();
	}

	/**
	 * Update key/object into Redis.
	 * 
	 * @param category it could be user
	 * @param key
	 * @param obj
     * @param hasSet whether the category has the sorted set "category:set"
	 * @return affect rows
	 */
	public int redisUpdate(String category, String key, Object obj, boolean hasSet) {
		List<Object> transRows = getRedis().transactionExec(CRUDEnum.UPDATE, category, key, obj, hasSet);
		return transRows.size();
	}
	
	/**
	 * Delete key/object from Redis.
	 * 
	 * @param category it could be user, topic, skill
	 * @param key
	 * @param obj
     * @param hasSet whether the category has the sorted set "category:set"
	 * @return affect rows
	 */
	public int redisDelete(String category, String key, boolean hasSet) {
		List<Object> transRows = getRedis().transactionExec(CRUDEnum.DELETE, category, key, null, hasSet);
		return transRows.size();
	}
	
	/**
	 * Insert key/object into Redis.
	 * 
	 * @param category it could be topic/skill:comment, topic/skill:praise
	 * @param key
	 * @param obj
     * @param setKey whether the category has the sorted set "category:set"
	 * @return affect rows
	 */
	public int redisInsertSub(String category, String key, Object obj, String setKey) {
		List<Object> transRows = getRedis().transactionExecSub(CRUDEnum.INSERT, category, key, obj, setKey);
		return transRows.size();
	}
	
	/**
	 * Delete key/object from Redis.
	 * 
	 * @param category it could be topic/skill:comment, topic/skill:praise
	 * @param key
	 * @param obj
     * @param setKey whether the category has the sorted set "category:set"
	 * @return affect rows
	 */
	public int redisDeleteSub(String category, String key, String setKey) {
		List<Object> transRows = getRedis().transactionExecSub(CRUDEnum.DELETE, category, key, null, setKey);
		return transRows.size();
	}
	
	/**
	 * Retrieve page list data.
	 * If it is first page, system will returns the top 10 objects, else system returns the next 10 objects from the "start" score.
	 * 
	 * @param hashKey
	 * @param setKey
	 * @param firstPage
	 * @param start
	 * @param clazz
	 * @return 10 objects
	 */
    public <T> List<T> getPageList(String hashKey, String setKey, boolean firstPage, int start, Class<T> clazz) {
    		List<T> rtnList = new ArrayList<T>();
		try {
			Set<String> idSet = null;
			if(firstPage) {
				idSet = getRedis().zRevrange(setKey, 0, 10);
			}
			else {
				// "start" is the score. 
				// Because of the score may be not continuous, minus 50 (more or less) to make sure 10 keys with this range.  
				int end = start - 50;
				idSet = getRedis().zRevRangeByScore(setKey, start, end, 1, 10);
			}
			// call responseSet.get() before trans.exec(), it causes "Exception: Please close pipeline or multi block before calling this method."
			if(idSet != null && !idSet.isEmpty()) {
				String[] idArray = (String[]) idSet.toArray();
				rtnList = getRedis().hmGet(hashKey, clazz, idArray);
			}
		}
		catch (Exception e) {
			logger.error("Failed to get page list from redis for hashKey={} setKey={} firstPage={} start={} clazz={}.", hashKey, setKey, firstPage, start, clazz, e);
		}
		
		return rtnList;
    }
	
	/**
	 * Shutdown the scheduled service executors.
	 * 
	 */
	public void destoryScheduledExecutor() {
		if(scheduledServive != null && !scheduledServive.isShutdown()) {
			scheduledServive.shutdownNow();	
		}
	}
}
