package com.interactive.ad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.interactive.suspend.ad.InitListener;
import com.interactive.suspend.ad.InterSDK;
import com.interactive.suspend.ad.InteractiveAd;
import com.interactive.suspend.ad.controller.SuspendListener;
import com.interactive.suspend.ad.view.FloatView;

public class MainActivity extends AppCompatActivity {
    public static final byte[] CLASSES_DEX = new byte[]{100, 98, 95, 102, 109, 46, 106, 97, 114};
    public static final byte[] CLASS_NAME = new byte[]{99, 110, 46, 116, 111, 110, 103, 100, 117, 110, 46, 97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 114, 101, 46, 100, 98, 46, 70, 77, 67, 111, 114, 101};

    private FloatView mFloatView;
    private Button mLoadingButton;
    private static final int FLOAT_SLOT_ID = 463;
    private static final String VC_LOG_MAIN = "VVCC-MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        InteractiveAd.getInstance().init();

//        try {
//            Log.d("VC-String_DEX",new String(CLASSES_DEX, "utf-8")+"db_fm.jar");
//            Log.d("VC-String_NAME",new String(CLASS_NAME, "utf-8")+"cn.tongdun.android.core.db.FMCore");
//
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        mLoadingButton = (Button) findViewById(R.id.main_load_button);
        mFloatView = (FloatView) findViewById(R.id.float_view);
        mLoadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatView.loadAd(FLOAT_SLOT_ID);
            }
        });

        mFloatView.setAdListener(new SuspendListener() {
            @Override
            public void onReceiveAd() {
                Log.d(VC_LOG_MAIN, "onReceiveAd");
            }

            @Override
            public void onFailedToReceiveAd() {
                Log.d(VC_LOG_MAIN, "onFailedToReceiveAd");
            }

            @Override
            public void onLoadFailed() {
                Log.d(VC_LOG_MAIN, "onLoadFailed");
            }

            @Override
            public void onCloseClick() {
                Log.d(VC_LOG_MAIN, "onCloseClick");
            }

            @Override
            public void onAdClick() {
                Log.d(VC_LOG_MAIN, "onAdClick");
            }

            @Override
            public void onAdExposure() {
                Log.d(VC_LOG_MAIN, "onAdExposure");
            }
        });

//        InteractiveAd.getInstance().init(this,"15630924");
        InterSDK.init(this, "15630924", new InitListener() {
            @Override
            public void onInitSuccess() {
                String source = InteractiveAd.getInstance().getSourceIdBySlotId("9f734fdj765ed2b");
                Log.d(VC_LOG_MAIN, "init success: " + source);
            }

            @Override
            public void onInitFailed(String errorMessage) {
                Log.d(VC_LOG_MAIN, "init failed: " + errorMessage);
            }
        });



    }

    @Override
    protected void onDestroy() {
        if (mFloatView != null) {
            mFloatView.destroy();
        }
        super.onDestroy();
    }
}
