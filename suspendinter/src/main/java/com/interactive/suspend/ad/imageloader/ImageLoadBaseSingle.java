//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class ImageLoadBaseSingle {
  private LruCacheManager mLruCacheManager = new LruCacheManager();
  private static ImageLoadBaseSingle mInstance;
  private ExecutorService mExecutorService = Executors.newFixedThreadPool(4);
  private Map<Context, Queue<WeakReference<Future<Bitmap>>>> mFutureMap = new WeakHashMap();
  private int mMaxFutureNum = 10;
  private static final int FixedThreadPoolNums = 4;

  public ImageLoadBaseSingle() {
  }

  public static ImageLoadBaseSingle getInstance() {
    if(mInstance == null) {
      mInstance = new InterImageLoad();
    }

    return mInstance;
  }

  public ExecutorService getExecutorService() {
    return this.mExecutorService == null?(this.mExecutorService = Executors.newFixedThreadPool(FixedThreadPoolNums)):this.mExecutorService;
  }

  public Future<Bitmap> getFuture(Context context, int var2, String var3, ImageLoadBaseSingle.LoadImageCallBack loadImageCallBack) {
    return this.getFuture(context, var2, var3, loadImageCallBack, 0, 0);
  }

  private Future<Bitmap> getFuture(Context context, int var2, String var3, ImageLoadBaseSingle.LoadImageCallBack loadImageCallBack, int var5, int var6) {
    Queue<WeakReference<Future<Bitmap>>> futureQueue = (Queue)this.mFutureMap.get(context);
    if(futureQueue == null) {
      futureQueue = new LinkedList();
      this.mFutureMap.put(context, futureQueue);
    }

    if(((Queue)futureQueue).size() >= this.mMaxFutureNum) {
      WeakReference weakReference = (WeakReference)((Queue)futureQueue).poll();
      if(weakReference != null && weakReference.get() != null) {
        try {
          ((Future)weakReference.get()).cancel(true);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }

    if(this.mExecutorService == null) {
      this.mExecutorService = this.getExecutorService();
      if(this.mLruCacheManager == null) {
        this.mLruCacheManager = new LruCacheManager();
      }
    }

    Future var11 = this.mExecutorService.submit(new LoadBitmapRunnable(var2, var3, loadImageCallBack, var6, var6));
    ((Queue)futureQueue).add(new WeakReference(var11));
    return var11;
  }

  public boolean isBitmapExist(String urlKey) {
    Bitmap bitmap = this.mLruCacheManager.getBitmapFromLruCache(urlKey);
    return bitmap != null && !bitmap.isRecycled();
  }

  public Bitmap f(String var1) {
    return this.mLruCacheManager.getBitmapFromLruCache(var1);
  }

  private String a(String var1, int var2, int var3) {
    String var4 = this.g(var1);
    if(var1.contains(".png")) {
      var1 = this.getUrlWithSize(var4, var2, var3, ".png");
    } else {
      var1 = this.getUrlWithSize(var4, var2, var3, ".jpg");
    }

    return var1;
  }

  protected abstract Bitmap a(String var1);

  protected abstract Bitmap getBitmapByNewSize(String var1, int var2, int var3);

  protected abstract String d(String var1);

  protected abstract void a(Bitmap var1, String var2, String var3);

  protected abstract Bitmap b(String var1);

  protected String g(String var1) {
    if(var1 != null && var1.trim().length() > 0) {
      String var2 = var1.hashCode() + "";
      if(var2 != null && var2.length() > 0) {
        return "" + File.separator + var2;
      }
    }

    return null;
  }

  protected String getUrlWithSize(String url, int width, int hight, String pictureType) {
    StringBuffer stringBuffer = new StringBuffer(url);
    if(".png".equals(pictureType)) {
      stringBuffer.append("_" + width + "x" + hight);
    } else {
      stringBuffer.append("_" + width + "x" + hight);
    }

    return stringBuffer.toString();
  }

  protected boolean isEmpty(String var1) {
    return var1 == null || "".equals(var1) || "null".equals(var1);
  }

  public interface LoadImageCallBack {
    void loadSuccessed(Bitmap var1, String var2);

    void loadFailed();
  }

  public class LoadBitmapRunnable implements Runnable {
    private int b;
    private String c;
    private ImageLoadBaseSingle.LoadImageCallBack loadImageCallback;
    private int e;
    private int f;

    public LoadBitmapRunnable(int var2, String var3, ImageLoadBaseSingle.LoadImageCallBack var4, int var5, int var6) {
      this.b = var2;
      this.c = var3;
      this.loadImageCallback = var4;
      this.e = var5;
      this.f = var6;
    }

    public void run() {
      Bitmap bitmap = ImageLoadBaseSingle.this.mLruCacheManager.getBitmapFromLruCache(this.c);
      if(bitmap != null && !bitmap.isRecycled()) {
        this.loadImageCallback.loadSuccessed(bitmap, this.c);
      } else {
        if(this.e != 0) {
          bitmap = ImageLoadBaseSingle.this.getBitmapByNewSize(this.c, this.e, this.f);
          this.c = ImageLoadBaseSingle.this.a(this.c, this.e, this.f);
        } else {
          bitmap = ImageLoadBaseSingle.this.a(this.c);
        }

        if(bitmap != null && !bitmap.isRecycled()) {
          ImageLoadBaseSingle.this.mLruCacheManager.setBitmapIntoLruCache(this.c, bitmap);
          this.loadImageCallback.loadSuccessed(bitmap, this.c);
        } else {
          this.loadImageCallback.loadFailed();
        }

      }
    }
  }
}
