package com.java.example.demo.test.netty.chatone;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter{

	
	//通道就绪就触发(客户端 --> 服务器端)
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush(Unpooled.copiedBuffer("hi 服务器",CharsetUtil.UTF_8));
	}
	
	
	//当通道有读取事件时触发（客户端 <-- 服务器端）
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf buf=(ByteBuf) msg;
		System.out.println("客户端接收的消息："+buf.toString(CharsetUtil.UTF_8));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();	
		ctx.channel().close();
	}


}
