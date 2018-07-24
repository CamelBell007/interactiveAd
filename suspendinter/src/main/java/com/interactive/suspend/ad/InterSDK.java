package com.interactive.suspend.ad;

import android.content.Context;

import com.interactive.suspend.ad.util.InitListener;

/**
 * Created by Administrator on 2018/6/26.
 */

public class InterSDK {

    public static void init(Context context, String appId, InitListener listener){
        try{
            InteractiveAd.getInstance().init(context,appId,listener);
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }

    public static void showFloatAd(){
        InteractiveAd.getInstance().showFlatAd();
    }

    public static void hideFloatAd(){
        InteractiveAd.getInstance().hideFloatAd();
    }
}
