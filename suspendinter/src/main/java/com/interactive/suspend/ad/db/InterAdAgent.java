//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.db;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Base64;

import com.interactive.suspend.ad.constant.CollectorError;
import com.interactive.suspend.ad.util.BoxUtil;
import com.interactive.suspend.ad.util.HelperJNI;
import com.interactive.suspend.ad.util.LogUtil;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class InterAdAgent {
    public static final String ENV_SANDBOX = "sandbox";
    public static final String ENV_PRODUCTION = "production";
    public static final String OPTION_PARTNER_CODE = "parter_code";
    public static final String OPTION_SKIP_GPS = "skip_gps";
    public static final String OPTION_CUST_PROCESS = "cust_process";
    public static final String OPTION_DOUBLE_URL = "double_url";
    public static final String OPTION_PROXY_URL = "proxy_url";
    public static final String OPTION_WAIT_TIME = "wait_time";
    public static final String OPTION_KILL_DEBUGGER = "kill_deugger";
    public static final String OPTION_ALWAYS_DEMOTION = "always_demotion";
    public static final String OPTION_INIT_TIMESPAN = "init_timespan";
    public static final String OPTION_BLACKBOX_MAXSIZE = "blackbox_maxsize";
    public static final String STATUS_UNINIT = "uninit";
    public static final String STATUS_LOADING = "loading";
    public static final String STATUS_COLLECTING = "collecting";
    public static final String STATUS_PROFILING = "profiling";
    public static final String STATUS_SUCCESSFUL = "successful";
    public static final String STATUS_FAILED = "failed";
    private static boolean mInited = false;
    private static FMInter mFmInter = null;
    private static long mLastInitTime = 0L;
    public static long mStartInitTime = 0L;
    public static String mStatus = "uninit";
    private static int mBlackboxMaxSize = 2147483647;

    public InterAdAgent() {
    }

    public static void openLog() {
        LogUtil.openLog();
        LogUtil.info("Open tongdun sdk log");
    }

    public static String getInitStatus() {
        return mStatus;
    }

    public static void init(Context var0, String var1, int var2, FMCallback var3) {
        CountDownLatch var4 = new CountDownLatch(1);
        init(var0, var1, (Map)null, var4);

        try {
            var4.await((long)var2, TimeUnit.MILLISECONDS);
            String var6 = onEvent(var0);
            var3.onEvent(var6);
        } catch (InterruptedException var5) {
            LogUtil.e("Call init with callback: " + CollectorError.catchErr(var5), var5);
        }
    }

    public static void init(Context var0, String var1) {
        init(var0, var1, (Map)null);
    }

    public static void init(Context var0, String var1, Map var2) {
        init(var0, var1, var2, (CountDownLatch)null);
    }

    private static void init(Context var0, String var1, Map var2, CountDownLatch var3) {
        mStatus = "loading";
        mStartInitTime = System.currentTimeMillis();

    }

    public static String onEvent(Context var0) {
        if(!mInited) {
            CollectorError.addError(CollectorError.TYPE.ERROR_INIT, "Did not invoke init");
            LogUtil.err("Must invoke `FMAgent.init` first!!!");
        }

        if(mFmInter != null) {
            return mFmInter.onEvent(var0);
        } else {
            JSONObject var1 = new JSONObject();

            try {
                JSONObject var2;
                (var2 = new JSONObject()).put("error_code", CollectorError.getErrorCode());
                var2.put("error_msg", CollectorError.getErrorMsg());
                String var4;
                if(VERSION.SDK_INT < 21) {
                    String[] var5;
                    (var5 = new String[2])[0] = Build.CPU_ABI;
                    var5[1] = Build.CPU_ABI2;
                    var4 = Arrays.toString(var5);
                } else {
                    var4 = Arrays.toString(Build.SUPPORTED_ABIS);
                }

                String var9 = HelperJNI.manager(4, new String[0]);
//                var2.put("device", Build.BRAND + "^^" + Build.MODEL + "^^" + VERSION.SDK_INT + "^^" + a.d(var0) + "^^" + var4 + "^^" + var9);
                var1.put("os", "Android");
                var1.put("version", "3.0.7");
//                var1.put("packages", a.b(var0));
                var1.put("error_init", var2);
                String var8 = Base64.encodeToString(BoxUtil.limitBox(var1, mBlackboxMaxSize).getBytes("utf-8"), 0).replaceAll("\\n", "").replaceAll(" ", "");
                LogUtil.dev("origLen: %d, base64Len: %d", new Object[]{Integer.valueOf(var1.toString().length()), Integer.valueOf(var8.length())});
                return var8;
            } catch (Throwable var6) {
                JSONObject var7 = CollectorError.catchErr(var6);
                LogUtil.e("onEvent: " + var7, var6);
                return var7.toString();
            }
        }
    }
}
