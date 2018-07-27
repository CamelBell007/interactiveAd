package com.interactive.suspend.ad.html;

import android.content.Context;
import android.util.Log;

import com.interactive.suspend.ad.util.LogUtil;

/**
 * Created by guojia on 2016/10/20.
 */

public class NativeLoader implements NativeLoaderInterface {

    private static NativeLoaderInterface sInstance;

    public static NativeLoaderInterface newInstance(Context ctx, int nativeSubType, boolean videoAllow) {
        NativeInterface nativeInterface = SdkLoader.getInstance(ctx).newNtInterface();
        if (nativeInterface != null) {
            return nativeInterface.getNativeLoader(ctx, nativeSubType, videoAllow);
        }
        return null;
    }

    public static synchronized NativeLoaderInterface getInstance(Context ctx, int nativeSubType, boolean videoAllow) {
        if (sInstance == null) {
            sInstance = newInstance(ctx, nativeSubType, videoAllow);
        }
        return sInstance;
    }

    // for display
    public void preload() {
        if (sInstance != null) {
            sInstance.preload();
        } else {
            Log.e(LogUtil.TAG,"No native preloader");
        }
    }

    public void load(IAdLoadListener listener, boolean forceReload, boolean cacheClean, String adpkg, int requestType) {
        if (sInstance != null) {
            sInstance.load(listener, forceReload, cacheClean, adpkg, requestType);
        } else {
            Log.e(LogUtil.TAG,"No native preloader");
        }
    }

    @Override
    public void load(IAdLoadListener listener, boolean forceReload, boolean cacheClean, long adpkgSize, int requestType) {
        if (sInstance != null) {
            sInstance.load(listener, forceReload, cacheClean, adpkgSize, requestType);
        } else {
            Log.e(LogUtil.TAG,"No native preloader");
        }
    }

    // for display
    public void load(IApxNativeAdListener listener, boolean forceReload, String excludeAds, boolean cacheClean, int num) {
        if (sInstance != null) {
            sInstance.load(listener, forceReload, excludeAds, cacheClean, num);
        } else {
            Log.e(LogUtil.TAG,"No native preloader");
        }
    }

}
