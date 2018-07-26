package com.interactive.suspend.ad.html;

import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.util.LogUtil;

/**
 * Created by hongwu on 3/17/17.
 */

public class GPEncode {


    public static String decodeClickid(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        try {
            String str = string.substring(0, string.indexOf('&'));
            return str.substring(str.indexOf('=') + 1, str.length());
        } catch (Exception e) {
            Log.e(LogUtil.TAG,e.getMessage());
            return "";
        }
    }

    public static String decodeGPUrl(String string) {
        // decode gp url from extra info
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        try {
            String str = string.substring(string.indexOf('&') + 1, string.length());
            String gp = str.substring(str.indexOf('=') + 1, str.length());
            if (TextUtils.isEmpty(gp)) {
                return "";
            }
            return DigestUtils.decrypt(gp);
        } catch (Exception e) {
            Log.e(LogUtil.TAG,e.getMessage());
            return "";
        }
    }

    public static String encodeShareGP(String gpurl) {
        if (TextUtils.isEmpty(gpurl)) {
            return "";
        }
        return DigestUtils.encrypt(gpurl);
    }
}
