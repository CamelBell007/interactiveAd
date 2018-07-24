//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.util;

import android.content.Context;
import android.os.Process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CacheManager {
    private static Map<String, CacheManager> mMap = new HashMap();
    private InnerAdA mInnerAdA;

    public static CacheManager getCacheManagerInstance(Context context) {
        return getCacheManagerInstance(context, "ACache");
    }

    public static CacheManager getCacheManagerInstance(Context context, String path) {
        File var2 = new File(context.getCacheDir(), path);
        return getCacheManagerInstance(var2, 50000000L, 2147483647);
    }

    public static CacheManager getCacheManagerInstance(File file, long var1, int var3) {
        CacheManager var4 = (CacheManager)mMap.get(file.getAbsoluteFile() + getProcessId());
        if(var4 == null) {
            var4 = new CacheManager(file, var1, var3);
            mMap.put(file.getAbsolutePath() + getProcessId(), var4);
        }

        return var4;
    }

    private static String getProcessId() {
        return "_" + Process.myPid();
    }

    private CacheManager(File var1, long var2, int var4) {
        if(var1.exists() || var1.mkdirs()) {
            this.mInnerAdA = new InnerAdA(var1, var2, var4);
        }

    }

    public void a(String var1, String var2) {
        File var3 = this.mInnerAdA.b(var1);
        BufferedWriter var4 = null;

        try {
            var4 = new BufferedWriter(new FileWriter(var3), 1024);
            var4.write(var2);
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            if(var4 != null) {
                try {
                    var4.flush();
                    var4.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

            this.mInnerAdA.a(var3);
        }

    }

    public void a(String var1, String var2, int var3) {
        this.a(var1, InnerAdB.b(var3, var2));
    }

    public String a(String var1) {
        File var2 = this.mInnerAdA.a(var1);
        if(!var2.exists()) {
            return null;
        } else {
            boolean var3 = false;
            BufferedReader var4 = null;

            String var7;
            try {
                String var6;
                try {
                    var4 = new BufferedReader(new FileReader(var2));

                    String var5;
                    for(var5 = ""; (var6 = var4.readLine()) != null; var5 = var5 + var6) {
                        ;
                    }

                    if(!InnerAdB.c(var5)) {
                        var7 = InnerAdB.d(var5);
                        return var7;
                    }

                    var3 = true;
                    var7 = null;
                } catch (IOException var18) {
                    var18.printStackTrace();
                    var6 = null;
                    return var6;
                }
            } finally {
                if(var4 != null) {
                    try {
                        var4.close();
                    } catch (IOException var17) {
                        var17.printStackTrace();
                    }
                }

                if(var3) {
                    this.b(var1);
                }

            }

            return var7;
        }
    }

    public boolean b(String var1) {
        return this.mInnerAdA.c(var1);
    }

    private static class InnerAdB {
        private static boolean c(String var0) {
            return a(var0.getBytes());
        }

        private static boolean a(byte[] var0) {
            String[] var1 = c(var0);
            if(var1 != null && var1.length == 2) {
                String var2;
                for(var2 = var1[0]; var2.startsWith("0"); var2 = var2.substring(1, var2.length())) {
                    ;
                }

                long var3 = Long.valueOf(var2).longValue();
                long var5 = Long.valueOf(var1[1]).longValue();
                if(System.currentTimeMillis() > var3 + var5 * 1000L) {
                    return true;
                }
            }

            return false;
        }

        private static String b(int var0, String var1) {
            return a(var0) + var1;
        }

        private static String d(String var0) {
            if(var0 != null && b(var0.getBytes())) {
                var0 = var0.substring(var0.indexOf(32) + 1, var0.length());
            }

            return var0;
        }

        private static boolean b(byte[] var0) {
            return var0 != null && var0.length > 15 && var0[13] == 45 && a(var0, ' ') > 14;
        }

        private static String[] c(byte[] var0) {
            if(b(var0)) {
                String var1 = new String(a(var0, 0, 13));
                String var2 = new String(a(var0, 14, a(var0, ' ')));
                return new String[]{var1, var2};
            } else {
                return null;
            }
        }

        private static int a(byte[] var0, char var1) {
            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var0[var2] == var1) {
                    return var2;
                }
            }

            return -1;
        }

        private static byte[] a(byte[] var0, int var1, int var2) {
            int var3 = var2 - var1;
            if(var3 < 0) {
                throw new IllegalArgumentException(var1 + " > " + var2);
            } else {
                byte[] var4 = new byte[var3];
                System.arraycopy(var0, var1, var4, 0, Math.min(var0.length - var1, var3));
                return var4;
            }
        }

        private static String a(int var0) {
            String var1;
            for(var1 = System.currentTimeMillis() + ""; var1.length() < 13; var1 = "0" + var1) {
                ;
            }

            return var1 + "-" + var0 + ' ';
        }
    }

    public class InnerAdA {
        private final AtomicLong c;
        private final AtomicInteger d;
        private final long e;
        private final int f;
        private final Map<File, Long> g;
        protected File a;

        private InnerAdA(File var2, long var3, int var5) {
            this.g = Collections.synchronizedMap(new HashMap());
            this.a = var2;
            this.e = var3;
            this.f = var5;
            this.c = new AtomicLong();
            this.d = new AtomicInteger();
            this.a();
        }

        private void a() {
            (new Thread(new Runnable() {
                public void run() {
                    int var1 = 0;
                    int var2 = 0;
                    File[] var3 = a.listFiles();
                    if(var3 != null) {
                        File[] var4 = var3;
                        int var5 = var3.length;

                        for(int var6 = 0; var6 < var5; ++var6) {
                            File var7 = var4[var6];
                            var1 = (int)((long)var1 + b(var7));
                            ++var2;
                            g.put(var7, Long.valueOf(var7.lastModified()));
                        }

                        c.set((long)var1);
                        d.set(var2);
                    }

                }
            })).start();
        }

        private void a(File var1) {
            long var3;
            for(int var2 = this.d.get(); var2 + 1 > this.f; var2 = this.d.addAndGet(-1)) {
                var3 = this.b();
                this.c.addAndGet(-var3);
            }

            this.d.addAndGet(1);
            var3 = this.b(var1);

            long var7;
            for(long var5 = this.c.get(); var5 + var3 > this.e; var5 = this.c.addAndGet(-var7)) {
                var7 = this.b();
            }

            this.c.addAndGet(var3);
            Long var9 = Long.valueOf(System.currentTimeMillis());
            var1.setLastModified(var9.longValue());
            this.g.put(var1, var9);
        }

        private File a(String var1) {
            File var2 = this.b(var1);
            Long var3 = Long.valueOf(System.currentTimeMillis());
            var2.setLastModified(var3.longValue());
            this.g.put(var2, var3);
            return var2;
        }

        private File b(String var1) {
            return new File(this.a, var1.hashCode() + "");
        }

        private boolean c(String var1) {
            File var2 = this.a(var1);
            return var2.delete();
        }

        private long b() {
            if(this.g.isEmpty()) {
                return 0L;
            } else {
                Long var1 = null;
                File var2 = null;
                Set var3 = this.g.entrySet();
                Map var4 = this.g;
                synchronized(this.g) {
                    Iterator var5 = var3.iterator();

                    while(true) {
                        if(!var5.hasNext()) {
                            break;
                        }

                        Entry var6 = (Entry)var5.next();
                        if(var2 == null) {
                            var2 = (File)var6.getKey();
                            var1 = (Long)var6.getValue();
                        } else {
                            Long var7 = (Long)var6.getValue();
                            if(var7.longValue() < var1.longValue()) {
                                var1 = var7;
                                var2 = (File)var6.getKey();
                            }
                        }
                    }
                }

                long var10 = this.b(var2);
                if(var2.delete()) {
                    this.g.remove(var2);
                }

                return var10;
            }
        }

        private long b(File var1) {
            return var1.length();
        }
    }
}
