//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.manager;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Properties;

public class FileUtils {
    private static String a = "/sdcard/data/my/my.properties";

    public static boolean a(String var0) {
        Properties var1 = new Properties();
        FileInputStream var2 = null;

        boolean var3;
        try {
            boolean var4;
            try {
                var2 = new FileInputStream(a);
                if(var2 != null) {
                    var1.load(var2);
                    String var21 = var1.getProperty("slct");
                    if(var21 == null) {
                        a(var0, System.currentTimeMillis() + "");
                        var4 = true;
                        return var4;
                    }

                    if(!c(var21)) {
                        return false;
                    }

                    String var22 = var1.getProperty("scm");
                    boolean var5;
                    if(var0 == null) {
                        var5 = true;
                        return var5;
                    }

                    if(!var22.equals(var0)) {
                        var5 = true;
                        return var5;
                    }

                    return false;
                }

                a(var0, System.currentTimeMillis() + "");
                var3 = true;
            } catch (IOException var19) {
                a(var0, System.currentTimeMillis() + "");
                var4 = true;
                return var4;
            }
        } finally {
            if(var2 != null) {
                try {
                    var2.close();
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }

        }

        return var3;
    }

    public static boolean a() {
        Properties var0 = new Properties();
        FileInputStream var1 = null;

        boolean var3;
        try {
            var1 = new FileInputStream(a);
            if(var1 == null) {
                boolean var17 = true;
                return var17;
            }

            var0.load(var1);
            String var2 = var0.getProperty("nslct");
            if(var2 != null && !d(var2)) {
                return false;
            }

            var3 = true;
        } catch (IOException var15) {
            var3 = true;
            return var3;
        } finally {
            if(var1 != null) {
                try {
                    var1.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                    return true;
                }
            }

        }

        return var3;
    }

    public static boolean b(String var0) {
        Properties var1 = new Properties();
        FileInputStream var2 = null;

        boolean var4;
        try {
            var2 = new FileInputStream(a);
            if(var2 == null) {
                b(var0, System.currentTimeMillis() + "");
                boolean var20 = true;
                return var20;
            }

            var1.load(var2);
            String var3 = var1.getProperty("nslct");
            if(var3 != null) {
                if(!d(var3)) {
                    return false;
                }

                String var21 = var1.getProperty("al");
                if(var21 != null && !var21.equals(var0)) {
                    b(var0, System.currentTimeMillis() + "");
                    boolean var5 = true;
                    return var5;
                }

                return false;
            }

            b(var0, System.currentTimeMillis() + "");
            var4 = true;
        } catch (IOException var18) {
            b(var0, System.currentTimeMillis() + "");
            var18.printStackTrace();
            var4 = true;
            return var4;
        } finally {
            if(var2 != null) {
                try {
                    var2.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

        }

        return var4;
    }

    public static void a(String var0, String var1) {
        String[] var2 = c("nslct", "al");
        Properties var3 = new Properties();
        FileOutputStream var4 = null;

        try {
            try {
                File var5 = new File(a);
                if(!var5.getParentFile().exists()) {
                    var5.getParentFile().mkdirs();
                }

                File var6 = new File(var5.getAbsolutePath() + ".lock");
                if(!var6.exists()) {
                    var6.createNewFile();
                }

                RandomAccessFile var7 = new RandomAccessFile(var6, "rw");
                FileChannel var8 = var7.getChannel();
                FileLock var9 = var8.tryLock();
                if(var9 == null) {
                    throw new IOException("Can not lock the registry cache file" + var5.getAbsolutePath());
                }

                var4 = new FileOutputStream(var5);
                if(var2[0] != null) {
                    var3.setProperty("nslct", var2[0]);
                }

                if(var2[1] != null) {
                    var3.setProperty("al", var2[1]);
                }

                var3.setProperty("slct", var1);
                var3.setProperty("scm", var0);
                var3.store(var4, "xianhongwei modify");
                var4.close();
                var9.release();
                var8.close();
                var7.close();
            } catch (FileNotFoundException var14) {
                var14.printStackTrace();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        } finally {
            ;
        }
    }

    public static void b(String var0, String var1) {
        String[] var2 = c("slct", "scm");
        Properties var3 = new Properties();
        FileOutputStream var4 = null;

        try {
            try {
                File var5 = new File(a);
                if(!var5.getParentFile().exists()) {
                    var5.getParentFile().mkdirs();
                }

                File var6 = new File(var5.getAbsolutePath() + ".lock");
                if(!var6.exists()) {
                    var6.createNewFile();
                }

                RandomAccessFile var7 = new RandomAccessFile(var6, "rw");
                FileChannel var8 = var7.getChannel();
                FileLock var9 = var8.tryLock();
                if(var9 == null) {
                    throw new IOException("Can not lock the registry cache file" + var5.getAbsolutePath());
                }

                var4 = new FileOutputStream(var5);
                if(var2[0] != null) {
                    var3.setProperty("slct", var2[0]);
                }

                if(var2[1] != null) {
                    var3.setProperty("scm", var2[1]);
                }

                var3.setProperty("nslct", var1);
                var3.setProperty("al", var0);
                var3.store(var4, "xianhongwei modify");
                var4.close();
                var9.release();
                var8.close();
                var7.close();
            } catch (FileNotFoundException var14) {
                var14.printStackTrace();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        } finally {
            ;
        }
    }

    public static boolean c(String var0) {
        long var1 = Long.parseLong(var0);
        return System.currentTimeMillis() - var1 > 86400000L;
    }

    public static boolean d(String var0) {
        long var1 = Long.parseLong(var0);
        return System.currentTimeMillis() - var1 > 1800000L;
    }

    public static String[] c(String var0, String var1) {
        String[] var2 = new String[2];
        Properties var3 = new Properties();
        FileInputStream var4 = null;

        try {
            var4 = new FileInputStream(a);
            if(var4 == null) {
                Log.i("读取错误", "没有找到该文件");
            } else {
                var3.load(var4);
                var2[0] = var3.getProperty(var0);
                var2[1] = var3.getProperty(var1);
            }
        } catch (IOException var14) {
            Log.i("文件读取失败", var14.getMessage());
        } finally {
            if(var4 != null) {
                try {
                    var4.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

        }

        return var2;
    }
}
