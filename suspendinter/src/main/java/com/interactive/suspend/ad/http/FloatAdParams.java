//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.http;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;

import com.interactive.suspend.ad.taskObject.DeviceUtils;
import com.interactive.suspend.ad.taskObject.DeviceUtil;
import com.interactive.suspend.ad.taskObject.StringUtils;
import com.interactive.suspend.ad.taskObject.LocationMananger;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class FloatAdParams extends LocationMananger {
    private String adserverURL;
    private String request_id;
    private String api_version;
    private String app_key;
    private String app_version;
    private String app_package;
    private String adslot_id;
    private String time;
    private String token;
    private String device_id;
    private int network_type;

    public FloatAdParams() {
    }

    private FloatAdParams(FloatAdParams.a var1) {
        this.adserverURL = "https://engine.lvehaisen.com/api/v1/activity/get";
        this.network_type = 0;
        this.request_id = var1.a;
        this.api_version = var1.b;
        this.app_key = var1.c;
        this.app_version = var1.d;
        this.app_package = var1.e;
        this.adslot_id = var1.mSlotKey;
        this.time = var1.g;
        this.device_id = var1.mDeviceUtil.a();
        this.network_type = var1.h;
    }

    public String a() {
        return this.app_key;
    }

    public String b() {
        return this.adslot_id;
    }

    public String c() {
        Uri var1 = Uri.parse(this.adserverURL);
        Builder var2 = var1.buildUpon();
        this.a(var2);
        String var3 = var2.build().toString();
        return var3;
    }

    public static String a(Map<String, String> var0) {
        TreeMap var1 = new TreeMap(var0);
        StringBuilder var2 = new StringBuilder();
        Iterator var3 = var1.entrySet().iterator();

        while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            String var5 = (String)var4.getValue();
            var2.append(var5);
        }

        return StringUtils.b(var2.toString());
    }

    private void a(Builder var1) {
        TreeMap var2 = new TreeMap();
        Field[] var3 = this.getClass().getDeclaredFields();
        int var4 = 0;

        for(int var5 = var3.length; var4 < var5; ++var4) {
            String var6 = var3[var4].getName();
                if(!var6.equals("adserverURL") && !var6.equals("API_VERSION") && !var6.equals("token")) {
                try {
                    boolean var7 = var3[var4].isAccessible();
                    var3[var4].setAccessible(true);
                    Object var8 = var3[var4].get(this);
                    if(var8 != null) {
                        String var9 = var8.toString();
                        if(!TextUtils.isEmpty(var9)) {
                            var1.appendQueryParameter(var6, var9);
                            var2.put(var6, var9);
                        }

                        var3[var4].setAccessible(var7);
                    }
                } catch (IllegalArgumentException var10) {
                    var10.printStackTrace();
                } catch (IllegalAccessException var11) {
                    var11.printStackTrace();
                }
            }
        }

        this.token = a((Map)var2);
        var1.appendQueryParameter("token", this.token);
    }

    public static final class a {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String mSlotKey;
        private String g;
        private int h = 0;
        private DeviceUtils mDeviceUtil;
        private DeviceUtil j;
        private LocationMananger k;

        public FloatAdParams.a a(int slotKey) {
            this.mSlotKey = String.valueOf(slotKey);
            this.a = this.mDeviceUtil.a() + this.mSlotKey + System.currentTimeMillis() + k.a();
            return this;
        }

        public a(Context var1) {
            this.mDeviceUtil = new DeviceUtils(var1);
            this.k = new LocationMananger(var1);
            this.j = new DeviceUtil(var1);
            this.e = var1.getPackageName();
            PackageManager var2 = var1.getPackageManager();
            if(this.j.a(var1) == 1) {
                this.h = 0;
            } else {
                this.h = 1;
            }

            try {
                ApplicationInfo var3 = var2.getApplicationInfo(this.e, PackageManager.GET_META_DATA);
                this.c = var3.metaData.getString("TUIA_APPKEY");
                PackageInfo var4 = var2.getPackageInfo(var1.getPackageName(), 0);
                this.d = var4.versionName;
            } catch (NameNotFoundException var5) {
                var5.printStackTrace();
            }

            this.b = "2.2.5";
            this.g = System.currentTimeMillis() + "";
        }

        public FloatAdParams a() {
            return new FloatAdParams(this);
        }
    }
}
