package com.netty.demo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    String TAG_LINE = "line.separator";
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler channelActive ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler channelInactive ");
        super.channelInactive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler handlerAdded ");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler handlerRemoved ");
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      String in = (String) msg;


        System.out.println("EchoServerHandler channelRead s:"+in);
        ctx.write(in);

        in = "reoly:" + in+System.getProperty(TAG_LINE);
        //ByteBuf resp = Unpooled.copiedBuffer(in.getBytes());
        ctx.writeAndFlush(in);
    }

  /*  @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
       *//* ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);*//*
      //  ctx.writeAndFlush("你好 我是服务端....");
        ctx.flush();
    }*/

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
