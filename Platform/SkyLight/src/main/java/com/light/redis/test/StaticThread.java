package com.light.redis.test;

public class StaticThread {

	    static {
	        new Thread(new Runnable() {
	            public void run() {

	                while (true) {
	                    try {
	                        Thread.sleep(1000L);
	                    } catch (InterruptedException e) {
	                    	System.out.println("Interrupted : " + e.getMessage());
	                    }
	                    System.out.println("job");
	                }
	            }
	        }).start();
	    }
	    
	    public void get() {
	    	System.out.println("get");
	    }

	    public void set() {
	    	System.out.println("set");
	    } 
	    public static void main(String[] args) {
	    	System.out.println("start...");
	    	
	    	StaticThread s = new StaticThread();
	    	s.get();
	    	s.set();
	    	
	    }
}
