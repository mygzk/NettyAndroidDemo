package com.gzk.netty.netty.server;

import com.gzk.netty.netty.NettyConstant;
import com.gzk.netty.netty.client.NettyClient;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;


public class NettyServer {
    private String TAG = NettyServer.class.getSimpleName();
    private NioEventLoopGroup boosGroup;
    private NioEventLoopGroup workerGroup;
    private ServerBootstrap b;
    private ChannelFuture f;
    private boolean isInit = false;


    public static class NettyServerHint {
        private static final NettyServer INSTANCE = new NettyServer();
    }

    public static final NettyServer getInstance() {
        return NettyServer.NettyServerHint.INSTANCE;
    }

    private NettyServer() {

    }


    public void init() {
        if (isInit) {
            return;
        }
        // NioEventLoopGroup 线程组包含一组NIO线程， 专门用于处理网络的事件。
        // 两个线程组，一个用于处理接受客户端的连接，一个用于进行 SocketChannel 的网络读取。
        boosGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        b = new ServerBootstrap();
        b.group(boosGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChildChannelHandler());

        // f = b.bind(NettyConstant.PORT).sync();     // 绑定端口
        f = b.bind(NettyConstant.PORT);     // 绑定端口
        f.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    NettyClient.getInstance().connect(new InetSocketAddress(NettyConstant.HOST,NettyConstant.PORT));
                }
            }
        });
        isInit = true;
    }


    public void shutDown() {
        if (f != null && f.isSuccess()) {
            isInit = false;
            f.channel().closeFuture();
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
           // ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            ByteBuf delimiter = Unpooled.copiedBuffer(NettyConstant.MAG_SEPARATOR.getBytes());
           // ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new NettyServerHandler());
        }
    }


}
