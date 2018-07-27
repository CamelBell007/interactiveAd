package com.interactive.suspend.ad.html.load;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.db.AdInfo;
import com.interactive.suspend.ad.db.AvDatabaseUtils;
import com.interactive.suspend.ad.html.FetchAdResult;
import com.interactive.suspend.ad.http.NetworkUtils;
import com.interactive.suspend.ad.util.LogUtil;
import com.interactive.suspend.ad.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



/**
 * Created by csc on 15/5/20.
 */
public class AdListLoadTask extends AsyncTask<Void, Void, FetchAdResult> {

    private static final String TAG = AdListLoadTask.class.getSimpleName();
    private Context mContext;
    private String sourceId;
    private String excludePackages;
    private int limitNumber;
    private int pageNumber;
    private int creatives;
    private String market;
    private String adpkg;
    private long adpkgSize;
    private AdListLoadTaskListener mListener;
    // clean cache when refresh, keep cache when loading more
    private boolean cacheClean = true;
    private String adType;
    private int subType;
    private int requestType;
    private boolean videoAllow;


    public AdListLoadTask(Context mContext, String sourceId, String excludePackages,
                          int limitNumber, int pageNumber, int creatives, String market,
                          String adpkg, long adpkgSize, AdListLoadTaskListener mListener, boolean cacheClean,
                          String adType, int subType, boolean videoAllow, int requestType) {
        this.mContext = mContext.getApplicationContext();
        this.sourceId = sourceId;
        this.excludePackages = excludePackages;
        this.limitNumber = limitNumber;
        this.pageNumber = pageNumber;
        this.creatives = creatives;
        this.mListener = mListener;
        this.market = market;
        this.cacheClean = cacheClean;
        this.adpkg = adpkg;
        this.adpkgSize = adpkgSize;
        this.adType = adType;
        this.subType = subType;
        this.requestType = requestType;
        this.videoAllow = videoAllow;
    }

    protected void onPreExecute() {
        Log.d(LogUtil.TAG,"onPreExecute");
        if (mListener != null) {
            mListener.onLoadAdListStart();
        }
    }

    @Override
    protected FetchAdResult doInBackground(Void... params) {
        if (TextUtils.isEmpty(sourceId)) {
            return null;
        }
        FetchAdResult ads = NetworkUtils.fetchAd(mContext, sourceId, excludePackages, limitNumber,
                pageNumber, creatives, market, adType, subType, videoAllow, adpkg, adpkgSize, requestType);
        if (!FetchAdResult.isFailed(ads)) {
            List<AdInfo> adInfoList = new ArrayList<AdInfo>();
            for (FetchAdResult.Ad data : ads.ads.ad) {
                if (!checkAdInfoError(data)) {
                    AdInfo info = new AdInfo(data, adType);
                    adInfoList.add(info);
//                    L.e("VC-->DB-->updateAdToDatabase", "AdListLoadTask");
                    AvDatabaseUtils.updateAdToDatabase(mContext, info);
                }
            }
            if (adpkgSize > 0) {
                //下载中的app大小
                savePkgsForSize(adpkgSize, adInfoList);
            }
            //是否删除数据
            if (cacheClean) {
                removeDeprecatedAds(mContext, adInfoList);
            }
            return ads;
        }
        return null;
    }

