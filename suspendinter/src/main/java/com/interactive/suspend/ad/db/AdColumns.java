package com.interactive.suspend.ad.db;

import android.provider.BaseColumns;

/**
 * Created by csc on 15/11/4.
 */
public class AdColumns implements BaseColumns {
    public static final String TABLE_NAME = "adinfos";
    public static final String SUBSCRIBE_TABLE_NAME = "subscribe_adinfos";

    public static final String CAMPAIGN_ID = "campaign_id";
    public static final String PAY_OUT = "pay_out";
    public static final String PKG_NAME = "pkg_name";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String CLK_URL = "clk_url";
    public static final String ICON = "icon";
    public static final String CATEGORY = "category";
    public static final String RATING = "rating";
    public static final String REVIEWNUMS = "reviewnums";
    public static final String INSTALLS = "installs";
    public static final String LOADED_CLICK_URL = "loaded_click_url";
    public static final String IMPRESSION_URL = "impression_url";
    public static final String PRECLICK_TIME = "preclick_time";
    public static final String NOTICE_URL = "notice_url";
    public static final String CLICK_MODE = "click_mode";
    public static final String CACHE_TIME = "cache_time";
    public static final String APP_SIZE = "app_size";
    public static final String CONNECTION_TYPE = "connection_type";
    public static final String COUNTRIES = "countries";
    public static final String DEVICE_TYPE = "device_type";
    public static final String CLKID = "clkid";
    public static final String GPURL = "gpurl";
    public static final String IMAGE_URL = "image_url";
    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_SIZE = "video_size";
    public static final String VIDEO_LENGTH = "video_length";
    public static final String VIDEO_RESOLUTION = "video_resolution";
    public static final String VIDEO_EXPIRE = "video_expire";
    public static final String AD_TYPE = "ad_type";
    public static final String IS_DISPLAY = "is_display";

    public static final String LOADING_TIME = "loading_time";

    // special for subscribe ad
    public static final String CARRIER = "carrier";
    public static final String KPI = "kpi";
    public static final String INCENT = "incent";


    public static final String CREATE =
            "CREATE TABLE IF NOT EXISTS " + AdColumns.TABLE_NAME
                    +" ( _id INTEGER PRIMARY KEY, "
                    +AdColumns.CAMPAIGN_ID + " TEXT ,"
                    +AdColumns.PAY_OUT + " TEXT ,"
                    +AdColumns.PKG_NAME + " TEXT ,"
                    +AdColumns.TITLE + " TEXT ,"
                    +AdColumns.DESCRIPTION + " TEXT ,"
                    +AdColumns.CLK_URL + " TEXT ,"
                    +AdColumns.ICON + " TEXT ,"
                    +AdColumns.CATEGORY + " TEXT ,"
                    +AdColumns.RATING + " TEXT ,"
                    +AdColumns.REVIEWNUMS + " TEXT ,"
                    +AdColumns.INSTALLS + " TEXT ,"
                    +AdColumns.LOADED_CLICK_URL + " TEXT, "
                    +AdColumns.IMPRESSION_URL + " TEXT ,"
                    +AdColumns.PRECLICK_TIME + " INTEGER ,"
                    +AdColumns.NOTICE_URL + " TEXT ,"
                    +AdColumns.CLICK_MODE + " INTEGER ,"
                    +AdColumns.CACHE_TIME + " INTEGER ,"
                    +AdColumns.APP_SIZE + " TEXT ,"
                    +AdColumns.CONNECTION_TYPE + " TEXT ,"
                    +AdColumns.COUNTRIES + " TEXT ,"
                    +AdColumns.DEVICE_TYPE + " TEXT ,"
                    +AdColumns.CLKID + " TEXT ,"
                    +AdColumns.GPURL + " TEXT ,"
                    +AdColumns.IMAGE_URL + " TEXT ,"
                    +AdColumns.VIDEO_URL + " TEXT ,"
                    +AdColumns.VIDEO_SIZE + " TEXT ,"
                    +AdColumns.VIDEO_LENGTH + " TEXT ,"
                    +AdColumns.VIDEO_RESOLUTION + " TEXT ,"
                    +AdColumns.VIDEO_EXPIRE + " INTEGER ,"
                    +AdColumns.AD_TYPE + " TEXT ,"
                    +AdColumns.IS_DISPLAY + " INTEGER ,"
                    +AdColumns.LOADING_TIME + " INTEGER  "
                    +");";

    public static final String CREATE_SUBSCRIBE =
            "CREATE TABLE IF NOT EXISTS " + AdColumns.SUBSCRIBE_TABLE_NAME
                    + " ( _id INTEGER PRIMARY KEY, "
                    + AdColumns.CAMPAIGN_ID + " TEXT ,"
                    + AdColumns.TITLE + " TEXT ,"
                    + AdColumns.DESCRIPTION + " TEXT ,"
                    + AdColumns.CLK_URL + " TEXT ,"
                    + AdColumns.IMPRESSION_URL + " TEXT ,"
                    + AdColumns.NOTICE_URL + " TEXT ,"
                    + AdColumns.CACHE_TIME + " INTEGER ,"
                    + AdColumns.COUNTRIES + " TEXT ,"
                    + AdColumns.IMAGE_URL + " TEXT ,"
                    + AdColumns.CARRIER + " TEXT ,"
                    + AdColumns.KPI + " TEXT ,"
                    + AdColumns.INCENT + " TEXT "
                    + ");";

    public static final String DELETE = "DROP TABLE IF EXISTS " + AdColumns.TABLE_NAME;
    public static final String DELETE_SUBSCRIBE = "DROP TABLE IF EXISTS " + AdColumns.SUBSCRIBE_TABLE_NAME;

    public static final String INSERT_TABLE_NAME_LOADING_TIME = "ALTER TABLE "+ AdColumns.TABLE_NAME +" add " + AdColumns.LOADING_TIME + " INTEGER ";
}
