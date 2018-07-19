//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.db;

import android.content.Context;
import java.util.concurrent.CountDownLatch;

public interface FMInter {
    void init(Context var1, String var2, String var3, int var4, boolean var5, String var6, String var7, boolean var8, boolean var9, CountDownLatch var10, int var11);

    String onEvent(Context var1);
}
