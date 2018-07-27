package com.interactive.suspend.ad.html.load;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.constant.AsyncStatus;
import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.db.SubscribeAdInfo;
import com.interactive.suspend.ad.html.FetchSubscribeAdResult;
import com.interactive.suspend.ad.html.IAdLoadListener;
import com.interactive.suspend.ad.html.IApxNativeAdListener;
import com.interactive.suspend.ad.html.SubscribeLoaderInterface;
import com.interactive.suspend.ad.model.AppConfig;
import com.interactive.suspend.ad.util.DeviceUtil;
import com.interactive.suspend.ad.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hongwu on 5/19/17.
 */

public class SubscribeLoader implements AdListLoadTaskListener<FetchSubscribeAdResult.Ad>,
        AdCacheTaskListener<SubscribeAdInfo>, SubscribeLoaderInterface {

    private Context mContext;
    private SubscribeAdLoadTask mLoaderTask;
    private IAdLoadListener mListener;
    private IApxNativeAdListener mApxNativeAdListener;
    private static final String TAG = "SubscribeLoader";
    private List<SubscribeAdInfo> mCacheAds = new ArrayList<>();
    private String mSourceId;

    public SubscribeLoader(Context context) {
        mContext = context.getApplicationContext();
        mSourceId = PreferenceUtils.getNativeSourceId(mContext);
    }

    public SubscribeLoader(Context context,String sourceId) {
        mContext = context.getApplicationContext();
        mSourceId = sourceId;
    }

    public void load(IAdLoadListener listener, boolean forceReload, boolean cacheClean) {
        mListener = listener;
        startLoader(forceReload, cacheClean, -1);
    }

    public void load(IApxNativeAdListener listener, boolean forceReload, boolean cacheClean, int num) {
        mApxNativeAdListener = listener;
        startLoader(forceReload, cacheClean, num);
    }

    private void startLoader(boolean forceReload, boolean cacheClean, int num) {
        Log.d(TAG, "startLoader");
        if (TextUtils.isEmpty(mSourceId)) {
            return;
        }
        int limitNum;
        if (num <= 0) {
            limitNum = AppConfig.getInstance(mContext).getAdCountLimit();
        } else {
            limitNum = num;
        }
        if (forceReload) {
            if (mLoaderTask != null && mLoaderTask.getStatus().equals(AsyncStatus.RUNNING)) {
                mLoaderTask.cancel(true);
                Log.d(TAG, "Loading and force reload.");
            }
            mLoaderTask = new SubscribeAdLoadTask(mContext, mSourceId, limitNum, cacheClean, this);
            mLoaderTask.execute();
        } else {
            if (mLoaderTask != null && mLoaderTask.getStatus().equals(AsyncStatus.RUNNING)) {
                Log.d(TAG, "Already loading, do nothing!");
                return;
            }
            SharedPreferences sp = mContext.getSharedPreferences(Constants.Preference.PREF_NAME, mContext.MODE_PRIVATE);
            long now = System.currentTimeMillis();
            long lastSuccessTime = sp.getLong(Constants.Preference.LAST_GET_SUBSCRIBE_TASK_SUCCESS_TIME, -1L);
            if (now - lastSuccessTime > AppConfig.getInstance(mContext).getAdValidTime() || mCacheAds == null || mCacheAds.size() == 0) {
                if (mLoaderTask != null) {
                    mLoaderTask.cancel(true);
                }
                mLoaderTask = new SubscribeAdLoadTask(mContext, mSourceId, limitNum, cacheClean, this);
                mLoaderTask.execute();
            } else {
                Log.d(TAG, "Data already loaded");
                if (mListener != null) {
                    mListener.onLoadAdSuccess();
                }

                // callback for advanced native ad
                if (mApxNativeAdListener != null) {
                    if (mCacheAds != null && mCacheAds.size() > 0) {
                        mApxNativeAdListener.onAdLoaded(mCacheAds);
                    } else {
                        mApxNativeAdListener.onError("no more data.");
                    }
                }
            }
        }
    }

    private void preLoadImg(List<SubscribeAdInfo> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        if (DeviceUtil.getNetworkType(mContext) == ConnectivityManager.TYPE_WIFI) {
            for (SubscribeAdInfo adInfo : dataList) {
                //FIXME 请求图片可以用项目中的imageLoader
               // ImageLoader.getInstance().doPreLoad(mContext, adInfo.imageUrl);
            }
        }
    }

    public List<SubscribeAdInfo> getApxAdList() {
        List<SubscribeAdInfo> list = new ArrayList<>();
        if (mCacheAds != null && mCacheAds.size() != 0) {
            list.addAll(mCacheAds);
        }
        return list;
    }

    @Override
    public void onLoadAdCacheSuccess(List<SubscribeAdInfo> data) {
        Log.d(TAG, "onLoadAdCacheSuccess");
        if (data != null && data.size() != 0) {
            mCacheAds = data;
        }
        if (mListener != null) {
            mListener.onLoadAdSuccess(); // call before preclk, decrease the callback time
        }

        // callback for advanced native ad
        if (mApxNativeAdListener != null) {
            if (mCacheAds != null && mCacheAds.size() > 0) {
                mApxNativeAdListener.onAdLoaded(mCacheAds);
            } else {
                mApxNativeAdListener.onError("no more data.");
            }
        }

        if (data != null && data.size() != 0) {
            if (mListener != null) {//高级原声不需要预加载图片, 渠道自己加载
                preLoadImg(data);
            }
        }
        mApxNativeAdListener = null;
        mListener = null;
    }

    @Override
    public void onLoadAdListSuccess(List<FetchSubscribeAdResult.Ad> newData) {
        Log.d(TAG, "onLoadAdListSuccess");
        // 若有新的数据，则对数据做预点击和预加载图片
        if (newData != null && newData.size() != 0) {
            // update cache ad data
            new FetchCacheSubscribeAdDataTask(mContext, -1, this).execute();
        } else { // 如果没有新数据，且当前没有缓存，则认为加载失败
            if (mCacheAds == null || mCacheAds.size() == 0) {
                if (mListener != null) {
                    mListener.onLoadAdFail(new Error("No more data."));
                }
            } else {
                if (mListener != null) {
                    mListener.onLoadAdSuccess();
                }
            }

            // callback for advanced native ad
            if (mApxNativeAdListener != null) {
                if (mCacheAds != null && mCacheAds.size() > 0) {
                    mApxNativeAdListener.onAdLoaded(mCacheAds);
                } else {
                    mApxNativeAdListener.onError("no more data.");
                }
            }
        }
    }

    @Override
    public void onLoadAdListStart() {
        if (mListener != null) {
            mListener.onLoadAdStart();
        }
    }

    @Override
    public void onLoadAdListFail(Error error) {
        if (mListener != null) {
            mListener.onLoadAdFail(error);
        }

        // callback for advanced native ad
        if (mApxNativeAdListener != null) {
            mApxNativeAdListener.onError(error.getMessage());
        }
    }
}
