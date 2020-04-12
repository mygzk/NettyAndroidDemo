package com.gzk.netty.netty.client;

import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    private boolean mReadOk = false;
    private boolean mNeedHeaed = true;
    /**
     * 空闲次数
     */
    private int idle_count = 1;
    /**
     * 客户端请求的心跳命令 内容应与服务端协议
     */
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hb_request" + "\r\n",
            CharsetUtil.UTF_8));

    private String TAG = NettyClientHandler.class.getSimpleName();


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Log.e(TAG, "client channelRead0");
        System.out.println("NettyClientHandler，client receive msg:" + s);
        mReadOk = true;
        NettyClient.getInstance().handMsg(s);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "client channelReadComplete");
        ctx.flush();
        if (mReadOk) {
            mReadOk = false;
            NettyClient.getInstance().removeCurrentReceiveLisenter();
        } else {
            ctx.read();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.e(TAG, "client exceptionCaught");
        NettyClient.getInstance().handErrorMsg(cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * userEventTriggered
     1）readerIdleTime：为读超时时间（即测试端一定时间内未接受到被测试端消息）

     2）writerIdleTime：为写超时时间（即测试端一定时间内向被测试端发送消息）

     3）allIdleTime：所有类型的超时时间
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.WRITER_IDLE.equals(event.state())) {  //如果写通道处于空闲状态,就发送心跳命令
                if (mNeedHeaed) {
                    ChannelFuture channelFuture = ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
                    if (!channelFuture.channel().isActive()) {
                        //服务端断开连接
                    }
                } else {
                    //todo

                }
            }
        }
    }

    /**
     * 设置是否需要心跳
     *
     * @param needHeaed
     */
    public void setNeedHeaed(boolean needHeaed) {
        this.mNeedHeaed = needHeaed;
    }
}
