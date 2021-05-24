package com.java.example.demo.test.javaThread.entiryForTest;

import java.util.concurrent.BlockingQueue;

//s消费者
	public class Consumer implements Runnable{

	    protected BlockingQueue queue = null;

	    public Consumer(BlockingQueue queue) {
	        this.queue = queue;
	    }

	    public void run() {
	        try {
	            System.out.println(queue.take());
	            System.out.println(queue.take());
	            System.out.println(queue.take());
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
