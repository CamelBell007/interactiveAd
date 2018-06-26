package com.interactive.ad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.interactive.suspend.ad.InteractiveAd;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InteractiveAd.getInstance().init();
    }
}
