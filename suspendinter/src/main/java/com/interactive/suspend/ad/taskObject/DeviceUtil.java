//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.taskObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class DeviceUtil {
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

    public DeviceUtil(Context var1) {
        WifiManager var2 = (WifiManager)var1.getSystemService(Context.WIFI_SERVICE);
        if(var2 != null && var2.getWifiState() == 3) {
            WifiInfo var3 = var2.getConnectionInfo();
            if(var3 != null) {
                this.e = this.k();
                this.f = String.valueOf(var3.getRssi());
                this.g = var3.getSSID();
                this.j = var3.getMacAddress();
            }
        }

        this.a = this.a(true);
        this.b = this.b(var1);
        this.c = this.c(var1);
        TelephonyManager var7 = (TelephonyManager)var1.getSystemService(Context.TELEPHONY_SERVICE);
        if(var7 != null && PermissionManager.checkPermissionIsGrant(var1, "android.permission.ACCESS_COARSE_LOCATION")) {
            try {
                int var4 = var7.getNetworkType();
                if(var4 != 4 && var4 != 7 && var4 != 5 && var4 != 6) {
                    GsmCellLocation var8 = (GsmCellLocation)var7.getCellLocation();
                    if(var8 != null) {
                        this.d = var8.getCid() + "";
                    }
                } else {
                    CdmaCellLocation var5 = (CdmaCellLocation)var7.getCellLocation();
                    if(var5 != null) {
                        this.d = var5.getBaseStationId() + "";
                    }
                }
            } catch (Exception var6) {
                ThreadStackLog.a().a(var6.toString());
            }
        }

    }

    private String k() {
        String var1 = "";

        try {
            NetworkInterface var3 = NetworkInterface.getByInetAddress(InetAddress.getByName(this.a(true)));
            byte[] var2 = var3.getHardwareAddress();
            var1 = this.a(var2);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return var1;
    }

    private String a(byte[] var1) {
        StringBuffer var2 = new StringBuffer(var1.length);
        String var3 = "";
        int var4 = var1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            var3 = Integer.toHexString(var1[var5] & 255);
            if(var3.length() == 1) {
                var2 = var2.append("0").append(var3);
            } else {
                var2 = var2.append(var3);
            }

            var2.append(":");
        }

        var2.deleteCharAt(var2.length() - 1);
        return String.valueOf(var2);
    }

    private String a(boolean var1) {
        try {
            Enumeration var2 = NetworkInterface.getNetworkInterfaces();

            while(var2.hasMoreElements()) {
                NetworkInterface var3 = (NetworkInterface)var2.nextElement();
                Enumeration var4 = var3.getInetAddresses();

                while(var4.hasMoreElements()) {
                    InetAddress var5 = (InetAddress)var4.nextElement();
                    if(!var5.isLoopbackAddress()) {
                        String var6 = var5.getHostAddress();
                        boolean var7 = var6.indexOf(58) < 0;
                        if(var1) {
                            if(var7) {
                                return var6;
                            }
                        } else if(!var7) {
                            int var8 = var6.indexOf(37);
                            return var8 < 0?var6.toUpperCase():var6.substring(0, var8).toUpperCase();
                        }
                    }
                }
            }
        } catch (SocketException var9) {
            var9.printStackTrace();
        }

        return null;
    }

    private String b(Context var1) {
        switch(this.a(var1)) {
        case -1:
            return "NO";
        case 0:
        default:
            return "UNKNOWN";
        case 1:
            return "WIFI";
        case 2:
            return "2G";
        case 3:
            return "3G";
        case 4:
            return "4G";
        }
    }

    public int a(Context var1) {
        byte var2 = -1;
        if(PermissionManager.checkPermissionIsGrant(var1, "android.permission.ACCESS_NETWORK_STATE")) {
            ConnectivityManager var3 = (ConnectivityManager)var1.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo var4 = var3.getActiveNetworkInfo();
            if(var4 != null && var4.isAvailable()) {
                if(var4.getType() == 1) {
                    var2 = 1;
                } else if(var4.getType() == 0) {
                    switch(var4.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                    case 16:
                        var2 = 2;
                        break;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                    case 17:
                        var2 = 3;
                        break;
                    case 13:
                    case 18:
                        var2 = 4;
                        break;
                    default:
                        String var5 = var4.getSubtypeName();
                        if(!var5.equalsIgnoreCase("TD-SCDMA") && !var5.equalsIgnoreCase("WCDMA") && !var5.equalsIgnoreCase("CDMA2000")) {
                            var2 = 5;
                        } else {
                            var2 = 3;
                        }
                    }
                } else {
                    var2 = 5;
                }
            }
        }

        return var2;
    }

    private String c(Context var1) {
        String var2 = null;
        TelephonyManager var3 = (TelephonyManager)var1.getSystemService(Context.TELEPHONY_SERVICE);
        if(var3 != null) {
            var2 = var3.getNetworkOperatorName();
            if(TextUtils.isEmpty(var2)) {
                var2 = var3.getSimOperatorName();
                if(TextUtils.isEmpty(var2)) {
                    String var4 = var3.getSimOperator();
                    if(var4 != null) {
                        byte var6 = -1;
                        switch(var4.hashCode()) {
                        case 49679470:
                            if(var4.equals("46000")) {
                                var6 = 0;
                            }
                            break;
                        case 49679471:
                            if(var4.equals("46001")) {
                                var6 = 3;
                            }
                            break;
                        case 49679472:
                            if(var4.equals("46002")) {
                                var6 = 1;
                            }
                            break;
                        case 49679473:
                            if(var4.equals("46003")) {
                                var6 = 4;
                            }
                        case 49679474:
                        case 49679475:
                        case 49679476:
                        default:
                            break;
                        case 49679477:
                            if(var4.equals("46007")) {
                                var6 = 2;
                            }
                        }

                        switch(var6) {
                        case 0:
                        case 1:
                        case 2:
                            var2 = "中国移动";
                            break;
                        case 3:
                            var2 = "中国联通";
                            break;
                        case 4:
                            var2 = "中国电信";
                        }
                    }
                }
            }
        }

        return var2;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public String d() {
        return this.e;
    }

    public String e() {
        return this.d;
    }

    public String f() {
        return this.f;
    }

    public String g() {
        return this.g;
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
}
