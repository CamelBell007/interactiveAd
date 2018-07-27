package com.interactive.suspend.ad.html.load;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.db.AvDatabaseUtils;
import com.interactive.suspend.ad.db.SubscribeAdInfo;
import com.interactive.suspend.ad.html.FetchSubscribeAdResult;
import com.interactive.suspend.ad.http.NetworkUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



/**
 * Created by csc on 15/5/20.
 */
public class SubscribeAdLoadTask extends AsyncTask<Void, Void, FetchSubscribeAdResult> {

    private static final String TAG = SubscribeAdLoadTask.class.getSimpleName();
    private Context mContext;
    private String sourceId;
    private int limitNumber;
    private AdListLoadTaskListener mListener;
    // clean cache when refresh, keep cache when loading more
    private boolean cacheClean = true;


    public SubscribeAdLoadTask(Context mContext, String sourceId, int limitNumber, boolean cacheClean, AdListLoadTaskListener listener) {
        this.mContext = mContext.getApplicationContext();
        this.sourceId = sourceId;
        this.limitNumber = limitNumber;
        this.cacheClean = cacheClean;
        this.mListener = listener;
    }

    protected void onPreExecute() {
        if (mListener != null) {
            mListener.onLoadAdListStart();
        }
    }

    @Override
    protected FetchSubscribeAdResult doInBackground(Void... params) {
        if (TextUtils.isEmpty(sourceId)) {
            return null;
        }
        FetchSubscribeAdResult ads = NetworkUtils.fetchSubscribeAd(mContext, sourceId, limitNumber);
        if (!FetchSubscribeAdResult.isFailed(ads)) {
            List<SubscribeAdInfo> adInfoList = new ArrayList<>();
            for (FetchSubscribeAdResult.Ad data : ads.ads.ad) {
                if (!checkAdInfoError(data)) {
                    SubscribeAdInfo info = new SubscribeAdInfo(data);
                    adInfoList.add(info);
                    AvDatabaseUtils.updateSubscribeAdToDatabase(mContext, info);
                }
            }
            if (cacheClean) {
                removeDeprecatedAds(mContext, adInfoList);
            }
            return ads;
        }
        return null;
    }

    @Override
    protected void onPostExecute(final FetchSubscribeAdResult result) {
        SharedPreferences sp = mContext.getSharedPreferences(Constants.Preference.PREF_NAME,
                mContext.MODE_PRIVATE);
        if (FetchSubscribeAdResult.isFailed(result) && mListener != null) {
            Log.d(TAG, "load ad failed");
            Error mError = new Error("fetch raw data error");
            mListener.onLoadAdListFail(mError);
            mListener = null;
            return;
        }
        try {
            sp.edit().putLong(Constants.Preference.LAST_GET_SUBSCRIBE_TASK_SUCCESS_TIME, System.currentTimeMillis()).apply();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (mListener != null) {
            mListener.onLoadAdListSuccess(result.ads.ad);
        }
        mListener = null;
    }

    private boolean checkAdInfoError(FetchSubscribeAdResult.Ad ad) {
        boolean isError = false;
        if (TextUtils.isEmpty(ad.campaignID) || TextUtils.isEmpty(ad.clickURL) || TextUtils.isEmpty(ad.imageUrl)) {
            isError = true;
        }
        return isError;
    }

    private void removeDeprecatedAds(Context context, List<SubscribeAdInfo> newDatas) {
        if (newDatas == null || newDatas.size() == 0) {
            return;
        }
        List<SubscribeAdInfo> cached = null;
        cached = AvDatabaseUtils.getCacheSubscribeData(context, -1);
        if (cached == null || cached.size() == 0) {
            return;
        }
        HashSet<String> newIds = new HashSet<>();
        for (int i = 0; i < newDatas.size(); i++) {
            newIds.add(newDatas.get(i).campaignid);
        }
        for (int i = 0; i < cached.size(); i++) {
            SubscribeAdInfo info = cached.get(i);
            if (!newIds.contains(info.campaignid)) {
                AvDatabaseUtils.removeSubscribeData(context, info);
            }
        }
    }
}
