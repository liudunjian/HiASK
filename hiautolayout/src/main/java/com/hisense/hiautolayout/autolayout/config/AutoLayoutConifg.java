package com.hisense.hiautolayout.autolayout.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.hisense.hiautolayout.autolayout.utils.L;
import com.hisense.hiautolayout.autolayout.utils.ScreenUtils;


/**
 * Created by zhy on 15/11/18.
 */
public class AutoLayoutConifg {

    private static AutoLayoutConifg sIntance = new AutoLayoutConifg();


    private static final String KEY_DESIGN_WIDTH = "design_width";
    private static final String KEY_DESIGN_HEIGHT = "design_height";
    private static final String KEY_REFER_TO_WIDTH = "refer_to_width";

    private int screenWidth;
    private int screenHeight;

    private int designWidth;
    private int designHeight;

    private boolean useDeviceSize;

    private boolean referToWidth = true;

    private AutoLayoutConifg() {
    }

    public void checkParams() {
        if (designHeight <= 0 || designWidth <= 0) {
            throw new RuntimeException(
                    "you must set " + KEY_DESIGN_WIDTH + " and " + KEY_DESIGN_HEIGHT + "  in your manifest file.");
        }
    }

    public boolean isReferToWidth() {
        return referToWidth;
    }

    public AutoLayoutConifg useDeviceSize() {
        useDeviceSize = true;
        return this;
    }


    public static AutoLayoutConifg getInstance() {
        return sIntance;
    }


    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getDesignWidth() {
        return designWidth;
    }

    public int getDesignHeight() {
        return designHeight;
    }


    public void init(Context context) {
        getMetaData(context);

        int[] screenSize = ScreenUtils.getScreenSize(context, useDeviceSize);
        screenWidth = screenSize[0];
        screenHeight = screenSize[1];
        L.e(" screenWidth =" + screenWidth + " ,screenHeight = " + screenHeight);
    }

    private void getMetaData(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(context
                    .getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                designWidth = (int) applicationInfo.metaData.get(KEY_DESIGN_WIDTH);
                designHeight = (int) applicationInfo.metaData.get(KEY_DESIGN_HEIGHT);
                referToWidth = applicationInfo.metaData.getBoolean(KEY_REFER_TO_WIDTH, true);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(
                    "you must set " + KEY_DESIGN_WIDTH + " and " + KEY_DESIGN_HEIGHT + "  in your manifest file.", e);
        }

        L.e(" designWidth =" + designWidth + " , designHeight = " + designHeight);
    }

    public int getPercentSize(int val) {
        if(isReferToWidth()){
            return getPercentWidthSize(val);
        }else{
            return getPercentHeightSize(val);
        }
    }


    private int getPercentWidthSize(int val) {
        int res = val * screenWidth;
        if (res % designWidth == 0) {
            return res / designWidth;
        } else {
            return res / designWidth + 1;
        }
    }

    private int getPercentHeightSize(int val) {
        int res = val * screenHeight;
        if (res % designHeight == 0) {
            return res / designHeight;
        } else {
            return res / designHeight + 1;
        }
    }

}
