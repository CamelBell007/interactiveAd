package com.interactive.suspend.ad.controller;

/**
 * Created by Administrator on 2018/6/28.
 */

public interface SuspendController {
    void destroy();

    void setAdListener(SuspendListener var1);

    void loadAd(String var1);
}
