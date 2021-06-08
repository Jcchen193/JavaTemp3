package com.java.example.demo.test.netty.http;

import java.net.URI;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	
	

	//channelRead0 读取客户端数据
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		// TODO Auto-generated method stub
		if(msg instanceof HttpRequest) {
			System.out.println("pipeline hashcode" + ctx.pipeline().hashCode() + " TestHttpServerHandler hash=" +
			this.hashCode());
			System.out.println("msg 类型=" + msg.getClass());
			System.out.println("客户端地址" + ctx.channel().remoteAddress());
			//获取到
			HttpRequest httpRequest = (HttpRequest) msg;
			//获取 uri, 过滤指定的资源
			URI uri = new URI(httpRequest.uri());
			if("/favicon.ico".equals(uri.getPath())) {
			System.out.println("请求了 favicon.ico, 不做响应");
			return;
			}
			//回复信息给浏览器 [http 协议]
			ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
			//构造一个 http 的相应，即 httpresponse
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
			//将构建好 response 返回
			ctx.writeAndFlush(response);
	}
}
}


