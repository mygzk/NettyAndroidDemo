package com.gzk.netty.netty;

public interface NettyConnectListener {
    void connectFail(String msg);

    void connectSucc();

    void disconnect();

}
