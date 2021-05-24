package com.java.example.demo.test.javaThread;

import com.java.example.demo.test.javaThread.entiryForTest.Deadlock;

public class DeadlockTest {
	
	    public static void main(String[] args) {  
	        final Deadlock obj1 = new Deadlock("obj1");  
	        final Deadlock obj2 = new Deadlock("obj2");  

	        Runnable runA = new Runnable() {  
	                public void run() {  
	                    obj1.checkOther(obj2);  
	                }  
	            };  

	        Thread threadA = new Thread(runA, "threadA");  
	        threadA.start();  

	        try { Thread.sleep(200); }   
	        catch ( InterruptedException x ) { }  

	        Runnable runB = new Runnable() {  
	                public void run() {  
	                    obj2.checkOther(obj1);  
	                }  
	            };  

	        Thread threadB = new Thread(runB, "threadB");  
	        threadB.start();  

	        try { Thread.sleep(5000); }   
	        catch ( InterruptedException x ) { }  

	        obj1.threadPrint("finished sleeping");  

	        obj1.threadPrint("about to interrupt() threadA");  
	        threadA.interrupt();  

	        try { Thread.sleep(1000); }   
	        catch ( InterruptedException x ) { }  

	        obj1.threadPrint("about to interrupt() threadB");  
	        threadB.interrupt();  

	        try { Thread.sleep(1000); }   
	        catch ( InterruptedException x ) { }  

	        obj1.threadPrint("did that break the deadlock?");  
	    }  
}
