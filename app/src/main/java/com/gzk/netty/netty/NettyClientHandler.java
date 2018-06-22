package com.gzk.netty.netty;

import android.util.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {


    private String TAG = NettyClientHandler.class.getSimpleName();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG , " channelActive。。");
        ctx.writeAndFlush("我是客户端 android" + "\r\n");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(TAG + " client receive mag:" + s);
        Log.e(TAG , "client receive mag:" + s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.e(TAG , "exceptionCaught receive mag:" + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
