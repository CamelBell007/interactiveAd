//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.task;

import android.util.Log;
import java.util.Hashtable;

public class g {
    private static Hashtable<String, g> a = new Hashtable();
    private String b;
    private static g c;

    public static g a() {
        if(c == null) {
            c = new g("@TUIA@");
        }

        return c;
    }

    private g(String var1) {
        this.b = var1;
    }

    private String b() {
        StackTraceElement[] var1 = Thread.currentThread().getStackTrace();
        if(var1 == null) {
            return null;
        } else {
            StackTraceElement[] var2 = var1;
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                StackTraceElement var5 = var2[var4];
                if(!var5.isNativeMethod() && !var5.getClassName().equals(Thread.class.getName()) && !var5.getClassName().equals(this.getClass().getName())) {
                    return this.b + "[ " + Thread.currentThread().getName() + ": " + var5.getFileName() + ":" + var5.getLineNumber() + " " + var5.getMethodName() + " ]";
                }
            }

            return null;
        }
    }

    public void a(Object var1) {
        String var2 = this.b();
        if(var2 != null) {
            Log.e("[TUIA_LOG]", var2 + " - " + var1);
        } else {
            Log.e("[TUIA_LOG]", var1.toString());
        }

    }
}
