package com.light.redis.test;

import redis.clients.jedis.Jedis;

public class RedisProducer {
	/** 
     * jedis操作List 
     */  
    public static void main(String[] args){
    	Jedis jedis = new Jedis("127.0.0.1", 6379); 
        for(int i = 0;i<10;i++) {
            jedis.lpush("informList","Produced value_" + i);
            try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        jedis.close();
    }

}
