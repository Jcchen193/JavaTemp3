package com.java.example.demo.test.noi.ChatGroup;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;




public class TestNonBlockingGroupClient {

	/*
	 * 群聊NIO实例服务端
	 * 1.发送消息到服务端
	 * 2.接收来自服务端的消息
	 * */
	
	//属性
	private final int port=6667;
	private final String host="127.0.0.1";
	private SocketChannel socketChannel;
	private Selector selector;
	private String username;//提示客户端IP地址
	
	//构造器，初始化
	public TestNonBlockingGroupClient() throws IOException { 
	// TODO Auto-generated constructor stub
			
			/**
			 * 1.获取SocketChannel的host和port
			 * 2.切换非阻塞
			 * 3.获取选择器
			 * 4.注册通道
			 * 5.发送数据给服务端
			 */
			socketChannel= SocketChannel.open(new InetSocketAddress("127.0.0.1",port));			
			selector=Selector.open();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			username=socketChannel.getLocalAddress().toString().substring(1);
		    System.out.println(username+" is ok...");
						  
    }
	
	
	//发送数据给服务端
	public void SendMessageToServerice(String info) {
		
      info=username+" 说： "+info;
     try {
		socketChannel.write(ByteBuffer.wrap(info.getBytes()));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
}
	
	//接收来自服务端的数据
	 public void AcceptMessageFromServerice(){
			/**
			 * 1.是否有就绪的通道
			 * 2.集合遍历所有SelectionKey
			 * 3.注册SocketChannel
			 * 4.SelectionKey反向获取客户端Channel
			 * 5.读取服务端数据
			 * 6.SelectionKey.remove()
			 */
		 
		try {
			if(selector.select()>0) {
				Iterator<SelectionKey> it= selector.selectedKeys().iterator();
				while(it.hasNext()) {
					SelectionKey key=it.next();
					if(key.isReadable()) {
						SocketChannel ssChannel=(SocketChannel)key.channel();
						ByteBuffer buffer=ByteBuffer.allocate(1024);					
						
						    ssChannel.read(buffer);
							String msg=new String(buffer.array());
							System.out.println(msg.trim());
						
						
					}
				
				it.remove();//删除当前的selectionKey, 防止重复操作
			}
		}else {
			//System.out.println("没有可以用的通道...");
		}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	 
	 public static void main(String[] args) throws IOException{
		 
		 //启动客户端
		TestNonBlockingGroupClient TestNonBlockingGroupClient=new TestNonBlockingGroupClient();//
		 
		 //启动一个线程,每3s读取从服务器端发送到客户端的数
		 new Thread() {
			 public void run() {
				 
				 while(true) {
					 TestNonBlockingGroupClient.AcceptMessageFromServerice();			 
					 try {
						Thread.currentThread();
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				
			 }
		 }.start();
		 
		 
		 //发送数据到服务器端
		 Scanner scanner=new Scanner(System.in);
		 while(scanner.hasNextLine()) {
			String s=scanner.next();
			TestNonBlockingGroupClient.SendMessageToServerice(s);

		}
	}	
	
}
