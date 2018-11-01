package com.hisense.hitools.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by liudunjian on 2018/4/13.
 */

public class AppUtils {

    /**
     * 获取Cpu名称
     * @return
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while (true) {
                String line = localBufferedReader.readLine();
                if (line == null) {
                    localBufferedReader.close();
                    break;
                }
                if (line.contains("Hardware")) {
                    return line.split(":")[1].trim();
                }
            }
        } catch (IOException var3) {
            ;
        }
        return null;
    }

    /**
     * 获取PackageManager
     **/
    public static String getPackageName() {
        return HiUtils.getContext().getPackageName();
    }

    /**
     * 获取PackageManager
     **/
    public static PackageManager getPackageManager() {
        PackageManager packageManager = HiUtils.getContext().getPackageManager();
        return packageManager;
    }

    /**
     * 获取单个App的Package信息
     **/
    public static PackageInfo getPackageInfo(@NonNull String packageName) {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 获取单个App的Application信息
     **/
    public static ApplicationInfo getApplicationInfo(@NonNull String packageName) {
        PackageManager packageManager = getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationInfo;
    }

    /**
     * 获取单个App图标
     **/
    public static Drawable getAppIcon(@NonNull String packageName) {
        PackageManager packageManager = getPackageManager();
        Drawable icon = null;
        try {
            icon = packageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return icon;
    }

    /**
     * 获取单个App名称
     **/
    public static String getAppName(String packageName) {
        PackageManager packageManager = getPackageManager();
        if (null == packageName) {
            packageName = getPackageName();
        }
        ApplicationInfo applicationInfo = getApplicationInfo(packageName);
        if (null != applicationInfo) {
            String appName = packageManager.getApplicationLabel(applicationInfo).toString();
            return appName;
        } else {
            return null;
        }
    }

    /**
     * 获取单个App版本号Name
     **/
    public static String getAppVersionName(@NonNull String packageName) {
        PackageInfo packageInfo = getPackageInfo(packageName);
        if (null != packageInfo) {
            return packageInfo.versionName;
        } else {
            return null;
        }
    }

    /**
     * 获取单个App版本号Code
     **/
    public static int getAppVersionCode(@NonNull String packageName) {
        PackageInfo packageInfo = getPackageInfo(packageName);
        if (null != packageInfo) {
            return packageInfo.versionCode;
        } else {
            return -1;
        }
    }

    /**
     * 获取设备IMEI设备号
     *
     * @return
     */

    public static String getDeviceIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) HiUtils.getContext()
                .getSystemService(HiUtils.getContext().TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    public static boolean isAppDebug() {
        try {
            ApplicationInfo info = getApplicationInfo(getPackageName());
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void openAppByPkg(String packageName) {
        try {
            PackageManager packageManager = getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            if (intent == null) {
                System.out.println("APP not found!");
                return;
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            HiUtils.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //安装 apk
    public static void installApk(String fileName) {
        File apk = new File(fileName);
        if (apk.exists()) {
            try {
                String cmd = "chmod 777 " + fileName;
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri uri = Uri.fromFile(apk);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            HiUtils.getContext().startActivity(intent);
        }

    }

    //获取当前位置经纬度
    public static String getLocation() throws SecurityException {
        LocationManager locationManager = (LocationManager) HiUtils.getContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location location = null;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location == null && providers.contains(LocationManager.NETWORK_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (null == location)
            return "(0,0)";
        return "(" + location.getLatitude() + "," + location.getLongitude() + ")";
    }

}
