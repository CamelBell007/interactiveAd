package com.interactive.suspend.ad.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.interactive.suspend.ad.R;
import com.interactive.suspend.ad.task.StartHtmlAsyncTask;

/**
 * Created by Administrator on 2018/6/28.
 */

public class BrowserLayout extends LinearLayout {
    private Context mContext = null;
    private WebView mWebView = null;
    private TextView mTextView = null;
    private View mView = null;
    private ImageButton vImageButton = null;
    private int mMarginTop = 5;
    private ProgressBar mProgressBar = null;
    private String mTitle;
    private OnClickListener mOnClickListener;

    public BrowserLayout(Context content) {
        super(content);
        this.init(content);
    }

    public BrowserLayout(Context content, AttributeSet attributSet) {
        super(content, attributSet);
        this.init(content);
    }

    public void setOnBackClickListener(OnClickListener var1) {
        this.mOnClickListener = var1;
    }

    private void init(Context content) {
        this.mContext = content;
        this.setOrientation(VERTICAL);
        mView = LayoutInflater.from(content).inflate(R.layout.tm_browser_controller,null);
        vImageButton = (ImageButton)mView.findViewById(R.id.browser_controller_back);
        mTextView = (TextView)mView.findViewById(R.id.browser_controller_title);
        vImageButton.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                if(canGoBack()) {
                    goback();
                } else if(mOnClickListener != null) {
                    mOnClickListener.onClick(var1);
                }

            }
        });
        addView(mView, -1, -2);
        mProgressBar = (ProgressBar)LayoutInflater.from(content).inflate(R.layout.tm_progress_horizontal,null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, -1, (int) TypedValue.applyDimension(0, mMarginTop, getResources().getDisplayMetrics()));
        mWebView = new WebView(content);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
//        this.b.getSettings().setPluginState(PluginState.ON);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.setDownloadListener(new DownLoadInfo());
        LayoutParams var2 = new LayoutParams(-1, 0, 1.0F);
        addView(mWebView, var2);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView var1, int var2) {
                super.onProgressChanged(var1, var2);
                if(var2 == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(var2);
                }

            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView var1, String var2) {
                super.onPageFinished(var1, var2);
                mTitle = var2;

                try {
                    if(mWebView != null && mWebView.getTitle() != null) {
                        if(mWebView.getTitle().length() >= 9) {
                            mTextView.setText(mWebView.getTitle().substring(0, 7) + "...");
                        } else {
                            mTextView.setText(mWebView.getTitle());
                        }
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }
        });
    }

    public void loadUrl(String var1) {
        mWebView.loadUrl(var1);
    }

    public boolean canGoBack() {
        return null != mWebView?mWebView.canGoBack():false;
    }

    public void goback() {
        if(null != mWebView) {
            mWebView.goBack();
        }

    }

    public WebView getWebView() {
        return mWebView != null?mWebView:null;
    }

    public void gone() {
        mView.setVisibility(View.GONE);
    }

    public void visible() {
        mView.setVisibility(View.VISIBLE);
    }

    public void setmTextView(String var1) {
        mTextView.setText(var1);
    }

    class DownLoadInfo implements DownloadListener {
        DownLoadInfo() {
        }

        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            if(url.endsWith(".apk")) {
                ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setProgressStyle(1);
                progressDialog.setTitle("正在下载");
                progressDialog.setMessage("完成进度...");
                progressDialog.setProgressStyle(1);
                progressDialog.setCancelable(true);
                final StartHtmlAsyncTask htmlTask = new StartHtmlAsyncTask(mContext, progressDialog);
                htmlTask.execute(new String[]{url});
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface var1) {
                        htmlTask.cancel(true);
                    }
                });
            }

        }
    }
}

