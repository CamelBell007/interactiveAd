package com.interactive.suspend.ad.http;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.html.FetchAdResult;
import com.interactive.suspend.ad.html.FetchAppConfigResult;
import com.interactive.suspend.ad.html.FetchSubscribeAdResult;
import com.interactive.suspend.ad.model.FetchAppId;
import com.interactive.suspend.ad.util.AesUtils;
import com.interactive.suspend.ad.util.ApplicationUtil;
import com.interactive.suspend.ad.util.DeviceUtil;
import com.interactive.suspend.ad.util.LogUtil;
import com.interactive.suspend.ad.util.PreferenceUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class NetworkUtils {

    private static final int READ_TIME_OUT = 30000;
    private static final int CONNECT_TIME_OUT = 30000;

    private static final String TAG = "network";

    public static FetchSubscribeAdResult fetchSubscribeAd(Context context, String sourceid, int limitNum) {
        if (!ApplicationUtil.isNetworkAvailable(context)) {
            return null;
        }
        String url = Urls.buildGetSubscribeAdURL(context, sourceid, limitNum);
        String ret = doGET(url, PreferenceUtils.getUserAgent(context));
        if (TextUtils.isEmpty(ret)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(ret, FetchSubscribeAdResult.class);
        } catch (Throwable e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        return null;
    }

    public static FetchAdResult fetchAd(Context context, String sourceId, String excludePackages,
                                        int limitNumber, int pageNumber, int creatives, String market,
                                        String apxAdType, int subType, boolean videoAllow, String adpkg,
                                        long adpkgSize, int requestType) {
        if (!ApplicationUtil.isNetworkAvailable(context)) {
            return null;
        }
        String url = "";
        switch (apxAdType) {
            case Constants.ApxAdType.NATIVE:
                url = Urls.buildGetNativeAdURL(context, sourceId, excludePackages, limitNumber, market, subType, videoAllow, adpkg,
                        adpkgSize, requestType);
                break;
        }

        // analytics
        long mLoadStartTime = 0, mLoadEndTime = 0;
        if (apxAdType.equals(Constants.ApxAdType.APPWALL)) {
            mLoadStartTime = SystemClock.elapsedRealtime();
        }
        String ret = doGET(url, PreferenceUtils.getUserAgent(context));
        if (apxAdType.equals(Constants.ApxAdType.APPWALL)) {
            mLoadEndTime = SystemClock.elapsedRealtime();
//            AnalyticsMgr.getInstance().uploadApiResponseEvent(context, mLoadStartTime, mLoadEndTime, PreferenceUtils.getMarketSourceId(context));
        }

        if (TextUtils.isEmpty(ret)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(ret, FetchAdResult.class);
        } catch (Throwable e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        return null;
    }

    public static FetchAppConfigResult fetchAppConfig(Context context, String appId) {
        if (!ApplicationUtil.isNetworkAvailable(context)) {
            return null;
        }
        final String url = Urls.buildGetAppConfigURL(context, appId);
        String ret = doGET(url, PreferenceUtils.getUserAgent(context));
        if (TextUtils.isEmpty(ret)) {
            return null;
        } else {
            if (!Constants.DEBUG_LOG && !Constants.DEBUG) {
                ret = AesUtils.getDecryptBody(ret);
                if (TextUtils.isEmpty(ret)) {
                    return null;
                }
            }
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(ret, FetchAppConfigResult.class);
        } catch (Throwable e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        return null;
    }


    public static FetchAppId fetchAppId(Context context) {
        if (context == null) {
            return null;
        }
        final String url = Urls.buildGetAppidUrl(context);
        String ret = doGET(url, PreferenceUtils.getUserAgent(context));
        if (TextUtils.isEmpty(ret)) {
            return null;
        } else {
            if (!Constants.DEBUG_LOG && !Constants.DEBUG) {
                ret = AesUtils.getDecryptBody(ret);
                if (TextUtils.isEmpty(ret)) {
                    return null;
                }
            }
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(ret, FetchAppId.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }




    public static boolean reportPkgInfo(Context context, String pkg, long size) {
//        L.d("VC--AddWrapper " , "matchPkgSize UPLOAD_PKG_SIZE-->pkg:"+pkg+"  size:"+size);
        if (context == null || TextUtils.isEmpty(pkg) || size <= 0) {
            return false;
        }
        String str = Urls.buildReportPkgInfo(pkg, size);
        try {
            URL url = new URL(str);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", PreferenceUtils.getUserAgent(context));
            if (conn.getResponseCode() == 200) {
                return true;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }



    public static boolean reportRewardEvent(Context context, String sourceId, int rewardEvent) {
        if (context == null || TextUtils.isEmpty(sourceId)) {
            return false;
        }
        String str = Urls.buildReportRewardEvent(context, sourceId, rewardEvent);
        try {
            URL url = new URL(str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECT_TIME_OUT);
            connection.setReadTimeout(READ_TIME_OUT);
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", PreferenceUtils.getUserAgent(context));
            if (connection.getResponseCode() == 200) {
                return true;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int reportTrueClick(Context context, String pingUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(pingUrl);
        if (PreferenceUtils.isAgreePermission(context)) {
            sb.append("&device_id=");
            sb.append(DeviceUtil.getDeviceId(context));
        } else {
            sb.append("&defaultid=").append(PreferenceUtils.getUUID(context)).append("&gdpr=false");
        }

        sb.append("&sdkverion=").append(Constants.SDK_VERSION);
        try {
            URL url = new URL(sb.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", PreferenceUtils.getUserAgent(context));
            return conn.getResponseCode();
        } catch (Exception e) {
            return 601;
        } catch (Error e){
            return 602;
        }
    }

    public static boolean reportGP(Context context, String gpurl) {
        if (context == null || TextUtils.isEmpty(gpurl)) {
            return false;
        }
        try {
            URL url = new URL(gpurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", PreferenceUtils.getUserAgent(context));
            int rspCode = conn.getResponseCode();
            if (rspCode != 200) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        return false;
    }


    public static boolean reportAdClickResult(Context context, String sourceid, String campaignId, String country,
                          int clkType, int clkResult, int jumpCount, long jumpTime) {
//        L.e("AdJumpHelper","reprot-result:"+clkResult);
        final String str = Urls.buildAdClickResultURL(context, sourceid, campaignId, country, clkType, clkResult, jumpCount, jumpTime);
//        L.e("AdJumpHelper","reprot-params:"+str);
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        try {
            URL url = new URL(str);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", PreferenceUtils.getUserAgent(context));
            int rspCode = conn.getResponseCode();
            if (rspCode != 200) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            Log.e(LogUtil.TAG, e.getMessage());
        }
        return false;
    }

    public static boolean reportDspDisplay(Context context, String pingUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(pingUrl);
        Log.e(LogUtil.TAG,pingUrl);
        try {
            URL url = new URL(sb.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", PreferenceUtils.getUserAgent(context));
            int rspCode = conn.getResponseCode();
            if (rspCode != 200) {
                return false;
            }
            Log.e(LogUtil.TAG,"success");
            return true;
        } catch (Throwable e) {
            Log.e(LogUtil.TAG,TAG, e);
        }
        return false;
    }

    public static boolean reportAdImpression(Context context, String url) {
        try {
            URL reportUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) reportUrl.openConnection();
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", PreferenceUtils.getUserAgent(context));
            int rspCode = conn.getResponseCode();
            if (rspCode != 200) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            Log.e(LogUtil.TAG, e.getMessage());
        }
        return false;
    }


    private static String doPOST(String _url, String userAgent, byte[] data) {
        try {
            URL url = new URL(_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", userAgent);
            conn.getOutputStream().write(data);
            conn.getOutputStream().flush();
            int rspCode = conn.getResponseCode();
            if (rspCode >= 400) {
                return null;
            }

            byte[] buffer = new byte[8192];
            BufferedInputStream bis = null;
            ByteArrayOutputStream baos = null;
            try {
                bis = new BufferedInputStream(conn.getInputStream());
                baos = new ByteArrayOutputStream();
                int len;
                while ((len = bis.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                return new String(baos.toByteArray());
            } finally {
                if (bis != null) {
                    bis.close();
                }

                if (baos != null) {
                    baos.close();
                }
            }
        } catch (Throwable e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        return null;
    }

    private static String doGET(String _url, String userAgent) {
        Log.e(LogUtil.TAG,"[doGET]  _url"+_url);
        try {
            URL url = new URL(_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent", userAgent);
            int rspCode = conn.getResponseCode();
            Log.e(LogUtil.TAG,"rspCode" + rspCode);
//            if (rspCode >= 400) {
//                return null;
//            }

            byte[] buffer = new byte[8192];
            BufferedInputStream bis = null;
            ByteArrayOutputStream baos = null;
            try {
                bis = new BufferedInputStream(conn.getInputStream());
                baos = new ByteArrayOutputStream();
                int len;
                while ((len = bis.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                final String ret = new String(baos.toByteArray());
//                Log.d("Camera360","respond content"+ ret);
                Log.e(LogUtil.TAG,"[doGET] " + ret);
                return ret;
            } finally {
                if (bis != null) {
                    bis.close();
                }

                if (baos != null) {
                    baos.close();
                }
            }
        } catch (Throwable e) {
            Log.d("Camera360","request exception:"+e.getMessage());
        }
        return null;
    }


    public static boolean downloadFile(String _url, String path) {
        try {

            URL url = new URL(_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Android");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            int rspCode = conn.getResponseCode();
            if (rspCode >= 400) {
                return false;
            }

            byte[] buffer = new byte[8192];
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            try {
                bis = new BufferedInputStream(conn.getInputStream());
                fos = new FileOutputStream(path);
                int len;
                while ((len = bis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();
            } finally {
                if (bis != null) {
                    bis.close();
                }

                if (fos != null) {
                    fos.close();
                }
            }

            return true;
        } catch (Throwable e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        return false;
    }
}
