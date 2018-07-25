package com.interactive.suspend.ad.manager;

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

public class CacheManager
{
    private static Map<String, CacheManager> mCacheManagerMap = new HashMap();
    private CacheManager.InnerTaskA mInnerTaskA;

    public static CacheManager getCacheManagerInstance(Context context)
    {
        return getCacheManagerInstance(context, "ACache");
    }

    public static CacheManager getCacheManagerInstance(Context context, String cacheFileName)
    {
        File cacheFile = new File(context.getCacheDir(), cacheFileName);
        return getCacheManagerInstance(cacheFile, 50000000L, Integer.MAX_VALUE);
    }

    public static CacheManager getCacheManagerInstance(File cacheFile, long fileSize, int defaultFileNum)
    {
        CacheManager cacheManager = (CacheManager) mCacheManagerMap.get(cacheFile.getAbsoluteFile() + getProcessId());
        if (cacheManager == null)
        {
            cacheManager = new CacheManager(cacheFile, fileSize, defaultFileNum);
            mCacheManagerMap.put(cacheFile.getAbsolutePath() + getProcessId(), cacheManager);
        }
        return cacheManager;
    }

    private static String getProcessId()
    {
        return "_" + Process.myPid();
    }

    private CacheManager(File cacheFile, long defaultFileSize, int defaultFileNum)
    {
        if ((cacheFile.exists()) || (cacheFile.mkdirs())) {
            this.mInnerTaskA = new InnerTaskA(cacheFile, defaultFileSize, defaultFileNum);
        }
    }

    public void a(String paramString1, String paramString2)
    {
        File localFile = mInnerTaskA.createAndRecordModify(paramString1);
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
            mInnerTaskA.a(localFile);
        }
    }

    public void a(String paramString1, String paramString2, int paramInt)
    {
        a(paramString1, InnerTaskB.b(paramInt, paramString2));
    }

    public String a(String var1) {
        File var2 = this.mInnerTaskA.createAndRecordModify(var1);
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
        return mInnerTaskA.c(paramString);
    }

    public class InnerTaskA
    {
        private final AtomicLong mFileSize;
        private final AtomicInteger mDealFileNum;
        private final long mDefaultFileSize;
        private final int mDefaultFileNum;
        private final Map<File, Long> mFileModifyMap = Collections.synchronizedMap(new HashMap());
        protected File parentFile;

        private InnerTaskA(File parentFile, long fileDefaultSize, int fileDefaultNum)
        {
            this.parentFile = parentFile;
            this.mDefaultFileSize = fileDefaultSize;
            this.mDefaultFileNum = fileDefaultNum;
            this.mFileSize = new AtomicLong();
            this.mDealFileNum = new AtomicInteger();
            taskAInit();
        }

        private void taskAInit()
        {
            new Thread(new Runnable()
            {
                public void run()
                {
                    int fileSize = 0;
                    int dealFileNum = 0;
                    File[] childFiles = parentFile.listFiles();
                    if(childFiles != null) {
                        File[] arrayFile = childFiles;
                        int fileNum = childFiles.length;

                        for(int i = 0; i < fileNum; ++i) {
                            File file = arrayFile[i];
                            fileSize = (int)((long)fileSize + mInnerTaskA.getFileSize(file));
                            ++dealFileNum;
                            mFileModifyMap.put(file, Long.valueOf(file.lastModified()));
                        }

                        mFileSize.set((long)fileSize);
                        mDealFileNum.set(dealFileNum);
                    }

                }
            }).start();
        }

        private void a(File file)
        {
            long var3;
            for(int i = this.mDealFileNum.get(); i + 1 > this.mDefaultFileNum; i = this.mDealFileNum.addAndGet(-1)) {
                var3 = this.createFile();
                this.mFileSize.addAndGet(-var3);
            }

            this.mDealFileNum.addAndGet(1);
            var3 = this.getFileSize(file);

            long var7;
            for(long var5 = this.mFileSize.get(); var5 + var3 > this.mDefaultFileSize; var5 = this.mFileSize.addAndGet(-var7)) {
                var7 = this.createFile();
            }

            this.mFileSize.addAndGet(var3);
            Long modifyTime = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(modifyTime.longValue());
            this.mFileModifyMap.put(file, modifyTime);
        }

        private File createAndRecordModify(String slotId)
        {
            File localFile = createFile(slotId);
            Long localLong = Long.valueOf(System.currentTimeMillis());
            localFile.setLastModified(localLong.longValue());
            this.mFileModifyMap.put(localFile, localLong);

            return localFile;
        }

        private File createFile(String slotId)
        {
            return new File(this.parentFile, slotId.hashCode() + "");
        }

        private boolean c(String paramString)
        {
            File localFile = createAndRecordModify(paramString);
            return localFile.delete();
        }

        private long createFile()
        {
            if (this.mFileModifyMap.isEmpty()) {
                return 0L;
            }
            Long var1 = null;
            File var2 = null;
            File localFile = null;
            Set localSet = this.mFileModifyMap.entrySet();
            synchronized (this.mFileModifyMap)
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
            long l = getFileSize(localFile);
            if (localFile.delete()) {
                this.mFileModifyMap.remove(localFile);
            }
            return l;
        }

        private long getFileSize(File file)
        {
            return file.length();
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
