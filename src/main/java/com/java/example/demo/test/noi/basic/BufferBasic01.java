package com.java.example.demo.test.noi.basic;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import io.netty.handler.codec.AsciiHeadersEncoder.NewlineType;


public class BufferBasic01 {
	public static void main(String[] args) {
		
		String string="Hello";
		
		//Buffer 的使用 (简单说明)
		//1.创建一个 Buffer, 大小为 10 		
		ByteBuffer buffer=ByteBuffer.allocate(10);
				
		System.out.println("------------allocate(10)----------------");
		Message(buffer);

		
		//2.Put（）放入string数据
		System.out.println("----------------Put()-------------------");
		buffer.put(string.getBytes());					
		Message(buffer);

		
		//3.切换读写模式
		buffer.flip();
		System.out.println("----------------flip()-------------------");
		Message(buffer);

		
		//4.Get()取出数据
		System.out.println("----------------Get()-------------------");
		byte[] dst=new byte[buffer.limit()];
		buffer.get(dst, 0, 2);
		System.out.println(new String(dst,0,2));
		//System.out.println((char)buffer.get());
		Message(buffer);

		
		//5.重复读取rewind()
		System.out.println("----------------rewind()-------------------");
		buffer.rewind();
		Message(buffer);
		System.out.println((char)buffer.get());
		
		
		//5.compact()从缓冲区释放部分数据
		System.out.println("----------------Compact()-------------------");
		buffer.compact();
		Message(buffer);
		
		
}
	
	public static void Message(ByteBuffer buffer){
		System.out.println("Pos="+buffer.position()+" limit= "+buffer.limit()+" Capacity= "
	  +buffer.capacity());
		
	}
	
	
	
}
