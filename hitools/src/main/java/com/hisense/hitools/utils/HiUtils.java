package com.hisense.hitools.utils;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by liudunjian on 2018/4/24.
 */

public class HiUtils {
    @SuppressLint("staticFieldLeak")
    private static Context context;

    private HiUtils() {
        throw new UnsupportedOperationException("u can't instantiate me");
    }

    public static void init(Context context) {
        HiUtils.context = context.getApplicationContext();
    }

    public static Context getContext() {
        return HiUtils.context;
    }
}
