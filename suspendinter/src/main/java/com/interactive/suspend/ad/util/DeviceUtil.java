package com.interactive.suspend.ad.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

/**
 * Created by drason on 2018/7/20.
 */

public class DeviceUtil {

    public static int getAppVersion(Context context) {
        int version = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionCode;
        } catch (Exception ignore) {
        }
        return version;
    }

    public static String getAppPackName(Context context) {
        if (context == null) {
            return "";
        }
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        String applicationName = "";
        synchronized (context) {
            try {
                packageManager = context.getPackageManager();
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                applicationInfo = null;
            } catch (Exception e) {
                applicationInfo = null;
            }
            if (applicationInfo != null) {
                applicationName = applicationInfo.packageName;
            }

        }
        return applicationName;
    }

    /**
     * @param context
     * @return 1：为新用户0:老用户
     */
    public static long getFristInstallTime(Context context) {

        PackageInfo packageInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
        }
        if (packageInfo != null) {
            return packageInfo.firstInstallTime;
        }
        return 0;
    }


    /**
     * @param context
     */
    public static int getIsNewUser(Context context) {
        PackageInfo packageInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
        }
        if (packageInfo != null && packageInfo.firstInstallTime == packageInfo.lastUpdateTime) {
            return 1;
        }
        return 0;
    }

    public static String getDeviceId(Context context) {
        String androidId = "";
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
//            return androidId ;
        }
        return androidId;
    }

    public static String getAndroidId(Context context) {
        String androidId = "";
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
//            return androidId ;
        }
        return androidId;
    }
}
