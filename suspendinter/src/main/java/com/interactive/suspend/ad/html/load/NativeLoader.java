package com.interactive.suspend.ad.html.load;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.constant.AsyncStatus;
import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.db.AdInfo;
import com.interactive.suspend.ad.html.FetchAdResult;
import com.interactive.suspend.ad.html.IAdLoadListener;
import com.interactive.suspend.ad.html.IApxNativeAdListener;
import com.interactive.suspend.ad.html.NativeLoaderInterface;
import com.interactive.suspend.ad.model.AppConfig;
import com.interactive.suspend.ad.util.DeviceUtil;
import com.interactive.suspend.ad.util.LogUtil;
import com.interactive.suspend.ad.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hongwu on 5/19/17.
 */

public class NativeLoader implements AdListLoadTaskListener<FetchAdResult.Ad>, AdCacheTaskListener<AdInfo>, NativeLoaderInterface {

    private Context mContext;
    private AdListLoadTask mLoaderTask;
    private IAdLoadListener mListener;
    private IApxNativeAdListener mApxNativeAdListener;
    private static final String TAG = "NativeLoader";
    private List<AdInfo> mCacheAds = new ArrayList<>();
    private String mAdType;
    private int mSubAdType;//NativeAdStyle.LARGE picture size model
    private boolean mVideoAllow = false;
    private String mSourceId;

    public NativeLoader(Context context, int subAdType, boolean videoAllow) {
        mContext = context.getApplicationContext();
        mSourceId = PreferenceUtils.getNativeSourceId(mContext);
        mAdType = Constants.ApxAdType.NATIVE;
        mSubAdType = subAdType;
        mVideoAllow = videoAllow;
    }
    public NativeLoader(Context context, String sourceId,int subAdType, boolean videoAllow) {
        mContext = context.getApplicationContext();
        mSourceId = sourceId;
        mAdType = Constants.ApxAdType.NATIVE;
        mSubAdType = subAdType;
        mVideoAllow = videoAllow;
    }

    public void loadConfig() {
    }

    // for display
    public void preload() {
        load(null, false, "", true, "", Constants.AdRequestType.DISPLAY);
    }

    public void load(IAdLoadListener listener, boolean forceReload, String excludeAds,
                     boolean cacheClean, String adpkg, int requestType) {
        mListener = listener;
        startLoader(forceReload, excludeAds, cacheClean, adpkg, 0, requestType, -1);
    }

    // load for adpkg
    public void load(IAdLoadListener listener, boolean forceReload, boolean cacheClean, String adpkg, int requestType) {
        load(listener, forceReload, "", cacheClean, adpkg, requestType);
    }

    // load for adpkgsize and alarm
    @Override
    public void load(IAdLoadListener listener, boolean forceReload, boolean cacheClean, long adpkgSize, int requestType) {
        mListener = listener;
        startLoader(forceReload, "", cacheClean, "", adpkgSize, requestType, -1);
    }

    // for display
    public void load(IApxNativeAdListener listener, boolean forceReload, String excludeAds, boolean cacheClean, int num) {
        mApxNativeAdListener = listener;
        startLoader(forceReload, excludeAds, cacheClean, "", 0, Constants.AdRequestType.DISPLAY, num);
    }

