package com.java.example.demo.test.jvm;

import javassist.ClassPool;

public class JvmOptimize2 {
	
	public static void main(String[] args) throws Exception {
	    for (int i = 0; i < 50000; i++) {
	      generate("eu.plumbr.demo.Generated" + i);//动态生成多个class 文件
	    }
	    
	    System.out.println("generate temp class done");
	  }

	  public static Class generate(String name) throws Exception {
	    ClassPool pool = ClassPool.getDefault();
	    return pool.makeClass(name).toClass();
	  }
	  
	  //java.lang.OutOfMemoryError: PermGen space (before jdk1.8)

	  //before : -Xmx200M -XX:MaxPermSize=1M
	  
	  //after : -Xmx200M -XX:MaxPermSize=512M
}
