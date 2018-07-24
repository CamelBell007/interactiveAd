//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.manager;

import android.util.Log;
import java.util.Hashtable;

public class ThreadStackLog {
    private static Hashtable<String, ThreadStackLog> a = new Hashtable();
    private String mName;
    private static ThreadStackLog mThreadStackLog;

    public static ThreadStackLog a() {
        if(mThreadStackLog == null) {
            mThreadStackLog = new ThreadStackLog("@TUIA@");
        }

        return mThreadStackLog;
    }

    private ThreadStackLog(String var1) {
        this.mName = var1;
    }

    private String b() {
        StackTraceElement[] var1 = Thread.currentThread().getStackTrace();
        if(var1 == null) {
            return null;
        } else {
            StackTraceElement[] var2 = var1;
            int var3 = var1.length;

            for(int i = 0; i < var3; ++i) {
                StackTraceElement var5 = var2[i];
                if(!var5.isNativeMethod() && !var5.getClassName().equals(Thread.class.getName()) && !var5.getClassName().equals(this.getClass().getName())) {
                    return this.mName + "[ " + Thread.currentThread().getName() + ": " + var5.getFileName() + ":" + var5.getLineNumber() + " " + var5.getMethodName() + " ]";
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
