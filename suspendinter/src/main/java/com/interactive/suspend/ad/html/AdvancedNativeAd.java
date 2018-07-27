package com.interactive.suspend.ad.html;

import android.content.Context;
import android.util.Log;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.constant.Platform;
import com.interactive.suspend.ad.model.AppConfig;
import com.interactive.suspend.ad.model.FetchAppConfigResult;

import java.util.List;

/**
 * Created by hongwu on 8/1/17.
 */

public class AdvancedNativeAd implements IAdvancedNativeAd {

    private static final String TAG = "AdvancedNativeAd: ";
    private String mUnitId;
    private Context mContext;
    private INativeAdLoadListener mINativeAdLoadListener;
    private FetchAppConfigResult.NativeUnit mNativeUnit;
    private FuseNativeAdLoader mFuseNativeAdLoader;

    public AdvancedNativeAd(Context context, String unitId) {
        mContext = context.getApplicationContext();
        mFuseNativeAdLoader = new FuseNativeAdLoader(mContext);
        mUnitId = unitId;
        init();
    }

    private void init() {
        Log.d(TAG, "init");
        mNativeUnit = AppConfig.getInstance(mContext.getApplicationContext()).getNativeAdUnit(mUnitId);
        long adValidTime = AppConfig.getInstance(mContext).getAdValidTime();
        if (mNativeUnit != null && mNativeUnit.adNetworks != null && mNativeUnit.adNetworks.size() != 0) {
            for (FetchAppConfigResult.AdNetwork adNetwork : mNativeUnit.adNetworks) {
                Log.e("platform: " + adNetwork.platform ,", key: " + adNetwork.key);
                if (adNetwork.platform.equals(Platform.INTER_AD)) {
                    mFuseNativeAdLoader.addNativeAdSource(adNetwork.platform, adNetwork.key, adValidTime, mNativeUnit);
                } else {
                    mFuseNativeAdLoader.addNativeAdSource(adNetwork.platform, adNetwork.key, adValidTime);
                }
            }
        } else {
            Log.e("","No native config!");
        }
    }

    @Override
    public void load(int adNum) {
        if (adNum < 1 || adNum > Constants.MAX_ADVANCED_NATIVE_ADS) {
            throw new IllegalArgumentException("Wrong ad num: " + adNum + ", should be in [1, " + Constants.MAX_ADVANCED_NATIVE_ADS + "]");
        }
        mFuseNativeAdLoader.setAdListener(new INativeAdLoadListener() {

            @Override
            public void onAdListLoaded(List<INativeAd> ads) {
                if (mINativeAdLoadListener != null) {
                    mINativeAdLoadListener.onAdListLoaded(ads);
                }
            }

            @Override
            public void onError(String error) {
                if (mINativeAdLoadListener != null) {
                    mINativeAdLoadListener.onError(error);
                }
            }

            @Override
            public void onAdClicked(INativeAd ad) {
                if (mINativeAdLoadListener != null) {
                    mINativeAdLoadListener.onAdClicked(ad);
                }
            }

            @Override
            public void onShowed() {
                if (mINativeAdLoadListener != null) {
                    mINativeAdLoadListener.onShowed();
                }
            }
        });
        mFuseNativeAdLoader.load(adNum);
    }

    public void setAdListener(INativeAdLoadListener listener) {
        mINativeAdLoadListener = listener;
    }

    @Override
    public void destroy() {
        if (mFuseNativeAdLoader != null) {
            mFuseNativeAdLoader.destroy();
        }
        mINativeAdLoadListener = null;
    }
}
