package com.uzskill.base.manager;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
	 * Shutdown the scheduled service executors.
	 * 
	 */
	public void destoryScheduledExecutor() {
		if(scheduledServive != null && !scheduledServive.isShutdown()) {
			scheduledServive.shutdownNow();	
		}
	}
}
