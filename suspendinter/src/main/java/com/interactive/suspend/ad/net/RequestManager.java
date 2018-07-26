package com.interactive.suspend.ad.net;

import android.content.Context;
import android.text.TextUtils;

import com.interactive.suspend.ad.constant.AdConstants;
import com.interactive.suspend.ad.model.AdConfigBean;
import com.interactive.suspend.ad.model.BeanParser;
import com.interactive.suspend.ad.util.AES;
import com.interactive.suspend.ad.http.UrlUtils;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by drason on 2018/7/20.
 */

public class RequestManager {

    static final String TAG = RequestManager.class.getSimpleName();
    private static String errorMsg = "request failed: 501";

    public static void requestAdConfig(Context context, String url, RequestDopConfigListener listener) {
        if (context == null) return;

        try {
            String configInfo = SharePUtil.getAdConfigInfo(context);
            String adConfigUrl = UrlUtils.buildGetAdConfigUrl(context, url, configInfo);
//            Log.d(TAG, "dop request url: " + adConfigUrl);
            HttpRequest response = HttpRequest.get(adConfigUrl).acceptJson().connectTimeout(AdConstants.NET_CONNECT_TIMEOUT)
                    .readTimeout(AdConstants.NET_READ_TIMEOUT);

            if (response.ok()) {
                InputStream inputStream = response.getConnection().getInputStream();
                String jsonStr = AES.doGetAES(inputStream);
                JSONObject json = new JSONObject(jsonStr);
                int code = json.optInt("code");

                if (0 == code) {
                    SharePUtil.putString(context, AdConstants.CONFIG_FULL_PATH, adConfigUrl);
                    SharePUtil.setAdConfigInfo(context, adConfigUrl, jsonStr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
//            Log.i(TAG, "failed!");
        }

        String adConfigInfo = SharePUtil.getAdConfigInfo(context);
        AdConfigBean adConfigBean = null;
        if (!TextUtils.isEmpty(adConfigInfo)) {
            adConfigBean = BeanParser.parseAdConfig(adConfigInfo);
        }

        if (listener != null) {
            if (null != adConfigBean) {
                listener.onRequestSuccess(adConfigBean);
            } else {
                listener.onRequestFailed(errorMsg);
            }
        }

    }

}
