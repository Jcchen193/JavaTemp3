package com.java.example.demo.test.javaThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

public class ThreadAndThreadPoolTest  {
 
	public static List<String> getThreadByThread() {
		//1.继承Thread
		List<String> threadList =new ArrayList<String>();
		Thread thread = new Thread() {
		  
		 public void run() {
		 System.out.println("继承Thread");
		 threadList.add("继承Thread");
		 super.run();
		 }
		 };
		 thread.start();
		return threadList;
	}

	 
	public static List<String> getThreadByRunable() {
		//2.实现runable接口
		List<String> threadList =new ArrayList<String>();
		Thread thread1 = new Thread(new Runnable() {
			  
			 public void run() {
			 System.out.println("实现runable接口");
			 threadList.add("实现runable接口");
		 }
		 });
		 thread1.start();
		 
		return threadList;
	}

	 
	public static List<String> getThreadByCallable() {
		//3.实现callable接口
		List<String> threadList =new ArrayList<String>();
		ExecutorService service = Executors.newSingleThreadExecutor();
		java.util.concurrent.Future future = service.submit(new Callable() {
		  
		 public String call() throws Exception {
			 threadList.add( "通过实现Callable接口");
			 return "通过实现Callable接口";
		 }
		 });
		 try {
		 Object result = future.get();
		 System.out.println(result);
		 } catch (InterruptedException e) {
		 e.printStackTrace();
		 } catch (ExecutionException e) {
		 e.printStackTrace();
		 }
		 return threadList;
	}

	 
	public static List<String> getMultipleThreadByThread() {
		
		List<String> threadList = new ArrayList<String>();
		//1.通过实现runnable接口并将其作为Thread对象的参数创建并发
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new Runnable() {
				
				 
				public void run() {
					System.out.println("我是通过过实现runnable接口并将其作为Thread对象的参数创建并发" + Thread.currentThread().getName() + ".");
					threadList.add("我是通过过实现runnable接口并将其作为Thread对象的参数创建并发" + Thread.currentThread().getName() + ".");
				}
			});
			t.start();
			//t.interrupted();
			//t.stop();
		}
		
		
		//System.out.println("------------------------------");
		
		//2.创建线程池创建并发
//		ExecutorService executorService = Executors.newCachedThreadPool();//创建一个可缓存的线程池
//		// Executors.newFiexedThreadPool(int num); // 创建固定数目线程的线程池
//		//Executors.newSingleThreadExecutor(); //创建单线程的线程池
//
//		for (int i = 0; i < 10; i++) {
//			Runnable r = new Runnable() {
//
//				public void run() {
//					System.out.println("我是创建线程池创建并发" + Thread.currentThread().getName() + ".");
//					threadList.add("我是创建线程池创建并发" + Thread.currentThread().getName() + ".");
//				}
//			};
//			executorService.execute(r);//调用该方法可以重用之前的线程
//		}
//		executorService.shutdown();
		
		//System.out.println("------------------------------");
		return threadList;

	}

	public static List<String> getThreadPoolExecutorThread() {
		List<String> threadList = new ArrayList<String>();
		
		//35直接调用ThreadPoolExecutor的构造函数来创建线程池
		ExecutorService executor = new ThreadPoolExecutor(10, 10,
		        60L, TimeUnit.SECONDS,
		        new ArrayBlockingQueue(10));
		for (int i = 0; i < 10; i++) {
			Runnable r = new Runnable() {
				 
				public void run() {
					System.out.println("我是调用ThreadPoolExecutor的构造函数创建线程池" + Thread.currentThread().getName() + ".");
					threadList.add("我是调用ThreadPoolExecutor的构造函数创建线程池" + Thread.currentThread().getName() + ".");
				}
			};
			executor.execute(r);
		}
		executor.shutdown();
		
		return threadList;
		
	}
	
	public static List<String> getScheduledRhreadPoolExecutorThread() throws InterruptedException, ExecutionException{
		List<String> threadList = new ArrayList<String>();
		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
		 System.out.println("start: " + System.currentTimeMillis());

	         //1执行一个无返回值任务，5秒后执行，只执行一次
	        scheduledThreadPoolExecutor.schedule(() -> {
	            System.out.println("spring: " + System.currentTimeMillis());
	        }, 5, TimeUnit.SECONDS);

	        //2执行一个有返回值任务，5秒后执行，只执行一次
	        ScheduledFuture<String> future = scheduledThreadPoolExecutor.schedule(() -> {
	            System.out.println("inner summer: " + System.currentTimeMillis());
	            return "outer summer: ";
	        }, 5, TimeUnit.SECONDS);
	        //3 获取返回值
	        System.out.println(future.get() + System.currentTimeMillis());

	        //4 按固定频率执行一个任务，每2秒执行一次，1秒后执行
	        //5 任务开始时的2秒后
	        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
	            System.out.println("autumn: " + System.currentTimeMillis());
	            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
	        }, 1, 2, TimeUnit.SECONDS); //scheduleAtFixedRate:处理和未来任务一样，只是会装饰成另外一个任务再去执行

	        //6 按固定延时执行一个任务，每延时2秒执行一次，1秒执行
	        //7 任务结束时的2秒后
	        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
	            System.out.println("winter: " + System.currentTimeMillis());
	            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
	        }, 1, 2, TimeUnit.SECONDS); 
		
		return threadList;
	}
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
	    // getThreadByThread();
		getScheduledRhreadPoolExecutorThread();
	}
}
