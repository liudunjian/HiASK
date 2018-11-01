package com.hisense.hitools.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by liudunjian on 2018/4/24.
 */

public class NetworkUtils {

    public static int getConnectedNetworkType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) HiUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    /**
     * 判断是否有网络
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) HiUtils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            LogUtils.e("couldn't get connectivity manager");
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].isAvailable() && info[i].getState() == NetworkInfo.State.CONNECTED) {
                        LogUtils.d("network is available");
                        return true;
                    }
                }
            }
        }
        LogUtils.e("network is not available");
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @return
     * @throws Exception
     */
    public static boolean isMobileDataEnable() {
        ConnectivityManager connectivity = (ConnectivityManager) HiUtils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;
        if (connectivity == null) {
            LogUtils.e("couldn't get connectivity manager");
        } else {
            isMobileDataEnable = connectivity.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).isConnected();
        }
        return isMobileDataEnable;
    }


    /**
     * 判断wifi 是否可用
     *
     * @return
     * @throws Exception
     */
    public static boolean isWifiDataEnable() {
        ConnectivityManager connectivity = (ConnectivityManager) HiUtils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable = false;
        if (connectivity == null) {
            LogUtils.e("couldn't get connectivity manager");
        } else {
            isWifiDataEnable = connectivity.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI).isConnected();
        }
        return isWifiDataEnable;
    }
}
