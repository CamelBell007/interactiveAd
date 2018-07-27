package com.interactive.suspend.ad.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.model.AppConfig;
import com.interactive.suspend.ad.util.AdUtils;
import com.interactive.suspend.ad.util.ApplicationUtil;
import com.interactive.suspend.ad.util.LogUtil;
import com.interactive.suspend.ad.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csc on 15/11/4.
 */
public class AvDatabaseUtils {
    public synchronized static List<AdInfo> getCacheAppWallData(Context context, int limit, int sortType) {
//        L.e("VC--DB-->", "getCacheAppWallData");
        if (context == null) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        List<AdInfo> ret = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            Cursor c;
            List<String> tabFilter = PreferenceUtils.getTabFilter(context);
            String sql_sel_all = "SELECT  * FROM " + AdColumns.TABLE_NAME + " where " + AdColumns.AD_TYPE + "="
                    + "'" + Constants.ApxAdType.APPWALL + "'";
//            L.d("VC-->DB-->getCacheAppWallData", sql_sel_all);
            if (sortType == Constants.ActivityAd.SORT_ALL) {
                c = db.rawQuery(sql_sel_all, null);
            } else if (sortType == Constants.ActivityAd.SORT_PRACTICAL) {
                String sql_sel_practical = sql_sel_all + " AND " + "(";
                for (String filter : tabFilter) {
                    if (tabFilter.indexOf(filter) == 0) {
                        sql_sel_practical += AdColumns.CATEGORY + " like '%" + filter + "%'";
                    } else {
                        sql_sel_practical += " OR " + AdColumns.CATEGORY + " like '%" + filter + "%'";
                    }
                }
                sql_sel_practical += ")";
                c = db.rawQuery(sql_sel_practical, null);
            } else {
                String sql_sel_app = sql_sel_all + " AND ";
                for (String filter : tabFilter) {
                    if (tabFilter.indexOf(filter) == 0) {
                        sql_sel_app += AdColumns.CATEGORY + " not like '%" + filter + "%'";
                    } else {
                        sql_sel_app += " AND " + AdColumns.CATEGORY + " not like '%" + filter + "%'";
                    }
                }
                c = db.rawQuery(sql_sel_app, null);
            }
            if (c == null) {
                return null;
            }
            List<AdInfo> installed = new ArrayList<>();
            c.moveToFirst();
            while (!c.isAfterLast()) {
                AdInfo a = new AdInfo();
                a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                a.payout = c.getString(c.getColumnIndex(AdColumns.PAY_OUT));
                a.pkgname = c.getString(c.getColumnIndex(AdColumns.PKG_NAME));
                a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                a.icon = c.getString(c.getColumnIndex(AdColumns.ICON));
                a.appcategory = c.getString(c.getColumnIndex(AdColumns.CATEGORY));
                a.appinstalls = c.getString(c.getColumnIndex(AdColumns.INSTALLS));
                a.apprating = c.getString(c.getColumnIndex(AdColumns.RATING));
                a.appreviewnum = c.getString(c.getColumnIndex(AdColumns.REVIEWNUMS));
                a.loadedclickurl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
                a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                a.preclickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
                a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                a.clickMode = c.getInt(c.getColumnIndex(AdColumns.CLICK_MODE));
                a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                a.appsize = c.getString(c.getColumnIndex(AdColumns.APP_SIZE));
                a.connectiontype = c.getString(c.getColumnIndex(AdColumns.CONNECTION_TYPE));
                a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                a.devicetype = c.getString(c.getColumnIndex(AdColumns.DEVICE_TYPE));
                a.clkid = c.getString(c.getColumnIndex(AdColumns.CLKID));
                a.shareGP = c.getString(c.getColumnIndex(AdColumns.GPURL));
                a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                a.videoUrl = c.getString(c.getColumnIndex(AdColumns.VIDEO_URL));
                a.videoSize = c.getString(c.getColumnIndex(AdColumns.VIDEO_SIZE));
                a.videoLength = c.getString(c.getColumnIndex(AdColumns.VIDEO_LENGTH));
                a.videoResolution = c.getString(c.getColumnIndex(AdColumns.VIDEO_RESOLUTION));
                a.videoExpire = c.getLong(c.getColumnIndex(AdColumns.VIDEO_EXPIRE));
                a.type = c.getString(c.getColumnIndex(AdColumns.AD_TYPE));
                a.isDisplay = c.getInt(c.getColumnIndex(AdColumns.IS_DISPLAY));

                a.loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));

