package com.interactive.suspend.ad.constant;

import com.interactive.suspend.ad.BuildConfig;
import com.interactive.suspend.ad.util.LogUtil;

public class Constants
{
  public static final int DEFAULT_WAIT_TIME = 3000;
  public static final int DEFAULT_INIT_TIMESPAN = 600000;
  public static final int DEFAULT_BLACKBOX_MAZSIZE = Integer.MAX_VALUE;
  public static final int DEFAULT_BLACKBOX_MINSIZE = 5120;
  public static final boolean DEFAULT_SKIP_GPS = false;
  public static final boolean DEFAULT_KILL_DEBUGGER = false;
  public static final boolean DEFAULT_ALWAYS_DEMOTION = false;
  public static final String DEFAULT_PARTNER_CODE = null;
  public static final String DEFAULT_DOUBLE_URL = null;
  public static final String DEFAULT_PROXY_URL = null;
  public static final String DEFAULT_CUST_PROCESS = null;
  public static final String DEFAULT_ENV_TYPE = null;
  public static final String OS = "Android";
  public static final String VERSION = "3.0.7";
  public static final String CORE_NAME = "fm-core-db-3.0.7";
  public static final byte[] CLASSES_DEX = { 100, 98, 95, 102, 109, 46, 106, 97, 114 };
  public static final byte[] CLASS_NAME = { 99, 110, 46, 116, 111, 110, 103, 100, 117, 110, 46, 97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 114, 101, 46, 100, 98, 46, 70, 77, 67, 111, 114, 101 };

  public static final String SDK_VERSION = BuildConfig.VERSION_NAME;
  public static final int MAX_ADVANCED_NATIVE_ADS = 100;
  public static boolean SUBSCRIPTION_AD_DEBUG = false;
  public static final boolean DEBUG_LOG = true; // /sdcard/log.debug

  public static final boolean DEBUG = LogUtil.isDebugMode(); // /sdcard/test.debug

  public static final class NativeAdType {
    public static final int NATIVE_AD_TYPE_INSTALL = 1 << 0; // 0001
    public static final int NATIVE_AD_TYPE_SUBSCRIBE = 1 << 1; // 0010
  }

  public static final class NativeAdStyle {
    public static final int SMALL = 1;
    public static final int MEDIUM = 2;
    public static final int LARGE = 3;
    public static final int CAROUSEL = 4;
    public static final int SUBSCRIBE = 5;
  }

  public static final class ApxAdType {
    public static final String UNKOWN = "unkown";
    public static final String APPWALL = "appwall";
    public static final String NATIVE = "native";
    public static final String SUBSCRIBE = "subscribe";
    public static final String REWARD = "reward";
    public static final String PLAYABLE = "playable";
    public static final String SMART = "smart";
  }

  public static final class NativeAdSource {
    public static final String AD_SOURCE_APX = "apx";
    public static final String AD_SOURCE_APX_INSTALL = "apx_install";
    public static final String AD_SOURCE_APX_CONTENT = "apx_content";
  }

  public static final class RefMode {
    public static final int SEND_ALL = 0; // send all refers
    public static final int SEND_FAST = 1; // send the fastest one
  }

  public static final class AdNetworks {
    public static final String APX = NativeAdSource.AD_SOURCE_APX;
    public static final String APX_H5 = "apx_h5";
  }

