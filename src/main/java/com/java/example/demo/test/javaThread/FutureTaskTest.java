package com.java.example.demo.test.javaThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
	
	//FutureTask执行多任务计算的使用场景
	public static void main(String[] args) {
		Task task = new Task();// 新建异步任务
		FutureTask<Integer> future = new FutureTask<Integer>(task) {
			//1 异步任务执行完成，回调
			@Override
			protected void done() {
				try {
					System.out.println("future.done():" + get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		//2 创建线程池（使用了预定义的配置）
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(future);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//3 可以取消异步任务
		 //future.cancel(true);

		try {
			//4 阻塞，等待异步任务执行完毕-获取异步任务的返回值
			System.out.println("future.get():" + future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

		//5 异步任务
		static class Task implements Callable<Integer> {
			//6 返回异步任务的执行结果
			@Override
			public Integer call() throws Exception {
				int i = 0;
				for (; i < 10; i++) {
					try {
						System.out.println(Thread.currentThread().getName() + "_" + i);
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return i;
			}
		}
}


