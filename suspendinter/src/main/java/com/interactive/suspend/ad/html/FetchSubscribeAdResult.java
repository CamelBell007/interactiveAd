package com.interactive.suspend.ad.html;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Content(仿照admob叫法)类型的广告数据
 */
public class FetchSubscribeAdResult {

    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("ads")
    public AdWrapper ads;

    public static class AdWrapper {
        @SerializedName("ad")
        public List<Ad> ad;
        @SerializedName("total_records")
        public String totalRecords;
    }

    public static boolean isFailed(FetchSubscribeAdResult result) {
        if (result == null || result.ads == null || result.ads.ad == null || !"OK".equals(result.status)) {
            return true;
        }
        return false;
    }

    public static class Ad {
        @SerializedName("campaignid")
        public String campaignID;
        @SerializedName("carrier")
        public String carrier;
        @SerializedName("kpi")
        public String kpi;
        @SerializedName("title")
        public String title;
        @SerializedName("description")
        public String description;
        @SerializedName("clkurl")
        public String clickURL;
        @SerializedName("impurls")
        public ArrayList<String> impurls;
        @SerializedName("notice_url")
        public String noticeUrl;
        @SerializedName("cache_time")
        public long cacheTime;
        @SerializedName("countries")
        public String countries;
        @SerializedName("incent")
        public String incent;
        @SerializedName("image_url")
        public String imageUrl;
    }
}