  public static class Preference {
    public static final String PREF_NAME = "sdk_preference";
    public static final String USER_AGENT = "user_agent";
    public static final String SOURCE_ID = "source_id";
    public static final String CORE_SOURCE_ID = "core_source_id";
    public static final String MARKET_SOURCE_ID = "market_source_id";
    public static final String NATIVE_SOURCE_ID = "native_source_id";
    public static final String REWARD_SOURCE_ID = "reward_source_id";
    public static final String PLAYABLE_SOURCE_ID = "playable_source_id";
    public static final String SMART_SOURCE_ID = "smart_source_id";
    public static final String APP_ID = "app_id";
    public static final String GAID = "gaid";
    public static final String BACKUP_GAID = "backup_gaid"; // if can't get gaid via normal way, use this for backup
    public static final String NEWS_SOURCE_ID = "news_source_id";
    public static final String ANDROID_ID = "android_id";
    public static final String GA_ID = "google_advertizing_id";
    public static final String DEFAULT_UA = "Mozilla/5.0 (Linux; Android 5.1; Nexus 5 Build/LMY47I) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Version/4.0 Chrome/40.0.0.0 Mobile Safari/537.36";
    public static final String MARKET_URL = "market_url";
    public static final String DEX_VERSION = "dex_version";
    public static final String LASTEST_USED_DEX_VERSION_CODE = "lastest_used_dex_version_code";
    public static final String PICTURE_VERSION = "picture_version";
    public static final String LAST_DOWNLOAD_RESOURCE_SUCCESS = "last_download_resource_success";
    public static final String LOCAL_PICTURE_RESOURCE = "local_picture_resource";
    public static final String LAST_DOWNLOAD_RESOURCE_TASK_SUCCESS_TIME = "download_resource_task_last_success";
    public static final String LAST_GET_APPWALL_TASK_SUCCESS_TIME = "last_get_appwall_task_success_time";
    public static final String LAST_GET_NATIVE_TASK_SUCCESS_TIME = "last_get_native_task_success_time";
    public static final String LAST_GET_REWARD_TASK_SUCCESS_TIME = "last_get_reward_task_success_time";
    public static final String LAST_GET_PLAYABLE_TASK_SUCCESS_TIME = "last_get_playable_task_success_time";
    public static final String LAST_GET_SMART_TASK_SUCCESS_TIME = "last_get_smart_task_success_time";
    public static final String LAST_GET_SUBSCRIBE_TASK_SUCCESS_TIME = "last_get_subscribe_task_success_time";
    public static final String LAST_GET_APP_CONFIG_TASK_SUCCESS_TIME = "last_get_app_config_task_success_time";
    public static final String CUR_SDK_VERSION = "cur_sdk_version";
    public static final String LAST_GET_FACEBOOK_AD_SUCCESS_TIME = "last_get_facebook_ad_success_time";
    public static final String LAST_PRECLICK_TIME = "last_preclick_time";
    public static final String JUMP_TO_MARKET = "jump_to_market";
    public static final String UPDATE_MARKET_RESOURCE = "update_market_resource";
    public static final String UPDATE_NEWS_RESOURCE = "update_news_resource";
    public static final String MARKET_BIG_NATIVE_AD_TYPE = "market_native_ad_type";
    public static final String ENBALE_FACEBOOK_AD_IN_NEWS = "enable_facebook_ad_in_news";
    public static final String MARKET_ADMOB_UNIT_ID = "market_google_unit_id";
    public static final String MARKET_VK_SLOT_ID = "market_vk_slot_id";
    public static final String MARKET_FACEBOOK_PLACEMENT_ID = "market_facebook_placement_id";
    public static final String NEWS_FEED_FB_NATIVE_PLACEMENT_ID = "news_feed_fb_native_placement_id";
    public static final String NEWS_FEED_FB_BANNER_PLACEMENT_ID = "news_feed_fb_banner_placement_id";
    public static final String NEWS_FEED_FB_INTERTITIAL_PLACEMENT_ID = "news_feed_fb_intertitial_placement_id";
    public static final String APP_MARKET_NAME = "market";
    public static final String IS_SHORT_CUT_CREATED = "is_shortcut_createdt";
    public static final String TYPE_APP_MARKET = "type_app_market";
    public static final String TYPE_NEWS_FEED = "type_news_feed";
    public static final String LAST_UPLOAD_IMPRESSION_SUCCESS_TIME = "last_upload_impression_success_time";
    public static final String IS_UPLOAD_MARKET_IMPRESSION_GRANTED = "is_upload_market_impression_granted";
    public static final String IS_UPLOAD_NEWS_IMPRESSION_GRANTED = "is_upload_news_impression_granted";
    public static final String FRAGMENT_MODE = "fragment_mode";
    public static final String NEWSFEED_FRAGMENT_MODE = "newsfeed_fragment_mode";
    public static final String NOTICE_INFO_PREF_NAME = "notice_preference";
    public static final String GPURL_INFO_PREF_NAME = "gprul_preference";
    public static final String KEY_NOTICE_INFOS = "notice_infos";
    public static final String KEY_GPURL_INFOS = "gpurl_infos";
    public static final String PACKAGE_HIT = "package_hit";
    public static final String REFER_SENT = "rf_sent";
    public static final String APP_CONFIG_VERSION = "app_config_version";
    public static final String TABFILTER = "tabfilter";
    public static final String DEFAULT_TABFILTER = "Tools|Shopping";
    //        public static final String ADSDK_COMMON_PACKAGE_NAME = "nativesdk.ad.adsdk";
//        public static final String ADSDK_CORE_PACKAGE_NAME = "nativesdk.ad.adsdkcore";
    public static final String FB_TEST_DEVICE_IDS = "fb_test_device_ids";
    public static final String ADMOB_TEST_DEVICE_ID = "admob_test_device_id";
    public static final String DOWNLOADING_APK_SIZE = "downloading_apk_size";
    public static final String PAKAGES_WITH_SAME_SIZE = "pkg_with_same_size";

    public static final String ARRKII_STATE = "ak_state";
    public static final String ARRKII_APP_Id = "ak_app_id";
    public static final String ARRKII_PUB_Id = "ak_pub_id";
    public static final String DEFAULT_ARRKII_APP_ID = "729";
    public static final String DEFAULT_ARRKII_PUB_ID = "9931";
    //        public static final String DEFAULT_ARRKII_APP_ID = "784"; // test1
//        public static final String DEFAULT_ARRKII_PUB_ID = "9931"; // test1
//        public static final String DEFAULT_ARRKII_APP_ID = "785"; // test2
//        public static final String DEFAULT_ARRKII_PUB_ID = "9931"; // test2
    public static final String IS_AGREE_PERMISSION = "is_agree_permission";
    public static final String UUID = "store_uuid";
  }

  public static final class ImpCheck {
    public static final int VISIBLE_ADVIEW = 0;
    public static final int EMPTY_ADVIEW = 1;
    public static final int NO_PARENT = 2;
    public static final int WINDOW_INVISIBLE = 3;
    public static final int ADVIEW_INVISIBLE = 4;
    public static final int TOO_TRANSPARENT = 5;
    public static final int NO_LOCATION_ON_SCREEN = 6;
    public static final int VISIBLE_AREA_TOO_SMALL = 7;
    public static final int OBSTRUCTED_BY_KEYGUARD = 8;
    public static final int INVALID_DIMENSIONS = 9;
  }

}
