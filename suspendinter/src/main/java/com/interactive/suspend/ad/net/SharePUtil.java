package com.interactive.suspend.ad.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.interactive.suspend.ad.constant.AdConstants;

/**
 * Created by vincent on 2016/5/9.
 */
public class SharePUtil {

    public static String getString(Context context, String key, String defaultValue) {
        if (context == null) {
            return "";
        }
        SharedPreferences settings = context.getSharedPreferences(AdConstants.PREF_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(AdConstants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(AdConstants.PREF_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(AdConstants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get ad info form dish
     * @param context
     * @return
     */
    public static String getAdConfigInfo(Context context){
        String fullPath = getString(context,AdConstants.CONFIG_FULL_PATH,"");
//        MyLog.d(MyLog.TAG, "get newFullPath:" + fullPath );
        return getString(context,fullPath,"");
    }

    public static String getApxAdConfigInfo(Context context) {
        String fullPath = getString(context,AdConstants.APX_CONFIG_PATH,"");
        return getString(context,fullPath,"");
    }


    public static void setAdConfigInfo(Context context, String key, String value){
        try {
            putString(context,key,value);
        } catch (Throwable e) {
//            MyLog.e(MyLog.TAG, "set ad configinfo error!");
        }
    }

}
