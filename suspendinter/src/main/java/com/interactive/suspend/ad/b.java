package com.interactive.suspend.ad;

import android.content.Context;

public class b
{
  private Context a;
  private static b b;
  private Class c;
  
  public static synchronized b a(Context paramContext)
  {
    if (b == null) {
      b = new b(paramContext);
    }
    return b;
  }
  
  private b(Context paramContext)
  {
    this.a = paramContext.getApplicationContext();
  }
  
  public Class a()
  {
    return this.c;
  }
  
  public void a(Class paramClass)
  {
    this.c = paramClass;
  }
  
  public void b()
  {
    this.c = null;
  }
}
