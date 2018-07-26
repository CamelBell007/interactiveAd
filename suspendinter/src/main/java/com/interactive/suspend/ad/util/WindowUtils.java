package com.interactive.suspend.ad.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.util.Log;
import android.view.Window;

import java.util.HashMap;
import java.util.Map;

public class WindowUtils {
    private static final String TAG = WindowUtils.class.getSimpleName();

    private WindowUtils() {
    }

    public static Map<String, String> a(Context var0) {
        HashMap var1 = new HashMap();

        try {
            var1.put("kgr", String.valueOf(b(var0)));
            if (var0 != null && var0 instanceof Activity) {
                Window var2 = ((Activity) var0).getWindow();
                if (var2 != null) {
                    int var3 = var2.getAttributes().flags;
                    int var4 = var2.getAttributes().type;
                    var1.put("wt", Integer.toString(var4));
                    String var5 = (var3 & 4194304) > 0 ? "1" : "0";
                    var1.put("wfdkg", var5);
                    String var6 = (var3 & 524288) > 0 ? "1" : "0";
                    var1.put("wfswl", var6);
                } else {
                    Log.e(LogUtil.TAG, "Invalid window in window interactive check, assuming interactive.");
                }
            } else {
                Log.e(LogUtil.TAG,  "Invalid Activity context in window interactive check, assuming interactive.");
            }
        } catch (Exception var7) {
            Log.e(LogUtil.TAG, "Exception in window info check: " + var7);
        }

        return var1;
    }

    public static boolean b(Context var0) {
        KeyguardManager var1 = (KeyguardManager) var0.getSystemService(Context.KEYGUARD_SERVICE);
        return var1 != null && var1.inKeyguardRestrictedInputMode();
    }

    public static boolean a(Map<String, String> var0) {
        if (var0 != null && !var0.isEmpty()) {
            String var1 = (String) var0.get("wfdkg");
            String var2 = (String) var0.get("wfswl");
            if (var1 != null && var1.equals("1") || var2 != null && var2.equals("1")) {
                return false;
            } else {
                String var3 = (String) var0.get("kgr");
                return var3 != null && var3.equals("true");
            }
        } else {
            Log.e(LogUtil.TAG, "Invalid Window info in window interactive check, assuming not obstructed by Keyguard.");
            return false;
        }
    }

    public static boolean b(Map<String, String> var0) {
        if (var0 != null && !var0.isEmpty()) {
            String var1 = (String) var0.get("wfdkg");
            String var2 = (String) var0.get("wfswl");
            String var3 = (String) var0.get("kgr");
            return var1 != null && var1.equals("1") && var2 != null && var2.equals("1") && var3 != null && var3.equals("true");
        } else {
            Log.e(LogUtil.TAG,  "Invalid Window info in window interactive check, assuming is not a Lockscreen.");
            return false;
        }
    }

    public static boolean c(Context var0) {
        return !a(a(var0));
    }
}

