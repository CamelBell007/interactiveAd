//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.task;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.interactive.suspend.ad.CheckCallingPermission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class d {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private int r;
    private int s;
    private float t;
    private int u;

    public d(Context var1) {
        DisplayMetrics var2 = new DisplayMetrics();
        WindowManager var3 = (WindowManager)var1.getSystemService(Context.WINDOW_SERVICE);
        TelephonyManager var4 = (TelephonyManager)var1.getSystemService(Context.TELEPHONY_SERVICE);
        var3.getDefaultDisplay().getMetrics(var2);
        this.s = var2.widthPixels;
        this.r = var2.heightPixels;
        this.t = var2.density;
        this.u = var2.densityDpi;
        if(var4 != null) {
            if(!this.a(var1)) {
                this.a = "手机端";
            } else {
                this.a = "平板";
            }
        }

        this.b = "Android";
        this.c = VERSION.RELEASE;
        this.d = Build.MANUFACTURER;
        this.e = this.s();
        this.f = this.s + "x" + this.r;
        this.g = this.a(var4, var1);
        this.h = Build.SERIAL;

        try {
            this.i = ((TelephonyManager)var1.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            this.j = ((TelephonyManager)var1.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
            this.k = System.getString(var1.getContentResolver(), "android_id");
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        this.l = this.c(var1);
        this.m = this.b(var1);
        this.n = (this.a(0) + this.b(0)) / 1024L / 1024L + "";
        this.o = (this.a(1) + this.b(1)) / 1024L / 1024L + "";
        this.p = this.u() + "";
        this.q = r() + "";
    }

    private boolean a(Context var1) {
        return (var1.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    private String s() {
        String var1 = Build.MODEL;
        if(var1 != null) {
            var1 = var1.trim().replaceAll("\\s*", "");
        } else {
            var1 = "";
        }

        return var1;
    }

    public String a() {
        return this.g;
    }

    public String b() {
        return this.f;
    }

    public String c() {
        return this.d;
    }

    public String d() {
        return this.c;
    }

    public String e() {
        return this.b;
    }

    public String f() {
        return this.a;
    }

    public String g() {
        return this.e;
    }

    public String h() {
        return this.h;
    }

    public String i() {
        return this.i;
    }

    public String j() {
        return this.j;
    }

    public String k() {
        return this.k;
    }

    private String a(TelephonyManager var1, Context var2) {
        WifiManager var3 = (WifiManager)var2.getSystemService(Context.WIFI_SERVICE);
        String var4 = "";
        if(var3 != null && var3.getConnectionInfo() != null) {
            var4 = var3.getConnectionInfo().getMacAddress();
        }

        String var5 = "";
        if(CheckCallingPermission.a(var2, "android.permission.READ_PHONE_STATE")) {
            var5 = var1.getDeviceId();
        }

        String var6 = var5 + this.t() + Secure.getString(var2.getContentResolver(), "android_id") + var4;
        MessageDigest var7 = null;

        try {
            var7 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var12) {
            var12.printStackTrace();
        }

        var7.update(var6.getBytes(), 0, var6.length());
        byte[] var8 = var7.digest();
        String var9 = new String();

        for(int var10 = 0; var10 < var8.length; ++var10) {
            int var11 = 255 & var8[var10];
            if(var11 <= 15) {
                var9 = var9 + "0";
            }

            var9 = var9 + Integer.toHexString(var11);
        }

        var9 = var9.toUpperCase();
        return var9;
    }

    private String t() {
        String var1 = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
        return var1;
    }

    public String l() {
        return this.l;
    }

    public String m() {
        return this.m;
    }

    public String n() {
        return this.n;
    }

    public String o() {
        return this.o;
    }

    public String p() {
        return this.p;
    }

    public String q() {
        return this.q;
    }

    private String b(Context var1) {
        ActivityManager var2 = (ActivityManager)var1.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo var3 = new MemoryInfo();
        var2.getMemoryInfo(var3);
        return var3.availMem / 1024L / 1024L + "";
    }

    private String c(Context var1) {
        String var2 = "/proc/meminfo";
        long var5 = 0L;

        try {
            FileReader var7 = new FileReader(var2);
            BufferedReader var8 = new BufferedReader(var7, 8192);
            String var3 = var8.readLine();
            String[] var4 = var3.split("\\s+");
            var5 = Long.parseLong(var4[1]);
            var8.close();
        } catch (Exception var9) {
            var9.printStackTrace();
            return "0";
        }

        return var5 / 1024L + "";
    }

    public long a(int var1) {
        String var2 = Environment.getExternalStorageState();
        long var3 = 0L;
        if("mounted".equals(var2)) {
            try {
                File var5 = Environment.getExternalStorageDirectory();
                StatFs var6 = new StatFs(var5.getPath());
                long var7 = 0L;
                long var9 = 0L;
                long var11 = 0L;
                if(VERSION.SDK_INT >= 18) {
                    var7 = var6.getBlockSizeLong();
                    var9 = var6.getBlockCountLong();
                    var11 = var6.getAvailableBlocksLong();
                } else {
                    var7 = (long)var6.getBlockSize();
                    var9 = (long)var6.getBlockCount();
                    var11 = (long)var6.getAvailableBlocks();
                }

                switch(var1) {
                case 0:
                    var3 = var7 * var9;
                    break;
                case 1:
                    var3 = var7 * var11;
                }
            } catch (Exception var13) {
                var13.printStackTrace();
                return 0L;
            }
        }

        return var3;
    }

    public long b(int var1) {
        long var2 = 0L;
        long var4 = 0L;
        long var6 = 0L;
        long var8 = 0L;

        try {
            File var10 = Environment.getRootDirectory();
            StatFs var11 = new StatFs(var10.getPath());
            if(VERSION.SDK_INT >= 18) {
                var2 = var11.getBlockSizeLong();
                var4 = var11.getBlockCountLong();
                var6 = var11.getAvailableBlocksLong();
            } else {
                var2 = (long)var11.getBlockSize();
                var4 = (long)var11.getBlockCount();
                var6 = (long)var11.getAvailableBlocks();
            }

            switch(var1) {
            case 0:
                var8 = var2 * var4;
                break;
            case 1:
                var8 = var2 * var6;
            }

            return var8;
        } catch (Exception var12) {
            var12.printStackTrace();
            return 0L;
        }
    }

    private int u() {
        try {
            File var1 = new File("/sys/devices/system/cpu/");
            class a implements FileFilter {
                a() {
                }

                public boolean accept(File var1) {
                    return Pattern.matches("cpu[0-9]", var1.getName());
                }
            }

            File[] var2 = var1.listFiles(new a());
            return var2.length;
        } catch (Exception var3) {
            var3.printStackTrace();
            return 1;
        }
    }

    public static int r() {
        String var0 = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
        boolean var1 = false;
        FileReader var2 = null;
        BufferedReader var3 = null;

        byte var5;
        try {
            var2 = new FileReader(var0);
            var3 = new BufferedReader(var2);
            String var4 = var3.readLine();
            int var25 = Integer.parseInt(var4.trim());
            return var25 / 1000;
        } catch (FileNotFoundException var22) {
            var5 = 0;
            return var5;
        } catch (IOException var23) {
            var5 = 0;
        } finally {
            if(var2 != null) {
                try {
                    var2.close();
                } catch (IOException var21) {
                    var21.printStackTrace();
                }
            }

            if(var3 != null) {
                try {
                    var3.close();
                } catch (IOException var20) {
                    var20.printStackTrace();
                }
            }

        }

        return var5;
    }
}
