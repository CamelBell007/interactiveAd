package com.interactive.suspend.ad.html.load;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.db.AdInfo;
import com.interactive.suspend.ad.db.AvDatabaseUtils;

import java.util.List;


/**
 * Created by hongwu on 4/6/17.
 */

public class FetchCacheAdDataTask extends AsyncTask<Void, Void, List<AdInfo>> {
    private Context mContext;
    private int mLimit;
    private String mAdType;
    private int mSubType;
    private AdCacheTaskListener mListener;

    public FetchCacheAdDataTask(Context context, int limit, String adType, int subType, AdCacheTaskListener listener) {
        mContext = context.getApplicationContext();
        mLimit = limit;
        mAdType = adType;
        mSubType = subType;
        mListener = listener;
    }

    @Override
    protected List<AdInfo> doInBackground(Void... params) {
        Log.d("FetchCacheAdDataTask: " ,mAdType +", subtype: " + mSubType);
        if (mAdType.equals(Constants.ApxAdType.APPWALL)) {
            return AvDatabaseUtils.getCacheAppWallData(mContext, mLimit, mSubType);
        } else if (mAdType.equals(Constants.ApxAdType.NATIVE)) {
            return AvDatabaseUtils.getCacheNativeAdData(mContext, mLimit, mSubType);
        } else if (mAdType.equals(Constants.ApxAdType.REWARD)) {
            return AvDatabaseUtils.getCacheRewardAdData(mContext, mLimit);
        }
        return null;
    }

    @Override
    protected void onPostExecute(final List<AdInfo> adInfos) {
        if (mListener != null) {
            mListener.onLoadAdCacheSuccess(adInfos);
            mListener = null;
        }
    }
}