                boolean isInstalled = ApplicationUtil.isAppInstalled(context, a.pkgname);
                if (isInstalled) {
                    if (!contains(installed, a)) { // remove duplicate camid or pkgname ad
                        installed.add(a);
                    }
                } else {
                    if (!contains(ret, a)) {
                        ret.add(a);
                    }
                }
                c.moveToNext();
                if (limit != -1 && limit == ret.size()) {
                    break;
                }
            }
            // put installed pkg info in end of this list
            ret.addAll(installed);
            db.close();
            c.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }

        ret = AdUtils.getValidAdInfo(context, ret,"getCacheAppWallData");
        return ret;
    }

    public synchronized static List<AdInfo> getCachePlayableAdData(Context context, int limit) {
        if (context == null) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        List<AdInfo> ret = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            String sql_sel = "SELECT * FROM " + AdColumns.TABLE_NAME + " where " + AdColumns.AD_TYPE + "="
                    + "'" + Constants.ApxAdType.PLAYABLE + "'";
//            L.e("VC-->DB-->getCachePlayableAdData", sql_sel);
            Cursor c = db.rawQuery(sql_sel, null);
            if (c == null) {
                return null;
            }

            c.moveToFirst();
            while (!c.isAfterLast()) {
                String pkg = c.getString(c.getColumnIndex(AdColumns.PKG_NAME));
                boolean isInstalled = ApplicationUtil.isAppInstalled(context, pkg);
                if (isInstalled) {
                    c.moveToNext();
                    continue;
                } else {
                    AdInfo a = new AdInfo();
                    a.pkgname = pkg;
                    a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                    a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                    a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                    a.payout = c.getString(c.getColumnIndex(AdColumns.PAY_OUT));
                    a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                    a.icon = c.getString(c.getColumnIndex(AdColumns.ICON));
                    a.appcategory = c.getString(c.getColumnIndex(AdColumns.CATEGORY));
                    a.appinstalls = c.getString(c.getColumnIndex(AdColumns.INSTALLS));
                    a.apprating = c.getString(c.getColumnIndex(AdColumns.RATING));
                    a.appreviewnum = c.getString(c.getColumnIndex(AdColumns.REVIEWNUMS));
                    a.loadedclickurl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
                    a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                    a.preclickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
                    a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                    a.clickMode = c.getInt(c.getColumnIndex(AdColumns.CLICK_MODE));
                    a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                    a.appsize = c.getString(c.getColumnIndex(AdColumns.APP_SIZE));
                    a.connectiontype = c.getString(c.getColumnIndex(AdColumns.CONNECTION_TYPE));
                    a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                    a.devicetype = c.getString(c.getColumnIndex(AdColumns.DEVICE_TYPE));
                    a.clkid = c.getString(c.getColumnIndex(AdColumns.CLKID));
                    a.shareGP = c.getString(c.getColumnIndex(AdColumns.GPURL));
                    a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                    a.videoUrl = c.getString(c.getColumnIndex(AdColumns.VIDEO_URL));
                    a.videoSize = c.getString(c.getColumnIndex(AdColumns.VIDEO_SIZE));
                    a.videoLength = c.getString(c.getColumnIndex(AdColumns.VIDEO_LENGTH));
                    a.videoResolution = c.getString(c.getColumnIndex(AdColumns.VIDEO_RESOLUTION));
                    a.videoExpire = c.getLong(c.getColumnIndex(AdColumns.VIDEO_EXPIRE));
                    a.type = c.getString(c.getColumnIndex(AdColumns.AD_TYPE));
                    a.isDisplay = c.getInt(c.getColumnIndex(AdColumns.IS_DISPLAY));

                    a.loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));

                    ret.add(a);
                }
                c.moveToNext();
                if (limit != -1 && limit == ret.size()) {
                    break;
                }
            }
            db.close();
            c.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }

        ret = AdUtils.getValidAdInfo(context, ret,"getCachePlayableAdData");
        return ret;
    }

    public synchronized static List<AdInfo> getCacheSmartAdData(Context context, int limit) {
        if (context == null) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        List<AdInfo> ret = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            String sql_sel = "SELECT * FROM " + AdColumns.TABLE_NAME + " where " + AdColumns.AD_TYPE + "="
                    + "'" + Constants.ApxAdType.SMART + "'";
//            L.e("VC-->DB-->getCacheSmartAdData", sql_sel);
            Cursor c = db.rawQuery(sql_sel, null);
            if (c == null) {
                return null;
            }

            c.moveToFirst();
            while (!c.isAfterLast()) {
                String pkg = c.getString(c.getColumnIndex(AdColumns.PKG_NAME));
                boolean isInstalled = ApplicationUtil.isAppInstalled(context, pkg);
                if (isInstalled) {
                    c.moveToNext();
                    continue;
                } else {
                    AdInfo a = new AdInfo();
                    a.pkgname = pkg;
                    a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                    a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                    a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                    a.payout = c.getString(c.getColumnIndex(AdColumns.PAY_OUT));
                    a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                    a.icon = c.getString(c.getColumnIndex(AdColumns.ICON));
                    a.appcategory = c.getString(c.getColumnIndex(AdColumns.CATEGORY));
                    a.appinstalls = c.getString(c.getColumnIndex(AdColumns.INSTALLS));
                    a.apprating = c.getString(c.getColumnIndex(AdColumns.RATING));
                    a.appreviewnum = c.getString(c.getColumnIndex(AdColumns.REVIEWNUMS));
                    a.loadedclickurl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
                    a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                    a.preclickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
                    a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                    a.clickMode = c.getInt(c.getColumnIndex(AdColumns.CLICK_MODE));
                    a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                    a.appsize = c.getString(c.getColumnIndex(AdColumns.APP_SIZE));
                    a.connectiontype = c.getString(c.getColumnIndex(AdColumns.CONNECTION_TYPE));
                    a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                    a.devicetype = c.getString(c.getColumnIndex(AdColumns.DEVICE_TYPE));
                    a.clkid = c.getString(c.getColumnIndex(AdColumns.CLKID));
                    a.shareGP = c.getString(c.getColumnIndex(AdColumns.GPURL));
                    a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                    a.videoUrl = c.getString(c.getColumnIndex(AdColumns.VIDEO_URL));
                    a.videoSize = c.getString(c.getColumnIndex(AdColumns.VIDEO_SIZE));
                    a.videoLength = c.getString(c.getColumnIndex(AdColumns.VIDEO_LENGTH));
                    a.videoResolution = c.getString(c.getColumnIndex(AdColumns.VIDEO_RESOLUTION));
                    a.videoExpire = c.getLong(c.getColumnIndex(AdColumns.VIDEO_EXPIRE));
                    a.type = c.getString(c.getColumnIndex(AdColumns.AD_TYPE));
                    a.isDisplay = c.getInt(c.getColumnIndex(AdColumns.IS_DISPLAY));

                    a.loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));

                    ret.add(a);
                }
                c.moveToNext();
                if (limit != -1 && limit == ret.size()) {
                    break;
                }
            }
            db.close();
            c.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        ret = AdUtils.getValidAdInfo(context, ret,"getCacheSmartAdData");
        return ret;
    }

    public synchronized static List<AdInfo> getCacheRewardAdData(Context context, int limit) {
        if (context == null) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        List<AdInfo> ret = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            String sql_sel_reward = "SELECT * FROM " + AdColumns.TABLE_NAME + " where " + AdColumns.AD_TYPE + "="
                    + "'" + Constants.ApxAdType.REWARD + "'" + " AND " + AdColumns.VIDEO_URL + " is not null";
//            L.e("VC-->DB-->getCacheRewardAdData", sql_sel_reward);
            Cursor c = db.rawQuery(sql_sel_reward, null);
            if (c == null) {
                return null;
            }

            c.moveToFirst();
            while (!c.isAfterLast()) {
                String pkg = c.getString(c.getColumnIndex(AdColumns.PKG_NAME));
                boolean isInstalled = ApplicationUtil.isAppInstalled(context, pkg);
                if (isInstalled) {
                    c.moveToNext();
                    continue;
                } else {
                    AdInfo a = new AdInfo();
                    a.pkgname = pkg;
                    a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                    a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                    a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                    a.payout = c.getString(c.getColumnIndex(AdColumns.PAY_OUT));
                    a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                    a.icon = c.getString(c.getColumnIndex(AdColumns.ICON));
                    a.appcategory = c.getString(c.getColumnIndex(AdColumns.CATEGORY));
                    a.appinstalls = c.getString(c.getColumnIndex(AdColumns.INSTALLS));
                    a.apprating = c.getString(c.getColumnIndex(AdColumns.RATING));
                    a.appreviewnum = c.getString(c.getColumnIndex(AdColumns.REVIEWNUMS));
                    a.loadedclickurl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
                    a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                    a.preclickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
                    a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                    a.clickMode = c.getInt(c.getColumnIndex(AdColumns.CLICK_MODE));
                    a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                    a.appsize = c.getString(c.getColumnIndex(AdColumns.APP_SIZE));
                    a.connectiontype = c.getString(c.getColumnIndex(AdColumns.CONNECTION_TYPE));
                    a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                    a.devicetype = c.getString(c.getColumnIndex(AdColumns.DEVICE_TYPE));
                    a.clkid = c.getString(c.getColumnIndex(AdColumns.CLKID));
                    a.shareGP = c.getString(c.getColumnIndex(AdColumns.GPURL));
                    a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                    a.videoUrl = c.getString(c.getColumnIndex(AdColumns.VIDEO_URL));
                    a.videoSize = c.getString(c.getColumnIndex(AdColumns.VIDEO_SIZE));
                    a.videoLength = c.getString(c.getColumnIndex(AdColumns.VIDEO_LENGTH));
                    a.videoResolution = c.getString(c.getColumnIndex(AdColumns.VIDEO_RESOLUTION));
                    a.videoExpire = c.getLong(c.getColumnIndex(AdColumns.VIDEO_EXPIRE));
                    a.type = c.getString(c.getColumnIndex(AdColumns.AD_TYPE));
                    a.isDisplay = c.getInt(c.getColumnIndex(AdColumns.IS_DISPLAY));

                    a.loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));

                    ret.add(a);
                }
                c.moveToNext();
                if (limit != -1 && limit == ret.size()) {
                    break;
                }
            }
            db.close();
            c.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        ret = AdUtils.getValidAdInfo(context, ret,"getCacheRewardAdData");

        return ret;
    }


    public synchronized static List<AdInfo> getCacheNativeAdData(Context context, int limit, int nativeStyle) {
//        L.e("VC--DB-->", "getCacheNativeAdData");
        if (context == null) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        List<AdInfo> ret = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            Cursor c;
            String sql_sel_native = "SELECT * FROM " + AdColumns.TABLE_NAME + " where " +
                    AdColumns.AD_TYPE + "=" + "'" + Constants.ApxAdType.NATIVE + "'";
//            L.e("VC-->DB-->getCacheNativeAdData", "sql_sel_native|-->"+sql_sel_native);
            if (nativeStyle == Constants.NativeAdStyle.SMALL || nativeStyle == Constants.NativeAdStyle.MEDIUM) {
                c = db.rawQuery(sql_sel_native, null);
//                L.e("VC-->DB-->getCacheNativeAdData", "sql_sel_native-->rawQuery-->SMALL");
            } else if (nativeStyle == Constants.NativeAdStyle.LARGE) {
                String sql_sel_native_large = sql_sel_native + " AND (" + AdColumns.IMAGE_URL + " is not null"
                        + " OR " + AdColumns.VIDEO_URL + " is not null)";
                c = db.rawQuery(sql_sel_native_large, null);
//                L.e("VC-->DB-->getCacheNativeAdData", "sql_sel_native-->rawQuery-->LARGE");
            } else {
                throw new IllegalArgumentException("Wrong native style: " + nativeStyle + ", should be: 1|2|3");
            }
            if (c == null) {
                return null;
            }

            c.moveToFirst();
            while (!c.isAfterLast()) {
                String pkg = c.getString(c.getColumnIndex(AdColumns.PKG_NAME));
                boolean isInstalled = ApplicationUtil.isAppInstalled(context, pkg);
                if (isInstalled) {
                    c.moveToNext();
                    continue;
                } else {
                    AdInfo a = new AdInfo();
                    a.pkgname = pkg;
                    a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                    a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                    a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                    a.payout = c.getString(c.getColumnIndex(AdColumns.PAY_OUT));
                    a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                    a.icon = c.getString(c.getColumnIndex(AdColumns.ICON));
                    a.appcategory = c.getString(c.getColumnIndex(AdColumns.CATEGORY));
                    a.appinstalls = c.getString(c.getColumnIndex(AdColumns.INSTALLS));
                    a.apprating = c.getString(c.getColumnIndex(AdColumns.RATING));
                    a.appreviewnum = c.getString(c.getColumnIndex(AdColumns.REVIEWNUMS));
                    a.loadedclickurl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
                    a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                    a.preclickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
                    a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                    a.clickMode = c.getInt(c.getColumnIndex(AdColumns.CLICK_MODE));
                    a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                    a.appsize = c.getString(c.getColumnIndex(AdColumns.APP_SIZE));
                    a.connectiontype = c.getString(c.getColumnIndex(AdColumns.CONNECTION_TYPE));
                    a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                    a.devicetype = c.getString(c.getColumnIndex(AdColumns.DEVICE_TYPE));
                    a.clkid = c.getString(c.getColumnIndex(AdColumns.CLKID));
                    a.shareGP = c.getString(c.getColumnIndex(AdColumns.GPURL));
                    a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                    a.videoUrl = c.getString(c.getColumnIndex(AdColumns.VIDEO_URL));
                    a.videoSize = c.getString(c.getColumnIndex(AdColumns.VIDEO_SIZE));
                    a.videoLength = c.getString(c.getColumnIndex(AdColumns.VIDEO_LENGTH));
                    a.videoResolution = c.getString(c.getColumnIndex(AdColumns.VIDEO_RESOLUTION));
                    a.videoExpire = c.getLong(c.getColumnIndex(AdColumns.VIDEO_EXPIRE));
                    a.type = c.getString(c.getColumnIndex(AdColumns.AD_TYPE));
                    a.isDisplay = c.getInt(c.getColumnIndex(AdColumns.IS_DISPLAY));

                    a.loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));

                    ret.add(a);
                }
                c.moveToNext();
                if (limit != -1 && limit == ret.size()) {
                    break;
                }
            }
            db.close();
            c.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        ret = AdUtils.getValidAdInfo(context, ret,"getCacheNativeAdData");

        return ret;
    }

    public synchronized static boolean isAdExists(Context context, AdInfo adInfo) {
        if (context == null || adInfo == null) {
            return false;
        }
        boolean isExists = false;
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return false;
            }
            Cursor c = db.query(AdColumns.TABLE_NAME, new String[]{"*"}, AdColumns.AD_TYPE + "=?" + " AND "
                    + AdColumns.CAMPAIGN_ID + "=?", new String[]{adInfo.type, adInfo.campaignid}, null, null, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                String clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                if (clkurl.equals(adInfo.clkurl)) {
                    isExists = true;
                } else {
                    isExists = false;
                }
            }
            if (c != null) {
                c.close();
            }
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Log.e("isExists: " + isExists ,", type: " + adInfo.type + ", id: " + adInfo.campaignid);
        return isExists;
    }

    public synchronized static AdInfo getCachedAdInfo(Context context, String pkg, String adType) {
//        L.e("VC--DB-->", "getCachedAdInfo");
        if (context == null || TextUtils.isEmpty(pkg)) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        AdInfo a = null;
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            String sql_sel_pkg = "SELECT  * FROM " + AdColumns.TABLE_NAME + " where " + AdColumns.AD_TYPE + "="
                    + "'" + adType + "'" + " AND " + AdColumns.PKG_NAME + "='" + pkg + "'";
//            L.e("VC-->DB-->getCachedAdInfo", sql_sel_pkg);
//            L.e(sql_sel_pkg);
            Cursor c = db.rawQuery(sql_sel_pkg, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                a = new AdInfo();
                a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                a.payout = c.getString(c.getColumnIndex(AdColumns.PAY_OUT));
                a.pkgname = pkg;
                a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                a.icon = c.getString(c.getColumnIndex(AdColumns.ICON));
                a.appcategory = c.getString(c.getColumnIndex(AdColumns.CATEGORY));
                a.appinstalls = c.getString(c.getColumnIndex(AdColumns.INSTALLS));
                a.apprating = c.getString(c.getColumnIndex(AdColumns.RATING));
                a.appreviewnum = c.getString(c.getColumnIndex(AdColumns.REVIEWNUMS));
                a.loadedclickurl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
                a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                a.preclickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
                a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                a.clickMode = c.getInt(c.getColumnIndex(AdColumns.CLICK_MODE));
                a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                a.appsize = c.getString(c.getColumnIndex(AdColumns.APP_SIZE));
                a.connectiontype = c.getString(c.getColumnIndex(AdColumns.CONNECTION_TYPE));
                a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                a.devicetype = c.getString(c.getColumnIndex(AdColumns.DEVICE_TYPE));
                a.clkid = c.getString(c.getColumnIndex(AdColumns.CLKID));
                a.shareGP = c.getString(c.getColumnIndex(AdColumns.GPURL));
                a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                a.videoUrl = c.getString(c.getColumnIndex(AdColumns.VIDEO_URL));
                a.videoSize = c.getString(c.getColumnIndex(AdColumns.VIDEO_SIZE));
                a.videoLength = c.getString(c.getColumnIndex(AdColumns.VIDEO_LENGTH));
                a.videoResolution = c.getString(c.getColumnIndex(AdColumns.VIDEO_RESOLUTION));
                a.videoExpire = c.getLong(c.getColumnIndex(AdColumns.VIDEO_EXPIRE));
                a.type = c.getString(c.getColumnIndex(AdColumns.AD_TYPE));
                a.isDisplay = c.getInt(c.getColumnIndex(AdColumns.IS_DISPLAY));

                a.loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));
            }

            if (c != null) {
                c.close();
            }
            db.close();

            //如果当前的广告超时了。则进行删除
            if (a!=null
                    &&System.currentTimeMillis() - a.loadingTime > AppConfig.getInstance(context).getAdValidTime()
                    &&AppConfig.getInstance(context).getAdValidTime() > 0) {
//                L.d("VC--DB-->getCachedAdInfo expireTime", AdColumns.CAMPAIGN_ID + ":    " + a.campaignid);
                removeData(context, a);
                a = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return a;
    }

    public synchronized static List<AdInfo> getCachedAdInfos(Context context, String pkg, String adType) {
        if (context == null || TextUtils.isEmpty(pkg)) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        List<AdInfo> list = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            String sql_sel_pkg = "SELECT  * FROM " + AdColumns.TABLE_NAME + " where " + AdColumns.AD_TYPE + "="
                    + "'" + adType + "'" + " AND " + AdColumns.PKG_NAME + "='" + pkg + "'";
//            L.e("VC-->DB-->getCachedAdInfos", sql_sel_pkg);
            Cursor c = db.rawQuery(sql_sel_pkg, null);
            if (c == null) {
                return null;
            }
            c.moveToFirst();
            while (!c.isAfterLast()) {
                AdInfo a = new AdInfo();
                a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                a.payout = c.getString(c.getColumnIndex(AdColumns.PAY_OUT));
                a.pkgname = pkg;
                a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                a.icon = c.getString(c.getColumnIndex(AdColumns.ICON));
                a.appcategory = c.getString(c.getColumnIndex(AdColumns.CATEGORY));
                a.appinstalls = c.getString(c.getColumnIndex(AdColumns.INSTALLS));
                a.apprating = c.getString(c.getColumnIndex(AdColumns.RATING));
                a.appreviewnum = c.getString(c.getColumnIndex(AdColumns.REVIEWNUMS));
                a.loadedclickurl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
                a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                a.preclickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
                a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                a.clickMode = c.getInt(c.getColumnIndex(AdColumns.CLICK_MODE));
                a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                a.appsize = c.getString(c.getColumnIndex(AdColumns.APP_SIZE));
                a.connectiontype = c.getString(c.getColumnIndex(AdColumns.CONNECTION_TYPE));
                a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                a.devicetype = c.getString(c.getColumnIndex(AdColumns.DEVICE_TYPE));
                a.clkid = c.getString(c.getColumnIndex(AdColumns.CLKID));
                a.shareGP = c.getString(c.getColumnIndex(AdColumns.GPURL));
                a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                a.videoUrl = c.getString(c.getColumnIndex(AdColumns.VIDEO_URL));
                a.videoSize = c.getString(c.getColumnIndex(AdColumns.VIDEO_SIZE));
                a.videoLength = c.getString(c.getColumnIndex(AdColumns.VIDEO_LENGTH));
                a.videoResolution = c.getString(c.getColumnIndex(AdColumns.VIDEO_RESOLUTION));
                a.videoExpire = c.getLong(c.getColumnIndex(AdColumns.VIDEO_EXPIRE));
                a.type = c.getString(c.getColumnIndex(AdColumns.AD_TYPE));
                a.isDisplay = c.getInt(c.getColumnIndex(AdColumns.IS_DISPLAY));

                a.loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));

                list.add(a);
                c.moveToNext();
            }
            c.close();
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        list = AdUtils.getValidAdInfo(context, list,"getCachedAdInfos-pkg");
        if(list != null){
            Log.e("pkg :" + pkg , " count: " + list.size());
        }
        return list;
    }

    public synchronized static List<AdInfo> getCachedAdInfos(Context context, final List<String> pkgs, String adType) {
        if (context == null || pkgs == null || pkgs.size() == 0) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        List<AdInfo> list = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            String sql_sel_pkg = "SELECT  * FROM " + AdColumns.TABLE_NAME + " where " + AdColumns.AD_TYPE + "="
                    + "'" + adType + "'" + " AND (";
            for (String str : pkgs) {
                if (pkgs.indexOf(str) != (pkgs.size() - 1)) {
                    sql_sel_pkg += AdColumns.PKG_NAME + "='" + str + "'" + " OR ";
                } else {
                    sql_sel_pkg += AdColumns.PKG_NAME + "='" + str + "'";
                }
            }
            sql_sel_pkg += ")";
//            L.e(sql_sel_pkg);
//            L.e("VC-->DB-->getCachedAdInfos", sql_sel_pkg);
            Cursor c = db.rawQuery(sql_sel_pkg, null);
            if (c == null) {
                return null;
            }
            c.moveToFirst();
            while (!c.isAfterLast()) {
                AdInfo a = new AdInfo();
                a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                a.payout = c.getString(c.getColumnIndex(AdColumns.PAY_OUT));
                a.pkgname = c.getString(c.getColumnIndex(AdColumns.PKG_NAME));
                a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                a.icon = c.getString(c.getColumnIndex(AdColumns.ICON));
                a.appcategory = c.getString(c.getColumnIndex(AdColumns.CATEGORY));
                a.appinstalls = c.getString(c.getColumnIndex(AdColumns.INSTALLS));
                a.apprating = c.getString(c.getColumnIndex(AdColumns.RATING));
                a.appreviewnum = c.getString(c.getColumnIndex(AdColumns.REVIEWNUMS));
                a.loadedclickurl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
                a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                a.preclickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
                a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                a.clickMode = c.getInt(c.getColumnIndex(AdColumns.CLICK_MODE));
                a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                a.appsize = c.getString(c.getColumnIndex(AdColumns.APP_SIZE));
                a.connectiontype = c.getString(c.getColumnIndex(AdColumns.CONNECTION_TYPE));
                a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                a.devicetype = c.getString(c.getColumnIndex(AdColumns.DEVICE_TYPE));
                a.clkid = c.getString(c.getColumnIndex(AdColumns.CLKID));
                a.shareGP = c.getString(c.getColumnIndex(AdColumns.GPURL));
                a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                a.videoUrl = c.getString(c.getColumnIndex(AdColumns.VIDEO_URL));
                a.videoSize = c.getString(c.getColumnIndex(AdColumns.VIDEO_SIZE));
                a.videoLength = c.getString(c.getColumnIndex(AdColumns.VIDEO_LENGTH));
                a.videoResolution = c.getString(c.getColumnIndex(AdColumns.VIDEO_RESOLUTION));
                a.videoExpire = c.getLong(c.getColumnIndex(AdColumns.VIDEO_EXPIRE));
                a.type = c.getString(c.getColumnIndex(AdColumns.AD_TYPE));
                a.isDisplay = c.getInt(c.getColumnIndex(AdColumns.IS_DISPLAY));

                a.loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));

                list.add(a);
                c.moveToNext();
            }
            c.close();
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        list = AdUtils.getValidAdInfo(context, list,"getCachedAdInfos-pkgs");
        if(list!=null){
            Log.e("pkgs :" + pkgs.toString() , ", count: " + list.size());
        }
        return list;
    }

    public synchronized static String getCachedLoadedUrl(Context context, AdInfo adInfo) {
        if (context == null || adInfo == null) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        String loadedUrl = null;
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            Cursor c = db.query(AdColumns.TABLE_NAME, new String[]{"*"}, AdColumns.AD_TYPE + "=?" + " AND "
                    + AdColumns.CAMPAIGN_ID + "=?", new String[]{adInfo.type, adInfo.campaignid}, null, null, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                loadedUrl = c.getString(c.getColumnIndex(AdColumns.LOADED_CLICK_URL));
            }
            if (c != null) {
                c.close();
            }
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return loadedUrl;
    }

    public synchronized static long getCachedPreclickTime(Context context, AdInfo adInfo) {
        if (context == null || adInfo == null) {
            return 0;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        long preClickTime = 0;
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return 0;
            }
            Cursor c = db.query(AdColumns.TABLE_NAME, new String[]{"*"}, AdColumns.AD_TYPE + "=?" + " AND "
                    + AdColumns.CAMPAIGN_ID + "=?", new String[]{adInfo.type, adInfo.campaignid}, null, null, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                preClickTime = c.getLong(c.getColumnIndex(AdColumns.PRECLICK_TIME));
            }
            if (c != null) {
                c.close();
            }
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return preClickTime;
    }

    public synchronized static long getCachedLoadingTime(Context context, AdInfo adInfo) {
        if (context == null || adInfo == null) {
            return 0;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        long loadingTime = 0;
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return 0;
            }
            Cursor c = db.query(AdColumns.TABLE_NAME, new String[]{"*"}, AdColumns.AD_TYPE + "=?" + " AND "
                    + AdColumns.CAMPAIGN_ID + "=?", new String[]{adInfo.type, adInfo.campaignid}, null, null, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                loadingTime = c.getLong(c.getColumnIndex(AdColumns.LOADING_TIME));
            }
            if (c != null) {
                c.close();
            }
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return loadingTime;
    }

    public synchronized static void updateAdToDatabase(Context context, AdInfo adInfo) {
//        L.e("VC--DB-->", "updateAdToDatabase");
        if (context == null || adInfo == null) {
            return;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context.getApplicationContext());
        try {
            SQLiteDatabase db = getWritable(helper);
            if (db == null) {
                return;
            }
            boolean exist = false;
            String sql_sel_pkg = "SELECT  * FROM " + AdColumns.TABLE_NAME + " where " + AdColumns.AD_TYPE + "="
                    + "'" + adInfo.type + "'" + " AND " + AdColumns.CAMPAIGN_ID + "='" + adInfo.campaignid + "'";
//            L.e("VC-->DB-->updateAdToDatabase", "sql_sel_pkg:"+sql_sel_pkg);
            Cursor c = db.rawQuery(sql_sel_pkg, null);
            if (c != null && c.getCount() > 0) {
                exist = true;
            }
            if (c != null) {
                c.close();
            }
            ContentValues values = new ContentValues();
            values.put(AdColumns.CAMPAIGN_ID, adInfo.campaignid);
            values.put(AdColumns.CLK_URL, adInfo.clkurl);
            values.put(AdColumns.DESCRIPTION, adInfo.description);
            values.put(AdColumns.PAY_OUT, adInfo.payout);
            String pkgName = adInfo.pkgname;
            values.put(AdColumns.PKG_NAME, pkgName);
            values.put(AdColumns.TITLE, adInfo.title);
            values.put(AdColumns.ICON, adInfo.icon);
            values.put(AdColumns.CATEGORY, adInfo.appcategory);
            values.put(AdColumns.RATING, adInfo.apprating);
            values.put(AdColumns.REVIEWNUMS, adInfo.appreviewnum);
            values.put(AdColumns.INSTALLS, adInfo.appinstalls);
            values.put(AdColumns.LOADED_CLICK_URL, adInfo.loadedclickurl);
            values.put(AdColumns.IMPRESSION_URL, adInfo.impurls);
            values.put(AdColumns.PRECLICK_TIME, adInfo.preclickTime);
            values.put(AdColumns.NOTICE_URL, adInfo.noticeUrl);
            values.put(AdColumns.CLICK_MODE, adInfo.clickMode);
            values.put(AdColumns.CACHE_TIME, adInfo.cacheTime);
            values.put(AdColumns.APP_SIZE, adInfo.appsize);
            values.put(AdColumns.CONNECTION_TYPE, adInfo.connectiontype);
            values.put(AdColumns.COUNTRIES, adInfo.countries);
            values.put(AdColumns.DEVICE_TYPE, adInfo.devicetype);
            values.put(AdColumns.CLKID, adInfo.clkid);
            values.put(AdColumns.GPURL, adInfo.shareGP);
            values.put(AdColumns.IMAGE_URL, adInfo.imageUrl);
            values.put(AdColumns.VIDEO_URL, adInfo.videoUrl);
            values.put(AdColumns.VIDEO_SIZE, adInfo.videoSize);
            values.put(AdColumns.VIDEO_LENGTH, adInfo.videoLength);
            values.put(AdColumns.VIDEO_RESOLUTION, adInfo.videoResolution);
            values.put(AdColumns.VIDEO_EXPIRE, adInfo.videoExpire);
            values.put(AdColumns.AD_TYPE, adInfo.type);
            values.put(AdColumns.IS_DISPLAY, adInfo.isDisplay);
            long currentTime = System.currentTimeMillis();
//            L.e("VC-->DB-->updateAdToDatabase",  "PKG-NAME:"+adInfo.pkgname+"     "+"currentLoadingTime:"+currentTime);
            values.put(AdColumns.LOADING_TIME, currentTime);

            if (exist) {
                db.update(AdColumns.TABLE_NAME, values, AdColumns.AD_TYPE + "=?" + " AND "
                        + AdColumns.CAMPAIGN_ID + " =? ", new String[]{adInfo.type, adInfo.campaignid});
            } else {
                db.insert(AdColumns.TABLE_NAME, null, values);
            }
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * remove-data
     *
     * @param context
     * @param info
     */
    public synchronized static void removeData(Context context, AdInfo info) {
//        L.e("VC--DB-->removeData", "removeData");
        if (context == null || info == null) {
            return;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        try {
            SQLiteDatabase db = getWritable(helper);
            if (db == null) {
                return;
            }
            db.delete(AdColumns.TABLE_NAME, AdColumns.AD_TYPE + "=?" + " AND "
                    + AdColumns.CAMPAIGN_ID + "=?", new String[]{info.type, info.campaignid});
//            L.e("VC--DB-->removeData", "PKG-NAME:"+info.pkgname+"     "+AdColumns.CAMPAIGN_ID + ":    " + info.campaignid);
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * delete a list about adInfo
     *
     * @param context
     * @param adInfos
     */
    public synchronized static void removeDataCollection(Context context, List<AdInfo> adInfos) {
//        L.e("VC--DB-->", "removeDataCollection");
        if (context == null || adInfos == null) {
//            L.e("VC--DB-->removeDataCollection","no expireTime AD 1");
            return;
        }
        if (adInfos.size() == 0) {
//            L.e("VC--DB-->removeDataCollection","no expireTime AD 2");
            return;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        try {
            SQLiteDatabase db = getWritable(helper);
            if (db == null) {
                return;
            }
            for (AdInfo adInfo : adInfos) {
                db.delete(AdColumns.TABLE_NAME, AdColumns.AD_TYPE + "=?" + " AND "
                        + AdColumns.CAMPAIGN_ID + "=?", new String[]{adInfo.type, adInfo.campaignid});
//                L.e("VC--DB-->removeDataCollection",  "PKG-NAME:"+adInfo.pkgname+"     "+AdColumns.CAMPAIGN_ID + ":    " + adInfo.campaignid);
            }
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private synchronized static SQLiteDatabase getWritable(SQLiteOpenHelper helper) {
        try {
            return helper.getWritableDatabase();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private synchronized static SQLiteDatabase getReadable(SQLiteOpenHelper helper) {
        try {
            return helper.getReadableDatabase();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static List<SubscribeAdInfo> getCacheSubscribeData(Context context, int limit) {
        if (context == null) {
            return null;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        List<SubscribeAdInfo> ret = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadable(helper);
            if (db == null) {
                return null;
            }
            String sql_sel_pkg = "SELECT * FROM " + AdColumns.SUBSCRIBE_TABLE_NAME;
//            L.e("VC-->SQL-->getCacheSubscribeData", sql_sel_pkg);
            Cursor c = db.rawQuery(sql_sel_pkg, null);
            if (c == null) {
                return null;
            }
            c.moveToFirst();
            while (!c.isAfterLast()) {
                SubscribeAdInfo a = new SubscribeAdInfo();
                a.campaignid = c.getString(c.getColumnIndex(AdColumns.CAMPAIGN_ID));
                a.clkurl = c.getString(c.getColumnIndex(AdColumns.CLK_URL));
                a.description = c.getString(c.getColumnIndex(AdColumns.DESCRIPTION));
                a.title = c.getString(c.getColumnIndex(AdColumns.TITLE));
                a.impurls = c.getString(c.getColumnIndex(AdColumns.IMPRESSION_URL));
                a.noticeUrl = c.getString(c.getColumnIndex(AdColumns.NOTICE_URL));
                a.cacheTime = c.getLong(c.getColumnIndex(AdColumns.CACHE_TIME));
                a.countries = c.getString(c.getColumnIndex(AdColumns.COUNTRIES));
                a.imageUrl = c.getString(c.getColumnIndex(AdColumns.IMAGE_URL));
                a.carrier = c.getString(c.getColumnIndex(AdColumns.CARRIER));
                a.kpi = c.getString(c.getColumnIndex(AdColumns.KPI));
                a.incent = c.getString(c.getColumnIndex(AdColumns.INCENT));

                ret.add(a);
                c.moveToNext();
                if (limit != -1 && limit == ret.size()) {
                    break;
                }
            }
            db.close();
            c.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Log.e(LogUtil.TAG,"cache ret: " + ret.size());
        return ret;
    }

    public synchronized static void updateSubscribeAdToDatabase(Context context, SubscribeAdInfo adInfo) {
        if (context == null || adInfo == null) {
            return;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        try {
            SQLiteDatabase db = getWritable(helper);
            if (db == null) {
                return;
            }
            boolean exist = false;
            String sql_sel_pkg = "SELECT  * FROM " + AdColumns.SUBSCRIBE_TABLE_NAME + " where "
                    + AdColumns.CAMPAIGN_ID + "='" + adInfo.campaignid + "'";
//            L.e("VC-->SQL-->updateSubscribeAdToDatabase", sql_sel_pkg);
            Cursor c = db.rawQuery(sql_sel_pkg, null);
            if (c != null && c.getCount() > 0) {
                exist = true;
            }
            if (c != null) {
                c.close();
            }
            ContentValues values = new ContentValues();
            values.put(AdColumns.CAMPAIGN_ID, adInfo.campaignid);
            values.put(AdColumns.CLK_URL, adInfo.clkurl);
            values.put(AdColumns.DESCRIPTION, adInfo.description);
            values.put(AdColumns.TITLE, adInfo.title);
            values.put(AdColumns.IMPRESSION_URL, adInfo.impurls);
            values.put(AdColumns.NOTICE_URL, adInfo.noticeUrl);
            values.put(AdColumns.CACHE_TIME, adInfo.cacheTime);
            values.put(AdColumns.COUNTRIES, adInfo.countries);
            values.put(AdColumns.IMAGE_URL, adInfo.imageUrl);
            values.put(AdColumns.CARRIER, adInfo.carrier);
            values.put(AdColumns.KPI, adInfo.kpi);
            values.put(AdColumns.INCENT, adInfo.incent);

            if (exist) {
                db.update(AdColumns.SUBSCRIBE_TABLE_NAME, values, AdColumns.CAMPAIGN_ID + " =? ", new String[]{adInfo.campaignid});
            } else {
                db.insert(AdColumns.SUBSCRIBE_TABLE_NAME, null, values);
            }
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * remove-data
     *
     * @param context
     * @param info
     */
    public synchronized static void removeSubscribeData(Context context, SubscribeAdInfo info) {
        if (context == null || info == null) {
            return;
        }
        AvDatabaseHelper helper = AvDatabaseHelper.getInstance(context);
        try {
            SQLiteDatabase db = getWritable(helper);
            if (db == null) {
                return;
            }
            db.delete(AdColumns.SUBSCRIBE_TABLE_NAME, AdColumns.CAMPAIGN_ID + "=?", new String[]{info.campaignid});
            db.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static boolean contains(List<AdInfo> list, AdInfo info) {
        if (list == null || info == null) {
            return false;
        }
        for (AdInfo ad : list) {
            if (ad.campaignid.equals(info.campaignid) || ad.pkgname.equals(info.pkgname)) {
                return true;
            }
        }
        return false;
    }
}
