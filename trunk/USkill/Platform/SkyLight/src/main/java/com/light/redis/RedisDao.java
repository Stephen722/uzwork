package com.light.redis;

import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.exception.ApplicationException;
import com.light.utils.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * Redis data access object
 *
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-07-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class RedisDao {

	private static final Logger logger = LogManager.getLogger(RedisDao.class);
	
	private static JedisPool jedisPool;
	private String configuration;
    
    /**
     * Get string
     * 
     * @param key
     * @return string
     */
    public String get(String key) {
    	String value = "";
    	Jedis jedis = getJedis();
		try {
			value = jedis.get(key);
		} catch (Exception e) {
			logger.error("Failed to get "+key+" from redis.", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
    }
    
    /**
     * Get object
     * 
     * @param key
     * @param clazz
     * @return object
     */
    public <T> T get(String key, Class<T> clazz) {
    	String value = get(key);
    	return RedisUtils.jsonUnSerialize(value, clazz);
    }
    
    /**
     * set string value
     * 
     * @param key
     * @param value
     * @return status code reply
     */
    public String set(String key, Object value) {
    	return set(key, value, 0);
    }
    
    /**
     * Set string value with cache second
     * 
     * @param key
     * @param value
     * @param cacheSeconds
     * @return status code reply
     */
    public String set(String key, Object value, int cacheSeconds) {
        String rtn = "";
        Jedis jedis = getJedis();
        try {
        	String strValue = "";
        	if(value instanceof String) {
        		strValue = (String) value;
        	}
        	else {
        		strValue = RedisUtils.jsonSerialize(value);
        	}
            rtn = jedis.set(key, strValue);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("Failed to set k/v (" + key + "," + value + ") into redis.", e);
        } finally {
        	if (jedis != null) {
				jedis.close();
			}
        }
        return rtn;
    }
    
    /**
     * Get string list
     * Command: lrange
     * 
     * @param key
     * @return string list
     */
    public List<String> lGet(String key){
        return lGet(key, 0, -1);
    }
    
    /**
     * Get string list with specified range
     * Command: lrange
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lGet(String key, int start, int end){
        List<String> value = null;
        Jedis jedis = getJedis();
        try {
            value = jedis.lrange(key, start, end);
        } 
        catch (Exception e) {
        	logger.error("Failed to get list "+key+" from redis.", e);
        } 
        finally {
            jedis.close();
        }
        return value;
    }
    
    /**
     * Get object list
     * Command: lrange
     * 
     * @param key
     * @param start
     * @param end
     * @param clazz
     * @return
     */
    public <T> List<T> lGet(String key, Class<T> clazz) {
    	return lGet(key, 0, -1, clazz);
    }

    /**
     * Get object list
     * Command: lrange
     * 
     * @param key
     * @param start
     * @param end
     * @param clazz
     * @return object list
     */
    public <T> List<T> lGet(String key, int start, int end, Class<T> clazz){
        List<T> value = new ArrayList<T>();
        try {
            List<String> listStr = lGet(key, start, end);
            for (String lstr : listStr) {
            	T obj = RedisUtils.jsonUnSerialize(lstr, clazz);
                value.add(obj);
            }
        }
        catch (Exception e){
        	logger.error("Failed to get object list "+key+" from redis.", e);
        }
        
        return value;
    }

    /**
     * Set string list
     * 
     * @param key
     * @param value
     * @return integer reply
     */
    public int lSet(String key, Object value){
    	return lSet(key, value, 0);
    }
    
    /**
     * Set string list with cache second
     * Command: rpush
     * 
     * @param key
     * @param value
     * @param cacheSeconds
     * @return integer reply
     */
    public int lSet(String key, Object value, int cacheSeconds){
        long rtn = 0;
        Jedis jedis = getJedis();
        try {
        	String strValue = "";
        	if(value instanceof String || value instanceof String[]) {
        		strValue = (String) value;
        		rtn = jedis.lpush(key, strValue);
        	}
        	else if(value instanceof List) {
        		List values = (List) value;
        		int size = values.size();
        		String[] jsonStr = new String[values.size()];
        		for(int i = 0; i < size; i++) {
        			Object obj = values.get(i);
        			String str = "";
        			if(obj instanceof String) {
        				str = (String) obj;
        			}
        			else {
        				str = RedisUtils.jsonSerialize(obj);
        			}
        			jsonStr[i] = str;
        		}
        		rtn = jedis.lpush(key, jsonStr);
        	}
        	else {
        		strValue = RedisUtils.jsonSerialize(value);
        		rtn = jedis.lpush(key, strValue);
        	}
            
            if (cacheSeconds != 0){
                jedis.expire(key, cacheSeconds);
            }
        }
        catch (Exception e){
        	logger.error("Failed to push list "+key+" into redis.", e);
        }
        finally {
            jedis.close();
        }
        return (int) rtn;
    }
    
    /**
     * Get the length of list
     * Command: llen
     * 
     * @param key
     * @return integer reply
     */
    public int lLen(String key){
    	long rtn = 0;
        Jedis jedis = getJedis();
        try {
            rtn = jedis.llen(key);
        }
        catch (Exception e){
        	logger.error("Failed to get the length of list "+key+" from redis.", e);
        }
        finally {
            jedis.close();
        }
        return (int) rtn;
    }
    
    /**
     * Keep the value which is in the specified range. 
     * The "start" comes from client, the list may be changed, use the latest size to trim list with transactions.
     * 
     * Command: ltrim
     * 
     * @param key
     * @param start
     * @param end
     */
    public void lTrim(String key, int start, int end){
        Jedis jedis = getJedis();
        try {
            jedis.ltrim(key, start, end);
        }
        catch (Exception e){
        	logger.error("Failed to trim list "+key+" from redis.", e);
        }
        finally {
            jedis.close();
        }
    }
    
    /**
     * List pop
     * 
     * @param isRight true: brpop, false: blpop
     * @param key
     * @param timeout
     * @return list string
     */
    public List<String> lBlockPop(boolean isRight, String key, int timeout){
    	List<String> value = null;
        Jedis jedis = getJedis();
        try {
        	if(isRight) {
        		value = jedis.brpop(timeout, key);
        	}
        	else {
        		value = jedis.blpop(timeout, key);
        	}
        } 
        catch (Exception e) {
        	logger.error("Failed to pop "+key+" from redis.", e);
        } 
        finally {
            jedis.close();
        }
        return value;
    }
    
    /**
     * Pop from source list and push into destination list
     * 
     * @param source
     * @param destination
     * @return pop string
     */
    public String lRightPopLeftPush(String source, String destination){
    	String value = null;
        Jedis jedis = getJedis();
        try {
        	value = jedis.rpoplpush(source, destination);
        } 
        catch (Exception e) {
        	logger.error("Failed to pop from "+source+", and push into "+destination+" from redis.", e);
        } 
        finally {
            jedis.close();
        }
        return value;
    }
    
    /**
     * Add value to sorted Set
     * 
     * @param key
     * @param score
     * @param value
     * @return
     */
    public int zAdd(String key, long score, Object value) {
    	long rtn = 0;
        Jedis jedis = getJedis();
        try {
        	String strValue = "";
        	if(value instanceof String) {
        		strValue = (String) value;
        	}
        	else {
        		strValue = RedisUtils.jsonSerialize(value);
        	}
        	rtn = jedis.zadd(key, score, strValue);
        } 
        catch (Exception e) {
        	logger.error("Failed to add key={} socre={} value={} into redis. Exception:{}", key, score, value, e);
        } 
        finally {
            jedis.close();
        }
        return (int) rtn;
    }
    
    /**
     * Remove members from sorted Set 
     * 
     * @param key
     * @param members
     * @return
     */
    public int zRem(String key, String... members) {
    	long rtn = 0;
        Jedis jedis = getJedis();
        try {
        	rtn = jedis.zrem(key, members);
        } 
        catch (Exception e) {
        	logger.error("Failed to add key={} members={} into redis. Exception:{}", key, members, e);
        } 
        finally {
            jedis.close();
        }
        return (int) rtn;
    }

    /**
     * Remove members from sorted Set 
     * 
     * @param key
     * @param members
     * @return
     */
    public int zRemRangeByRank(String key, int start, int end) {
    	long rtn = 0;
        Jedis jedis = getJedis();
        try {
        	rtn = jedis.zremrangeByRank(key, start, end);
        } 
        catch (Exception e) {
        	logger.error("Failed to remove range by rank key={} start={} end={}into redis. Exception:{}", key, start, end, e);
        } 
        finally {
            jedis.close();
        }
        return (int) rtn;
    }
    
    /**
     * Get the size of sorted Set 
     * 
     * @param key
     * @return the size of sorted set
     */
    public int zCard(String key) {
    	long rtn = 0;
        Jedis jedis = getJedis();
        try {
        	rtn = jedis.zcard(key);
        } 
        catch (Exception e) {
        	logger.error("Failed to get the size of sorted set, key={} from redis. Exception:{}", key, e);
        } 
        finally {
            jedis.close();
        }
        return (int) rtn;
    }    
    /**
     * Return the rank/index of member
     * 
     * @param key
     * @param member
     * @return
     */
    public int zRank(String key, String member) {
    	long rtn = 0;
        Jedis jedis = getJedis();
        try {
        	rtn = jedis.zrank(key, member);
        } 
        catch (Exception e) {
        	logger.error("Failed to get rank key={} member={} into redis. Exception:{}", key, member, e);
        } 
        finally {
            jedis.close();
        }
        return (int) rtn;
    }
    
    /**
     * Return all the members between start and end, from low to high.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRange(String key, int start, int end) {
    	Set<String> rtnSet = null;
        Jedis jedis = getJedis();
        try {
        	rtnSet = jedis.zrange(key, start, end);
        } 
        catch (Exception e) {
        	logger.error("Failed to get range key={} start={} end={} into redis. Exception:{}", key, start, end, e);
        } 
        finally {
            jedis.close();
        }
        return rtnSet;
    }
    
    /**
     * Return all the members between start and end, from low to high.
     * 
     * @param key
     * @param start
     * @param end
     * @param clazz
     * @return
     */
    public <T> List<T> zRange(String key, int start, int end, Class<T> clazz) {
    	List<T> rtnList = null;
        Jedis jedis = getJedis();
        try {
        	Set<String> strSet = jedis.zrange(key, start, end);
        	rtnList = new ArrayList<T>(strSet.size());
        	for(String str : strSet) {
        		T t = RedisUtils.jsonUnSerialize(str, clazz);
        		rtnList.add(t);
        	}
        } 
        catch (Exception e) {
        	logger.error("Failed to get range key={} start={} end={} clazz={} into redis. Exception:{}", key, start, end, clazz, e);
        } 
        finally {
            jedis.close();
        }
        return rtnList;
    } 
    /**
     * Return all the members between start and end, from high to low.
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRevrange(String key, int start, int end) {
    	Set<String> rtnSet = null;
        Jedis jedis = getJedis();
        try {
        	rtnSet = jedis.zrevrange(key, start, end);
        } 
        catch (Exception e) {
        	logger.error("Failed to get rev range key={} start={} end={} into redis. Exception:{}", key, start, end, e);
        } 
        finally {
            jedis.close();
        }
        return rtnSet;
    }

    /**
     * Return all the members which score between min and max, from low to high.
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zRangeByScore(String key, int min, int max) {
    	Set<String> rtnSet = null;
        Jedis jedis = getJedis();
        try {
        	rtnSet = jedis.zrangeByScore(key, min, max);
        } 
        catch (Exception e) {
        	logger.error("Failed to get range by score key={} min={} max={} into redis. Exception:{}", key, min, max, e);
        } 
        finally {
            jedis.close();
        }
        return rtnSet;
    }

    /**
     * Return all the members which score between max and min, from high to low.
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zRevRangeByScore(String key, int max, int min) {
    	Set<String> rtnSet = null;
        Jedis jedis = getJedis();
        try {
        	rtnSet = jedis.zrevrangeByScore(key, max, min);
        } 
        catch (Exception e) {
        	logger.error("Failed to get rev range by score key={} max={} min={} into redis. Exception:{}", key, min, max, e);
        } 
        finally {
            jedis.close();
        }
        return rtnSet;
    }

    /**
     * Return the members which score between max and min (with offset and count), from high to low.
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zRevRangeByScore(String key, int max, int min, int offset, int count) {
    	Set<String> rtnSet = null;
        Jedis jedis = getJedis();
        try {
        	rtnSet = jedis.zrevrangeByScore(key, max, min, offset, count);
        } 
        catch (Exception e) {
        	logger.error("Failed to get rev range by score key={} max={} min={} offset={} count={} into redis. Exception:{}", key, max, min, offset, count, e);
        } 
        finally {
            jedis.close();
        }
        return rtnSet;
    }
    
    /**
     * Get hash string
     * 
     * @param key
     * @param field
     * @return value
     */
    public String hGet(String key, String field){
        String value = "";
        Jedis jedis = getJedis();
        try {
            value = jedis.hget(key, field);
        } 
        catch (Exception e) {
        	logger.error("Failed to get (hash) string "+field+" from redis.", e);
        } 
        finally {
            jedis.close();
        }
        return value;
    }

    /**
     * Get hash object
     * 
     * @param key
     * @param field
     * @return object
     */
    public <T> T hGet(String key, String field, Class<T> clazz){
    	String hashStr = hGet(key, field);
    	return RedisUtils.jsonUnSerialize(hashStr, clazz);
    }

    /**
     * Get hash string
     * 
     * @param key
     * @param field
     * @return value
     */
    public boolean hHas(String key, String field){
    	boolean value = false;
        Jedis jedis = getJedis();
        try {
            value = jedis.hexists(key, field);
        } 
        catch (Exception e) {
        	logger.error("Failed to check (hash) field "+field+" from redis.", e);
        } 
        finally {
            jedis.close();
        }
        return value;
    }
    
    /**
     * Get hash string
     * 
     * @param key
     * @param field
     * @return value
     */
    public int hSet(String key, String field, Object value){
        long rtn = 0;
        Jedis jedis = getJedis();
        try {
        	String strValue = "";
        	if(value instanceof String) {
        		strValue = (String) value;
        	}
        	else {
        		strValue = RedisUtils.jsonSerialize(value);
        	}
            rtn = jedis.hset(key, field, strValue);
        } 
        catch (Exception e) {
        	logger.error("Failed to set (hash) string "+key+" into redis.", e);
        } 
        finally {
            jedis.close();
        }
        return (int) rtn;
    }
 
    
    /**
     * Get the length of hash
     * Command: hlen
     * 
     * @param key
     * @return integer reply
     */
    public int hLen(String key){
        long rtn = 0;
        Jedis jedis = getJedis();
        try {
            rtn = jedis.hlen(key);
        }
        catch (Exception e){
        	logger.error("Failed to get the lenght of hash "+key+" from redis.", e);
        }
        finally {
            jedis.close();
        }
        return (int) rtn;
    }
    
    /**
     * Delete hash key
     * 
     * @param key
     * @param field
     * @return If the field was present it is deleted and 1 is returned, else 0 is returned.
     */
    public int hDel(String key, String field){
        long rtn = 0;
        Jedis jedis = getJedis();
        try {
            rtn = jedis.hdel(key, field);
        } 
        catch (Exception e) {
        	logger.error("Failed to delete hash key "+key+" from redis.", e);
        } 
        finally {
            jedis.close();
        }
        return (int) rtn;
    }
    
    /**
     * Get multiple hash key
     * 
     * @param key
     * @param fields
     * @return String list
     */
    public List<String> hmGet(String key, String... fields){
    	List<String> value = null;
    	Jedis jedis = getJedis();
        try {
             value = jedis.hmget(key, fields);
        } 
        catch (Exception e) {
        	logger.error("Failed to mulitiple get (hash) string "+fields+" from redis.", e);
        } 
        finally {
            jedis.close();
        }
        return value;
    	
    }
    
    /**
     * Get multiple hash object
     * 
     * @param key
     * @param clazz
     * @param fields
     * @return
     */
    public <T> List<T> hmGet(String key, Class<T> clazz, String... fields) {
    	List<T> value = new ArrayList<T>();
    	List<String> listStr = hmGet(key, fields);
        for (String lstr : listStr) {
        	T obj = RedisUtils.jsonUnSerialize(lstr, clazz);
            value.add(obj);
        }
        return value;
    }
    
    /**
     * Save data locally
     * 
     * @return status code reply
     */
    public String save() {
    	Jedis jedis = getJedis();
    	String rtn = "";
		try {
			rtn = jedis.save();
		} 
		catch (Exception e) {
			logger.error("Failed to save data into redis.", e);
		} 
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return rtn;
    }

   /**
    * Save(insert/update/delete) data into Redis with transaction.
    * 
    * Inserting and updating have the some logic, add the key into list directly. If hasSet, key is the member.
    * For deleting, the value is null, the key is valid.
    * 
    * @param operation
    * @param category
    * @param key
    * @param value
    * @param hasSet whether the category has the sorted set "category:set" 
    * @return
    */
    public List<Object> transactionExec(CRUDEnum operation, String category, String key, Object value, boolean hasSet) {
    	Jedis jedis = getJedis();
    	List<Object> transRtn = null;
		try {
			String listKey = category + ":" + operation.name().toLowerCase();
			String hashKey = category + ":map";
			
			// delete operation has different logic
			if(operation.equals(CRUDEnum.DELETE)) {
				Transaction trans = jedis.multi();
				if(hasSet) {
					// delete from sorted set
					trans.zrem(category + ":set", key);
				}
				// delete from hash map (by key)
				trans.hdel(hashKey, key);
				// add the key into delete list tail
				trans.rpush(listKey, key);
				
				transRtn = trans.exec();
			}
			else {
				String strValue = "";
				if(value instanceof String) {
	        		strValue = (String) value;
	        	}
	        	else {
	        		strValue = RedisUtils.jsonSerialize(value);
	        	}
				
				Transaction trans = jedis.multi();
				if(hasSet) {
					// insert/update sorted set, score and member are the same key
					trans.zadd(category + ":set", Double.valueOf(key), key);
				}
				// insert/update hash map
				trans.hset(hashKey, key, strValue);
				// add the value into list tail
				trans.rpush(listKey, strValue);
				transRtn = trans.exec();
			}
		} 
		catch (Exception e) {
			logger.error("Failed to save data into redis.", e);
		} 
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return transRtn;
    }

    /**
     * Save(insert/update/delete) subsidiary data into Redis with transaction.
     * 
     * Inserting and updating have the some logic, add the key into list directly. Value is the member of sorted set.
     * For deleting, the value is null, the key is valid.
     * 
     * @param operation
     * @param category
     * @param key
     * @param value
     * @param hasSet whether the category has the sorted set "category:set" 
     * @return
     */
    public List<Object> transactionExecSub(CRUDEnum operation, String category, String key, Object value, String setKey) {
    	Jedis jedis = getJedis();
    	List<Object> transRtn = null;
		try {
			String listKey = category + ":" + operation.name().toLowerCase();
			
			// delete operation has different logic
			if(operation.equals(CRUDEnum.DELETE)) {
				
				Transaction trans = jedis.multi();
				// delete from sorted set
				trans.zrem(setKey, key);
				// add the key into delete list
				trans.rpush(listKey, key);
				transRtn = trans.exec();
			}
			else {
				String strValue = "";
				if(value instanceof String) {
	        		strValue = (String) value;
	        	}
	        	else {
	        		strValue = RedisUtils.jsonSerialize(value);
	        	}
				
				Transaction trans = jedis.multi();
				// insert/update sorted set
				trans.zadd(setKey, Double.valueOf(key), strValue);
				// add to list
				trans.rpush(listKey, strValue);
				transRtn = trans.exec();
			}
		} 
		catch (Exception e) {
			logger.error("Failed to save data into redis.", e);
		} 
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return transRtn;
    }
    
    public <T> List<T> getSortSetObject(String hashKey, String setKey, boolean firstPage, int start, Class<T> clazz) {
    	Jedis jedis = getJedis();
    	List<T> rtnList = new ArrayList<T>();
		try {
			List<String> strList = null;
			Response<Set<String>> responseSet= null;
			Transaction trans = jedis.multi();
			if(firstPage) {
				responseSet = trans.zrevrange(setKey, 0, 10);
			}
			else {
				// "start" is the score. 
				// Because of the score may be not continuous, minus 100 (more or less) to make sure 10 keys with this range.  
				int end = start - 100;
				responseSet = trans.zrevrangeByScore(setKey, start, end, 1, 10);
			}
			trans.exec(); 
			// call responseSet.get() before trans.exec(), it causes "Exception: Please close pipeline or multi block before calling this method."
			Set<String> idSet = responseSet.get();
			if(idSet != null && !idSet.isEmpty()) {
				String[] idArray = (String[]) idSet.toArray();
				strList = trans.hmget(hashKey, idArray).get();
			}
			
			if(strList != null && strList.size() > 0) {
				for(String objStr : strList) {
					T t = RedisUtils.jsonUnSerialize(objStr, clazz);
					rtnList.add(t);
				}
			}
			
		} 
		catch (Exception e) {
			logger.error("Failed to save data into redis.", e);
		} 
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return rtnList;
    }
    
    /**
     * Increment the number stored at key by one 
     * 
     * @param key
     * @return long
     */
    public long increase(String key) {
    	Jedis jedis = getJedis();
    	Long rtn = 0L;
		try {
			rtn = jedis.incr(key);
		} 
		catch (Exception e) {
			logger.error("Failed to increase "+key+".", e);
		} 
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return rtn;
    }
    
    public long decrease(String key) {
    	Jedis jedis = getJedis();
    	Long rtn = 0L;
		try {
			rtn = jedis.decr(key);
		} 
		catch (Exception e) {
			logger.error("Failed to decrease "+key+".", e);
		} 
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return rtn;
    }
    
    /**
     * Set expire time
     * 
     * @param key
     * @param seconds
     * @return integer reply
     */
    public int expire(String key, int seconds) {
    	long rtn = 0;
        Jedis jedis = getJedis();
        try {
            rtn = jedis.expire(key, seconds);
        }
        catch (Exception e){
        	logger.error("Failed to set expired time for "+key+" in redis.", e);
        }
        finally {
			if (jedis != null) {
				jedis.close();
			}
		}
        return (int) rtn;
    }
    
    /**
     * Delete key
     * 
     * @param key
     * @return integer reply
     */
    public long del(String key) {
    	long rtn = 0;
        Jedis jedis = getJedis();
        try {
            rtn = jedis.del(key);
        }
        catch (Exception e){
        	logger.error("Failed to delete "+key+" from redis.", e);
        }
        finally {
			if (jedis != null) {
				jedis.close();
			}
		}
        return rtn;
    }
    
    /**
     * Get jedis
     * 
     * @return jedis
     */
    private Jedis getJedis() {
    	if(jedisPool == null) {
    		initializeJedisPool();
    	}
    	return jedisPool.getResource();
    }
    /**
	 * 初始化JedisPool
	 * 
	 * @return JedisPool
	 */
	private void initializeJedisPool() {
		logger.info("Starting to initialize JedisPool.");
		try {
			Resources.setCharset(Charset.forName("UTF-8"));
			Reader reader = Resources.getResourceAsReader(configuration);
			Properties pps = new Properties();
			pps.load(reader);
            
			String host = pps.getProperty("redis.pool.host", "127.0.0.1");
			int port = StringUtils.getInt(pps.getProperty("redis.pool.port", "6379"));
			int timeout = StringUtils.getInt(pps.getProperty("redis.pool.timeout", "5000"));
			String password = pps.getProperty("redis.pool.password", null); // password should be null if no password
			
			int maxTotal = StringUtils.getInt(pps.getProperty("redis.pool.maxTotal", "20000"));
			int minIdle = StringUtils.getInt(pps.getProperty("redis.pool.minIdle", "100"));
			int maxIdle = StringUtils.getInt(pps.getProperty("redis.pool.maxIdle", "2000"));
			boolean testOnBorrow = StringUtils.getBoolean(pps.getProperty("redis.pool.testOnBorrow", "true"));
			
			JedisPoolConfig config = new JedisPoolConfig();  
			config.setMaxTotal(maxTotal);
			config.setMaxIdle(maxIdle);
			config.setMinIdle(minIdle);
			config.setTestOnBorrow(testOnBorrow);
			
			jedisPool = new JedisPool(config, host, port, timeout, password);
			if (jedisPool == null || jedisPool.getResource() == null) {
				logger.info("Initialized JedisPool unsuccessfully.");
			}
			else {
				logger.info("Initialized JedisPool successfully.");
			}
		}
		catch (Exception e) {
			logger.error("Failed to initialize JedisPool.\n Exception:", e);
			ApplicationException ae = new ApplicationException("Failed to initialize JedisPool.", e);
			throw ae;
		}
	}
	
	public String getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
}
