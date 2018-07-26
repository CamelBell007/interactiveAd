package com.interactive.suspend.ad.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hongwu on 9/19/17.
 * 通过包名获取APPID:无appid传入的时候，通过包名获取对应的APPid,做功能的初始化。
 */

public class FetchAppId {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public AppIdWrapper appidWrapper;

    public static final class AppIdWrapper {
        @SerializedName("appid")
        public String appid;
    }

    public static boolean isFailed(FetchAppId result) {
        if (result == null || result.appidWrapper == null
                || TextUtils.isEmpty(result.appidWrapper.appid) || !"OK".equals(result.status)) {
            return true;
        }
        return false;
    }
}
