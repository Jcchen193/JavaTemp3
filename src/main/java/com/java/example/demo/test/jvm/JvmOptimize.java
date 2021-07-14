package com.java.example.demo.test.jvm;

import java.util.ArrayList;
import java.util.List;

public class JvmOptimize {
	
	public static void main(String args[]){
		List<byte[]> byteList = new ArrayList<byte[]>();
		byteList.add(new byte[10 * 1024 * 1024]);
		
		System.out.println(byteList.size());
	}

	//堆溢出
	//before: java -Xmn10M -Xms10M -Xmx10M -XX:+PrintGC JvmOptimize
	//after: java -verbose:gc -Xmn10M -Xms50M -Xmx50M -XX:+PrintGC JvmOptimize
}
