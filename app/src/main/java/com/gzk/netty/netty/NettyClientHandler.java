package com.gzk.netty.netty;

import android.util.Log;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 客户端请求的心跳命令
     */
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hb_request" + "\r\n",
            CharsetUtil.UTF_8));

    private String TAG = NettyClientHandler.class.getSimpleName();

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG,"client channelActive");
        ctx.writeAndFlush("我是客户端 android" + "\r\n");
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Log.e(TAG, "client channelRead0");
        NettyClient.getInstance().handMsg(s);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "client channelReadComplete");
        NettyClient.getInstance().removeCurrentReceiveLisenter();
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyClient.getInstance().handErrorMsg(cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 空闲次数
     */
    private int idle_count = 1;
    /**
     * 循环次数
     */
    private int fcount = 1;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        //super.userEventTriggered(ctx, evt);
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.WRITER_IDLE.equals(event.state())) {  //如果写通道处于空闲状态,就发送心跳命令
                Log.e(TAG, "IdleState WRITER_IDLE");
              //  System.out.println("循环请求的时间：" + new Date() + "，次数" + fcount);
                if (idle_count <= 3) {   //设置发送次数
                    idle_count++;
                    ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
                } else {
                    System.out.println("不再发送心跳请求了！");
                }
                fcount++;
            } else if (IdleState.READER_IDLE.equals(event.state())) {
                Log.e(TAG, "IdleState READER_IDLE");
            } else {
                Log.e(TAG, "IdleState ALL_IDLE");
            }
        }
    }
}
