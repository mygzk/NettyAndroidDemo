package com.gzk.netty.netty.client;

import android.util.Log;

import com.gzk.netty.netty.NettyConstant;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

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


    public void connect(InetSocketAddress socketAddress) {
        mEventLoopGroup = new NioEventLoopGroup();
        mBootstrap = new Bootstrap();
        mBootstrap.group(mEventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        ByteBuf delimiter = Unpooled.copiedBuffer(NettyConstant.MAG_SEPARATOR.getBytes());
                         socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                        // socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });

        mChannelFuture = mBootstrap.connect(socketAddress); // 发起异步连接操作
        mChannel = mChannelFuture.channel();
        //   mChannelFuture.channel().closeFuture();// 等待客户端链路关闭。

        mChannelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    mChannel = channelFuture.channel();
                }

            }
        });
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
            mChannel.writeAndFlush(msg + NettyConstant.MAG_SEPARATOR);
        }
    }


}
