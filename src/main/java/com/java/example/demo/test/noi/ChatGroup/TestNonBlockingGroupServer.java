package com.java.example.demo.test.noi.ChatGroup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;



public class TestNonBlockingGroupServer {

	/*
	 * 群聊NIO实例服务端
	 * 1.接收客户端消息，并转发消息到客户端，实现上线，离线
	 * */
	
	//属性
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private final int port= 6667;
	
	//构造器
	//初始化
	public TestNonBlockingGroupServer() {
		
		/*
		 * 1.获取ServerSocketChannel
		 * 2.获取并绑定端口，jdk1.7以前用Socket（）.bind();jdk1.7后用bind（）
		 * 3.获取选择器
		 * 4.切换非阻塞
		 * 5.通道注册到选择器*/
		
		try {
			serverSocketChannel=ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(port));
			selector=Selector.open();		
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	
	//接收来自客户端的消息
	public void ClientMessage(){
		
		/**
		 * 1.是否有就绪的通道
		 * 2.集合遍历所有SelectionKey
		 * 3.连接客户端
		 * 4.注册SocketChannel
		 * 5.SelectionKey反向获取客户端Channel
		 * 6.读取客户端数据
		 * 7.SelectionKey.remove()
		 */
		try {
			while (true) {
				if(selector.select()>0) {
					 Iterator<SelectionKey> it= selector.selectedKeys().iterator();
					 
					 while(it.hasNext()) {
						 SelectionKey key=it.next();
						 
						 if(key.isAcceptable()) {
							
							SocketChannel sChannel=serverSocketChannel.accept();
							sChannel.configureBlocking(false);
							sChannel.register(selector, SelectionKey.OP_READ);
							System.out.println(sChannel.getRemoteAddress() +" 上线 ");
						 }
						 if(key.isReadable()) {
							 
							 //读取客户端发送的数据
							 ReadClientMessage(key);
							 
						 }
						 
						 it.remove();
					 }
					
					 
					}else {
					System.out.println("等待....");
				}
				
			}
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	//读取客户端发送的数据
	public void ReadClientMessage(SelectionKey key) {		
		
	SocketChannel ssChannel=null;	
		
      try {
			ssChannel= (SocketChannel) key.channel();
						
			ByteBuffer buf=ByteBuffer.allocate(1024);
			
			while(ssChannel.read(buf)>0) {
				buf.flip();
				
				String msg=new String(buf.array());
				System.out.println(" from 客户端( "+ssChannel.getRemoteAddress()+"):" +msg);
				
				
				//向其他客户端发送消息，排除自己
				SendClientMessage(msg,ssChannel);
				buf.clear();
			}
					
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				System.out.println(ssChannel.getRemoteAddress()+"  离线了 ");
				//取消注册
				key.cancel();
				//关闭通道
				ssChannel.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block			
				e1.printStackTrace();
			}

		}
	}
	
	
	
	//向其他客户端发送消息，排除自己
	public void SendClientMessage(String msg,SocketChannel self) {
		
		/**
		 * 1.遍历所有注册到Selector上的SocketChannel -->selector.keys()
		 * 2.写入到通道中传给客户端
		 */
           try {
				System.out.println("服务器端转发消息.....");
						
				for(SelectionKey key: selector.keys()) {
				Channel tagetChannel=key.channel();
				
				if(tagetChannel instanceof SocketChannel &&tagetChannel != self) {
				 SocketChannel dest=(SocketChannel)tagetChannel;
				  ByteBuffer buf2=ByteBuffer.wrap(msg.getBytes());
				  dest.write(buf2);
					
			 }
		}

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	public static void main(String[] args) {
		//启动服务端
		TestNonBlockingGroupServer server=new TestNonBlockingGroupServer();
		server.ClientMessage();
	}
	

	
}
