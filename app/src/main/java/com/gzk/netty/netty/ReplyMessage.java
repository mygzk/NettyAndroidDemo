package com.gzk.netty.netty;

public class ReplyMessage {
    private String msg;
    private int type;
    private NettyConnectListener connectListener;
    private NettyReceiveListener receiveListener;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public NettyConnectListener getConnectListener() {
        return connectListener;
    }

    public void setConnectListener(NettyConnectListener connectListener) {
        this.connectListener = connectListener;
    }

    public NettyReceiveListener getReceiveListener() {
        return receiveListener;
    }

    public void setReceiveListener(NettyReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }
}
