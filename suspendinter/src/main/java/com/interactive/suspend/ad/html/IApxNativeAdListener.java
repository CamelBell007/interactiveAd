package com.interactive.suspend.ad.html;

import java.util.List;

/**
 * Created by hongwu on 5/18/17.
 */

public interface IApxNativeAdListener<T> {
    void onError(String msg);

    void onAdLoaded(List<T> list);

    void onAdClicked(T info);

    void onAdShowed();

    void onAdClosed();
}
