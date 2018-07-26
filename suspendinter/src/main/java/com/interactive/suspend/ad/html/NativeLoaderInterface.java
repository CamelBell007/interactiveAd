package com.interactive.suspend.ad.html;


/**
 * Created by guojia on 2016/10/20.
 */

public interface NativeLoaderInterface extends BaseLoaderInterface {
    void load(IApxNativeAdListener listener, boolean forceReload, String excludeAds, boolean cacheClean, int num);
}
