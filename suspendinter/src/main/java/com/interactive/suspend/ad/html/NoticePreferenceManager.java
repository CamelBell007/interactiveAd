package com.interactive.suspend.ad.html;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.model.FetchNoticeInfo;
import com.interactive.suspend.ad.util.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by hongwu on 12/8/16.
 */

public class NoticePreferenceManager {

    private static final String TAG = "NoticePreferenceManager: ";
    private static NoticePreferenceManager sInstance;
    private PreferencesManager mNoticePrefMgr;

    public synchronized static NoticePreferenceManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NoticePreferenceManager(context.getApplicationContext());
        }
        return sInstance;
    }

    private NoticePreferenceManager(Context context) {
        if (context == null) {
            return;
        }
        try {
            //Todo: verifyError?
            mNoticePrefMgr = new PreferencesManager(context).setName(Constants.Preference.NOTICE_INFO_PREF_NAME).init();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public synchronized void add(long id, String noticeUrl, long lastReportTime, String campaignId, String position) {
        Log.e(LogUtil.TAG,  "add notice url: id: " + id + ", time: " + lastReportTime +", url: "+ noticeUrl);
        if (id <= 0 || TextUtils.isEmpty(noticeUrl) || lastReportTime <= 0) {
            return;
        }
        List<FetchNoticeInfo.NoticeInfo> infos = getAll();
        if (infos != null) {
            FetchNoticeInfo.NoticeInfo noticeInfo = get(id);
            if (noticeInfo != null) {
                // already exist, update notice info
                Log.e(LogUtil.TAG, "already exist");
                int index = infos.indexOf(noticeInfo);
                noticeInfo.retryCount++;
                noticeInfo.lastReportTime = lastReportTime;
                infos.set(index, noticeInfo);
            } else {
                Log.e(LogUtil.TAG, "new one");
                // not exist, new one
                noticeInfo = new FetchNoticeInfo.NoticeInfo(id, noticeUrl, 1, lastReportTime, campaignId, position);
                infos.add(noticeInfo);
            }
        } else {
            FetchNoticeInfo.NoticeInfo noticeInfo = new FetchNoticeInfo.NoticeInfo(id, noticeUrl, 1, lastReportTime, campaignId, position);
            infos = new ArrayList<>();
            infos.add(noticeInfo);
        }
        if (mNoticePrefMgr != null) {
            FetchNoticeInfo fetchInfo= new FetchNoticeInfo();
            fetchInfo.noticeInfoList = infos;
            mNoticePrefMgr.putObject(Constants.Preference.KEY_NOTICE_INFOS, fetchInfo);
        }
    }


    public synchronized void remove(long id) {
        if (id <= 0 || !contains(id)) {
            return;
        }
        List<FetchNoticeInfo.NoticeInfo> infos = getAll();
        if (infos != null) {
            Iterator<FetchNoticeInfo.NoticeInfo> iterator = infos.iterator();
            while (iterator.hasNext()) {
                FetchNoticeInfo.NoticeInfo info = iterator.next();
                if (info.id == id) {
                    iterator.remove();
                }
            }
            if (mNoticePrefMgr != null) {
                FetchNoticeInfo fetchInfo = new FetchNoticeInfo();
                fetchInfo.noticeInfoList = infos;
                mNoticePrefMgr.putObject(Constants.Preference.KEY_NOTICE_INFOS, fetchInfo);
            }
        }
    }

    public void remove(FetchNoticeInfo.NoticeInfo notice) {
        if (notice == null) {
            return;
        }
        remove(notice.id);
    }

    public List<FetchNoticeInfo.NoticeInfo> getAll() {
        if (mNoticePrefMgr != null) {
            FetchNoticeInfo fetchInfo = mNoticePrefMgr.getObject(Constants.Preference.KEY_NOTICE_INFOS, FetchNoticeInfo.class);
            if (fetchInfo != null) {
                return fetchInfo.noticeInfoList;
            }
        }
        return null;
    }

    public FetchNoticeInfo.NoticeInfo get(long noticeId) {
        if (noticeId <= 0) {
            return null;
        }
        List<FetchNoticeInfo.NoticeInfo> infos = getAll();
        if (infos != null) {
            for (FetchNoticeInfo.NoticeInfo info : infos) {
                if (info.id == noticeId) {
                    return info;
                }
            }
        }
        return null;
    }

    public boolean contains(long noticeId) {
        if (noticeId <= 0) {
            return false;
        }
        List<FetchNoticeInfo.NoticeInfo> infos = getAll();
        if (infos != null) {
            for (FetchNoticeInfo.NoticeInfo info : infos) {
                if (info.id == noticeId) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(FetchNoticeInfo.NoticeInfo notice) {
        if (notice == null) {
            return false;
        }
        return contains(notice.id);
    }

    public synchronized void clear() {
        if (mNoticePrefMgr != null) {
            mNoticePrefMgr.putObject(Constants.Preference.KEY_NOTICE_INFOS, null);
        }
    }

//    private void dump() {
//        List<FetchNoticeInfo.NoticeInfo> list = getAll();
//        if (list != null) {
//            L.e(TAG, "===================[Pending notice urls]===================");
//            for (FetchNoticeInfo.NoticeInfo info : list) {
//                L.e(TAG, " notice url: " + info.url +
//                        ",\n lastReportTime: " + info.lastReportTime +
//                        ",\n retryCount: " + info.retryCount +
//                        ",\n id: " + info.id);
//            }
//            L.e(TAG, "==========================[End]===========================");
//        }
//    }

}
