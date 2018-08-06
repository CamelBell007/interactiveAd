package com.interactive.suspend.ad.net;

/**
 * Created by drason on 2018/7/26.
 */

public interface ReportResultListener {
    void onReportSuccess();
    void onReportFailed(int code, String msg);
}
