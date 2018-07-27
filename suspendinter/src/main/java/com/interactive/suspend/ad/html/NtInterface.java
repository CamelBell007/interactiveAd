package com.interactive.suspend.ad.html;


import android.content.Context;

/**
 * Created by hongwu on 6/26/17.
 */

public class NtInterface implements NativeInterface {

    private static NtInterface sInstance = null;

    public synchronized static NtInterface getInstance() {
        if (sInstance == null) {
            sInstance = new NtInterface();
        }
        return sInstance;
    }

    public synchronized NativeLoaderInterface getNativeLoader(Context context, int nativeSubType, boolean videoAllow) {
        return new com.interactive.suspend.ad.html.load.NativeLoader(context, nativeSubType, videoAllow);
    }

    public synchronized SubscribeLoaderInterface getSubscribeLoader(Context context) {
        return new com.interactive.suspend.ad.html.load.SubscribeLoader(context);
    }
}
