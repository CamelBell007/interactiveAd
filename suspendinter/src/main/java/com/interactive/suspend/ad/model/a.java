//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.Uri.Builder;

public class a extends e {
    private String a = "https://engine.lvehaisen.com/api/v1/activity/error";
    private int mReqFail;
    private int mExposureFail;
    private int mClickFail;

    public a(Context var1) {
        SharedPreferences var2 = var1.getSharedPreferences("failureTimes", 0);
        this.mReqFail = var2.getInt("req_fail", 0);
        this.mExposureFail = var2.getInt("exposure_fail", 0);
        this.mClickFail = var2.getInt("click_fail", 0);
    }

    public String a() {
        Uri uri = Uri.parse(this.a);
        Builder builder = uri.buildUpon();
        builder.appendQueryParameter("req_fail", this.mReqFail + "");
        builder.appendQueryParameter("exposure_fail", this.mExposureFail + "");
        builder.appendQueryParameter("click_fail", this.mClickFail + "");
        String params = builder.build().toString();
        return params;
    }
}
