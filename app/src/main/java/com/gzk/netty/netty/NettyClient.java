package com.gzk.netty.netty;

import android.util.Log;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * netty client
 */
public class NettyClient {
    private String TAG = NettyClient.class.getSimpleName();
    private EventLoopGroup mEventLoopGroup;
    private Bootstrap mBootstrap;
    private ChannelFuture mChannelFuture;
    private Channel mChannel;


    public static class NettyClientHint {
        private static final NettyClient INSTANCE = new NettyClient();
    }

    private NettyClient() {

    }

    public static final NettyClient getInstance() {
        return NettyClientHint.INSTANCE;
    }

    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connect(new InetSocketAddress(NettyConstant.HOST, NettyConstant.PORT));
            }
        }).start();
    }

    public void connect(InetSocketAddress socketAddress) {
        try {
            mEventLoopGroup = new NioEventLoopGroup();
            mBootstrap = new Bootstrap();
            mBootstrap.group(mEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //粘包处理
                            pipeline.addLast("line", new LineBasedFrameDecoder(1024));
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("handler", new NettyClientHandler());
                        }
                    });

            mChannelFuture = mBootstrap.connect(socketAddress).sync(); // 发起异步连接操作
            mChannel = mChannelFuture.channel();
            mChannelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Log.e(TAG, "android client 连接成功");
                    if (channelFuture.isSuccess()) {
                        mChannel = channelFuture.channel();
                    }

                }
            });
            mChannel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mEventLoopGroup.shutdownGracefully();
        }


    }


    public void send(String msg) {
        if (mChannel == null) {
            Log.e(TAG, "send: channel is null");
            return;
        }

        if (!mChannel.isWritable()) {
            Log.e(TAG, "send: channel is not Writable");
            return;
        }
        if (!mChannel.isActive()) {
            Log.e(TAG, "send: channel is not active!");
            return;
        }
        if (mChannel != null) {
            mChannel.writeAndFlush(msg + System.getProperty(NettyConstant.MAG_SEPARATOR_1));
        }
    }


}