    @Override
    protected void onPostExecute(final FetchAdResult result) {

        SharedPreferences sp = mContext.getSharedPreferences(Constants.Preference.PREF_NAME, Context.MODE_PRIVATE);
        if (FetchAdResult.isFailed(result) && mListener != null) {
            Log.d(TAG, "load ad failed");
            Error mError = new Error("fetch raw data error");
            mListener.onLoadAdListFail(mError);
            mListener = null;
            return;
        }

        if (adType.equals(Constants.ApxAdType.APPWALL)) {
            try {
                sp.edit().putLong(Constants.Preference.LAST_GET_APPWALL_TASK_SUCCESS_TIME, System.currentTimeMillis()).apply();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else if (adType.equals(Constants.ApxAdType.NATIVE)) {
            try {
                sp.edit().putLong(Constants.Preference.LAST_GET_NATIVE_TASK_SUCCESS_TIME, System.currentTimeMillis()).apply();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else if (adType.equals(Constants.ApxAdType.REWARD)) {
            try {
                sp.edit().putLong(Constants.Preference.LAST_GET_REWARD_TASK_SUCCESS_TIME, System.currentTimeMillis()).apply();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else if (adType.equals(Constants.ApxAdType.PLAYABLE)) {
            try {
                sp.edit().putLong(Constants.Preference.LAST_GET_PLAYABLE_TASK_SUCCESS_TIME, System.currentTimeMillis()).apply();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else if (adType.equals(Constants.ApxAdType.SMART)) {
            try {
                sp.edit().putLong(Constants.Preference.LAST_GET_SMART_TASK_SUCCESS_TIME, System.currentTimeMillis()).apply();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (mListener != null) {
            mListener.onLoadAdListSuccess(result.ads.ad);
        }
        mListener = null;
    }

    private boolean checkAdInfoError(FetchAdResult.Ad ad) {
        boolean isError = false;

        if (TextUtils.isEmpty(ad.campaignID) ||
                TextUtils.isEmpty(ad.packageName) || TextUtils.isEmpty(ad.clickURL)
                || TextUtils.isEmpty(ad.icon)) {
            isError = true;
        }
        return isError;
    }

    private void removeDeprecatedAds(Context context, List<AdInfo> newDatas) {
        if (newDatas == null || newDatas.size() == 0) {
            return;
        }
        List<AdInfo> cached = null;
        if (newDatas.get(0).type.equals(Constants.ApxAdType.APPWALL)) {
            cached = AvDatabaseUtils.getCacheAppWallData(context, -1, Constants.ActivityAd.SORT_ALL);
        } else if (newDatas.get(0).type.equals(Constants.ApxAdType.NATIVE)) {
            cached = AvDatabaseUtils.getCacheNativeAdData(context, -1, Constants.NativeAdStyle.SMALL);
        } else if (newDatas.get(0).type.equals(Constants.ApxAdType.REWARD)) {
            cached = AvDatabaseUtils.getCacheRewardAdData(context, -1);
        } else if (newDatas.get(0).type.equals(Constants.ApxAdType.PLAYABLE)) {
            cached = AvDatabaseUtils.getCachePlayableAdData(context, -1);
        } else if (newDatas.get(0).type.equals(Constants.ApxAdType.SMART)) {
            cached = AvDatabaseUtils.getCacheSmartAdData(context, -1);
        }
        if (cached == null || cached.size() == 0) {
            return;
        }
        HashSet<String> newIds = new HashSet<>();
        for (int i = 0; i < newDatas.size(); i++) {
            newIds.add(newDatas.get(i).campaignid);
        }
        for (int i = 0; i < cached.size(); i++) {
            AdInfo info = cached.get(i);
            if (!newIds.contains(info.campaignid)) {
//                L.d("VC--DB-->AdListLoadTask", AdColumns.CAMPAIGN_ID + ":    " + info.campaignid);
                AvDatabaseUtils.removeData(context, info);
            }
        }
    }

    private void savePkgsForSize(long pkgSize, List<AdInfo> data) {
        if (pkgSize > 0 && data != null && data.size() > 0) {
            HashSet<String> matchPkgs = new HashSet<>();
            for (AdInfo info : data) {
                if (!matchPkgs.contains(info.pkgname)) {
                    matchPkgs.add(info.pkgname);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (String str : matchPkgs) {
                sb.append(str).append(";");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                Log.e(TAG, "pkgs: " + sb.toString() + ", size: " + pkgSize);
                if (!TextUtils.isEmpty(sb.toString())) {
                    PreferenceUtils.setPkgsWithSameSize(mContext, sb.toString());
                }
            }
        }
    }
}
