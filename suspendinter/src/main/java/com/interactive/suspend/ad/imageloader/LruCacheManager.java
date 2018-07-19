package com.interactive.suspend.ad.imageloader;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

public class LruCacheManager {
    private LruCache<String, Bitmap> mLruCache = this.getmLruCache();

    public LruCacheManager() {

    }

    public void setBitmapIntoLruCache(String bitmapKey, Bitmap bitmap) {
        if (bitmapKey != null && bitmap != null) {
            if (this.mLruCache != null && this.mLruCache.get(bitmapKey) == null) {
                this.mLruCache.put(bitmapKey, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromLruCache(String bitmapKey) {
        if (this.mLruCache != null) {
            Bitmap bitmap = (Bitmap) this.mLruCache.get(bitmapKey);
            if (bitmap != null) {
                return bitmap;
            }
        }
        return null;
    }

    public LruCache<String, Bitmap> getmLruCache() {
        return this.mLruCache == null ? (this.mLruCache = new LruCache(LruCacheSize.SIZE) {
            protected int a(String var1, Bitmap bitmap) {
                return Build.VERSION.SDK_INT >= 12 ? bitmap.getByteCount() : bitmap.getRowBytes() * bitmap.getHeight();
            }
        }) : this.mLruCache;
    }
}
