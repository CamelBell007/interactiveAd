package com.interactive.suspend.ad.net;

import com.interactive.suspend.ad.constant.AdConstants;
import com.interactive.suspend.ad.http.UrlUtils;
import com.interactive.suspend.ad.util.AES;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by drason on 2018/7/26.
 */

public class ReportManager {


    public void report(String url, HashMap<String, String> paramsMap, ReportResultListener listener) {
        try {
            String reportUrl = UrlUtils.appendReportParams(url, paramsMap);
            HttpRequest response = HttpRequest.get(reportUrl).acceptJson().connectTimeout(AdConstants.NET_CONNECT_TIMEOUT)
                    .readTimeout(AdConstants.NET_READ_TIMEOUT);

            if (response.ok()) {
                InputStream inputStream = response.getConnection().getInputStream();
                String jsonStr = AES.doGet(inputStream);
                JSONObject json = new JSONObject(jsonStr);
                int code = json.optInt("code");

                if (listener != null) {
                    if (code == 0) {
                        listener.onReportSuccess();
                    } else {
                        listener.onReportFailed(code, "report failed");
                    }
                }
            } else {
                if (listener != null) {
                    listener.onReportFailed(500, "report failed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onReportFailed(404, "report failed");
            }
        }
    }

}
