package com.java.example.demo.test.javaThread;

public class VolatileTest {
	

    public static void main(String[] args) {  
        try {  
            //a通过该构造函数可以获取实时时钟的当前时间  
            Volatile vol = new Volatile();  

            //a稍停100ms，以让实时时钟稍稍超前获取时间，使print（）中创建的消息打印的时间值大于0  
            Thread.sleep(100);    

            Thread t = new Thread(vol);  
            t.start();  

            //a休眠100ms，让刚刚启动的线程有时间运行  
            Thread.sleep(100);    
            //workMethod方法在main线程中运行  
            vol.workMethod();  
        } catch ( InterruptedException x ) {  
            System.err.println("one of the sleeps was interrupted");  
        }  
    }  
}

class Volatile extends Object implements Runnable{
	//value变量没有被标记为volatile  
    private int value;    
    //missedIt变量被标记为volatile  
    private volatile boolean missedIt;  
    //creationTime不需要声明为volatile，因为代码执行中它没有发生变化  
    private long creationTime;   

    public Volatile() {  
        value = 10;  
        missedIt = false;  
        //11a 获取当前时间，亦即调用Volatile构造函数时的时间  
        //creationTime = System.currentTimeMillis();  
        
        print("in Volatile() - just set value=" + value);  
        print("in Volatile() - just set missedIt=" + missedIt);  
    }  

    public void run() {  
        print("entering run()");  

        //2a循环检查value的值是否不同  
        while ( value < 20 ) {  
            //a如果missedIt的值被修改为true，则通过break退出循环  
            if  ( missedIt ) {  
                //3q 进入同步代码块前，将value的值赋给currValue  
                int currValue = value;  
                //4a 在一个任意对象上执行同步语句，目的是为了让该线程在进入和离开同步代码块时，  
                //5a 将该线程中的所有变量的私有拷贝与共享内存中的原始值进行比较，  
                //6a 从而发现没有用volatile标记的变量所发生的变化  
                Object lock = new Object();  
                synchronized ( lock ) {  
                    //a不做任何事  
                }  
                //a离开同步代码块后，将此时value的值赋给valueAfterSync  
                int valueAfterSync = value;  
                print("in run() - see value=" + currValue +", but rumor has it that it changed!");  
                print("in run() - valueAfterSync=" + valueAfterSync);  
                break;   
            }  
        }  
        print("leaving run()");  
    }  

    public void workMethod() throws InterruptedException {  
        print("entering workMethod()");  
        print("in workMethod() - about to sleep for 2 seconds");  
        Thread.sleep(2000);  
        //a仅在此改变value的值  
        value = 50;  
        print("in workMethod() - just set value=" + value);  
        print("in workMethod() - about to sleep for 5 seconds");  
        Thread.sleep(5000);  
        //a仅在此改变missedIt的值  
        missedIt = true;  
        print("in workMethod() - just set missedIt=" + missedIt);  
        print("in workMethod() - about to sleep for 3 seconds");  
        Thread.sleep(3000);  
        print("leaving workMethod()");  
    }  

/* 
*该方法的功能是在要打印的msg信息前打印出程序执行到此所化去的时间，以及打印msg的代码所在的线程 
*/  
    private void print(String msg) {  
        //a使用java.text包的功能，可以简化这个方法，但是这里没有利用这一点  
//        long interval = System.currentTimeMillis() - creationTime;  
//        String tmpStr = "    " + ( interval / 1000.0 ) + "000";       
//        int pos = tmpStr.indexOf(".");  
//        String secStr = tmpStr.substring(pos - 2, pos + 4);  
//        String nameStr = "        " + Thread.currentThread().getName();  
//        nameStr = nameStr.substring(nameStr.length() - 8, nameStr.length());      
//        System.out.println(secStr + " " + nameStr + ": " + msg);  
    	System.out.println("msg:"  + msg);  
    }  
}
