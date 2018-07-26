package com.interactive.suspend.ad.html;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */

public interface INativeAdLoadListener {
    void onAdListLoaded(List<INativeAd> ads);
    void onError(String error);
    void onAdClicked(INativeAd ad);

    void onShowed();
}
