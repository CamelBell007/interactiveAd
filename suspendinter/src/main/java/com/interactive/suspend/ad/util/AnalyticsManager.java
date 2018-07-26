package com.interactive.suspend.ad.util;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.interactive.suspend.ad.http.NetworkUtils;


/**
 * Created by csc on 15/12/7.
 */
public class AnalyticsManager {

    private Context mContext;
    private static AnalyticsManager mInstance;
    private reportImpressionTask mTask;

    private AnalyticsManager(Context context) {
        mContext = context;
    }

    public static AnalyticsManager getInstance(Context context) {
        if (mInstance == null && context != null) {
            mInstance = new AnalyticsManager(context.getApplicationContext());
        }
        return mInstance;
    }

    public synchronized void doUpload(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        mTask = new reportImpressionTask(url);
        mTask.execute();
    }

    private class reportImpressionTask extends AsyncTask<Void, Void, Boolean> {
        private String mUrl;

        public reportImpressionTask(String url) {
            this.mUrl = url;
        }

        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return NetworkUtils.reportAdImpression(mContext, mUrl);
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }

}
