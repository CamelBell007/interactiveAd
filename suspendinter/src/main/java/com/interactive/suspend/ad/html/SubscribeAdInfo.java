package com.interactive.suspend.ad.html;

import java.io.Serializable;

import static android.R.attr.type;

/**
 * Created by csc on 15/11/4.
 */
public class SubscribeAdInfo implements Cloneable, Serializable {

    public String campaignid;
    public String title;
    public String description;
    public String clkurl;
    public String impurls;
    public String noticeUrl;
    public long cacheTime;
    public String countries;
    public String imageUrl;
    public String carrier;
    public String kpi;
    public String incent;

    public SubscribeAdInfo(FetchSubscribeAdResult.Ad ad) {
        campaignid = ad.campaignID;
        title = ad.title;
        description = ad.description;
        clkurl = ad.clickURL;
        if (ad.impurls != null && ad.impurls.size() > 0) {
            impurls = ad.impurls.get(0);
        } else {
            impurls = null;
        }
        noticeUrl = ad.noticeUrl;
        cacheTime = ad.cacheTime * 1000; //ms
        countries = ad.countries;
        imageUrl = ad.imageUrl;
        carrier = ad.carrier;
        kpi = ad.kpi;
        incent = ad.incent;
    }

    public SubscribeAdInfo() {
    }

    @Override
    public SubscribeAdInfo clone() {
        SubscribeAdInfo info = null;
        try {
            info = (SubscribeAdInfo) super.clone();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return info;
    }

    @Override
    public String toString() {
        return "SubscribeAdInfo{" +
                "campaignid='" + campaignid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", clkurl='" + clkurl + '\'' +
                ", noticeUrl='" + noticeUrl + '\'' +
                ", cacheTime='" + cacheTime + '\'' +
                ", carrier='" + carrier + '\'' +
                ", kpi='" + kpi + '\'' +
                ", incent='" + incent + '\'' +
                ", countries='" + countries + '\'' +
                ", imageurl='" + imageUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
