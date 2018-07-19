package com.interactive.suspend.ad.controller;

/**
 * Created by Administrator on 2018/6/28.
 */

public interface SuspendListener {

        void onReceiveAd();

        void onFailedToReceiveAd();

        void onLoadFailed();

        void onCloseClick();

        void onAdClick();

        void onAdExposure();
    }
