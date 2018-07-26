package com.interactive.suspend.ad.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class LogUtil
{
  public static final String TAG = "VVCC--";
  public static final boolean DEV = false;
  public static boolean D = false;
  public static boolean I = false;
  public static boolean E = false;
  private static boolean existDebugFile = false;
  
  public static void info(boolean paramBoolean)
  {
    I = paramBoolean;
  }
  
  public static void debug(boolean paramBoolean)
  {
    D = paramBoolean;
  }
  
  public static void error(boolean paramBoolean)
  {
    E = paramBoolean;
  }
  
  public static void openLog()
  {
    D = true;
    I = true;
    E = true;
  }
  
  public static void i(String paramString)
  {
    i("TD_JAVA", paramString);
  }
  
  public static void i(String paramString1, String paramString2)
  {
    if (I) {
      Log.i(paramString1, paramString2);
    }
  }
  
  public static void d(String paramString)
  {
    d("TD_JAVA", paramString);
  }
  
  public static void d(String paramString1, String paramString2)
  {
    if (D) {
      Log.d(paramString1, paramString2);
    }
  }
  
  public static void e(String paramString, Throwable paramThrowable)
  {
    Log.e("TD_JAVA", paramString);
    if (E) {
      paramThrowable.printStackTrace();
    }
  }
  
  public static void e(String paramString)
  {
    e("TD_JAVA", paramString);
  }
  
  public static void e(String paramString1, String paramString2)
  {
    if (E) {
      Log.e(paramString1, paramString2);
    }
  }
  
  public static void dev(String paramString) {}
  
  public static void dev(String paramString, Object... paramVarArgs)
  {
    String.format(paramString, paramVarArgs);
  }
  
  public static void err(String paramString)
  {
    Log.e("TD_JAVA", paramString);
  }
  
  public static void info(String paramString)
  {
    Log.i("TD_JAVA", paramString);
  }

  public static boolean isDebugMode() {
    if (existDebugFile) {
      return true;
    }
    try {
      File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "test.debug");
      if (file.exists()) {
        existDebugFile = true;
        return true;
      }
    } catch (Throwable e) {
      Log.e(LogUtil.TAG,e.getMessage());
    }
    return false;
  }

}
