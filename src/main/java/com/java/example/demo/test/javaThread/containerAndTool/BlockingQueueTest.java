package com.java.example.demo.test.javaThread.containerAndTool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.java.example.demo.test.javaThread.entiryForTest.Consumer;
import com.java.example.demo.test.javaThread.entiryForTest.Producer;

public class BlockingQueueTest {
	
	public static void main(String[] args) throws Exception {

        BlockingQueue queue = new ArrayBlockingQueue(1024);

        Producer producer = new Producer(queue);//生产者
        Consumer consumer = new Consumer(queue);//消费者
        
		new Thread(producer ).start();
		new Thread(consumer).start();

        Thread.sleep(4000);
    }
	
	

}
