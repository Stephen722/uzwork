package com.light.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class RedisUtils {
	private static final Logger logger = LogManager.getLogger(RedisUtils.class);
	

	/**
	 * 
	 * @Description 序列化
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static byte[] serialize(Object object) throws Exception {
		if(object == null) return null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	/**
	 * 
	 * @Description 反序列化
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static Object unSerialize(byte[] bytes) throws Exception {
		if(bytes == null) return null;
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	public static String jsonSerialize(Object object) {
		return JSON.toJSONString(object);
	}
	
	public static <T> T jsonUnSerialize(String objString, Class<T> clazz) {
		T obj = null;
		if(objString != null && objString.length() > 1) {
			obj = JSON.parseObject(objString, clazz);
		}
		return obj;
	}
}
