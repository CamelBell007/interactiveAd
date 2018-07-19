package com.interactive.suspend.ad;

/**
 * Created by Administrator on 2018/6/26.
 */

public class InterSDK {

    public static void init(){
        try{
            InteractiveAd.getInstance().init();
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
