package com.interactive.suspend.ad.html;

import android.content.Context;
import android.util.Log;

import com.interactive.suspend.ad.util.LogUtil;


/**
 * Created by guojia on 2016/10/20.
 */

public class SubscribeLoader implements SubscribeLoaderInterface {

    private static SubscribeLoaderInterface sInstance;

    public static SubscribeLoaderInterface newInstance(Context ctx) {
        NativeInterface nativeInterface = SdkLoader.getInstance(ctx).newNtInterface();
        if (nativeInterface != null) {
            return nativeInterface.getSubscribeLoader(ctx);
        }
        return null;
    }

    public static synchronized SubscribeLoaderInterface getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = newInstance(ctx);
        }
        return sInstance;
    }

    @Override
    public void load(IApxNativeAdListener listener, boolean forceReload, boolean cacheClean, int num) {
        if (sInstance != null) {
            sInstance.load(listener, forceReload, cacheClean, num);
        } else {
            Log.e(LogUtil.TAG,"No subscribe preloader");
        }
    }
}
