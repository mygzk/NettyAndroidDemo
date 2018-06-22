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
        System.out.println("server receive msg:"+s);

        channelHandlerContext.writeAndFlush("我是服务端 你好啊 我发沙发沙发舒服舒服撒地方撒打发士大夫士大夫士大夫士大夫士大夫撒地方撒打发士大夫士大夫士大夫撒打发士大夫收到多舒服撒打发士大夫士大夫士大夫撒地方撒打发士大夫士大夫士大夫士大夫士大夫撒飞洒地方撒旦法撒旦飞洒地方的撒旦是的方法撒地方撒打发士大夫士大夫撒发生的发生大法师法师法师的法师法撒旦范德萨法撒旦飞洒地方撒发生地方萨芬撒飞洒地方撒发生大幅发送到发送到发士大夫撒飞洒发生的撒地方大" +
                "士大夫撒大范德萨发沙发沙发撒的发送到发送到发的说法是范德萨发生发生大法师法师法师 1" +
                "法师法师的法师法师法师打发斯蒂芬是腐恶点 你 沙发沙发沙发沙发上的发生法撒旦法师打发士大夫 2" +
                "撒飞洒地方是法师打发士大夫士大夫三大发送到发送到发烧法师打发士大夫撒旦飞洒地方撒旦法师打发送到发送到发斯蒂芬   123："+System.getProperty(TAG_LINE));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      /* cause.printStackTrace();
       ctx.close();*/

        Channel incoming = ctx.channel();
        if(!incoming.isActive())
            System.out.println("SimpleClient:" + incoming.remoteAddress()
                    + "异常");

        cause.printStackTrace();
        ctx.close();
    }
}
