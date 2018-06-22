package com.gzk.netty.netty;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.net.ConnectException;
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
    private Channel mChannel;
    private NettyClientHandler mNettyClientHandler;
    private DispterMessage mDispterMessage;
    private Thread mClientThread;

    private NettyConnectListener mNettyConnectListener;
    private List<NettyReceiveListener> mNettyReceiveListeners = new ArrayList<>();
    private NettyReceiveListener mNettyReceiveListener;


    private static class NettyClientHint {
        private static final NettyClient INSTANCE = new NettyClient();
    }

    private NettyClient() {
        mNettyClientHandler = new NettyClientHandler();
        mDispterMessage = new DispterMessage();
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
        if (mChannel != null) {
            mChannel = null;
        }
        try {
            mEventLoopGroup = new NioEventLoopGroup();
            Bootstrap mBootstrap = new Bootstrap();
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

            ChannelFuture mChannelFuture = mBootstrap
                    .connect(new InetSocketAddress(NettyConstant.HOST, NettyConstant.PORT)).sync();
            mChannel = mChannelFuture.channel();
            mChannelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        isConnect = true;
                        mChannel = channelFuture.channel();
                        if (mNettyConnectListener != null) {
                            //   mNettyConnectListener.connectSucc();
                            postMsg(null, mNettyConnectListener, null, DispterMessage.MSG_CONN_SUCC);
                        }

                    } else {
                        if (mNettyConnectListener != null) {
                            //   mNettyConnectListener.connectFail("连接失败,channelFuture is not success");
                            postMsg("连接失败,channelFuture is not success", mNettyConnectListener, null, DispterMessage.MSG_CONN_FAIL);
                        }
                        isConnect = false;

                    }

                }
            });
            mChannel.closeFuture().sync();
        } catch (InterruptedException e) {
            if (mNettyConnectListener != null) {
                //  mNettyConnectListener.connectFail(e.getMessage());
                postMsg(e.getMessage(), mNettyConnectListener, null, DispterMessage.MSG_CONN_FAIL);
            }
            isConnect = false;
            e.printStackTrace();
        } catch (Exception e) {
            if (mNettyConnectListener != null) {
                postMsg(e.getMessage(), mNettyConnectListener, null, DispterMessage.MSG_CONN_FAIL);
            }
            e.printStackTrace();
        } finally {
            isConnect = false;
            if (mNettyConnectListener != null) {
                postMsg(null, mNettyConnectListener, null, DispterMessage.MSG_CONN_DIS);
            }
            disconnect();
            mEventLoopGroup.shutdownGracefully();

        }


    }

    public void disconnect() {
       /* if (mClientThread != null) {
            mClientThread.interrupt();
            mClientThread = null;
        }*/
        if (mNettyConnectListener != null) {
            postMsg(null, mNettyConnectListener, null, DispterMessage.MSG_CONN_DIS);
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
                SystemClock.sleep(reconnectIntervalTime);
                connectServer();
            }
        }
    }

    public synchronized void send(String msg, NettyReceiveListener listener) {
        mNettyReceiveListener = listener;
        if (mChannel == null) {
            postMsg("channel is null", null, mNettyReceiveListener, DispterMessage.MSG_RECEIVE_FAIL);
            return;
        }

        if (!mChannel.isWritable()) {
            postMsg("channel is not Writable", null, mNettyReceiveListener, DispterMessage.MSG_RECEIVE_FAIL);
            return;
        }
        if (!mChannel.isActive()) {
            postMsg("channel is not active!", null, mNettyReceiveListener, DispterMessage.MSG_RECEIVE_FAIL);
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
        if (mNettyReceiveListener != null && mNettyReceiveListeners.size() > 0) {
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
                postMsg(msg, null, listener, DispterMessage.MSG_RECEIVE_SUCC);
            }
        }
    }

    public void handErrorMsg(String msg) {
        for (NettyReceiveListener listener : mNettyReceiveListeners) {
            if (listener != null) {
                postMsg(msg, null, listener, DispterMessage.MSG_RECEIVE_FAIL);
            }
        }
    }


    private void postMsg(String msg, NettyConnectListener connectListener, NettyReceiveListener receiveListener, int type) {
        ReplyMessage message = new ReplyMessage();
        message.setConnectListener(connectListener);
        message.setMsg(msg);
        message.setReceiveListener(receiveListener);
        message.setType(type);

        mDispterMessage.handMsg(message);
    }


}
