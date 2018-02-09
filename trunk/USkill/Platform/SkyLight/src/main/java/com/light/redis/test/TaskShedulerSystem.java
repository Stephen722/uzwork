package com.light.redis.test;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskShedulerSystem {
	public static void main(String[] args) throws Exception {  
        
//        // 启动一个生产者线程，模拟任务的产生  
//        new Thread(new TaskProducer()).start();  
//          
//        Thread.sleep(12000);  
//          
//        //启动一个线程者线程，模拟任务的处理  
//        new Thread(new TaskConsumer()).start();  
          
        //主线程休眠  
        //Thread.sleep(Long.MAX_VALUE);  
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2));
        pool.execute(new TaskProducer());
        Thread.sleep(12000);  
        pool.execute(new TaskConsumer());
        
    	ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();    
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间    
        service.scheduleAtFixedRate(new TaskProducer(), 60, 60, TimeUnit.SECONDS);  
    }  
}
