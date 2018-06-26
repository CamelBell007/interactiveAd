package com.interactive.suspend.ad;

/**
 * Created by VC on 2018/6/26.
 */

public class InteractiveAd {
    public static volatile InteractiveAd INTERACTIVE_AD;

    private InteractiveAd() {

    }

    public static InteractiveAd getInstance() {
        if (INTERACTIVE_AD == null) {
            synchronized (InteractiveAd.class) {
                if (INTERACTIVE_AD == null) {
                    INTERACTIVE_AD = new InteractiveAd();
                }
            }
        }
        return INTERACTIVE_AD;
    }

    /**
     * init interactive ad sdk
     * get ad configuration and do some init
     */
    public void init(){

    }
}
