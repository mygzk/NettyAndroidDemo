package com.netty.demo.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

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
    /** 空闲次数 */
    private int idle_count =1;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE.equals(event.state())) {  //如果读通道处于空闲状态，说明没有接收到心跳命令
                System.out.println("  IdleState  READER_IDLE");
                System.out.println("已经5秒没有接收到客户端的信息了");
                if (idle_count > 2) {
                    System.out.println("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
                idle_count++;
            }else if(IdleState.WRITER_IDLE.equals(event.state())){
                System.out.println("  IdleState  WRITER_IDLE");
            }else {
                System.out.println("  IdleState  ALL_IDLE");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
