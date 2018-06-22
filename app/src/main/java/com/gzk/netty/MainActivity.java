package com.gzk.netty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gzk.netty.netty.NettyClient;
import com.gzk.netty.netty.NettyConnectListener;
import com.gzk.netty.netty.NettyReceiveListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = MainActivity.class.getSimpleName();
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /// initService();


        etContent = findViewById(R.id.et_content);
        findViewById(R.id.tv_send).setOnClickListener(this);
        findViewById(R.id.tv_connect).setOnClickListener(this);
        findViewById(R.id.tv_disconnect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                send();
                break;
            case R.id.tv_connect:
                connect();
                break;
            case R.id.tv_disconnect:
                NettyClient.getInstance().disconnect();
                break;
            default:
                break;
        }
    }


    private void connect() {
        NettyClient.getInstance().connect(new NettyConnectListener() {
            @Override
            public void connectFail(String msg) {
                Log.e(TAG, "connectFail..."+msg);
            }

            @Override
            public void connectSucc() {
                Log.e(TAG, "connectSucc...");
            }

            @Override
            public void disconnect() {
                Log.e(TAG, "disconnect...");
            }
        });
    }

    private void send() {
        String str = etContent.getText().toString();
        NettyClient.getInstance().send(str, new NettyReceiveListener() {
            @Override
            public void receiveSucc(String msg) {
                Log.e(TAG, "receiveSucc: " + msg);
            }

            @Override
            public void receiveFail(String msg) {
                Log.e(TAG, "receiveFail： " + msg);
            }
        });

    }


}
