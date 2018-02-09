package com.light.redis.test;

import java.util.Random;
import java.util.UUID;

import redis.clients.jedis.Jedis;

public class TaskProducer implements Runnable {
	Jedis jedis = new Jedis("127.0.0.1",6379);  
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random random = new Random();
//        while(true){  
		long tl = jedis.llen("task-queue"); 
		while(tl < 15) {
            try{  
                Thread.sleep(random.nextInt(600) + 600);  
                // 模拟生成一个任务  
                UUID taskid = UUID.randomUUID();  
                //将任务插入任务队列：task-queue  
                jedis.lpush("task-queue", taskid.toString());  
                System.out.println("生成新任务:"+ taskid);
                tl = jedis.llen("task-queue");
            }catch(Exception e){  
                e.printStackTrace();  
            }
        }
	}

}
