package com.interactive.suspend.ad.html;

import com.interactive.suspend.ad.db.SubscribeAdInfo;

/**
 * Created by guojia on 2016/10/20.
 */

public interface SubscribeLoaderInterface {
    void load(IApxNativeAdListener<SubscribeAdInfo> listener, boolean forceReload, boolean cacheClean, int num);
}
