package com.java.example.demo.test.netty.chatone;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
      public static void main(String[] args) throws InterruptedException  {
    	  
		EventLoopGroup e=new NioEventLoopGroup();
		
		try {
			Bootstrap bootstrap=new Bootstrap();
			bootstrap.group(e)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>(){

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ChannelPipeline pipeline=ch.pipeline();
							pipeline.addLast(new NettyClientHandler());
						}
				
						
			});
			
			
			ChannelFuture channelFuture=bootstrap.connect("127.0.0.1",7000).sync();
			channelFuture.channel().closeFuture().sync();
			
		} finally {
			e.shutdownGracefully();
		}
	
		
	}
}
