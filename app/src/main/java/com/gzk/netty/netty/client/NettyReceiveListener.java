package com.gzk.netty.netty.client;

public interface NettyReceiveListener<T> {

    void receiveFail(String msg);

    void receiveSucc(T t);

}
