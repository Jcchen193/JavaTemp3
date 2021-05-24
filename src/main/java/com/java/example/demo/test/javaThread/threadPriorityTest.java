package com.java.example.demo.test.javaThread;

public class threadPriorityTest {
	public static void main(String[] args) {

		System.out.println(Thread.currentThread().getName()+"("+Thread.currentThread().getPriority()+ ")");
	
		MyThread t1=new MyThread("t1"); //thread t1
		MyThread t2=new MyThread("t2"); //thread t2
		MyThread t3=new MyThread("t3"); //thread t2
		t1.setPriority(1); //  set t1的优先级为1
		t2.setPriority(10); // set t2的优先级为10
		t3.setPriority(10); // set t3的优先级为10
		t1.start(); // 启动t1
		t2.start(); // 启动t2
		t3.start(); // 启动t3
	}
}

class MyThread extends Thread{
	private String name;
	public MyThread(String name) {
		this.name = name;
	}
	
	public void run(){
	for (int i=0; i<5; i++) {
		System.out.println(Thread.currentThread().getName() +"("+Thread.currentThread().getPriority()+ ")" +", loop "+i +",name:"+name +"运行");
		}
	}

}