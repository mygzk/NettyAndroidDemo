package com.gzk.netty.netty.server;

import android.util.Log;

import com.gzk.netty.netty.NettyConstant;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
String TAG = NettyServerHandler.class.getSimpleName();
    private String recermsg="";


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       // super.channelRead(ctx, msg);
      String  body = (String) msg;
        Log.e(TAG,"msg:"+body);
        body = "reply: "+body+ NettyConstant.MAG_SEPARATOR;
        ctx.writeAndFlush(body);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
