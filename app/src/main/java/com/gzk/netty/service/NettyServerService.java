package com.gzk.netty.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gzk.netty.netty.NettyConstant;
import com.gzk.netty.netty.client.NettyClient;
import com.gzk.netty.netty.server.NettyServer;

import java.net.InetSocketAddress;

public class NettyServerService extends Service {
    public NettyServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       /* // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
*/
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // NettyServer.getInstance().init();
        NettyClient.getInstance().connect(new InetSocketAddress(NettyConstant.HOST,NettyConstant.PORT));
        return super.onStartCommand(intent, flags, startId);
    }
}
