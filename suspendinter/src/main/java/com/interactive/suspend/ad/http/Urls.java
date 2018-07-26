package com.interactive.suspend.ad.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.util.AesUtils;
import com.interactive.suspend.ad.util.DeviceUtil;
import com.interactive.suspend.ad.util.LogUtil;
import com.interactive.suspend.ad.util.PreferenceUtils;

import java.net.URLEncoder;
import java.util.UUID;



public class Urls {

    public static final String HOST = "http://api.c.avazunativeads.com";

    public static final String DEBUG_HOST = "http://169.55.74.169";
    public static final String AD_RESULT_DEBUG_HOST = "http://192.168.5.222:17087";

    public static String SHARE_GP_URL = "/apw/rcdrefer?";
    public static String GET_APP_LIST = "/c2s?";
    public static String GET_APPWALL = "/appwall?";// encrpt
    public static String GET_NATIVE_AD = "/anative/nativeads?";// encrpt
    public static String GET_REWARD_AD = "/anative/rwdvideoads?";// encrpt
    public static String GET_APP_CONFIG = "/anative/appcfg?";
    public static String REPORT_AD_RESULT = "/aw/jumpinfo?";
    public static String GET_SUBSCRIBE_AD = "/anative/subscribe?";// encrpt
    public static String GET_APPID = "/anative/apppkgcfg?";
    public static String GET_PLAYABLE_AD = "/anative/playableads?"; // encrpt
    public static String GET_SMART_AD = "/anative/smartads?"; // encrpt
    public static String REPORT_INFO = "/anative/infodump?"; // encrpt
    public static String GET_APP_APK = "/pkgversion?";


