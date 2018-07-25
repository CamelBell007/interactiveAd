//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.interactive.suspend.ad.R;
import com.interactive.suspend.ad.imageloader.LoadImageCallBack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GifView extends android.support.v7.widget.AppCompatImageView implements com.interactive.suspend.ad.imageloader.ImageLoadBaseSingle.LoadImageCallBack {
    private LoadImageCallBack mLoadImageCallBack;
    private int mGifViewResource;
    private volatile boolean mGifViewPause;
    private Movie mMovie;
    private long mRecordTime;
    private int mRelativeTime;
    private float mXLocation;
    private float mYLocation;
    private float mSimple;
    private int mWidth;
    private int mHeight;
    private boolean mIsVisible;

    public GifView(Context context) {
        this(context, (AttributeSet) null);
    }

    public GifView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.styleable.GifView_gif);
    }

    public GifView(Context context, AttributeSet attributeSet, int defAttr) {
        super(context, attributeSet, defAttr);
        this.mIsVisible = true;
        this.init(context, attributeSet, defAttr);
    }

    @SuppressLint({"NewApi"})
    private void init(Context context, AttributeSet attributeSet, int defStyleAttr) {
        if (VERSION.SDK_INT >= 11) {
            this.setLayerType(1, (Paint) null);
        }

        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.GifView, defStyleAttr, R.style.Widget_GifView);
        this.mGifViewResource = typeArray.getResourceId(R.styleable.GifView_gif, -1);
        this.mGifViewPause = typeArray.getBoolean(R.styleable.GifView_paused, false);
        typeArray.recycle();
        if (this.mGifViewResource != -1) {
            this.mMovie = Movie.decodeStream(this.getResources().openRawResource(this.mGifViewResource));
        }

    }

    public void setGifResource(int viewResource) {
        this.mGifViewResource = viewResource;
        this.mMovie = Movie.decodeStream(this.getResources().openRawResource(this.mGifViewResource));
        this.requestLayout();
    }

    public void setGifUrl(String gifUrl) {
        RequestGifUrlAndShowAsyncTask var2 = new RequestGifUrlAndShowAsyncTask();
        var2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{gifUrl});
    }

    public void setLoadCallback(LoadImageCallBack loadImageCallback) {
        this.mLoadImageCallBack = loadImageCallback;
    }

    protected void onMeasure(int var1, int var2) {
        if (this.mMovie != null) {
            int var3 = this.mMovie.width();
            int var4 = this.mMovie.height();
            float var5 = 1.0F;
            float var6 = 1.0F;
            int var7 = MeasureSpec.getMode(var1);
            int var8;
            if (var7 != 0) {
                var8 = MeasureSpec.getSize(var1);
                var6 = (float) var3 / (float) var8;
            }

            var8 = MeasureSpec.getMode(var2);
            if (var8 != 0) {
                int var9 = MeasureSpec.getSize(var2);
                var5 = (float) var4 / (float) var9;
            }

            this.mSimple = 1.0F / Math.min(var6, var5);
            this.mWidth = (int) ((float) var3 * this.mSimple);
            this.mHeight = (int) ((float) var4 * this.mSimple);
            this.setMeasuredDimension(this.mWidth, this.mHeight);
        } else {
            super.onMeasure(var1, var2);
        }

    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        super.onLayout(var1, var2, var3, var4, var5);
        if (this.mMovie != null) {
            this.mXLocation = (float) (this.getWidth() - this.mWidth) / 2.0F;
            this.mYLocation = (float) (this.getHeight() - this.mHeight) / 2.0F;
            this.mIsVisible = this.getVisibility() == View.VISIBLE;
        }

    }

    protected void onDraw(Canvas var1) {
        if (this.mMovie != null) {
            if (!this.mGifViewPause) {
                this.getRelativeTime();
                this.a(var1);
                this.refresh();
            } else {
                this.a(var1);
            }
        } else {
            super.onDraw(var1);
        }

    }

    @SuppressLint({"NewApi"})
    private void refresh() {
        if (this.mIsVisible) {
            if (VERSION.SDK_INT >= 16) {
                this.postInvalidateOnAnimation();
            } else {
                this.invalidate();
            }
        }

    }

    private void getRelativeTime() {
        long noSleepTime = SystemClock.uptimeMillis();
        if (this.mRecordTime == 0L) {
            this.mRecordTime = noSleepTime;
        }

        int movieDuration = this.mMovie.duration();
        if (movieDuration == 0) {
            movieDuration = 1000;
        }

        this.mRelativeTime = (int) ((noSleepTime - this.mRecordTime) % (long) movieDuration);
    }

    private void a(Canvas var1) {
        this.mMovie.setTime(this.mRelativeTime);
        var1.save(Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
        var1.scale(this.mSimple, this.mSimple);
        this.mMovie.draw(var1, this.mXLocation / this.mSimple, this.mYLocation / this.mSimple);
        var1.restore();
    }

    @SuppressLint({"NewApi"})
    public void onScreenStateChanged(int var1) {
        super.onScreenStateChanged(var1);
        this.mIsVisible = var1 == 1;
        this.refresh();
    }

    @SuppressLint({"NewApi"})
    protected void onVisibilityChanged(View var1, int var2) {
        super.onVisibilityChanged(var1, var2);
        this.mIsVisible = var2 == View.VISIBLE;
        this.refresh();
    }

    protected void onWindowVisibilityChanged(int var1) {
        super.onWindowVisibilityChanged(var1);
        this.mIsVisible = var1 == View.VISIBLE;
        this.refresh();
    }

    private static byte[] refresh(InputStream var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream(1024);
        byte[] var2 = new byte[1024];

        int var3;
        try {
            while ((var3 = var0.read(var2)) >= 0) {
                var1.write(var2, 0, var3);
            }
        } catch (IOException var5) {
            ;
        }

        return var1.toByteArray();
    }

    @Override
    public void loadSuccessed(Bitmap bitmap, String url) {
        if (bitmap != null && !bitmap.isRecycled() && this.mLoadImageCallBack != null) {
            this.mLoadImageCallBack.loadImageSuccessed();
        }
    }

    @Override
    public void loadFailed() {
        if (this.mLoadImageCallBack != null) {
            this.mLoadImageCallBack.loadImageFailed();
        }
    }

    public class RequestGifUrlAndShowAsyncTask extends AsyncTask<String, Void, Object> {
        public RequestGifUrlAndShowAsyncTask() {
        }

        @Override
        protected Object doInBackground(String... params) {
            InputStream inputStream = null;
            Object object = null;
            HttpURLConnection httpURLConnection = null;

            byte[] arrayByte;
            try {
                String message;
                try {
                    URL url = new URL(params[0]);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() != 200) {
                        message = "Server returned HTTP " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage();
                        return message;
                    }

                    int contentLength = httpURLConnection.getContentLength();
                    inputStream = httpURLConnection.getInputStream();
                    byte[] gifByte = GifView.refresh(inputStream);
                    if (object != null) {
                        ((OutputStream) object).close();
                    }

                    if (inputStream != null) {
                        inputStream.close();
                    }

                    arrayByte = gifByte;
                } catch (Exception var12) {
                    message = var12.toString();
                    return message;
                }
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }

            }

            return arrayByte;
        }

        protected void onPostExecute(Object object) {
            if (object instanceof byte[]) {
                if (GifView.this.mLoadImageCallBack != null) {
                    GifView.this.mLoadImageCallBack.loadImageSuccessed();
                }

                byte[] gifArrayByte = (byte[]) object;
                GifView.this.refresh();
                GifView.this.mMovie = Movie.decodeByteArray(gifArrayByte, 0, gifArrayByte.length);
                GifView.this.requestLayout();
            }

        }
    }
}
