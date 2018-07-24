package com.interactive.suspend.ad.util;

public abstract interface LoadAdCallback
{
  public abstract void loadSuccess();
  
  public abstract void loadFail(String paramString);
}
