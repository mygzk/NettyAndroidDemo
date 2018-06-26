package com.gzk.netty.netty;

public interface NettyReceiveListener<T> {

    void receiveFail(String msg);

    void receiveSucc(T t);

}
