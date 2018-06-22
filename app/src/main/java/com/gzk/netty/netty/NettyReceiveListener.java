package com.gzk.netty.netty;

public interface NettyReceiveListener {

    void receiveSucc(String msg);
    void receiveFail(String msg);

}
