package com.interactive.suspend.ad.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.interactive.suspend.ad.imageloader.LoadImageCallBack;
import com.interactive.suspend.ad.imageloader.ImageLoadBaseSingle;

import java.util.concurrent.Future;

public class WebImageView extends android.support.v7.widget.AppCompatImageView implements ImageLoadBaseSingle.LoadImageCallBack {
  private String mURL;
  private Future<Bitmap> mBitmapFuture;
  private Handler mHandler = new Handler();
  private LoadImageCallBack mLoadCallback;
  private int mDefaultBackground;
  private  Runnable mRunnable;

  public WebImageView(Context context) {
    super(context);
  }

  public WebImageView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  public WebImageView(Context context, AttributeSet attributeSet, int defStyleAttr) {
    super(context, attributeSet, defStyleAttr);
  }

  public void loadSource(String url, int background) {
    this.mDefaultBackground = background;
    if(TextUtils.isEmpty(url)) {
      this.mURL = null;
      this.cancelTask(false);
    } else {
      this.mURL = url;
      this.cancelTask(false);

      try {
        if(ImageLoadBaseSingle.getInstance().isBitmapExist(url)) {
          this.setImageBitmap(ImageLoadBaseSingle.getInstance().f(this.mURL));
          if(mLoadCallback != null) {
            mLoadCallback.loadImageSuccessed();
          }
        } else {
          this.mBitmapFuture = ImageLoadBaseSingle.getInstance().getFuture(this.getContext(), 0, this.mURL, this);
        }

      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  public void setLoadCallback(LoadImageCallBack loadCallBack) {
    mLoadCallback = loadCallBack;
  }

  public void cancelTask(boolean isFinish) {
    if(this.mRunnable != null) {
      this.mHandler.removeCallbacks(this.mRunnable);
      this.mRunnable = null;
    }

    if(this.mBitmapFuture != null) {
      try {
        this.mBitmapFuture.cancel(true);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }

    if(isFinish) {
      mLoadCallback = null;
    }

  }

  public void loadSuccessed(final Bitmap var1, String var2) {
    if(var2.equals(this.mURL) && var1 != null && !var1.isRecycled()) {
      this.mHandler.post(this.mRunnable = new Runnable() {
        public void run() {
          WebImageView.this.setImageBitmap(var1);
          if(mLoadCallback != null) {
             mLoadCallback.loadImageSuccessed();
          }

        }
      });
    }

    this.mBitmapFuture = null;
  }

  public void loadFailed() {
    if(mLoadCallback!= null) {
      mLoadCallback.loadImageFailed();
    }

  }
}
