package com.hisense.hiask.app;

import android.app.Application;

import com.hisense.hiask.higreendao.manager.DaoManager;
import com.hisense.hitools.utils.HiUtils;


/**
 * Created by liudunjian on 2018/4/24.
 */

public class HiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HiUtils.init(this);
      //  LeakCanary.install(this);
        DaoManager.getInstance().initDaoDatabase();
    }
}
