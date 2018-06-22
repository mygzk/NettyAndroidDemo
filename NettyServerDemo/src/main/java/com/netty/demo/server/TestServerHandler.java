package com.netty.demo.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<String> {
    String TAG_LINE = "line.separator";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("server receive msg:" + s);

        channelHandlerContext.writeAndFlush("[reply]:" + s + System.getProperty(TAG_LINE));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      /* cause.printStackTrace();
       ctx.close();*/

        Channel incoming = ctx.channel();
        if (!incoming.isActive())
            System.out.println("SimpleClient:" + incoming.remoteAddress()
                    + "异常");

        cause.printStackTrace();
        ctx.close();
    }
}
