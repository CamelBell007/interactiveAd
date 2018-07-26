package com.interactive.suspend.ad.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.constant.AdConstants;
import com.interactive.suspend.ad.model.ApxAdConfigBean;
import com.interactive.suspend.ad.model.BeanParser;
import com.interactive.suspend.ad.util.AES;
import com.interactive.suspend.ad.http.UrlUtils;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by drason on 2018/7/23.
 */

public class ApxConfigRequestManager {

    private static String TAG = "ApxConfigRequestManager";

    public static void requestAdConfig(Context context, String appid, RequestApxConfigListener listener) {
        if (context == null) return;

        String adConfigUrl = UrlUtils.buildGetApxConfigURL(context, appid);

        Log.i(TAG, "apx init url: " + adConfigUrl);
        try {
//            String configInfo = SharePUtil.getAdConfigInfo(context);
            HttpRequest response = HttpRequest.get(adConfigUrl).acceptJson().connectTimeout(AdConstants.NET_CONNECT_TIMEOUT)
                    .readTimeout(AdConstants.NET_READ_TIMEOUT);

            if (response.ok()) {
                InputStream inputStream = response.getConnection().getInputStream();
                String jsonStr = AES.doGet(inputStream);
                JSONObject json = new JSONObject(jsonStr);
                String status = json.optString("status");
                JSONObject dataObject = json.optJSONObject("data");

                if ("OK".equalsIgnoreCase(status) && null != dataObject) {
                    SharePUtil.putString(context, AdConstants.APX_CONFIG_PATH, adConfigUrl);
                    SharePUtil.setAdConfigInfo(context, adConfigUrl, jsonStr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
//            Log.i(TAG, "failed!");
        }

        String apxConfigJson = SharePUtil.getApxAdConfigInfo(context);
        Log.i(TAG, "sp get apx json: " + apxConfigJson);
        ApxAdConfigBean apxAdConfigBean = null;
        if (!TextUtils.isEmpty(apxConfigJson)) {
            apxAdConfigBean = BeanParser.parseApxConfigBean(apxConfigJson);
        }
        if (listener != null) {
            if (apxAdConfigBean != null) {
                listener.onRequestSuccess(apxAdConfigBean);
            } else {
                listener.onRequestFailed("request config failed: 504");
            }
        }

    }

}
