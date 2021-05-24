package com.java.example.demo.test.javaThread.containerAndTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentContainerTest1 {
	
	//ConcurrentHashMap
	public static List<String> getConcurrentMapTest() {
		
		List<String> threadList = new ArrayList<String>();
		
		ConcurrentHashMap map1 = new ConcurrentHashMap();
		map1.put("map1key", "map1vaue1");
		
		System.out.println("map1 ConcurrentMap- key: " + map1.get("map1key") );
		
		
		ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
		//ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>(2);

		map.put("k1", "v1");

		map.put("k2", "v2");

		//map.put("k1", "v2");

		map.putIfAbsent("k1", "vv1"); //if map miss key k1 then put vv1 as value of k1

		for(Map.Entry me : map.entrySet()) {
			System.out.println("ConcurrentMap- key: " + me.getKey() + ",value: " + me.getValue());
			threadList.add("ConcurrentMap -key: " + me.getKey() + ",value: " + me.getValue());
		}
		
		return threadList;

		}

	
	//CopyOnWriteArrayList
	public static List<String> getCopyOnWriteTest() {
		List<String> threadList = new ArrayList<String>();
		CopyOnWriteArrayList copylist = new CopyOnWriteArrayList<>();

		copylist.add("1");

		copylist.add("2");

		for(int i=0;i<copylist.size();i++) {
			System.out.println(copylist.get(i));
			threadList.add("copyOnWrite:" + copylist.get(i));
		}

		return threadList;
	}
	
	public static void main(String[] args) throws InterruptedException {
		getConcurrentMapTest();
	}
}
