package com.gzk.netty.netty;

import android.os.SystemClock;
import android.util.Log;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

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
    /**
     * 重连间隔时间
     */
    private long reconnectIntervalTime = 5000;
    /**
     * 连接状态
     */
    private volatile boolean isConnect = false;
    /**
     * 是否需要重连
     */
    private boolean isNeedReconnect = true;
    /**
     * 重连次数
     */
    private static int reconnectNum = Integer.MAX_VALUE;

    private EventLoopGroup mEventLoopGroup;
    private Bootstrap mBootstrap;
    private ChannelFuture mChannelFuture;
    private Channel mChannel;
    private NettyClientHandler mNettyClientHandler;
    private Thread mClientThread;

    private NettyConnectListener mNettyConnectListener;
    private List<NettyReceiveListener> mNettyReceiveListeners = new ArrayList<>();
    private NettyReceiveListener mNettyReceiveListener;


    private static class NettyClientHint {
        private static final NettyClient INSTANCE = new NettyClient();
    }

    private NettyClient() {
        mNettyClientHandler = new NettyClientHandler();
    }

    public static NettyClient getInstance() {
        return NettyClientHint.INSTANCE;
    }

    public void connect(NettyConnectListener listener) {
        if (isConnect) {
            return;
        }
        mNettyReceiveListeners.clear();

        mNettyConnectListener = listener;
        mClientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                connectServer();
            }
        });
        mClientThread.start();
    }

    /**
     * 连接到netty服务器
     */
    private void connectServer() {
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
                            pipeline.addLast("handler", mNettyClientHandler);
                        }
                    });

            mChannelFuture = mBootstrap
                    .connect(new InetSocketAddress(NettyConstant.HOST, NettyConstant.PORT)).sync(); // 发起异步连接操作
            mChannel = mChannelFuture.channel();
            mChannelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        isConnect = true;
                        mChannel = channelFuture.channel();
                        if (mNettyConnectListener != null) {
                            mNettyConnectListener.connectSucc();
                        }

                    } else {
                        if (mNettyConnectListener != null) {
                            mNettyConnectListener.connectFail();
                        }
                        isConnect = false;

                    }

                }
            });
            mChannel.closeFuture().sync();
        } catch (InterruptedException e) {
            if (mNettyConnectListener != null) {
                mNettyConnectListener.connectFail();
            }
            isConnect = false;
            e.printStackTrace();
        } finally {
            isConnect = false;
            if (mNettyConnectListener != null) {
                mNettyConnectListener.disconnect();
            }
            mEventLoopGroup.shutdownGracefully();
        }


    }

    public void disconnect() {
        if (mClientThread != null) {
            mClientThread.interrupt();
            mClientThread = null;
        }
        if (mNettyConnectListener != null) {
            mNettyConnectListener.disconnect();
        }
        clearReceiveLisenter();

        isConnect = false;
        isNeedReconnect = false;
        mEventLoopGroup.shutdownGracefully();
    }

    public void reconnect() {
        Log.e(TAG, "reconnect");
        if (isNeedReconnect && reconnectNum > 0 && !isConnect) {
            reconnectNum--;
            SystemClock.sleep(reconnectIntervalTime);
            if (isNeedReconnect && reconnectNum > 0 && !isConnect) {
                disconnect();
                connectServer();
            }
        }
    }

    public synchronized void send(String msg, NettyReceiveListener listener) {
        mNettyReceiveListener = listener;
        if (mChannel == null) {
            mNettyReceiveListener.receiveFail("channel is null");
            return;
        }

        if (!mChannel.isWritable()) {
            mNettyReceiveListener.receiveFail("channel is not Writable");
            return;
        }
        if (!mChannel.isActive()) {
            mNettyReceiveListener.receiveFail("channel is not active!");
            return;
        }
        if (mChannel != null) {
            addReceiveLisenter(mNettyReceiveListener);
            mChannel.writeAndFlush(msg + System.getProperty(NettyConstant.MAG_SEPARATOR_1));
        }
    }

    public void addReceiveLisenter(NettyReceiveListener listener) {
        if (listener != null && !mNettyReceiveListeners.contains(listener)) {
            mNettyReceiveListeners.add(listener);
        }
    }

    public void removeCurrentReceiveLisenter() {
        if(mNettyReceiveListener!=null&&mNettyReceiveListeners.size()>0){
            mNettyReceiveListeners.remove(mNettyReceiveListener);
        }

    }
    public void removeReceiveLisenter(NettyReceiveListener listener) {
        if (listener != null && mNettyReceiveListeners.contains(listener)) {
            mNettyReceiveListeners.remove(listener);
        }
    }

    public void clearReceiveLisenter() {
        mNettyReceiveListeners.clear();
    }

    public void handMsg(String msg) {
        for (NettyReceiveListener listener : mNettyReceiveListeners) {
            if (listener != null) {
                listener.receiveSucc(msg);
            }
        }
    }

    public void handErrorMsg(String msg) {
        for (NettyReceiveListener listener : mNettyReceiveListeners) {
            if (listener != null) {
                listener.receiveFail(msg);
            }
        }
    }


}
