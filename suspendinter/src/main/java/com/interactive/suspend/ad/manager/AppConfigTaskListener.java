package com.interactive.suspend.ad.manager;


import com.interactive.suspend.ad.model.FetchAppConfigResult;

/**
 * Created by hongwu on 11/14/16.
 */

public interface AppConfigTaskListener {
    void onLoadAppConfigSuccess(FetchAppConfigResult.AppConfig config);
    void onLoadAppConfigStart();
    void onLoadAppConfigFail(Error error);
}
