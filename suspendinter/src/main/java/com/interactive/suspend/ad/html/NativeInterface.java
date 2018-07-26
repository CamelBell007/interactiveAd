package com.interactive.suspend.ad.html;

import android.content.Context;

/**
 * Created by hongwu on 6/26/17.
 */

public interface NativeInterface {
    NativeLoaderInterface getNativeLoader(Context context, int nativeSubType, boolean videoAllow);

    SubscribeLoaderInterface getSubscribeLoader(Context context);
}
