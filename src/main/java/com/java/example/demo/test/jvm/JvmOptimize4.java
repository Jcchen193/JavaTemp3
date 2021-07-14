package com.java.example.demo.test.jvm;

public class JvmOptimize4 {
	
	//static javassist.ClassPool cp = javassist.ClassPool.getDefault();
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i<80000; i++) { 
		     // Class c = cp.makeClass("eu.plumbr.demo.Generated" + i).toClass();
			JvmOptimize4Test jvmt= new JvmOptimize4Test(i);
		    }
	    
	    System.out.println("JvmOptimize4 done");
	  }

	  
	  //java.lang.OutOfMemoryError: Metaspace

	  //before : -Xmx200M -XX:MaxMetaspaceSize=6m
	  
	  //after : -Xmx200M -XX:MaxMetaspaceSize=512m
	
	//-XX:+HeapDumpOnOutOfMemoryError
	 
	//#生成堆文件地址：
	 
	//-XX:HeapDumpPath=D:/standalone/jvmlogs/
}

 class JvmOptimize4Test {
	 
	 public JvmOptimize4Test(int i) {
		 System.out.println("class "+i);
	}
	
}