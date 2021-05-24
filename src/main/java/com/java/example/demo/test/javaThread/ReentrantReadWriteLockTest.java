package com.java.example.demo.test.javaThread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
    
    public static void main(String[] args) { 
    	MyLock myLock = new MyLock();
        for (int i=0;i<5;i++){
            final int temp = i;
            new Thread(()->{
            	myLock.put(temp+"",temp+"");
           },String.valueOf(i)).start();
        }
        for (int i=0;i<5;i++){
            final int temp = i;
            new Thread(()->{
            	myLock.get(temp+"");
           },String.valueOf(i)).start();
        }

    }

}

class MyLock{//资源类
    private volatile Map<String ,Object> map= new HashMap<>();//模拟缓存 volatile可见性
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
 
    //private ReentrantLock lock = new ReentrantLock();
    //1a传统的锁不足以满足多个线程读，ReentrantLock只能一个线程操作读或者写
 
    public void put(String key,Object value){
        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在写入"+key);
            try{TimeUnit.MILLISECONDS.sleep(300);}catch(Exception e){e.printStackTrace();}
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	rwLock.writeLock().unlock();
        }
    }
    //a1读共享且时间段不一样
    public void get(String key){
        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在读取");
            map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成");
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	rwLock.readLock().unlock();
        }
    }
 
}
