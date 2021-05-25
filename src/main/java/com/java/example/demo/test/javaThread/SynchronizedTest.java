package com.java.example.demo.test.javaThread;

public class SynchronizedTest {
	public static void main(String[] args) {
		MyMethod my = new MyMethod();
		newThread t1 = new newThread(my);
		newThread01  t2 = new newThread01(my);
		t1.start();
		t2.start();
	}
	
}

class MyMethod{
	synchronized public void testMethodA(){
		for(int i=1;i<100;i++){
			System.out.println(i);
		}
	}
	synchronized public void testMethodB(){
		for(int i=100;i>0;i--)
		{
			System.out.println(i);
		}
	}
}

class newThread extends Thread{
	private MyMethod myMethod;
 
	public newThread(MyMethod myMethod) {
		this.myMethod = myMethod;
	}
 
	public void run() {
		myMethod.testMethodA();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class newThread01 extends Thread{
	private MyMethod myMethod;
 
	public newThread01(MyMethod myMethod) {
		this.myMethod = myMethod;
	}
	public void run() {
		
		myMethod.testMethodB();
	}
}