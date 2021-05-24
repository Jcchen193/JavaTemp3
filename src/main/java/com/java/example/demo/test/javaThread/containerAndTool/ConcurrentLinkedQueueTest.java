package com.java.example.demo.test.javaThread.containerAndTool;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentLinkedQueueTest {
    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
    private static int count = 100;//100000
    private static int count2 = 3;
    private static CountDownLatch cd = new CountDownLatch(count2);
    public static void dothis() 
    {
        for(int i = 0; i < count; i++) {
            queue.offer(i);//提供 入队
        }
    }
    public static void main(String[] args) throws InterruptedException {
        long timeStart = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(count2);
        ConcurrentLinkedQueueTest.dothis();
        //      Thread.sleep(1000);
        for(int i = 0; i < count2; i++) 
        {
            es.submit(new Poll());
        }
        cd.await();
        System.out.println("cost time " + (System.currentTimeMillis()-timeStart) + " ms");
        es.shutdown();
    }
    static class Poll implements Runnable {
        public void run() {
            ////size() 是要遍历一遍集合，O(n)时间复杂度，所以尽量要避免用size()而改用isEmpty().
            //while(queue.size() > 0) {
            while(!queue.isEmpty()){
                System.out.println(queue.poll());//剪短/出队列
            }
            cd.countDown();
        }
    }

}