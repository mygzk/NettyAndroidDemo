package com.gzk.netty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gzk.netty.netty.NettyConstant;
import com.gzk.netty.netty.client.NettyClient;
import com.gzk.netty.service.NettyServerService;

import java.net.InetSocketAddress;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();



        etContent = findViewById(R.id.et_content);
        findViewById(R.id.tv_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                send();
                break;
            default:
                break;
        }
    }


    private void initService(){
        Intent intent = new Intent(this, NettyServerService.class);
        startService(intent);
    }
    private void send(){
        String str= etContent.getText().toString();
        NettyClient.getInstance().send(str);

    }



}
