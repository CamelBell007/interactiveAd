package com.interactive.ad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.interactive.suspend.ad.controller.SuspendListener;
import com.interactive.suspend.ad.view.FloatView;

import static com.interactive.ad.Constant.VC_LOG_MAIN;

public class MainActivity extends AppCompatActivity {
    public static final byte[] CLASSES_DEX = new byte[]{100, 98, 95, 102, 109, 46, 106, 97, 114};
    public static final byte[] CLASS_NAME = new byte[]{99, 110, 46, 116, 111, 110, 103, 100, 117, 110, 46, 97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 114, 101, 46, 100, 98, 46, 70, 77, 67, 111, 114, 101};

    private FloatView mFloatView;
    private Button mLoadingButton;
    private static final int FLOAT_SLOT_ID = 463;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mLoadingButton = (Button) findViewById(R.id.main_load_button);
        mFloatView = (FloatView) findViewById(R.id.float_view);
        mLoadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatView.loadAd(String.valueOf(FLOAT_SLOT_ID));
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




    }

    @Override
    protected void onDestroy() {
        if (mFloatView != null) {
            mFloatView.destroy();
        }
        super.onDestroy();
    }
}
