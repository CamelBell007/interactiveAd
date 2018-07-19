//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.task;

import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

public class k {
    public static String a() {
        Random var0 = new Random();
        String var1 = var0.nextInt(10000) + "";
        int var2 = var1.length();
        if(var2 < 4) {
            for(int var3 = 1; var3 <= 4 - var2; ++var3) {
                var1 = "0" + var1;
            }
        }

        return var1;
    }

    public static String a(String var0) {
        String var1 = var0;
        if(!TextUtils.isEmpty(var0) && !var0.startsWith("http")) {
            var1 = "http" + var0;
        }

        return var1;
    }

    public static String b(String var0) {
        try {
            MessageDigest var1 = MessageDigest.getInstance("SHA-1");
            var1.update(var0.getBytes());
            byte[] var2 = var1.digest();
            StringBuffer var3 = new StringBuffer();

            for(int var4 = 0; var4 < var2.length; ++var4) {
                String var5 = Integer.toHexString(var2[var4] & 255);
                if(var5.length() < 2) {
                    var3.append(0);
                }

                var3.append(var5);
            }

            return var3.toString();
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
            return "";
        }
    }

    public static String a(Map<String, String> var0) {
        TreeMap var1 = new TreeMap(var0);
        StringBuilder var2 = new StringBuilder();
        boolean var3 = true;
        Iterator var4 = var1.entrySet().iterator();

        while(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            String var6 = (String)var5.getValue();
            String var7 = (String)var5.getKey();
            if(var3) {
                var2.append(var7 + "=" + var6);
                var3 = false;
            } else {
                var2.append("&" + var7 + "=" + var6);
            }
        }

        return var2.toString();
    }

    public static String c(String var0) {
        String var1 = null;

        try {
            MessageDigest var2 = MessageDigest.getInstance("MD5");
            byte[] var3 = var2.digest(var0.getBytes());
            StringBuffer var4 = new StringBuffer();
            byte[] var5 = var3;
            int var6 = var3.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                byte var8 = var5[var7];
                int var9 = var8 & 255;
                if(var9 < 16) {
                    var4.append(0);
                }

                var4.append(Integer.toHexString(var9));
            }

            var1 = var4.toString();
        } catch (NoSuchAlgorithmException var10) {
            var10.printStackTrace();
        }

        return var1;
    }

    public static String a(byte[] var0) {
        if(var0 == null) {
            return null;
        } else {
            try {
                ByteArrayOutputStream var1 = new ByteArrayOutputStream();
                GZIPOutputStream var2 = new GZIPOutputStream(var1);
                var2.write(var0);
                var2.close();
                return Base64.encodeToString(var1.toByteArray(), 0);
            } catch (IOException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }
}
