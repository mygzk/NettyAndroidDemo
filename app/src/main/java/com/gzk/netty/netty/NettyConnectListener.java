package com.gzk.netty.netty;

public interface NettyConnectListener {
    void connectFail();

    void connectSucc();

    void disconnect();

}
