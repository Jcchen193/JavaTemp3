package com.java.example.demo.test.javaThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentToolTest1 {
	
	//CountDownLatch use ，1裁判员 维护 6 个运动员
	private static CountDownLatch startSignal = new CountDownLatch(1);
	
	private static CountDownLatch endSignal = new CountDownLatch(6);//
	public static void main(String[] args) throws InterruptedException {
	 ExecutorService executorService = Executors.newFixedThreadPool(6);
	 for (int i = 0; i < 6; i++) {
	 executorService.execute(() -> {
	 try {
	 System.out.println(Thread.currentThread().getName() + " 运动员等待裁判员响哨！！！");
	 startSignal.await();
	 System.out.println(Thread.currentThread().getName() + "正在全力冲刺");
	 endSignal.countDown();
	 System.out.println(Thread.currentThread().getName() + " 到达终点");
	 } catch (InterruptedException e) {
	 e.printStackTrace();
	 }
	 });
	 }
	 System.out.println("裁判员发号施令啦！！！");
	 startSignal.countDown();
	 endSignal.await();
	 System.out.println("所有运动员到达终点，比赛结束！");
	 executorService.shutdown();
	}
}
