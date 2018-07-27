package com.interactive.suspend.ad.html.load;

import android.content.Context;
import android.os.AsyncTask;

import com.interactive.suspend.ad.db.AvDatabaseUtils;
import com.interactive.suspend.ad.db.SubscribeAdInfo;

import java.util.List;

/**
 * Created by hongwu on 4/6/17.
 */

public class FetchCacheSubscribeAdDataTask extends AsyncTask<Void, Void, List<SubscribeAdInfo>> {
    private Context mContext;
    private int mLimit;
    private AdCacheTaskListener mListener;

    public FetchCacheSubscribeAdDataTask(Context context, int limit, AdCacheTaskListener listener) {
        mContext = context.getApplicationContext();
        mLimit = limit;
        mListener = listener;
    }

    @Override
    protected List<SubscribeAdInfo> doInBackground(Void... params) {
        return AvDatabaseUtils.getCacheSubscribeData(mContext, mLimit);
    }

    @Override
    protected void onPostExecute(final List<SubscribeAdInfo> adInfos) {
        mListener.onLoadAdCacheSuccess(adInfos);
        mListener = null;
    }
}
