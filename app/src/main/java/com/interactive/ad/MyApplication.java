package com.interactive.ad;

import android.app.Application;
import android.util.Log;

import com.interactive.suspend.ad.InterSDK;
import com.interactive.suspend.ad.InteractiveAd;
import com.interactive.suspend.ad.util.InitListener;

import static com.interactive.ad.Constant.VC_LOG_MAIN;

/**
 * Created by VC on 2018/7/24.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InterSDK.init(this, Constant.APP_ID, new InitListener() {
            @Override
            public void onInitSuccess() {
                String source = InteractiveAd.getInstance().getSourceIdBySlotId("9f734fdj765ed2b");
                Log.d(VC_LOG_MAIN, "init success: " + source);
            }

            @Override
            public void onInitFailed(String errorMessage) {
                Log.d(VC_LOG_MAIN, "init failed: " + errorMessage);
            }
        });
    }
}
