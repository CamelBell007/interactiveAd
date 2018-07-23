package com.interactive.suspend.ad.taskObject;

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

public class a
{
    private static Map<String, a> a = new HashMap();
    private a.InnerTaskA b;

    public static a a(Context paramContext)
    {
        return a(paramContext, "ACache");
    }

    public static a a(Context paramContext, String paramString)
    {
        File localFile = new File(paramContext.getCacheDir(), paramString);
        return a(localFile, 50000000L, Integer.MAX_VALUE);
    }

    public static a a(File paramFile, long paramLong, int paramInt)
    {
        a locala = (a)a.get(paramFile.getAbsoluteFile() + a());
        if (locala == null)
        {
            locala = new a(paramFile, paramLong, paramInt);
            a.put(paramFile.getAbsolutePath() + a(), locala);
        }
        return locala;
    }

    private static String a()
    {
        return "_" + Process.myPid();
    }

    private a(File paramFile, long paramLong, int paramInt)
    {
        if ((paramFile.exists()) || (paramFile.mkdirs())) {
            this.b = new InnerTaskA(paramFile, paramLong, paramInt);
        }
    }

    public void a(String paramString1, String paramString2)
    {
        File localFile = b.a(paramString1);
        BufferedWriter localBufferedWriter = null;
        try
        {
            localBufferedWriter = new BufferedWriter(new FileWriter(localFile), 1024);
            localBufferedWriter.write(paramString2);
        }
        catch (IOException localIOException2)
        {
            localIOException2.printStackTrace();
        }
        finally
        {
            if (localBufferedWriter != null) {
                try
                {
                    localBufferedWriter.flush();
                    localBufferedWriter.close();
                }
                catch (IOException localIOException4)
                {
                    localIOException4.printStackTrace();
                }
            }
            b.a(localFile);
        }
    }

    public void a(String paramString1, String paramString2, int paramInt)
    {
        a(paramString1, InnerTaskB.b(paramInt, paramString2));
    }

    public String a(String var1) {
        File var2 = this.b.a(var1);
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

                    if(!InnerTaskB.c(var5)) {
                        var7 = InnerTaskB.d(var5);
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

    public boolean b(String paramString)
    {
        return b.c(paramString);
    }

    public class InnerTaskA
    {
        private final AtomicLong c;
        private final AtomicInteger d;
        private final long e;
        private final int f;
        private final Map<File, Long> g = Collections.synchronizedMap(new HashMap());
        protected File a;

        private InnerTaskA(File paramFile, long paramLong, int paramInt)
        {
            this.a = paramFile;
            this.e = paramLong;
            this.f = paramInt;
            this.c = new AtomicLong();
            this.d = new AtomicInteger();
            a();
        }

        private void a()
        {
            new Thread(new Runnable()
            {
                public void run()
                {
                    int var1 = 0;
                    int var2 = 0;
                    File[] var3 = a.listFiles();
                    if(var3 != null) {
                        File[] var4 = var3;
                        int var5 = var3.length;

                        for(int var6 = 0; var6 < var5; ++var6) {
                            File var7 = var4[var6];
                            var1 = (int)((long)var1 + b.b(var7));
                            ++var2;
                            g.put(var7, Long.valueOf(var7.lastModified()));
                        }

                        c.set((long)var1);
                        d.set(var2);
                    }

                }
            }).start();
        }

        private void a(File paramFile)
        {
            long var3;
            for(int var2 = this.d.get(); var2 + 1 > this.f; var2 = this.d.addAndGet(-1)) {
                var3 = this.b();
                this.c.addAndGet(-var3);
            }

            this.d.addAndGet(1);
            var3 = this.b(paramFile);

            long var7;
            for(long var5 = this.c.get(); var5 + var3 > this.e; var5 = this.c.addAndGet(-var7)) {
                var7 = this.b();
            }

            this.c.addAndGet(var3);
            Long var9 = Long.valueOf(System.currentTimeMillis());
            paramFile.setLastModified(var9.longValue());
            this.g.put(paramFile, var9);
        }

        private File a(String paramString)
        {
            File localFile = b(paramString);
            Long localLong = Long.valueOf(System.currentTimeMillis());
            localFile.setLastModified(localLong.longValue());
            this.g.put(localFile, localLong);

            return localFile;
        }

        private File b(String paramString)
        {
            return new File(this.a, paramString.hashCode() + "");
        }

        private boolean c(String paramString)
        {
            File localFile = a(paramString);
            return localFile.delete();
        }

        private long b()
        {
            if (this.g.isEmpty()) {
                return 0L;
            }
            Long var1 = null;
            File var2 = null;
            File localFile = null;
            Set localSet = this.g.entrySet();
            synchronized (this.g)
            {
                Iterator var5 = localSet.iterator();

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
            long l = b(localFile);
            if (localFile.delete()) {
                this.g.remove(localFile);
            }
            return l;
        }

        private long b(File paramFile)
        {
            return paramFile.length();
        }
    }

    private static class InnerTaskB
    {
        private static boolean c(String paramString)
        {
            return a(paramString.getBytes());
        }

        private static boolean a(byte[] paramArrayOfByte)
        {
            String[] arrayOfString = c(paramArrayOfByte);
            if ((arrayOfString != null) && (arrayOfString.length == 2))
            {
                String str = arrayOfString[0];
                while (str.startsWith("0")) {
                    str = str.substring(1, str.length());
                }
                long l1 = Long.valueOf(str).longValue();
                long l2 = Long.valueOf(arrayOfString[1]).longValue();
                if (System.currentTimeMillis() > l1 + l2 * 1000L) {
                    return true;
                }
            }
            return false;
        }

        private static String b(int paramInt, String paramString)
        {
            return a(paramInt) + paramString;
        }

        private static String d(String paramString)
        {
            if ((paramString != null) && (b(paramString.getBytes()))) {
                paramString = paramString.substring(paramString.indexOf(' ') + 1, paramString
                        .length());
            }
            return paramString;
        }

        private static boolean b(byte[] paramArrayOfByte)
        {
            return (paramArrayOfByte != null) && (paramArrayOfByte.length > 15) && (paramArrayOfByte[13] == 45) &&
                    (a(paramArrayOfByte, ' ') > 14);
        }

        private static String[] c(byte[] paramArrayOfByte)
        {
            if (b(paramArrayOfByte))
            {
                String str1 = new String(a(paramArrayOfByte, 0, 13));
                String str2 = new String(a(paramArrayOfByte, 14,
                        a(paramArrayOfByte, ' ')));
                return new String[] { str1, str2 };
            }
            return null;
        }

        private static int a(byte[] paramArrayOfByte, char paramChar)
        {
            for (int i = 0; i < paramArrayOfByte.length; i++) {
                if (paramArrayOfByte[i] == paramChar) {
                    return i;
                }
            }
            return -1;
        }

        private static byte[] a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
        {
            int i = paramInt2 - paramInt1;
            if (i < 0) {
                throw new IllegalArgumentException(paramInt1 + " > " + paramInt2);
            }
            byte[] arrayOfByte = new byte[i];
            System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0,
                    Math.min(paramArrayOfByte.length - paramInt1, i));
            return arrayOfByte;
        }

        private static String a(int paramInt)
        {
            String str = System.currentTimeMillis() + "";
            while (str.length() < 13) {
                str = "0" + str;
            }
            return str + "-" + paramInt + ' ';
        }
    }
}
