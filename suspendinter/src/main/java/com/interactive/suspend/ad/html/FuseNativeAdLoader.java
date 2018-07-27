package com.interactive.suspend.ad.html;

import android.content.Context;
import android.util.Log;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.model.FetchAppConfigResult;
import com.interactive.suspend.ad.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by guojia on 2016/11/15.
 */

public class FuseNativeAdLoader implements IAdvancedNativeAd {
    private Context mContext;
    private List<NativeAdConfig> mNativeAdConfigList = new ArrayList();
    private HashMap<String, List<INativeAd>> mNativeAdCache = new HashMap<>();
    private INativeAdLoadListener mListener;
    private int currentLoadingIdx = -1;
    private IAdvancedNativeAd mLoader;

    private class NativeAdConfig {
        public String key;
        public String source;
        public long cacheTime;
        public FetchAppConfigResult.NativeUnit nativeUnit;

        public NativeAdConfig(String source, String key, long cacheTime, FetchAppConfigResult.NativeUnit nativeUnit) {
            this.key = key;
            this.source = source;
            this.cacheTime = cacheTime;
            this.nativeUnit = nativeUnit;
        }
    }

    public FuseNativeAdLoader(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * Add native ad sources
     *
     * @param source    source of the native ad: ab, ab_install, ab_content, fb, apx,vk ... see Constants.NativeAdSource
     * @param key       the key of the ad source,
     * @param cacheTime cache time of the ad source, or you can set it -1 to use the default one.
     */
    public void addNativeAdSource(String source, String key, long cacheTime) {
        addNativeAdSource(source, key, cacheTime, null);
    }

    public void addNativeAdSource(String source, String key, long cacheTime, FetchAppConfigResult.NativeUnit nativeUnit) {
        mNativeAdConfigList.add(new NativeAdConfig(source, key, cacheTime, nativeUnit));
    }

    public void setAdListener(INativeAdLoadListener listener) {
        this.mListener = listener;
    }

    @Override
    public void load(int num) {
        if (num < 1 || num > Constants.MAX_ADVANCED_NATIVE_ADS) {
            throw new IllegalArgumentException("Wrong ad num: " + num + ", should be in [1, " + Constants.MAX_ADVANCED_NATIVE_ADS + "]");
        }
        if (mListener == null) {
            return;
        }
        if (mNativeAdConfigList.size() == 0) {
            mListener.onError("No ad source detected!");
            return;
        }
        currentLoadingIdx = 0;
        loadNextNativeAd(num);
    }

    private void loadNextNativeAd(final int num) {
        if (currentLoadingIdx >= mNativeAdConfigList.size()) {
            Log.e(LogUtil.TAG, "Tried to load all source, no fill. Index : " + currentLoadingIdx);
            if (mListener != null) {
                mListener.onError("No Fill");
            }
            return;
        }
        NativeAdConfig config = mNativeAdConfigList.get(currentLoadingIdx);
        Log.e(LogUtil.TAG, "load platform: " + config.source);
        //Find cache
        List<INativeAd> ads = mNativeAdCache.get(config.key);
        if (ads != null && ads.size() != 0) { // already shown, load next ads
            if (ads.get(0).isShowed() || (System.currentTimeMillis() - ads.get(0).getLoadedTime()) > config.cacheTime) {
                Log.e(LogUtil.TAG, "Ad cache time out : type: " + ads.get(0).getAdType());
                mNativeAdCache.remove(config.key);
            } else { // not show, just show it
                if (mListener != null) {
                    mListener.onAdListLoaded(ads);
                }
                return;
            }
        }
        //Do load
        if (mLoader == null) { // new one only if config changed
            mLoader = getNativeAdAdapter(config);
        }
        if (mLoader == null) {
            mListener.onError("Wrong config");
            return;
        }
        mLoader.setAdListener(new INativeAdLoadListener() {

            @Override
            public void onAdListLoaded(List<INativeAd> ads) {
                if (currentLoadingIdx < mNativeAdConfigList.size()) {
                    mNativeAdCache.put(mNativeAdConfigList.get(currentLoadingIdx).key, ads);
                } else {
                    Log.e(LogUtil.TAG, "Ads loaded but not put into cache");
                }
                if (mListener != null) {
                    mListener.onAdListLoaded(ads);
                }
            }

            @Override
            public void onError(String error) {
                if (currentLoadingIdx >= mNativeAdConfigList.size()) {
                    Log.e(LogUtil.TAG, "Tried to load all source, no fill. Index : " + currentLoadingIdx);
                    if (mListener != null) {
                        mListener.onError("No Fill");
                    }
                    return;
                }
                Log.e(LogUtil.TAG, "Load current source " + mNativeAdConfigList.get(currentLoadingIdx).source + " error : " + error);
                currentLoadingIdx++;
                mLoader = null;
                loadNextNativeAd(num);
            }

            @Override
            public void onAdClicked(INativeAd ad) {
                if (mListener != null) {
                    mListener.onAdClicked(ad);
                }
            }

            @Override
            public void onShowed() {
                if (mListener != null) {
                    mListener.onShowed();
                }
            }
        });
        mLoader.load(num);
    }

    private IAdvancedNativeAd getNativeAdAdapter(NativeAdConfig config) {
        if (config == null || config.source == null) {
            return null;
        }
        return new ApxNativeAdapter(mContext, config.key, config.nativeUnit);
    }



    @Override
    public void destroy() {
        if (mLoader != null) {
            mLoader.destroy();
        }
        mListener = null;
    }
}
