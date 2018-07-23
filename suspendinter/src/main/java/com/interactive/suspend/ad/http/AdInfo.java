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

import com.interactive.suspend.ad.db.InterAdAgent;
import com.interactive.suspend.ad.taskObject.AppVersion;
import com.interactive.suspend.ad.taskObject.DeviceUtils;
import com.interactive.suspend.ad.taskObject.FileUtils;
import com.interactive.suspend.ad.taskObject.LocationMananger;
import com.interactive.suspend.ad.taskObject.StringUtils;
import com.interactive.suspend.ad.taskObject.DeviceUtil;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class AdInfo extends BaseData {
    private String adserverURL;
    private String click_url;
    private String type;
    private String adslot_id;
    private String app_key;
    private String spm_id;
    private String activity_id;
    private String ip;
    private String time;
    private String device_id;
    private String black_box;
    private String os_type;
    private String token;
    private String sdata;
    public InnerC a;
    public InnerB b;
    private String nsdata;
    String c;
    String d;
    private String data1;
    private String data2;
    private String sdk_type;
    private String sdk_version;
    private String app_version;
    private String app_package;
    private String app_name;

    private AdInfo(InnerA var1) {
        this.adserverURL = "https://engine.lvehaisen.com/api/v1/activity/spm";
        this.sdata = "";
        this.nsdata = "";
        this.type = var1.a;
        this.adslot_id = var1.b;
        this.app_key = var1.c;
        this.spm_id = var1.d;
        this.activity_id = var1.e;
        this.ip = var1.i.a();
        this.time = var1.g;
        this.device_id = var1.mDeveiceUtil.a();
        this.os_type = var1.mDeveiceUtil.e();
        this.data1 = var1.m;
        this.data2 = var1.n;
        this.click_url = var1.o;
        this.black_box = var1.p;
        this.sdk_type = var1.q;
        this.sdk_version = var1.r;
        this.app_name = var1.u;
        this.app_package = var1.t;
        this.app_version = var1.s;
        this.b = new InnerB(var1);
        this.a = new InnerC(var1);
        this.d();
        this.e();
    }

    public String a() {
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
            if(!((String)var4.getKey()).equals("black_box")) {
                var2.append(var5);
            }
        }

        return StringUtils.b(var2.toString());
    }

    private void a(Builder var1) {
        TreeMap var2 = new TreeMap();
        Field[] var3 = this.getClass().getDeclaredFields();
        int var4 = 0;

        for(int var5 = var3.length; var4 < var5; ++var4) {
            String var6 = var3[var4].getName();
            if(!var6.equals("adserverURL") && !var6.equals("token") && !var6.equals("getSDtaField") && !var6.equals("getNSData") && !var6.equals("applist_md5") && !var6.equals("sData_md5")) {
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
        var1.appendQueryParameter("time", System.currentTimeMillis() + "");
    }

    public String b() {
        TreeMap var1 = new TreeMap();
        Field[] var2 = InnerC.class.getDeclaredFields();
        int var3 = 0;

        for(int var4 = var2.length; var3 < var4; ++var3) {
            String var5 = var2[var3].getName();

            try {
                boolean var6 = var2[var3].isAccessible();
                var2[var3].setAccessible(true);
                Object var7 = var2[var3].get(this.a);
                if(var7 != null) {
                    String var8 = var7.toString();
                    if(!TextUtils.isEmpty(var8)) {
                        var1.put(var5, var8);
                    }

                    var2[var3].setAccessible(var6);
                }
            } catch (IllegalArgumentException var9) {
                var9.printStackTrace();
            } catch (IllegalAccessException var10) {
                var10.printStackTrace();
            }
        }

        return StringUtils.a(var1);
    }

    public void c() {
        String var1 = this.b();

        try {
            this.sdata = StringUtils.a(var1.toString().getBytes());
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public String a(String var1) {
        TreeMap var2 = new TreeMap();
        Field[] var3 = InnerB.class.getDeclaredFields();
        int var4 = 0;

        for(int var5 = var3.length; var4 < var5; ++var4) {
            String var6 = var3[var4].getName();

            try {
                boolean var7 = var3[var4].isAccessible();
                var3[var4].setAccessible(true);
                Object var8 = var3[var4].get(this.b);
                if(var8 != null && (!var6.equals("app_list") || FileUtils.b(var1))) {
                    String var9 = var8.toString();
                    if(!TextUtils.isEmpty(var9)) {
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

        return StringUtils.a(var2);
    }

    public void b(String var1) {
        String var2 = this.a(var1);

        try {
            this.nsdata = StringUtils.a(var2.toString().getBytes());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void d() {
        this.d = StringUtils.c(this.b());
        if(FileUtils.a(this.d)) {
            this.c();
        } else {
            FileUtils.a(this.d, System.currentTimeMillis() + "");
        }

    }

    public void e() {
        this.c = StringUtils.c(this.b.a());
        if(FileUtils.a()) {
            this.b(this.c);
        } else {
            FileUtils.b(this.c, System.currentTimeMillis() + "");
        }

    }

    public String f() {
        return this.sdata;
    }

    public String g() {
        return this.nsdata;
    }

    public String h() {
        return this.type;
    }

    public static final class InnerB {
        private String device_id;
        private String ipv4;
        private String connection_type;
        private String operator_type;
        private String cellular_id;
        private String ap_mac;
        private String rssi;
        private String ap_name;
        private String wifi_type;
        private String hotspot_type;
        private String coordinate_type;
        private String longitude;
        private String latitude;
        private String app_list;
        private String app_use_list;
        private String mem_size;
        private String mem_free_size;
        private String storage_size;
        private String storage_free_size;
        private String cpu_cores;
        private String cpu_frequency;

        public InnerB(InnerA var1) {
            this.device_id = var1.mDeveiceUtil.a();
            this.ipv4 = var1.i.a();
            this.connection_type = var1.i.b();
            this.operator_type = var1.i.c();
            this.cellular_id = var1.i.e();
            this.ap_mac = var1.i.d();
            this.rssi = var1.i.f();
            this.ap_name = var1.i.g();
            this.wifi_type = var1.i.h();
            this.hotspot_type = var1.i.i();
            this.coordinate_type = var1.j.c();
            this.longitude = var1.j.a();
            this.latitude = var1.j.b();
            this.app_list = var1.l.a();
            this.app_use_list = var1.k.a();
            this.mem_size = var1.mDeveiceUtil.l();
            this.mem_free_size = var1.mDeveiceUtil.m();
            this.storage_size = var1.mDeveiceUtil.n();
            this.storage_free_size = var1.mDeveiceUtil.o();
            this.cpu_cores = var1.mDeveiceUtil.p();
            this.cpu_frequency = var1.mDeveiceUtil.q();
        }

        public String a() {
            return this.app_list;
        }
    }

    public static final class InnerC {
        private String device_id;
        private String device_type;
        private String os_type;
        private String os_version;
        private String vendor;
        private String model;
        private String screen_size;
        private String serial;
        private String imei;
        private String imsi;
        private String android_id;
        private String mac;
        private String phone;
        private String app_package;
        private String app_version;
        private String app_name;

        public InnerC(InnerA var1) {
            this.device_id = var1.mDeveiceUtil.a();
            this.device_type = var1.mDeveiceUtil.f();
            this.os_type = var1.mDeveiceUtil.e();
            this.os_version = var1.mDeveiceUtil.d();
            this.vendor = var1.mDeveiceUtil.c();
            this.model = var1.mDeveiceUtil.g();
            this.screen_size = var1.mDeveiceUtil.b();
            this.serial = var1.mDeveiceUtil.h();
            this.imei = var1.mDeveiceUtil.i();
            this.imsi = var1.mDeveiceUtil.j();
            this.android_id = var1.mDeveiceUtil.k();
            this.mac = var1.i.j();
            this.phone = var1.f;
            this.app_package = var1.l.c;
            this.app_name = var1.l.b;
            this.app_version = var1.l.d;
        }
    }

    public static final class InnerA {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private String g;
        private DeviceUtils mDeveiceUtil;
        private DeviceUtil i;
        private LocationMananger j;
        private UseRecordData.InnerBA k;
        private AppVersion l;
        private String m;
        private String n;
        private String o;
        private String p;
        private String q = "Android";
        private String r = "2.2.5";
        private String s;
        private String t;
        private String u;

        public InnerA a(String var1) {
            this.b = var1;
            return this;
        }

        public InnerA b(String var1) {
            this.a = var1;
            return this;
        }

        public InnerA c(String var1) {
            this.d = var1;
            return this;
        }

        public InnerA d(String var1) {
            this.m = var1;
            return this;
        }

        public InnerA e(String var1) {
            this.n = var1;
            return this;
        }

        public InnerA f(String var1) {
            this.o = var1;
            return this;
        }

        public InnerA g(String var1) {
            this.e = var1;
            return this;
        }

        public InnerA(Context var1) {
            this.mDeveiceUtil = new DeviceUtils(var1);
            this.i = new DeviceUtil(var1);
            this.j = new LocationMananger(var1);
            this.l = new AppVersion(var1);
            this.k = new UseRecordData.InnerBA(var1);
            this.p = InterAdAgent.onEvent(var1);
            PackageManager var2 = var1.getPackageManager();

            try {
                ApplicationInfo var3 = var2.getApplicationInfo(var1.getPackageName(), PackageManager.GET_META_DATA);
                this.c = var3.metaData.getString("TUIA_APPKEY");
                PackageInfo var4 = var2.getPackageInfo(var1.getPackageName(), 0);
                this.t = var1.getPackageName();
                this.s = var4.versionName;
                this.u = var2.getApplicationLabel(var2.getApplicationInfo(this.t, PackageManager.GET_META_DATA)).toString();
            } catch (NameNotFoundException var5) {
                var5.printStackTrace();
            }

            this.g = System.currentTimeMillis() + "";
            UseRecordData.InnerBA var6 = new UseRecordData.InnerBA(var1);
            this.f = var6.b();
        }

        public AdInfo a() {
            return new AdInfo(this);
        }
    }
}
