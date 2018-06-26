package com.gzk.netty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gzk.netty.netty.BaseResult;
import com.gzk.netty.netty.NettyClient;
import com.gzk.netty.netty.NettyConnectListener;
import com.gzk.netty.netty.NettyReceiveListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = MainActivity.class.getSimpleName();
    private EditText etContent;
    private ListView lsRecord;
    private RecordAdapter mAdapter;
    private List<RecordBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etContent = findViewById(R.id.et_content);
        findViewById(R.id.tv_send).setOnClickListener(this);
        findViewById(R.id.tv_connect).setOnClickListener(this);
        findViewById(R.id.tv_disconnect).setOnClickListener(this);

        lsRecord = findViewById(R.id.ls_record);
        mAdapter = new RecordAdapter(this, mData);
        lsRecord.setAdapter(mAdapter);
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
                Log.e(TAG, "connectFail..." + msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectSucc() {
                Log.e(TAG, "connectSucc...");
                Toast.makeText(MainActivity.this, "connectSucc", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void disconnect() {
                Log.e(TAG, "disconnect...");
                Toast.makeText(MainActivity.this, "disconnect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void send() {
        final String str = etContent.getText().toString();
        NettyClient.getInstance().send(str, new NettyReceiveListener<List<RecordBean>>() {

            @Override
            public void receiveFail(String msg) {
                Toast.makeText(MainActivity.this, "receiveFail :" + msg, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "receiveFail： " + msg);
            }

            @Override
            public void receiveSucc(List<RecordBean> s) {
                Log.e(TAG, "receiveSucc1： " );
                mAdapter.addData(s);
            }
        });

    }


}
