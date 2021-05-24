package com.java.example.demo.test.javaThread.containerAndTool;

import java.util.Locale;

public class ThreadLocalTest {

	 private static ThreadLocal<Integer> count = new ThreadLocal<Integer>(){
	        @Override
	        protected Integer initialValue() {
	            return 5;
	        }
	    }; //cout的初始值是0

	    private static ThreadLocal<Locale> language = new ThreadLocal<Locale>(){
	        @Override
	        protected Locale initialValue() {
	            return Locale.CHINA;
	        }
	    };//languag的初始值为CHINA

	    public static void main(String[] args) {

	        Thread[] threads = new Thread[5];

	        for (int i = 0; i < threads.length; i++) {
	            threads[i] = new Thread(() -> {

	                //language
	                Locale lan = language.get();
	                System.out.println(Thread.currentThread().getName() + ".language:" + lan);

	                language.set(Locale.ENGLISH);//每个线程可针对具体场景设置该线程需要的值

	                System.out.println(Thread.currentThread().getName() + ".language:" + language.get());

	                //--------------------------------------------------------------------------------
	                int num = count.get();//每个线程获取count的一个拷贝 
	                num += 5;
	                count.set(num);//进行加5后再保存到线程里面

	                count.get();//业务中需要获取到count的值
	                System.out.println(Thread.currentThread().getName() + ":" + count.get());//再获取到count的值

	                count.remove();
	                language.remove();
	            }, "thread-" + i);
	            threads[i].start();
	        }
	    }
}
