package com.interactive.suspend.ad.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.interactive.suspend.ad.constant.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * Created by hongwu on 12/12/16.
 */

public class PreferenceUtils {

    public static void initUserAgent(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.USER_AGENT, ApplicationUtil.initUserAgent(context)).apply();
    }

    public static final String getUserAgent(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, Context.MODE_PRIVATE);
        String ua = sp.getString(Constants.Preference.USER_AGENT, "");
        if (TextUtils.isEmpty(ua)) {
            ua = ApplicationUtil.initUserAgent(context);
        }
        return ua;
    }

    public static void setPkgsWithSameSize(Context context, String pkgs) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.PAKAGES_WITH_SAME_SIZE, pkgs).apply();
    }

    public static String getPkgsWithSameSize(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.PAKAGES_WITH_SAME_SIZE, "");
    }

    public static void setDownloadApkSize(Context context, long pkgSize) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putLong(Constants.Preference.DOWNLOADING_APK_SIZE, pkgSize).apply();
    }

    public static final long getDownloadApkSize(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getLong(Constants.Preference.DOWNLOADING_APK_SIZE, 0);
    }

    public static final String getNewsSourceID(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.NEWS_SOURCE_ID, "");
    }

    public static void setGoogleAdvertisingId(Context context, String gaid) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.GAID, gaid).apply();
    }

    public static String getGoogleAdvertisingId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.GAID, "");
    }

    // if can't get gaid via normal way, we get it from the system service
    public static void setBackupGoogleAdvertisingId(Context context, String gaid) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.BACKUP_GAID, gaid).apply();
    }

    public static String getBackupGoogleAdvertisingId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.BACKUP_GAID, "");
    }

    public static final String getAppConfigVersion(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.APP_CONFIG_VERSION, "");
    }

    public static final long getLastFetchAppWallSuccessTime(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(Constants.Preference.LAST_GET_APPWALL_TASK_SUCCESS_TIME, -1);
    }

    public static final long getLastFetchSmartSuccessTime(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(Constants.Preference.LAST_GET_SMART_TASK_SUCCESS_TIME, -1);
    }

    public static final long getLastFetchRewardSuccessTime(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(Constants.Preference.LAST_GET_REWARD_TASK_SUCCESS_TIME, -1);
    }

    public static final long getLastFetchNativeSuccessTime(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(Constants.Preference.LAST_GET_NATIVE_TASK_SUCCESS_TIME, -1);
    }

    public static final long getLastFetchAppConfigSuccessTime(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(Constants.Preference.LAST_GET_APP_CONFIG_TASK_SUCCESS_TIME, -1L);
    }

    public static final void setCurSdkVersion(Context context, long versionCode) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putLong(Constants.Preference.CUR_SDK_VERSION, versionCode).apply();
    }

    public static final long getCurSdkVersion(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(Constants.Preference.CUR_SDK_VERSION, 0);
    }

    public static final List<String> getFbTestDeviceIds(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        String ids = sp.getString(Constants.Preference.FB_TEST_DEVICE_IDS, "");
        List<String> devices = new ArrayList<>();
        if (!TextUtils.isEmpty(ids)) {
            devices.addAll(Arrays.asList(ids.split(",")));
        }
        return devices;
    }

    public static final String getAdmobTestDeviceId(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.ADMOB_TEST_DEVICE_ID, "");
    }

    public static void setAppId(Context context, String appId) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.APP_ID, appId).apply();
    }

    public static String getAppId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.APP_ID, "");
    }

    public static void setCoreSourceId(Context context, String coreSourceId) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.CORE_SOURCE_ID, coreSourceId).apply();
    }

    public static String getCoreSourceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.CORE_SOURCE_ID, "");
    }

    public static void setMarketSourceId(Context context, String marketSourceId) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.MARKET_SOURCE_ID, marketSourceId).apply();
    }

    public static String getMarketSourceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.MARKET_SOURCE_ID, "");
    }

    public static void setNativeSourceId(Context context, String nativeSourceId) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.NATIVE_SOURCE_ID, nativeSourceId).apply();
    }

    public static String getNativeSourceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.NATIVE_SOURCE_ID, "");
    }

    public static void setRewardVideoSourceId(Context context, String rewardSourceId) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.REWARD_SOURCE_ID, rewardSourceId).apply();
    }

    public static String getRewardVideoSourceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.REWARD_SOURCE_ID, "");
    }

    public static void setPlayableSourceId(Context context, String playableSourceId) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.PLAYABLE_SOURCE_ID, playableSourceId).apply();
    }

    public static String getPlayableSourceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.PLAYABLE_SOURCE_ID, "");
    }

    public static void setSmartSourceId(Context context, String smartSourceId) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.SMART_SOURCE_ID, smartSourceId).apply();
    }

    public static String getSmartSourceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.SMART_SOURCE_ID, "");
    }

    public static void setArkkiiAppid(Context context, String arkkiiAppid) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.ARRKII_APP_Id, arkkiiAppid).apply();
    }

    public static String getArkkiiAppid(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.ARRKII_APP_Id, Constants.Preference.DEFAULT_ARRKII_APP_ID);
    }

    public static void setArkkiiPubid(Context context, String arkkiiPubid) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.ARRKII_PUB_Id, arkkiiPubid).apply();
    }

    public static String getArkkiiPubid(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getString(Constants.Preference.ARRKII_PUB_Id, Constants.Preference.DEFAULT_ARRKII_PUB_ID);
    }

    public static void enableArkkii(Context context, boolean enable) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        sp.edit().putBoolean(Constants.Preference.ARRKII_STATE, enable).apply();
    }

    public static boolean isArkkiiEnabled(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, context.MODE_PRIVATE);
        return sp.getBoolean(Constants.Preference.ARRKII_STATE, true);
    }


    public static boolean isAgreePermission(Context context) {
        if (context == null) return false;
        SharedPreferences preferences = context.getSharedPreferences(Constants.Preference.PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(Constants.Preference.IS_AGREE_PERMISSION, false);
    }

    public static void setAgreePermission(Context context,boolean isAgree) {
        if (context == null) return;
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(Constants.Preference.IS_AGREE_PERMISSION,isAgree).apply();
    }

    public static String getUUID(Context context) {
        if (context == null) return "";
        SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME, Context.MODE_PRIVATE);
        String uuid = sp.getString(Constants.Preference.UUID, "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            sp.edit().putString(Constants.Preference.UUID,uuid).apply();
        }
        return uuid;
    }
    public static List<String> getTabFilter(Context context) {
        List<String> filters = new ArrayList<>();
        final SharedPreferences sp = context.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        String tabFilter = sp.getString(Constants.Preference.TABFILTER, Constants.Preference.DEFAULT_TABFILTER);
        filters.addAll(Arrays.asList(tabFilter.split("\\|")));
        return filters;
    }

}
