package com.interactive.suspend.ad;

public abstract interface LoadAdCallback
{
  public abstract void loadSuccess();
  
  public abstract void loadFail(String paramString);
}
