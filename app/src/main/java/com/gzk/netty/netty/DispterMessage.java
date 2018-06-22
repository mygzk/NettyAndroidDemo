package com.gzk.netty.netty;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class DispterMessage {
    public final static int MSG_CONN_SUCC = 1;
    public final static int MSG_CONN_FAIL = 2;
    public final static int MSG_CONN_DIS = 3;
    public final static int MSG_RECEIVE_SUCC = 4;
    public final static int MSG_RECEIVE_FAIL = 5;

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {

            if (msg == null) {
                return;
            }
            ReplyMessage replyMessage = (ReplyMessage) msg.obj;
            if (replyMessage == null) {
                return;
            }
            switch (replyMessage.getType()) {
                case MSG_CONN_SUCC:
                    replyMessage.getConnectListener().connectSucc();
                    break;
                case MSG_CONN_FAIL:
                    replyMessage.getConnectListener().connectFail(replyMessage.getMsg());
                    break;
                case MSG_CONN_DIS:
                    replyMessage.getConnectListener().disconnect();
                    break;
                case MSG_RECEIVE_SUCC:
                    replyMessage.getReceiveListener().receiveSucc(replyMessage.getMsg());
                    break;
                case MSG_RECEIVE_FAIL:
                    replyMessage.getReceiveListener().receiveFail(replyMessage.getMsg());
                    break;
                default:
                    break;
            }
        }
    };

    public void handMsg(ReplyMessage replyMessage) {
        Message message = new Message();
        message.arg1 = replyMessage.getType();
        message.obj = replyMessage;
        mHandler.sendMessage(message);

    }


}
