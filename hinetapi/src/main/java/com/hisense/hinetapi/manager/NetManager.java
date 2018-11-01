package com.hisense.hinetapi.manager;

import android.content.Context;

import com.hisense.hinetapi.provider.AskProvider;
import com.hisense.hinetapi.provider.AuthorizeProvider;
import com.hisense.hitools.utils.EmptyUtils;

/**
 * Created by liudunjian on 2018/4/26.
 */

public class NetManager {

    private Context context;
    private AskProvider askProvider;
    private AuthorizeProvider authorizeProvider;

    private static NetManager instance = null;
    private static final Object lock = new Object();

    public static NetManager getInstance(Context context) {
        if (null == instance) {
            synchronized (lock) {
                instance = new NetManager(context);
            }
        }
        return instance;
    }

    protected NetManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public static AskProvider askService(Context context) {
        return NetManager.getInstance(context).generateAskService();
    }

    public static AuthorizeProvider authorizeProvider(Context context) {
        return NetManager.getInstance(context).generateAuthorizeService();
    }

    private AskProvider generateAskService() {
        if (EmptyUtils.isEmpty(this.askProvider)) {
            this.askProvider = new AskProvider(context);
            this.askProvider.generateAsstService();
        }
        return this.askProvider;
    }
    private AuthorizeProvider generateAuthorizeService() {
        if (EmptyUtils.isEmpty(this.authorizeProvider)) {
            this.authorizeProvider = new AuthorizeProvider(context);
            this.authorizeProvider.generateAuthorizeService();
        }
        return this.authorizeProvider;
    }
}
