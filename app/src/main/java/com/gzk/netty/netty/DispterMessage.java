package com.gzk.netty.netty;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.gzk.netty.GsonUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


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
                    if (replyMessage.getConnectListener() != null)
                        replyMessage.getConnectListener().connectSucc();
                    break;
                case MSG_CONN_FAIL:
                    if (replyMessage.getConnectListener() != null)
                        replyMessage.getConnectListener().connectFail(replyMessage.getMsg());
                    break;
                case MSG_CONN_DIS:
                    if (replyMessage.getConnectListener() != null)
                        replyMessage.getConnectListener().disconnect();
                    break;
                case MSG_RECEIVE_SUCC:
                    if (replyMessage.getReceiveListener() != null) {
                        Type type = getSuperclassTypeParameter(replyMessage.getReceiveListener().getClass());
                        replyMessage.getReceiveListener().receiveSucc(GsonUtil.fromJson(replyMessage.getMsg(), type));
                    }

                    break;
                case MSG_RECEIVE_FAIL:
                    if (replyMessage.getReceiveListener() != null)
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

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        ParameterizedType parameterizedType = (ParameterizedType) subclass.getGenericInterfaces()[0];
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return actualTypeArguments[0];
    }


}