    private void startLoader(boolean forceReload, String excludeAds, boolean cacheClean, String adpkg,
                             long adpkgSize, int requestType, int num) {
//        L.d("VC--"+TAG, "startLoader");
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
//                L.d("VC--"+TAG, "Loading and force reload.");
            }
            mLoaderTask = new AdListLoadTask(mContext, mSourceId, excludeAds, limitNum,
                    1, 1, "google", adpkg, adpkgSize, this,
                    cacheClean, mAdType, mSubAdType, mVideoAllow, requestType);
            mLoaderTask.execute();
        } else {
            if (mLoaderTask != null && mLoaderTask.getStatus().equals(AsyncStatus.RUNNING)) {
//                L.d("VC--"+TAG, "Already loading, do nothing!");
                return;
            }
            SharedPreferences sp = mContext.getSharedPreferences(Constants.Preference.PREF_NAME, mContext.MODE_PRIVATE);
            long now = System.currentTimeMillis();
            long lastSuccessTime = sp.getLong(Constants.Preference.LAST_GET_NATIVE_TASK_SUCCESS_TIME, -1L);
            List<AdInfo> dataList = getApxAdList();
            if (now - lastSuccessTime > AppConfig.getInstance(mContext).getAdValidTime() || dataList == null || dataList.size() == 0) {
                if (mLoaderTask != null) {
                    mLoaderTask.cancel(true);
                }
                mLoaderTask = new AdListLoadTask(mContext, mSourceId, excludeAds,
                        limitNum, 1, 1,
                        "google", adpkg, adpkgSize, this, cacheClean, mAdType, mSubAdType,
                        mVideoAllow, requestType);
                mLoaderTask.execute();
            } else {
//                L.d("VC--"+TAG, "Data already loaded");
                if (mListener != null) {
                    mListener.onLoadAdSuccess();
                }
                // callback for advanced native ad
                if (mApxNativeAdListener != null) {
                    List<AdInfo> list = getApxAdList();
                    if (list != null && list.size() > 0) {
                        mApxNativeAdListener.onAdLoaded(list);
                    } else {
                        mApxNativeAdListener.onError("no more data.");
                    }
                }
            }
        }
    }

    public List<AdInfo> getApxAdList() {
        List<AdInfo> list = new ArrayList<>();
        int network = DeviceUtil.getNetworkType(mContext);
        if (mCacheAds != null && mCacheAds.size() != 0) {
            if (mSubAdType == Constants.NativeAdStyle.LARGE) {
                for (AdInfo info : mCacheAds) {
                    if (!TextUtils.isEmpty(info.imageUrl)
                            || (mVideoAllow && !TextUtils.isEmpty(info.videoUrl) && (System.currentTimeMillis() / 1000 < info.videoExpire) && network == ConnectivityManager.TYPE_WIFI)) {
                        list.add(info);
                    }
                }
            } else {
                list.addAll(mCacheAds);
            }
        } else {
            Log.e(LogUtil.TAG,"cache is empty!");
        }

        // DO NOT show these ads which isDisplay = 0
        Iterator<AdInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            AdInfo info = iterator.next();
            if (info.isDisplay == 0) {
                iterator.remove();
            }
        }

        return list;
    }

    @Override
    public void onLoadAdCacheSuccess(List<AdInfo> data) {
//        L.d("VC--"+TAG, "onLoadAdCacheSuccess");

        if (data != null && data.size() != 0) {
            mCacheAds = data;
        }
        if (mListener != null) {
            mListener.onLoadAdSuccess(); // call before preclk, decrease the callback time
        }

        // callback for advanced native ad
        if (mApxNativeAdListener != null) {
            List<AdInfo> list = getApxAdList();
            if (list != null && list.size() > 0) {
                mApxNativeAdListener.onAdLoaded(list);
            } else {
                mApxNativeAdListener.onError("no more data.");
            }
        }
//FIXME 不需要预点击操作
//        if (data != null && data.size() != 0) {
//            if (ApplicationUtil.getCoreSourceIdType(mContext).equals(Constants.ApxAdType.NATIVE)) {
//                Utils.doPreClick(mContext, mSourceId, data);
//            }
//            if (mListener != null) {//高级原声不需要预加载图片, 渠道自己加载
//                Utils.preLoadImg(mContext, data, Constants.ImageType.ALL);
//            }
//        }
        mApxNativeAdListener = null;
        mListener = null;
    }

    @Override
    public void onLoadAdListSuccess(List<FetchAdResult.Ad> newData) {
//        L.d("VC--"+TAG, "onLoadAdListSuccess");
        // 若有新的数据，则对数据做预点击和预加载图片
        if (newData != null && newData.size() != 0) {
            // update cache ad data
            new FetchCacheAdDataTask(mContext, -1, mAdType, mSubAdType, this).execute();
        } else { // 如果没有新数据，且当前没有缓存，则认为加载失败
            if (mCacheAds == null || mCacheAds.size() == 0) {
                if (mListener != null) {
                    mListener.onLoadAdFail(new Error("No more data."));
                    mListener = null;
                }
            } else {
                if (mListener != null) {
                    mListener.onLoadAdSuccess();
                    mListener = null;
                }
            }

            // callback for advanced native ad
            if (mApxNativeAdListener != null) {
                List<AdInfo> list = getApxAdList();
                if (list != null && list.size() > 0) {
                    mApxNativeAdListener.onAdLoaded(list);
                } else {
                    mApxNativeAdListener.onError("no more data.");
                }
                mApxNativeAdListener = null;
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
            mListener = null;
        }

        // callback for advanced native ad
        if (mApxNativeAdListener != null) {
            mApxNativeAdListener.onError(error.getMessage());
            mApxNativeAdListener = null;
        }
    }
}
