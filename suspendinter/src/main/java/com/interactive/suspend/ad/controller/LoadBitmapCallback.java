package com.interactive.suspend.ad.controller;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018/6/28.
 */

public interface LoadBitmapCallback {
        void loadSuccess(Bitmap var1, String var2);

        void loadFail();
}
