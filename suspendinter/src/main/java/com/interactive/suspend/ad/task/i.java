package com.interactive.suspend.ad.task;

import android.content.Context;
import android.content.pm.PackageManager;

public class i
{
  public static boolean a(Context paramContext, String paramString)
  {
    try
    {
      return paramContext.checkCallingOrSelfPermission(paramString) == PackageManager.PERMISSION_GRANTED;
    }
    catch (Exception localException) {}
    return false;
  }
}
