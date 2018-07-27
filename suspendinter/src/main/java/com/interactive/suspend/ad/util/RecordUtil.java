package com.interactive.suspend.ad.util;

import android.content.Context;

import com.interactive.suspend.ad.constant.Constants;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2018/7/26.
 */

public class RecordUtil {
    public static <T> void uploadAdImpressionEvent(Context context, List<T> list, int visibleResult, String sourceid) {
    try{
        if (context != null && list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            if (list.get(0) instanceof AdInfo) {
                sb.append(((AdInfo) list.get(0)).impurls);
                sb.append("&adlist=");
                for (int i = 0; i < list.size(); i++) {
                    sb.append(((AdInfo) list.get(i)).campaignid);
                    if (i != (list.size() - 1)) {
                        sb.append(",");
                    }
                }
            } else if (list.get(0) instanceof SubscribeAdInfo) {
                sb.append(((SubscribeAdInfo) list.get(0)).impurls);
                sb.append("&adlist=");
                for (int i = 0; i < list.size(); i++) {
                    sb.append(((SubscribeAdInfo) list.get(i)).campaignid);
                    if (i != (list.size() - 1)) {
                        sb.append(",");
                    }
                }
            }
            sb.append("&impid=").append(UUID.randomUUID().toString());
            sb.append("&imppage=appstore");
            sb.append("&sdkv=").append(Constants.SDK_VERSION);
            sb.append("&replace_src=").append(sourceid);
            sb.append("&impcheck=").append(visibleResult);
            AnalyticsManager.getInstance(context).doUpload(sb.toString());
        }
    }catch (Throwable throwable){
        throwable.printStackTrace();
    }

}
}
