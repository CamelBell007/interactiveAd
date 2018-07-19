//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.task;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.interactive.suspend.ad.CheckCallingPermission;

import java.util.Iterator;
import java.util.List;

public class e {
    private String a;
    private String b;
    private String c;
    private String d;
    public e() {

    }
    public e(Context var1) {
        try {
            if(CheckCallingPermission.a(var1, "android.permission.ACCESS_FINE_LOCATION")) {
                LocationManager var2 = (LocationManager)var1.getSystemService("location");
                if(var2 != null && (var2.isProviderEnabled("gps") || var2.isProviderEnabled("network"))) {
                    try {
                        List var3 = var2.getProviders(true);
                        Location var4 = null;
                        Iterator var5 = var3.iterator();

                        while(true) {
                            Location var7;
                            do {
                                do {
                                    if(!var5.hasNext()) {
                                        if(var4 != null) {
                                            this.a = var4.getProvider();
                                            this.b = String.valueOf(var4.getLongitude());
                                            this.c = String.valueOf(var4.getLatitude());
                                            this.d = String.valueOf(System.currentTimeMillis());
                                        }

                                        return;
                                    }

                                    String var6 = (String)var5.next();
                                    var7 = var2.getLastKnownLocation(var6);
                                } while(var7 == null);
                            } while(var4 != null && var7.getAccuracy() >= var4.getAccuracy());

                            var4 = var7;
                        }
                    } catch (Exception var8) {
                        var8.printStackTrace();
                    }
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    public String a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.a;
    }
}
