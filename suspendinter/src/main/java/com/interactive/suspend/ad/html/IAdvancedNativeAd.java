package com.interactive.suspend.ad.html;

/**
 * Created by Administrator on 2018/7/26.
 */

public interface IAdvancedNativeAd {

    void load(int adNum);

    void setAdListener(INativeAdLoadListener listener);

    void destroy();
}
