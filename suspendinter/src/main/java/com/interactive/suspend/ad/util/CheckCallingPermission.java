//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class CheckCallingPermission {
    public static boolean a(Context var0, String var1) {
        try {
            return var0.checkCallingOrSelfPermission(var1) == PackageManager.PERMISSION_GRANTED;
        } catch (Exception var3) {
            return false;
        }
    }
}
