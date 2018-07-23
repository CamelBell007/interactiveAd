//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.taskObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class AppVersion {
    public String a = "";
    public String b;
    public String c;
    public String d;
    PackageManager e;

    public AppVersion(Context var1) {
        this.a = this.b(var1);
        this.e = var1.getPackageManager();
        this.a(var1);
    }

    private String b(Context var1) {
        try {
            PackageManager var2 = var1.getPackageManager();
            TreeMap var3 = new TreeMap();
            List var4 = var2.getInstalledPackages(0);
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
                PackageInfo var6 = (PackageInfo)var5.next();
                var3.put(var6.packageName, var6.packageName);
            }

            return this.a((Map)var3);
        } catch (Exception var7) {
            return "";
        }
    }

    public String a(Map<String, String> var1) {
        TreeMap var2 = new TreeMap(var1);
        StringBuilder var3 = new StringBuilder();
        boolean var4 = true;
        Iterator var5 = var2.entrySet().iterator();

        while(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            String var7 = (String)var6.getValue();
            if(var4) {
                var4 = false;
                var3.append(var7);
            } else {
                var3.append("," + var7);
            }
        }

        return var3.toString();
    }

    public String a() {
        return this.a;
    }

    public String a(Context var1) {
        try {
            PackageInfo var2 = this.e.getPackageInfo(var1.getPackageName(), 0);
            this.d = var2.versionCode + "";
            this.c = var2.packageName;
            this.b = this.a(this.c);
        } catch (NameNotFoundException var4) {
            var4.printStackTrace();
        }

        return "";
    }

    public String a(String var1) {
        try {
            ApplicationInfo var2 = this.e.getApplicationInfo(var1, 0);
            return var2.loadLabel(this.e).toString();
        } catch (NameNotFoundException var3) {
            var3.printStackTrace();
            return "";
        }
    }
}
