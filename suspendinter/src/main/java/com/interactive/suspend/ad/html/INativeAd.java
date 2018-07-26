package com.interactive.suspend.ad.html;

import android.view.View;

/**
 * Created by guojia on 2016/10/31.
 */

public interface INativeAd {
    String getPackageName();

    String getAdType();

    String getCoverImageUrl();

    String getIconImageUrl();

    String getSubtitle();
    //获取app的类型
    String getAppCategory();
    //获取app的大小
    String getAppSize();

    double getStarRating();

    String getTitle();

    String getCallToActionText();

    Object getAdObject();

    String getId();

    String getBody();

    void registerViewForInteraction(View view);

    void registerTransitionViewForAdClick(View view);

    void enableTransitionViewForAdClick(boolean enable);

    void registerPrivacyIconView(View view);

    String getPrivacyIconUrl();

    String getPlacementId();

    int getShowCount();

    boolean isShowed();

    long getLoadedTime();

    String getClickUrl();
}
