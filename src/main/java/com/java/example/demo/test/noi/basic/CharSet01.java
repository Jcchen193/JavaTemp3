package com.java.example.demo.test.noi.basic;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.junit.Test;

public class CharSet01 {

	@Test
	public void Char() throws IOException {
	
		Charset cs1=Charset.forName("GBK");
		
		//1.获取编码器（消息对象转化成字节流）
		CharsetEncoder ce=cs1.newEncoder();
		
		//2.获取解码器(字节流转换成消息对象)
		CharsetDecoder cd=cs1.newDecoder();
		
		
		CharBuffer buf=CharBuffer.allocate(1024);
		buf.put("学习Java NIO中的编码器和解码器");
		buf.flip();
		
		
		System.out.println("-----------以\"GBK\"编码----------------");
		//编码,以"GBK"编码
		ByteBuffer buf1=ce.encode(buf);
		for(int i=0;i<buf1.limit();i++) {
			System.out.print(buf1.get()+" ");
		}
       System.out.println();
       
       
		System.out.println("---------以\"GBK\"解码------------------");		
		//解码,以"GBK"解码
		buf1.flip();
		CharBuffer buf2=cd.decode(buf1);
		System.out.println(buf2.toString());
		
		
		
		System.out.println("------------以\"UTF-8\"解码---------------");	
		//解码，以"UTF-8"解码
		buf1.flip();
		CharsetDecoder cdd=Charset.forName("UTF-8").newDecoder();
		CharBuffer cbuf=cdd.decode(buf1);
		System.out.println(cbuf.toString());
		
		
	}
}
