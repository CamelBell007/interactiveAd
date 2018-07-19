//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class f {
    public static void a(Context var0, String var1, String var2) {
        SharedPreferences var3 = var0.getSharedPreferences(var1, 0);
        int var4 = var3.getInt(var2, 0);
        Editor var5 = var3.edit();
        var5.putInt(var2, var4 + 1);
        var5.commit();
    }

    public static void a(Context var0, String var1, String var2, String var3, String var4) {
        SharedPreferences var5 = var0.getSharedPreferences(var1, 0);
        Editor var6 = var5.edit();
        var6.putInt(var2, 0);
        var6.putInt(var3, 0);
        var6.putInt(var4, 0);
        var6.commit();
    }

    public static boolean b(Context var0, String var1, String var2, String var3, String var4) {
        SharedPreferences var5 = var0.getSharedPreferences(var1, 0);
        int var6 = var5.getInt(var2, 0);
        int var7 = var5.getInt(var3, 0);
        int var8 = var5.getInt(var4, 0);
        return var6 != 0 || var7 != 0 || var8 != 0;
    }
}
