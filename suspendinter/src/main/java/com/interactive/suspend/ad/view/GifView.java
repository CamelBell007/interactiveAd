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
import com.interactive.suspend.ad.imageloader.d;
import com.interactive.suspend.ad.imageloader.ImageLoadBaseSingle.LoadImageCallBack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GifView extends android.support.v7.widget.AppCompatImageView implements LoadImageCallBack {
    private com.interactive.suspend.ad.imageloader.d a;
    private int b;
    private Movie c;
    private long d;
    private int e;
    private float f;
    private float g;
    private float h;
    private int i;
    private int j;
    private volatile boolean k;
    private boolean l;

    public GifView(Context var1) {
        this(var1, (AttributeSet) null);
    }

    public GifView(Context var1, AttributeSet var2) {
        this(var1, var2, R.styleable.GifView_gif);
    }

    public GifView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.l = true;
        this.a(var1, var2, var3);
    }

    @SuppressLint({"NewApi"})
    private void a(Context var1, AttributeSet var2, int var3) {
        if (VERSION.SDK_INT >= 11) {
            this.setLayerType(1, (Paint) null);
        }

        TypedArray var4 = var1.obtainStyledAttributes(var2, R.styleable.GifView, var3, R.style.Widget_GifView);
        this.b = var4.getResourceId(R.styleable.GifView_gif, -1);
        this.k = var4.getBoolean(R.styleable.GifView_paused, false);
        var4.recycle();
        if (this.b != -1) {
            this.c = Movie.decodeStream(this.getResources().openRawResource(this.b));
        }

    }

    public void setGifResource(int var1) {
        this.b = var1;
        this.c = Movie.decodeStream(this.getResources().openRawResource(this.b));
        this.requestLayout();
    }

    public void setGifUrl(String var1) {
        GifView.a var2 = new GifView.a();
        var2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{var1});
    }

    public void setLoadCallback(d var1) {
        this.a = var1;
    }

    protected void onMeasure(int var1, int var2) {
        if (this.c != null) {
            int var3 = this.c.width();
            int var4 = this.c.height();
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

            this.h = 1.0F / Math.min(var6, var5);
            this.i = (int) ((float) var3 * this.h);
            this.j = (int) ((float) var4 * this.h);
            this.setMeasuredDimension(this.i, this.j);
        } else {
            super.onMeasure(var1, var2);
        }

    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        super.onLayout(var1, var2, var3, var4, var5);
        if (this.c != null) {
            this.f = (float) (this.getWidth() - this.i) / 2.0F;
            this.g = (float) (this.getHeight() - this.j) / 2.0F;
            this.l = this.getVisibility() == View.VISIBLE;
        }

    }

    protected void onDraw(Canvas var1) {
        if (this.c != null) {
            if (!this.k) {
                this.c();
                this.a(var1);
                this.b();
            } else {
                this.a(var1);
            }
        } else {
            super.onDraw(var1);
        }

    }

    @SuppressLint({"NewApi"})
    private void b() {
        if (this.l) {
            if (VERSION.SDK_INT >= 16) {
                this.postInvalidateOnAnimation();
            } else {
                this.invalidate();
            }
        }

    }

    private void c() {
        long var1 = SystemClock.uptimeMillis();
        if (this.d == 0L) {
            this.d = var1;
        }

        int var3 = this.c.duration();
        if (var3 == 0) {
            var3 = 1000;
        }

        this.e = (int) ((var1 - this.d) % (long) var3);
    }

    private void a(Canvas var1) {
        this.c.setTime(this.e);
        var1.save(Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
        var1.scale(this.h, this.h);
        this.c.draw(var1, this.f / this.h, this.g / this.h);
        var1.restore();
    }

    @SuppressLint({"NewApi"})
    public void onScreenStateChanged(int var1) {
        super.onScreenStateChanged(var1);
        this.l = var1 == 1;
        this.b();
    }

    @SuppressLint({"NewApi"})
    protected void onVisibilityChanged(View var1, int var2) {
        super.onVisibilityChanged(var1, var2);
        this.l = var2 == View.VISIBLE;
        this.b();
    }

    protected void onWindowVisibilityChanged(int var1) {
        super.onWindowVisibilityChanged(var1);
        this.l = var1 == View.VISIBLE;
        this.b();
    }

    private static byte[] b(InputStream var0) {
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
    public void loadSuccessed(Bitmap var1, String var2) {
        if (var1 != null && !var1.isRecycled() && this.a != null) {
            this.a.a();
        }
    }

    @Override
    public void loadFailed() {
        if (this.a != null) {
            this.a.b();
        }
    }

    public class a extends AsyncTask<String, Void, Object> {
        public a() {
        }

        @Override
        protected Object doInBackground(String... params) {
            InputStream var2 = null;
            Object var3 = null;
            HttpURLConnection var4 = null;

            byte[] var8;
            try {
                String var6;
                try {
                    URL var5 = new URL(params[0]);
                    var4 = (HttpURLConnection) var5.openConnection();
                    var4.connect();
                    if (var4.getResponseCode() != 200) {
                        var6 = "Server returned HTTP " + var4.getResponseCode() + " " + var4.getResponseMessage();
                        return var6;
                    }

                    int var14 = var4.getContentLength();
                    var2 = var4.getInputStream();
                    byte[] var7 = GifView.b(var2);
                    if (var3 != null) {
                        ((OutputStream) var3).close();
                    }

                    if (var2 != null) {
                        var2.close();
                    }

                    var8 = var7;
                } catch (Exception var12) {
                    var6 = var12.toString();
                    return var6;
                }
            } finally {
                if (var4 != null) {
                    var4.disconnect();
                }

            }

            return var8;
        }

        protected void onPostExecute(Object var1) {
            if (var1 instanceof byte[]) {
                if (GifView.this.a != null) {
                    GifView.this.a.a();
                }

                byte[] var2 = (byte[]) ((byte[]) var1);
                GifView.this.b();
                GifView.this.c = Movie.decodeByteArray(var2, 0, var2.length);
                GifView.this.requestLayout();
            }

        }
    }
}
