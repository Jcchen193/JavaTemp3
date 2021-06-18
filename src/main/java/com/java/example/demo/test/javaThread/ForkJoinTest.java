package com.java.example.demo.test.javaThread;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	// 阈值
	private static final int THRESHOLD = 2;
	private int start;
	private int end;
	
	public ForkJoinTest(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Integer compute() {
		int sum = 0;
		// 判断任务是否足够小
		boolean canCompute = (end - start) <= THRESHOLD;
		if (canCompute) {
			// 如果小于阈值，就进行运算
			for (int i = start; i <= end; i++) {
			 sum += i;
			 }
			 System.out.println(Thread.currentThread().getName()+" A sum:"+sum);
		} else {
			// 如果大于阈值，就再进行任务拆分
			int middle = (start + end) / 2;
			 System.out.println(Thread.currentThread().getName()+" start:"+start+",middle:"+middle+",end:"+end);
			 ForkJoinTest leftTask = new ForkJoinTest(start, middle);
			 ForkJoinTest rightTask = new ForkJoinTest(middle + 1, end);
			// 执行子任务
			 leftTask.fork();
			 rightTask.fork();
			// 等待子任务执行完，并得到执行结果
			int leftResult = leftTask.join();
			int rightResult = rightTask.join();
			// 合并子任务
			 sum = leftResult + rightResult;
			 System.out.println(Thread.currentThread().getName()+" B sum:"+sum);
		 }
		return sum;
		
	}
	
	// leftTask.fork()方法 ： fork方法内部会先判断当前线程是否是ForkJoinWorkerThread的实例，如果满足条件，则将task任务push到当前线程所维护的双端队列中。
		//在push方法中，会调用ForkJoinPool的signalWork方法唤醒或创建一个工作线程来异步执行该task任务。
		//通过doJoin方法返回的任务状态来判断，如果不是NORMAL，则抛异常
		//先查看任务状态，如果已经完成，则直接返回任务状态；如果没有完成，则从任务队列中取出任务并执行。
	
	
	
	public static void main(String[] args) {
	 ForkJoinPool forkJoinPool = new ForkJoinPool();// 这边也可以指定一个最大线程数
	 ForkJoinTest task = new ForkJoinTest(1, 10);
	 // 执行一个任务
	 Future<Integer> result = forkJoinPool.submit(task);
	 try {
		 System.out.println(result.get());
	 } catch (InterruptedException e) {
	 e.printStackTrace();
	 } catch (ExecutionException e) {
	 e.printStackTrace();
	 }
	 
	 
	}
	
}

