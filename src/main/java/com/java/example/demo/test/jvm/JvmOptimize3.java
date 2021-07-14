package com.java.example.demo.test.jvm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JvmOptimize3 {
	
	public static void main(String args[]){
		 Map map = new HashMap<>();
		 Random r = new Random();
	        while (true) {
	            map.put(r.nextInt(), "value");
	        }
	}

	//GC Overhead limit exceeded
	//before : -Xmx5m -XX:+UseParallelGC -XX:+PrintGC
	//after : -Xmx5m -XX:-UseGCOverheadLimit
	
//	不能真正地解决问题，只能推迟一点 out of memory 错误发生的时间，到最后还得进行其他处理。指定这个选项, 会将原来的 java.lang.OutOfMemoryError: GC overhead limit exceeded 错误掩盖，变成更常见的 java.lang.OutOfMemoryError: Java heap space 错误消息。
//
//	需要注意: 有时候触发 GC overhead limit 错误的原因, 是因为分配给JVM的堆内存不足。这种情况下只需要增加堆内存大小即可。
//
//	在大多数情况下, 增加堆内存并不能解决问题。例如程序中存在内存泄漏, 增加堆内存只能推迟产生 java.lang.OutOfMemoryError: Java heap space 错误的时间。
//
//	当然, 增大堆内存, 还有可能会增加 GC pauses 的时间, 从而影响程序的 吞吐量或延迟。
}
