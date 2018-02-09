package com.light.redis.test;

import java.util.List;

import redis.clients.jedis.Jedis;

public class ScheduleMQ extends Thread {
	
	@Override
    public void run() {
        while(true) {
        	Jedis jedis = new Jedis("127.0.0.1", 6379);
            System.out.println("Starting consume");
            //阻塞式brpop，List中无数据时阻塞
            //参数0表示一直阻塞下去，直到List出现数据
            List<String> list = jedis.brpop(0, "informList");
            for(String s : list) {
            	if(s != null && "informList".equals(s)) {
            		continue;
            	}
                System.out.println("Consumer."+s);
            }
            jedis.close();
        }
        
    }
}
