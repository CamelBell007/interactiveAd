package com.interactive.suspend.ad.util;

import android.content.res.AssetManager;

import com.interactive.suspend.ad.constant.CollectorError;

import org.json.JSONObject;

public class HelperJNI
{
  static
  {
    try
    {
      System.loadLibrary("tongdun_db");
    }
    catch (Throwable localThrowable2)
    {
      Throwable localThrowable1;
      JSONObject localJSONObject = CollectorError.catchErr(localThrowable1 =
      
        localThrowable2);LogUtil.e("Couldn't load so:" + localJSONObject, localThrowable1);CollectorError.addError(CollectorError.TYPE.ERROR_SO_LOAD, localJSONObject);
    }
  }
  
  public static int decode(AssetManager paramAssetManager, String paramString1, String paramString2)
  {
    return 2;
  }
  
  public static byte[] compress(byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return null;
    }
//    try
//    {
//      return n1(paramArrayOfByte, paramInt);
//    }
//    catch (Throwable localThrowable)
//    {
//    }
    return null;
  }
  
  public static String manager(int paramInt, String... paramVarArgs)
  {
    return null;
  }
  
//  private static native int n0(byte[] paramArrayOfByte, int paramInt, String paramString1, String paramString2);
//
//  private static native byte[] n1(byte[] paramArrayOfByte, int paramInt);
//
//  private static native String n2(String paramString);
//
//  private static native String n3(int paramInt);
//
//  private static native String n4();
//
//  private static native String n5();
//
//  private static native String n6();
//
//  private static native String n7();
//
//  private static native int n8();
//
//  private static native void n9();
//
//  private static native void n10(int paramInt);
//
//  private static native void n11();
//
//  private static native boolean n12(String paramString);
}
