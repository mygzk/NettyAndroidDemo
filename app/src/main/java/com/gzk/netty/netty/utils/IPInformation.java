package com.gzk.netty.netty.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * @Description: <p>
 * <p>
 * @Project: NettyAndroidDemo
 * @Files: ${CLASS_NAME}
 * @Author: HQ
 * @Version: 0.0.1
 * @Date: 2020/4/12 00:29
 * @Copyright:
 */
public class IPInformation {

    private  WifiManager mWifiManager;
    private WifiInfo mWifiInfo;

    public IPInformation(Context mContext){

        //获取wifi服务
        mWifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
        mWifiInfo = mWifiManager.getConnectionInfo();
    }
    public  String getWIFILocalIpAdress() {
        int ipAddress = mWifiInfo.getIpAddress();
        return formatIpAddress(ipAddress);
    }
    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF ) + "." +
                ((ipAdress >> 8 ) & 0xFF) + "." +
                ((ipAdress >> 16 ) & 0xFF) + "." +
                ( ipAdress >> 24 & 0xFF) ;
    }
    public  String getMacAddress() {
        return mWifiInfo.getMacAddress();
    }

}
