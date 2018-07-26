package com.interactive.suspend.ad.util;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.List;
import java.util.Locale;

import static com.interactive.suspend.ad.util.PreferenceUtils.getBackupGoogleAdvertisingId;

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


    public static int getNetworkType(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    return ConnectivityManager.TYPE_WIFI;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return ConnectivityManager.TYPE_MOBILE;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return locale.getLanguage() + "-" + country.toUpperCase();
    }

    // gaid > backup gaid > deviceid > android id
    public static final String getGoogleAdvertisingId(Context context) {
        if (context == null) {
            return "";
        }
        String id = PreferenceUtils.getGoogleAdvertisingId(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        try {
            String gaid = AdvertisingIdClient.getAdvertisingIdInfo(context).getId();
            if (!TextUtils.isEmpty(gaid)) {
                PreferenceUtils.setGoogleAdvertisingId(context, gaid);
                return gaid;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return getBackupGoogleAdvertisingId(context);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getCountryCode(Context context) {
        return context.getApplicationContext().getResources().getConfiguration().locale.getCountry();
    }

    public static boolean isFacebookAppExist(Context context) {
        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (PackageInfo packageInfo : installedPackages) {
            if ("com.facebook.katana".equals(packageInfo.packageName)) {
                return true;
            }
        }
        return false;
    }

    public static long getAppInstallTime(Context context, String pkgName) {
        try {
            return context.getPackageManager().getPackageInfo(pkgName, 0).firstInstallTime;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return  -1;
    }

    public static boolean isLockScreenOn(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
            return true;
        } else {
            return false;
        }
    }
}