    public static String buildGetAppWallURL(Context context, String sourceId, String excludePackages,
                                            int limitNumber, int pageNumber, int creatives, String market,
                                            String adpkg, long pkgSize, int requestType) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.DEBUG) {
            stringBuilder.append(DEBUG_HOST);
        } else {
            stringBuilder.append(HOST);
        }
        stringBuilder.append(GET_APPWALL);

        //encrypt
        StringBuilder encryptFileds = new StringBuilder();
        encryptFileds.append("sourceid=").append(sourceId);
        if (limitNumber > 0) {
            encryptFileds.append("&limit=").append(limitNumber);
        }
        if (!TextUtils.isEmpty(adpkg)) {
            encryptFileds.append("&adpkg=").append(adpkg);
        }
        if (pkgSize > 0) {
            encryptFileds.append("&pkgsize=").append(pkgSize);
        }
        encryptFileds.append("&req_type=").append(requestType);
        Log.e(LogUtil.TAG,encryptFileds.toString());
        stringBuilder.append("rinfo=").append(AesUtils.getEncryptBody(encryptFileds.toString()));
        //end encrypt

        if (Constants.DEBUG) { // FUCK: DO NOT OPEN IT!
            SharedPreferences sp = context.getSharedPreferences("country", Context.MODE_PRIVATE);
            String ct = sp.getString("country", "US");
            stringBuilder.append("&country=").append(ct);
        }
        if (pageNumber > 0) {
            stringBuilder.append("&page=").append(pageNumber);
        }
        if (!TextUtils.isEmpty(excludePackages)) {
            stringBuilder.append("&exclude=").append(excludePackages);
        }
        if (creatives == 1) {
            stringBuilder.append("&creatives=").append(creatives);
        }
        if (market == "ddl" || market == "google" || market == "optin") {
            stringBuilder.append("&market=").append(market);
        }
        stringBuilder.append("&incent=").append(2);
        stringBuilder.append(getCommonParams(context));
        if (Constants.SUBSCRIPTION_AD_DEBUG) {
            stringBuilder.append("&clientip=193.54.67.2");
        }
        return stringBuilder.toString();
    }

    public static String buildGetNativeAdURL(Context context, String sourceId, String excludePackages,
                                             int limitNumber, String market, int subType, boolean videoAllow,
                                             String adpkg, long pkgSize, int requestType) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.DEBUG) {
            stringBuilder.append(DEBUG_HOST);
        } else {
            stringBuilder.append(HOST);
        }
        stringBuilder.append(GET_NATIVE_AD);

        //encrypt
        StringBuilder encryptFileds = new StringBuilder();
        encryptFileds.append("sourceid=").append(sourceId);
        if (limitNumber > 0) {
            encryptFileds.append("&limit=").append(limitNumber);
        }
        if (!TextUtils.isEmpty(adpkg)) {
            encryptFileds.append("&adpkg=").append(adpkg);
        }
        if (pkgSize > 0) {
            encryptFileds.append("&pkgsize=").append(pkgSize);
        }
        encryptFileds.append("&req_type=").append(requestType);
        Log.e(LogUtil.TAG,encryptFileds.toString());
        stringBuilder.append("rinfo=").append(AesUtils.getEncryptBody(encryptFileds.toString()));
        //end encrypt

        if (Constants.DEBUG) { // FUCK: DO NOT OPEN IT!
            SharedPreferences sp = context.getSharedPreferences("country", Context.MODE_PRIVATE);
            String ct = sp.getString("country", "US");
            stringBuilder.append("&country=").append(ct);
        }
        if (subType != -1) {
            stringBuilder.append("&slotstyle=").append(subType);
        }
        if (!TextUtils.isEmpty(excludePackages)) {
            stringBuilder.append("&exclude=").append(excludePackages);
        }
        if (market == "ddl" || market == "google" || market == "optin") {
            stringBuilder.append("&market=").append(market);
        }
        if (videoAllow) {
            stringBuilder.append("&video=1");
        }
        stringBuilder.append(getCommonParams(context));
        return stringBuilder.toString();
    }

    public static String buildGetSmartAdURL(Context context, String sourceId, String excludePackages,
                                            int limitNumber, String market, String adpkg, long pkgSize, int requestType) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.DEBUG) {
            stringBuilder.append(DEBUG_HOST);
        } else {
            stringBuilder.append(HOST);
        }
        stringBuilder.append(GET_SMART_AD);

        //encrypt
        StringBuilder encryptFileds = new StringBuilder();
        encryptFileds.append("sourceid=").append(sourceId);
        if (limitNumber > 0) {
            encryptFileds.append("&limit=").append(limitNumber);
        }
        if (!TextUtils.isEmpty(adpkg)) {
            encryptFileds.append("&adpkg=").append(adpkg);
        }
        if (pkgSize > 0) {
            encryptFileds.append("&pkgsize=").append(pkgSize);
        }
        encryptFileds.append("&req_type=").append(requestType);
        Log.e(LogUtil.TAG,encryptFileds.toString());
        stringBuilder.append("rinfo=").append(AesUtils.getEncryptBody(encryptFileds.toString()));
        //end encrypt

        if (Constants.DEBUG) { // FUCK: DO NOT OPEN IT!
            SharedPreferences sp = context.getSharedPreferences("country", Context.MODE_PRIVATE);
            String ct = sp.getString("country", "US");
            stringBuilder.append("&country=").append(ct);
        }
        if (!TextUtils.isEmpty(excludePackages)) {
            stringBuilder.append("&exclude=").append(excludePackages);
        }
        if (market == "ddl" || market == "google" || market == "optin") {
            stringBuilder.append("&market=").append(market);
        }
        stringBuilder.append(getCommonParams(context));
        return stringBuilder.toString();
    }

    public static String buildGetPlayableAdURL(Context context, String sourceId, String excludePackages,
                                               int limitNumber, String market, String adpkg, long pkgSize, int requestType) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.DEBUG) {
            stringBuilder.append(DEBUG_HOST);
        } else {
            stringBuilder.append(HOST);
        }
        stringBuilder.append(GET_PLAYABLE_AD);

        //encrypt
        StringBuilder encryptFileds = new StringBuilder();
        encryptFileds.append("sourceid=").append(sourceId);
        if (limitNumber > 0) {
            encryptFileds.append("&limit=").append(limitNumber);
        }
        if (!TextUtils.isEmpty(adpkg)) {
            encryptFileds.append("&adpkg=").append(adpkg);
        }
        if (pkgSize > 0) {
            encryptFileds.append("&pkgsize=").append(pkgSize);
        }
        encryptFileds.append("&req_type=").append(requestType);
        Log.e(LogUtil.TAG,encryptFileds.toString());
        stringBuilder.append("rinfo=").append(AesUtils.getEncryptBody(encryptFileds.toString()));
        //end encrypt

        if (Constants.DEBUG) { // FUCK: DO NOT OPEN IT!
            SharedPreferences sp = context.getSharedPreferences("country", Context.MODE_PRIVATE);
            String ct = sp.getString("country", "US");
            stringBuilder.append("&country=").append(ct);
        }
        if (!TextUtils.isEmpty(excludePackages)) {
            stringBuilder.append("&exclude=").append(excludePackages);
        }
        if (market == "ddl" || market == "google" || market == "optin") {
            stringBuilder.append("&market=").append(market);
        }
        stringBuilder.append(getCommonParams(context));
        return stringBuilder.toString();
    }

    public static String buildGetRewardAdURL(Context context, String sourceId, String excludePackages,
                                             int limitNumber, String market, String adpkg, long pkgSize, int requestType) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.DEBUG) {
            stringBuilder.append(DEBUG_HOST);
        } else {
            stringBuilder.append(HOST);
        }
        stringBuilder.append(GET_REWARD_AD);

        //encrypt
        StringBuilder encryptFileds = new StringBuilder();
        encryptFileds.append("sourceid=").append(sourceId);
        if (limitNumber > 0) {
            encryptFileds.append("&limit=").append(limitNumber);
        }
        if (!TextUtils.isEmpty(adpkg)) {
            encryptFileds.append("&adpkg=").append(adpkg);
        }
        if (pkgSize > 0) {
            encryptFileds.append("&pkgsize=").append(pkgSize);
        }
        encryptFileds.append("&req_type=").append(requestType);
        Log.e(LogUtil.TAG,encryptFileds.toString());
        stringBuilder.append("rinfo=").append(AesUtils.getEncryptBody(encryptFileds.toString()));
        //end encrypt

        if (Constants.DEBUG) { // FUCK: DO NOT OPEN IT!
            SharedPreferences sp = context.getSharedPreferences("country", Context.MODE_PRIVATE);
            String ct = sp.getString("country", "US");
            stringBuilder.append("&country=").append(ct);
        }
        if (!TextUtils.isEmpty(excludePackages)) {
            stringBuilder.append("&exclude=").append(excludePackages);
        }
        if (market == "ddl" || market == "google" || market == "optin") {
            stringBuilder.append("&market=").append(market);
        }
        stringBuilder.append(getCommonParams(context));
        return stringBuilder.toString();
    }

    public static String buildGetSubscribeAdURL(Context context, String sourceId, int limitNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.DEBUG) {
            stringBuilder.append(DEBUG_HOST);
        } else {
            stringBuilder.append(HOST);
        }
        stringBuilder.append(GET_SUBSCRIBE_AD);

        //encrypt
        StringBuilder encryptFileds = new StringBuilder();
        encryptFileds.append("sourceid=").append(sourceId);
        if (limitNumber > 0) {
            encryptFileds.append("&limit=").append(limitNumber);
        }
        Log.e(LogUtil.TAG,encryptFileds.toString());
        stringBuilder.append("rinfo=").append(AesUtils.getEncryptBody(encryptFileds.toString()));
        //end encrypt

        if (Constants.DEBUG) { // FUCK: DO NOT OPEN IT!
            SharedPreferences sp = context.getSharedPreferences("country", Context.MODE_PRIVATE);
            String ct = sp.getString("country", "US");
            stringBuilder.append("&country=").append(ct);
        }
        stringBuilder.append("&image_size=").append("1200x627");
        stringBuilder.append(getCommonParams(context));
        return stringBuilder.toString();
    }

    public static String buildGetAppListURL(Context context, String sourceId, String excludePackages,
                                            int limitNumber, int pageNumber, int creatives, String market) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HOST)
                .append(GET_APP_LIST)
                .append(String.format("sourceid=%s", sourceId));

        if (limitNumber > 0) {
            stringBuilder.append("&limit=").append(limitNumber);
        }
        if (pageNumber > 0) {
            stringBuilder.append("&page=").append(pageNumber);
        }
        if (!TextUtils.isEmpty(excludePackages)) {
            stringBuilder.append("&exclude=").append(excludePackages);
        }
        if (creatives == 1) {
            stringBuilder.append("&creatives=").append(creatives);
        }
        if (market == "ddl" || market == "google" || market == "optin") {
            stringBuilder.append("&market=").append(market);
        }
        stringBuilder.append(getCommonParams(context));
        if (Constants.SUBSCRIPTION_AD_DEBUG) {
            stringBuilder.append("&clientip=193.54.67.2");
        }
        return stringBuilder.toString();
    }

    public static String buildReportPkgInfo(String pkg, long size) {
        StringBuilder sb = new StringBuilder();
        if (Constants.DEBUG) {
            sb.append(DEBUG_HOST);
        } else {
            sb.append(HOST);
        }
        sb.append(REPORT_INFO);
        //encrypt
        StringBuilder encryptFileds = new StringBuilder();
        encryptFileds.append("pkginfo=").append(pkg).append("_").append(size);
        Log.e(LogUtil.TAG,encryptFileds.toString());
        sb.append("rinfo=").append(AesUtils.getEncryptBody(encryptFileds.toString()));
        //end encrypt
        sb.append("&sdkversion=").append(Constants.SDK_VERSION);
        return sb.toString();
    }

    public static String buildReportRewardEvent(Context context, String sourceId, int rewardEvent) {
        StringBuilder sb = new StringBuilder();
        if (Constants.DEBUG) {
            sb.append(DEBUG_HOST);
        } else {
            sb.append(HOST);
        }
        sb.append(REPORT_INFO);
        //encrypt
        StringBuilder encryptFileds = new StringBuilder();
        encryptFileds.append("sourceid=").append(sourceId)
                .append("&re=").append(rewardEvent);
        if (PreferenceUtils.isAgreePermission(context)) {
            encryptFileds.append("&maid=").append(DeviceUtil.getAndroidId(context));
        } else {
            encryptFileds.append("&defaultid=").append(PreferenceUtils.getUUID(context)).append("&gdpr=false");
        }
        Log.e(LogUtil.TAG,encryptFileds.toString());
        sb.append("rinfo=").append(AesUtils.getEncryptBody(encryptFileds.toString()));
        //end encrypt
        sb.append("&sdkversion=").append(Constants.SDK_VERSION);
        return sb.toString();
    }

    public static String buildGetAppConfigURL(Context context, String appId) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.DEBUG) {
            stringBuilder.append(DEBUG_HOST);
        } else {
            stringBuilder.append(HOST);
        }
        stringBuilder.append(GET_APP_CONFIG)
                .append("appid=").append(appId)
                .append("&version=").append(PreferenceUtils.getAppConfigVersion(context))
                .append("&sdkversion=").append(Constants.SDK_VERSION)
                .append("&pkg=").append(context.getPackageName());
        if (PreferenceUtils.isAgreePermission(context)) {
            stringBuilder.append("&androidid=").append(DeviceUtil.getAndroidId(context));
        } else {
            stringBuilder.append("&defaultid=").append(PreferenceUtils.getUUID(context))
                    .append("&gdpr=false");
        }
        if (Constants.DEBUG_LOG || Constants.DEBUG) {
            stringBuilder.append("&d=1");
        }
        return stringBuilder.toString();
    }

    public static String buildGetAppidUrl(Context context) {
        StringBuilder str = new StringBuilder();
        if (Constants.DEBUG) {
            str.append(DEBUG_HOST);
        } else {
            str.append(HOST);
        }
        str.append(GET_APPID)
                .append("pkg=").append(context.getPackageName())
                .append(getCommonParams(context));
        if (Constants.DEBUG_LOG || Constants.DEBUG) {
            str.append("&d=1");
        }
        return str.toString();
    }


    public static String buildAdClickResultURL(Context context, String sourceid,  String campaginId, String country,
                       int clkType, int clkResult, int jumpCount, long jumpTime) {
        StringBuilder sb = new StringBuilder();
        if (Constants.DEBUG) {
            sb.append(AD_RESULT_DEBUG_HOST);
        } else {
            sb.append(HOST);
        }
        sb.append(REPORT_AD_RESULT)
                .append("adid=").append(campaginId)
                .append("&sourceid=").append(sourceid)
                .append("&country=").append(country)
                .append("&connectiontype=").append(DeviceUtil.getNetworkType(context))
                .append("&jump_count=").append(jumpCount)
                .append("&jump_time=").append(jumpTime)
                .append("&clk_result=").append(clkResult)
                .append("&clk_type=").append(clkType)
                .append("&sdk_version=").append(Constants.SDK_VERSION);
        return sb.toString();
    }

    public static String getCommonParams(Context context) {
        String str = "";
        if (!PreferenceUtils.isAgreePermission(context)) {
            String param = "&sdkversion=%s&pkg=%s&os=android&language=%sreqId=%s&defaultid=%s&gdpr=false";
            try {
                str = String.format(param, Constants.SDK_VERSION, context.getPackageName(),
                        DeviceUtil.getLanguage(), UUID.randomUUID().toString(),
                        PreferenceUtils.getUUID(context));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String param = "&deviceid=%s&sdkversion=%s&pkg=%s&ua=%s&os=android&language=%s&" +
                    "reqId=%s&maid=%s&gpid=%s";

            try {
                str = String.format(param, DeviceUtil.getDeviceId(context), Constants.SDK_VERSION,
                        context.getPackageName(), URLEncoder.encode(PreferenceUtils.getUserAgent(context), "UTF-8"),
                        DeviceUtil.getLanguage(),
                        UUID.randomUUID().toString(),
                        DeviceUtil.getAndroidId(context),
                        DeviceUtil.getGoogleAdvertisingId(context));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return str;
    }


}
