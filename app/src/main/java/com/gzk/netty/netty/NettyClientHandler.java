package com.gzk.netty.netty;

import android.util.Log;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {


    private String TAG = NettyClientHandler.class.getSimpleName();

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG,"client channelActive");
        ctx.writeAndFlush("我是客户端 android" + "\r\n");
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Log.e(TAG,"client channelRead0");
        NettyClient.getInstance().handMsg(s);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG,"client channelReadComplete");
        NettyClient.getInstance().removeCurrentReceiveLisenter();
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyClient.getInstance().handErrorMsg(cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
