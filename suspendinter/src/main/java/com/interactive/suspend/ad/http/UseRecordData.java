//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.http;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;

import com.interactive.suspend.ad.manager.DeviceUtils;
import com.interactive.suspend.ad.manager.PermissionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class UseRecordData extends BaseData {
    private String adserverURL;
    private String app_use_list;
    private String phone;
    private String device_id;
    private String token;

    public static final class InnerBA {
        private String a;
        private String b;
        private DeviceUtils mDeveiceUtil;

        public InnerBA(Context context) {
            this.mDeveiceUtil = new DeviceUtils(context);
            if(PermissionManager.checkPermissionIsGrant(context, "android.permission.READ_SMS")) {
                TelephonyManager var2 = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                this.b = var2.getLine1Number();
            }

            this.a = this.a(context);
        }

        private String a(Context var1) {
            JSONArray var2 = new JSONArray();
            if(VERSION.SDK_INT >= 21) {
                Calendar var3 = Calendar.getInstance();
                long var4 = var3.getTimeInMillis();
                var3.add(Calendar.DAY_OF_WEEK, -2);
                long var6 = var3.getTimeInMillis();
                UsageStatsManager var8 = (UsageStatsManager)var1.getSystemService(Context.USAGE_STATS_SERVICE);
                List var9 = var8.queryUsageStats(1, var6, var4);
                Iterator var10 = var9.iterator();

                while(var10.hasNext()) {
                    UsageStats var11 = (UsageStats)var10.next();

                    try {
                        JSONObject var12 = new JSONObject();
                        var12.put("packageName", var11.getPackageName());
                        long var13 = var11.getTotalTimeInForeground();
                        long var15 = var11.getClass().getDeclaredField("mLaunchCount").getLong(var11);
                        if(var13 > 0L && var15 > 0L) {
                            var12.put("totalTimeInForeground", var13);
                            var12.put("useTimes", var15);
                            var2.put(var12);
                        }
                    } catch (IllegalAccessException var18) {
                        var18.printStackTrace();
                    } catch (NoSuchFieldException var19) {
                        var19.printStackTrace();
                    } catch (JSONException var20) {
                        var20.printStackTrace();
                    } catch (RuntimeException var21) {
                        var21.printStackTrace();
                    }
                }
            } else {
                byte var22 = 10;
                PackageManager var23 = var1.getPackageManager();
                ActivityManager var5 = (ActivityManager)var1.getSystemService(Context.ACTIVITY_SERVICE);
                List var24 = var5.getRecentTasks(var22, 1);
                Iterator var7 = var24.iterator();

                while(var7.hasNext()) {
                    RecentTaskInfo var25 = (RecentTaskInfo)var7.next();
                    Intent var26 = var25.baseIntent;
                    ResolveInfo var27 = var23.resolveActivity(var26, 0);
                    if(var27 != null) {
                        JSONObject var28 = new JSONObject();

                        try {
                            var28.put("packageName", var27.activityInfo.packageName);
                            var2.put(var28);
                        } catch (JSONException var17) {
                            var17.printStackTrace();
                        }
                    }
                }
            }

            return var2.toString();
        }

        public String a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }
    }
}
