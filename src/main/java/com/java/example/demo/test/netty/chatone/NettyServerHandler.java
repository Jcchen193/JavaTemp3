package com.java.example.demo.test.netty.chatone;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter{
	
	//读取数据实际，读取客户端的数据 （服务器端  <-- 客服端）
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf buf=(ByteBuf) msg;
		System.out.println("接收来自客户端的数据:"+buf.toString(CharsetUtil.UTF_8));
	}
	
	//数据读取完毕,发送消息给客户端 （服务器端 --> 客服端）
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush(Unpooled.copiedBuffer("hi 客户端",CharsetUtil.UTF_8));
	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();	
		ctx.channel().close();
	}
}
