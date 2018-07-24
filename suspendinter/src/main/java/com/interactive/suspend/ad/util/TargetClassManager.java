package com.interactive.suspend.ad.util;

import android.content.Context;

public class TargetClassManager
{
  private Context mContext;
  private static TargetClassManager mTargetClassManager;
  private Class mTargetClass;
  
  public static synchronized TargetClassManager getSingleInstance(Context paramContext)
  {
    if (mTargetClassManager == null) {
      mTargetClassManager = new TargetClassManager(paramContext);
    }
    return mTargetClassManager;
  }
  
  private TargetClassManager(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }
  
  public Class getNeedClass()
  {
    return this.mTargetClass;
  }
  
  public void setTargetClass(Class paramClass)
  {
    this.mTargetClass = paramClass;
  }
  
  public void releaseClass()
  {
    this.mTargetClass = null;
  }
}
