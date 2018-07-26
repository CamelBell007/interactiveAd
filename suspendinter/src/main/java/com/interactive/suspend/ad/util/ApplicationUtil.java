package com.interactive.suspend.ad.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.html.AdInfo;
import com.interactive.suspend.ad.html.SubscribeAdInfo;
import com.interactive.suspend.ad.task.AdReportTrueClickTask;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by VC on 2018/6/28.
 *
 */

public class ApplicationUtil {
    private static String[] AVOID_BUILD_MODEL = {"J7","G610M"};

    public  static void installApk(Context context,File file) {
        if(context == null){
            return;
        }
        if(file == null){
            return;
        }
        Intent intent  = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        String dataAndType = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(file), dataAndType);
        context.startActivity(intent);
    }

    public static boolean shouldShowSubscribeAd(Context context, int adType, int optinRate) {
        if (context == null || adType < 0 || optinRate < 0 || optinRate > 100) {
            return false;
        }
        if (isNativeInstallAdAllow(adType) && !isNativeSubscribeAdAllow(adType)) { // only allow install ad
            return false;
        }
        if (!isNativeInstallAdAllow(adType) && isNativeSubscribeAdAllow(adType)) { // only allow subscribe ad
            return true;
        }
        if (isNativeInstallAdAllow(adType) && isNativeSubscribeAdAllow(adType)) { // both allow install and subscribe ads
            int num = 1 + (int) (Math.random() * 100); // [1, 100],  0 <= Math.random() < 1
            Log.e("random num: " + num ,", rate: " + optinRate);
            if (num <= optinRate) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static boolean isNativeInstallAdAllow(final int adType) {
        return (adType & Constants.NativeAdType.NATIVE_AD_TYPE_INSTALL) == Constants.NativeAdType.NATIVE_AD_TYPE_INSTALL;
    }

    public static boolean isNativeSubscribeAdAllow(final int adType) {
        return (adType & Constants.NativeAdType.NATIVE_AD_TYPE_SUBSCRIBE) == Constants.NativeAdType.NATIVE_AD_TYPE_SUBSCRIBE;
    }

    public static <T> boolean contains(List<T> list, T info) {
        if (list == null || info == null) {
            return false;
        }
        if (info instanceof AdInfo) {
            for (T ad : list) {
                if (((AdInfo) ad).campaignid.equals(((AdInfo) info).campaignid)) {
                    return true;
                }
            }
        } else if (info instanceof SubscribeAdInfo) {
            for (T ad : list) {
                if (((SubscribeAdInfo) ad).campaignid.equals(((SubscribeAdInfo) info).campaignid)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return true;
        }
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (Exception e){
            Log.e(LogUtil.TAG,e.getMessage());
        } catch (Error e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        return true;
    }

    public static String initUserAgent(Context context) {
        String ua = Constants.Preference.DEFAULT_UA;

        if (!PreferenceUtils.isAgreePermission(context)) {
            return ua;
        }
        //if getUserAgent from sick Webkit system ,use default UA;
        for(int i =0;i < AVOID_BUILD_MODEL.length;i++){
            if(android.os.Build.MODEL.contains(AVOID_BUILD_MODEL[i])){
                Log.d("aNative","catch the bad guy:"+AVOID_BUILD_MODEL[i]);
                return ua;
            }
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ua = WebSettings.getDefaultUserAgent(context);
            } else {
                ua = new WebView(context).getSettings().getUserAgentString();
            }
        } catch (Throwable e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        Log.d("aNative","catch the result guy:"+ua);
        return ua;
    }

    public static int isViewVisible(View adView, boolean judgeVisibleArea, int visibleThreshold) {
        if (adView == null) {
            Log.e(LogUtil.TAG,"adView is null.");
            return Constants.ImpCheck.EMPTY_ADVIEW;
        } else if (adView.getParent() == null) {
            Log.e(LogUtil.TAG,"adView has no parent.");
            return Constants.ImpCheck.NO_PARENT;
        } else if (adView.getWindowVisibility() != View.VISIBLE) {   // ?
            Log.e(LogUtil.TAG,"adView window is not set to VISIBLE.");
            return Constants.ImpCheck.WINDOW_INVISIBLE;
        } else if (adView.getVisibility() != View.VISIBLE) {
            Log.e(LogUtil.TAG,"adView is not set to VISIBLE.");
            return Constants.ImpCheck.ADVIEW_INVISIBLE;
        } else if (DeviceUtil.px2dp(adView.getContext(), adView.getMeasuredWidth()) > 30
                && DeviceUtil.px2dp(adView.getContext(), adView.getMeasuredHeight()) > 30) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (adView.getAlpha() < 0.9F) {
                    Log.e(LogUtil.TAG,"adView is too transparent.");
                    return Constants.ImpCheck.TOO_TRANSPARENT;
                }
            }
            int var2 = adView.getWidth();
            int var3 = adView.getHeight();
            int[] var4 = new int[2];

            try {
                adView.getLocationOnScreen(var4);
            } catch (Throwable var13) {
                Log.e(LogUtil.TAG,"Cannot get location on screen.");
                return Constants.ImpCheck.NO_LOCATION_ON_SCREEN;
            }

            Context var5 = adView.getContext();
            DisplayMetrics var6 = var5.getResources().getDisplayMetrics();
            Rect var7 = new Rect(0, 0, var6.widthPixels, var6.heightPixels);
            float var8 = 0.0F;
            if (var7.intersect(var4[0], var4[1], var4[0] + var2, var4[1] + var3)) {
                var8 = (float) (var7.width() * var7.height()) * 1.0F / (float) (var2 * var3);
            }

            if (judgeVisibleArea) {
                var8 *= 100.0F; // daview在屏幕可见区域所占adview整个面积的百分比
                if (var8 < (float) visibleThreshold) {
                    Log.e(LogUtil.TAG,String.format("adView visible area is too small [%.2f%% visible, current threshold %d%%]",
                            new Object[]{Float.valueOf(var8), Integer.valueOf(visibleThreshold)}));
                    return Constants.ImpCheck.VISIBLE_AREA_TOO_SMALL;
                }
            } else {
            }

            Map var14 = WindowUtils.a(var5);
            if (WindowUtils.a(var14)) {
                Log.e(LogUtil.TAG,"Keyguard is obstructing view.");
                return Constants.ImpCheck.OBSTRUCTED_BY_KEYGUARD;
            }

            Log.e(LogUtil.TAG,"adView is visible.");
            return Constants.ImpCheck.VISIBLE_ADVIEW;
        } else {
            Log.e(LogUtil.TAG,"adView has invisible dimensions (w=" + adView.getMeasuredWidth() + ", h=" + adView.getMeasuredHeight() + ")");
            return Constants.ImpCheck.INVALID_DIMENSIONS;
        }
    }

    public static void jumpToWebSite(Context context, SubscribeAdInfo info, String sourceid) {
        if (info == null) {
            return;
        }
        Uri uri = Uri.parse(info.clkurl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // report notice
        String noticeUrl = info.noticeUrl + "&replace_src=" + sourceid;
        new AdReportTrueClickTask(context, noticeUrl, 0, true, info.campaignid, "unknown", -1,
                PreferenceUtils.getNativeSourceId(context.getApplicationContext())).execute();
    }
}
