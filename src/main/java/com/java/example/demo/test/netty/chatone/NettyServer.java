package com.java.example.demo.test.netty.chatone;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
   public static void main(String[] args) throws InterruptedException {
	   
	    EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		
		try {

			ServerBootstrap serverBootstrap=new ServerBootstrap();
			serverBootstrap.group(bossGroup,workerGroup)
							.channel(NioServerSocketChannel.class)
							.option(ChannelOption.SO_BACKLOG,128)
							.childOption(ChannelOption.SO_KEEPALIVE, true)
							.childHandler(new ChannelInitializer<SocketChannel>(){

								@Override
								protected void initChannel(SocketChannel ch) throws Exception {
									// TODO Auto-generated method stub
									ChannelPipeline pipeline=ch.pipeline();
									pipeline.addLast(new NettyServerHandler());
								}
		
				  
			});
			System.out.println("服务器启动了....");
			 ChannelFuture channelFuture= serverBootstrap.bind(7000).sync();
			 
			 channelFuture.channel().closeFuture().sync();
			
		} finally {
			// TODO: handle exception
				bossGroup.shutdownGracefully();
				workerGroup.shutdownGracefully();
		} 
		
		
}
}
