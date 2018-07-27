package com.interactive.suspend.ad.html;

/**
 * Created by hongwu on 8/21/17.
 */

public interface IAdSdkListener {

    void onInitSuccess();

    void onInitFailed(String msg);
}
