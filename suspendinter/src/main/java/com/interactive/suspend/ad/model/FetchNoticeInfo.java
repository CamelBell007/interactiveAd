package com.interactive.suspend.ad.model;

import java.util.List;

/**
 * Created by hongwu on 12/7/16.
 * 广告点击的上报（预点击，模拟点击，用户的真实点击所有的类型），以及重试次数
 */

public class FetchNoticeInfo {

    public List<NoticeInfo> noticeInfoList;

    public static class NoticeInfo {
        public long id;
        public String campaignId;
        public String position;
        public String url;
        public int retryCount;
        public long lastReportTime;

        public NoticeInfo(long id, String url, int retryCount, long lastReportTime, String campaignid, String position) {
            this.id = id;
            this.url = url;
            this.retryCount = retryCount;
            this.lastReportTime = lastReportTime;
            this.campaignId = campaignid;
            this.position = position;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof NoticeInfo) {
                if (((NoticeInfo)obj).id == id) {
                    return true;
                }
            }
            return false;
        }
    }
}
