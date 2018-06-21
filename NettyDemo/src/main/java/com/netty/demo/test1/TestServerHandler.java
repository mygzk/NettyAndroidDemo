package com.netty.demo.test1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<String> {
    String TAG_LINE = "line.separator";
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("server receive msg:"+s);

        channelHandlerContext.writeAndFlush("我是服务端"+System.getProperty(TAG_LINE));

    }
}
