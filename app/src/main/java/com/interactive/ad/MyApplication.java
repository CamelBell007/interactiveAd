package com.interactive.ad;

import android.app.Application;

import com.interactive.suspend.ad.InteractiveAd;
import com.interactive.suspend.ad.util.InitListener;

/**
 * Created by VC on 2018/7/24.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InteractiveAd.getInstance().init(this.getApplicationContext(), Constant.APP_ID, new InitListener() {
            @Override
            public void onInitSuccess() {

            }

            @Override
            public void onInitFailed(String errorMessage) {

            }
        });
    }
}
