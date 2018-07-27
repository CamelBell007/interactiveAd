package com.interactive.suspend.ad.db;

import com.interactive.suspend.ad.html.FetchAdResult;
import com.interactive.suspend.ad.html.GPEncode;

import java.io.Serializable;


/**
 * Created by csc on 15/11/4.
 */
public class AdInfo implements Cloneable, Serializable {
    public String campaignid;
    public String payout;
    public String pkgname;
    public String title;
    public String description;
    public String clkurl;
    public String icon;
    public String appcategory;
    public String appsize;
    public String apprating;
    public String appreviewnum;
    public String appinstalls;
    public String loadedclickurl;
    public String impurls;
    public long preclickTime;
    public String noticeUrl;
    public int clickMode;
    public long cacheTime;
    public String connectiontype;
    public String countries;
    public String devicetype;
    public String clkid;
    public String shareGP;
    public String imageUrl;
    public String videoUrl;
    public String videoSize;
    public String videoLength;
    public String videoResolution;
    public long videoExpire;
    public String type;
    public int isDisplay;
    //get ad offer and save DB time
    public long loadingTime;

    public AdInfo(FetchAdResult.Ad ad, String type) {
        campaignid = ad.campaignID;
        payout = ad.payOut;
        pkgname = ad.packageName;
        title = ad.title;
        description = ad.description;
        clkurl = ad.clickURL;
        icon = ad.icon;
        appcategory = ad.appCategory;
        apprating = ad.appRating;
        appreviewnum = ad.appReviewNum;
        appinstalls = ad.appInstalls;
        if (ad.impurls != null && ad.impurls.size() > 0) {
            impurls = ad.impurls.get(0);
        } else {
            impurls = null;
        }
        preclickTime = 0;
        noticeUrl = ad.noticeUrl;
        clickMode = ad.clickMode;
        cacheTime = ad.cacheTime * 1000; //ms
        appsize = ad.appSize;
        connectiontype = ad.connectiontype;
        countries = ad.countries;
        devicetype = ad.devicetype;
        clkid = GPEncode.decodeClickid(ad.extra);
        shareGP = GPEncode.decodeGPUrl(ad.extra);
        imageUrl = ad.imageUrl;
        videoUrl = ad.videoUrl;
        videoSize = ad.videoSize;
        videoLength = ad.videoLength;
        videoResolution = ad.videoResolution;
        this.type = type;
        isDisplay = ad.isDisplay;
        videoExpire = ad.videoExpire;
    }

    public AdInfo(AdInfo info) {
        campaignid = info.campaignid;
        payout = info.payout;
        pkgname = info.pkgname;
        title = info.title;
        description = info.description;
        clkurl = info.clkurl;
        icon = info.icon;
        appcategory = info.appcategory;
        appsize = info.appsize;
        apprating = info.apprating;
        appreviewnum = info.appreviewnum;
        appinstalls = info.appinstalls;
        impurls = info.impurls;
        preclickTime = info.preclickTime;
        noticeUrl = info.noticeUrl;
        clickMode = info.clickMode;
        cacheTime = info.cacheTime;
        connectiontype = info.connectiontype;
        countries = info.countries;
        devicetype = info.devicetype;
        clkid = info.clkid;
        shareGP = info.shareGP;
        imageUrl = info.imageUrl;
        videoUrl = info.videoUrl;
        videoSize = info.videoSize;
        videoLength = info.videoLength;
        videoResolution = info.videoResolution;
        type = info.type;
        isDisplay = info.isDisplay;
        videoExpire = info.videoExpire;
        loadingTime = info.loadingTime;
    }

    public AdInfo() {
    }

    @Override
    public String toString() {
        return "AdInfo{" +
                "campaignid='" + campaignid + '\'' +
                ", payout='" + payout + '\'' +
                ", pkgname='" + pkgname + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", clkurl='" + clkurl + '\'' +
                ", icon='" + icon + '\'' +
                ", appcategory='" + appcategory + '\'' +
                ", apprating='" + apprating + '\'' +
                ", appreviewnum='" + appreviewnum + '\'' +
                ", appinstalls='" + appinstalls + '\'' +
                ", loadedclickurl='" + loadedclickurl + '\'' +
                ", preclickTime='" + preclickTime + '\'' +
                ", noticeUrl='" + noticeUrl + '\'' +
                ", clickMode='" + clickMode + '\'' +
                ", cacheTime='" + cacheTime + '\'' +
                ", appsize='" + appsize + '\'' +
                ", connectiontype='" + connectiontype + '\'' +
                ", countries='" + countries + '\'' +
                ", devicetype='" + devicetype + '\'' +
                ", clkid='" + clkid + '\'' +
                ", sharegp='" + shareGP + '\'' +
                ", imageurl='" + imageUrl + '\'' +
                ", videourl='" + videoUrl + '\'' +
                ", videosize='" + videoSize + '\'' +
                ", videolength='" + videoLength + '\'' +
                ", videoresolution='" + videoResolution + '\'' +
                ", videoreexpire='" + videoExpire + '\'' +
                ", type='" + type + '\'' +
                ", isDisplay='" + isDisplay + '\'' +
                '}';
    }
}
