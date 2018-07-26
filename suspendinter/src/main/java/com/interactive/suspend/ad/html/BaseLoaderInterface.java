package com.interactive.suspend.ad.html;

/**
 * Created by guojia on 2016/10/20.
 */

public interface BaseLoaderInterface {
    void preload();
    void load(IAdLoadListener listener, boolean forceReload, boolean cacheClean, String adpkg, int requestType);
    void load(IAdLoadListener listener, boolean forceReload, boolean cacheClean, long adpkgSize, int requestType);
}
