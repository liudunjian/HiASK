package com.hisense.hitools.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;

import com.hisense.hitools.utils.NetworkUtils;

/**
 * Created by liudunjian on 2018/4/24.
 */

public class NetworkListener extends BroadcastReceiver {

    public static final String TAG = NetworkListener.class.getSimpleName();

    public static final int HANDLER_NETWORK_CHANGE_MSG = 0x001;

    private Handler handler;

    public NetworkListener(Handler handler) {
        this.handler = handler;
    }

    public static IntentFilter intentFilter() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            handler.obtainMessage(HANDLER_NETWORK_CHANGE_MSG,NetworkUtils.getConnectedNetworkType(),-1).sendToTarget();
        }
    }

}
