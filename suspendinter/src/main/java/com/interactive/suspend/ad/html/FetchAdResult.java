package com.interactive.suspend.ad.html;


import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * APX-->API获取到的广告列表
 */
public class FetchAdResult {

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

    public static boolean isFailed(FetchAdResult result) {
        if (result == null || result.ads == null || result.ads.ad == null || !"OK".equals(result.status)) {
            return true;
        }
        return false;
    }

    public static class creatives {
        @SerializedName("320x50")
        public ArrayList<String> banner;
        @SerializedName("320x480")
        public ArrayList<String> phone_fullscreen;
        @SerializedName("480x320")
        public ArrayList<String> phone_fullscreen_landscape;
        @SerializedName("1024x768")
        public ArrayList<String> tablet_fullscreen;
        @SerializedName("768x1024")
        public ArrayList<String> tablet_fullscreen_landscape;
        @SerializedName("300x250")
        public ArrayList<String> medium;
        @SerializedName("728x90")
        public ArrayList<String> leaderboard;
        @SerializedName("160x600")
        public ArrayList<String> skyscraper;
        @SerializedName("1200x627")
        public ArrayList<String> content_strean_image;
    }

    private BigDecimal getMoney(String money) {
        if (TextUtils.isEmpty(money)) {
            return BigDecimal.ZERO;
        }
        int length = money.length();
        String number = money.substring(0, length - 1);
        return new BigDecimal(number);
    }

    public static class Ad {
        @SerializedName("campaignid")
        public String campaignID;
        @SerializedName("payout")
        public String payOut;
        @SerializedName("pkgname")
        public String packageName;
        @SerializedName("title")
        public String title;
        @SerializedName("description")
        public String description;
        @SerializedName("clkurl")
        public String clickURL;
        @SerializedName("icon")
        public String icon;
        @SerializedName("appcategory")
        public String appCategory;
        @SerializedName("apprating")
        public String appRating;
        @SerializedName("appreviewnum")
        public String appReviewNum;
        @SerializedName("appinstalls")
        public String appInstalls;
        @SerializedName("appsize")
        public String appSize;
//        @SerializedName("creatives")
//        public creatives creatives;
        @SerializedName("impurls")
        public ArrayList<String> impurls;
        @SerializedName("notice_url")
        public String noticeUrl;
        @SerializedName("click_mode")
        public int clickMode;
        @SerializedName("cache_time")
        public long cacheTime;
        @SerializedName("connectiontype")
        public String connectiontype;
//        @SerializedName("convflow")
//        public String convflow;
        @SerializedName("countries")
        public String countries;
        @SerializedName("devicetype")
        public String devicetype;
//        @SerializedName("incent")
//        public String incent;
        @SerializedName("market")
        public String market;
//        @SerializedName("minosv")
//        public String minosv;
//        @SerializedName("os")
//        public String os;
        @SerializedName("is_display")
        public int isDisplay;
        @SerializedName("extra")
        public String extra;
        @SerializedName("image_url")
        public String imageUrl;
        @SerializedName("video_url")
        public String videoUrl;
        @SerializedName("video_size")
        public String videoSize;
        @SerializedName("video_length")
        public String videoLength;
        @SerializedName("video_resolution")
        public String videoResolution;
        @SerializedName("video_expire")
        public long videoExpire;

        public static Comparator<Ad> DEFAULT_COMPARATOR = new Comparator<Ad>() {
            @Override
            public int compare(Ad ad, Ad ad2) {
                return 0;
            }
        };
    }
}
