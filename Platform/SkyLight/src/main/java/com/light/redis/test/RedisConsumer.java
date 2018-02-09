package com.light.redis.test;

public class RedisConsumer {
	 /** 
     * jedis操作List 
     */  
    public static void main(String[] args){
       ScheduleMQ mq = new ScheduleMQ();
       mq.start();
    }   
}
