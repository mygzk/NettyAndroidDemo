package com.gzk.netty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gzk.netty.netty.NettyClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /// initService();


        etContent = findViewById(R.id.et_content);
        findViewById(R.id.tv_send).setOnClickListener(this);
        findViewById(R.id.tv_connect).setOnClickListener(this);
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
            default:
                break;
        }
    }


    private void connect() {
        NettyClient.getInstance().connect();
    }

    private void send() {
        String str = etContent.getText().toString();
        NettyClient.getInstance().send(str);

    }


}
