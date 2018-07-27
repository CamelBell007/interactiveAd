//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.interactive.suspend.ad.R;
import com.interactive.suspend.ad.util.TargetClassManager;
import com.interactive.suspend.ad.view.BrowserLayout;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class TmActivity extends Activity {
    private String mBundleKeyURL = null;
    private boolean mBundleKeyShowButtonBar = true;
    private static Intent mIntent;
    private BrowserLayout mBrowserLayout;

    public TmActivity() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.tm_activity_webview_in_sdk);
        Intent intent = this.getIntent();
        this.mBundleKeyURL = intent.getExtras().getString("BUNDLE_KEY_URL");
        this.mBundleKeyShowButtonBar = intent.getExtras().getBoolean("BUNDLE_KEY_SHOW_BOTTOM_BAR");
        this.mBrowserLayout = (BrowserLayout)this.findViewById(R.id.tm_common_web_browser_layout);
        if(!TextUtils.isEmpty(this.mBundleKeyURL)) {
            this.mBrowserLayout.loadUrl(this.mBundleKeyURL);
        }

        if(!this.mBundleKeyShowButtonBar) {
            this.mBrowserLayout.gone();
        } else {
            this.mBrowserLayout.visible();
        }

        this.mBrowserLayout.setOnBackClickListener(new OnClickListener() {
            public void onClick(View view) {
                TmActivity.this.loadMoreSource();
            }
        });
    }

    public static void jumptoShowActivity(Context context, String keyUrl) {
        if(!TextUtils.isEmpty(keyUrl)) {
            Intent intent = new Intent(context, TmActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("BUNDLE_KEY_URL", keyUrl);
            intent.putExtra("BUNDLE_KEY_SHOW_BOTTOM_BAR", true);
            context.startActivity(intent);
        }

    }

    public static void jumptoShowActivity(Context context, String keyUrl, Intent intent) {
        if(!TextUtils.isEmpty(keyUrl)) {
            mIntent = intent;
            Intent var3 = new Intent(context, TmActivity.class);
            var3.addFlags(268435456);
            var3.putExtra("BUNDLE_KEY_URL", keyUrl);
            var3.putExtra("BUNDLE_KEY_SHOW_BOTTOM_BAR", true);
            context.startActivity(var3);
        }

    }

    private void loadMoreSource() {
        if(this.mBrowserLayout.canGoBack()) {
            this.mBrowserLayout.goback();
        } else {
            this.mBrowserLayout.loadUrl("about:blank");
            this.finish();
            Class targetActivity = TargetClassManager.getSingleInstance(this).getNeedClass();
            if(targetActivity != null) {
                if(mIntent == null) {
                    Intent var2 = new Intent(this, targetActivity);
                    this.startActivity(var2);
                    TargetClassManager.getSingleInstance(this).releaseClass();
                } else {
                    this.startActivity(mIntent);
                    TargetClassManager.getSingleInstance(this).releaseClass();
                }
            }
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
        case 4:
            this.loadMoreSource();
            return true;
        default:
            return super.onKeyDown(keyCode, keyEvent);
        }
    }
}
