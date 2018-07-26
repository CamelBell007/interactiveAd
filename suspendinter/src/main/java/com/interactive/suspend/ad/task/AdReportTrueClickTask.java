package com.interactive.suspend.ad.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.interactive.suspend.ad.html.NoticePreferenceManager;
import com.interactive.suspend.ad.http.NetworkUtils;
import com.interactive.suspend.ad.util.LogUtil;


/**
 * Created by csc on 15/11/25.
 *
 */

//FIXME 通过noticeURL上报点击
public class AdReportTrueClickTask extends AsyncTask<Void, Void, Integer> {
    private Context mContext;
    private String mUrl;
    private int mRetryTimes;
    private boolean mIsRealClick;
    private String mCampaignId;
    private String mPosition;
    private long mNoticeId;
    private String mSourceId;

    public AdReportTrueClickTask(Context context, String url, int retryTimes, boolean isRealClick,
                                 String campaignId, String position, long noticeId, String souceid) {
        this.mContext = context.getApplicationContext();
        this.mUrl = url;
        this.mRetryTimes = retryTimes;
        this.mIsRealClick = isRealClick;
        this.mCampaignId = campaignId;
        this.mPosition = position;
        this.mNoticeId = noticeId;
        this.mSourceId = souceid;
    }

    protected void onPreExecute() {
        Log.d(LogUtil.TAG,"onPreExecute");
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Log.d(LogUtil.TAG,"doInBackground");
        return NetworkUtils.reportTrueClick(mContext, mUrl);
    }

    @Override
    protected void onPostExecute(Integer result) {
        Log.d(LogUtil.TAG,"onPostExecute: result: " + result);
//        if (mIsRealClick && AppConfig.getInstance(mContext).isNoticeAnalyticsAllow()) {
//            // only analytics for real click
//            AnalyticsMgr.getInstance().uploadReportNoticeResult(mContext, mUrl, result, mRetryTimes, mCampaignId, mPosition, mSourceId);
//        }
        if (result == 200) {
            // remove the notice info if it already exist in noticePreference.
            if (NoticePreferenceManager.getInstance(mContext).contains(mNoticeId)) {
                NoticePreferenceManager.getInstance(mContext).remove(mNoticeId);
            } else {
                Log.d(LogUtil.TAG,"Not exist in notice preference");
            }
        } else {
            Log.d(LogUtil.TAG,"Failed to report notice url, report next time.");
            if (mNoticeId >= 0) {
                NoticePreferenceManager.getInstance(mContext).add(mNoticeId, mUrl, System.currentTimeMillis(), mCampaignId, mPosition);
            } else {
                // use current time as notice id
                NoticePreferenceManager.getInstance(mContext).add(System.currentTimeMillis(), mUrl, System.currentTimeMillis(), mCampaignId, mPosition);
            }
        }
    }
}
