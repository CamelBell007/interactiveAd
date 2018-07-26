package com.interactive.suspend.ad.html;

/**
 * Created by guojia on 2016/10/20.
 */

public interface IAdLoadListener {
    void onLoadAdFail(Error error);
    void onLoadAdSuccess();
    void onLoadAdStart();
}
