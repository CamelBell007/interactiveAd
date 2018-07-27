package com.interactive.suspend.ad.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * APX相对应当前应用的功能配置包含广告配置
 */
public class FetchAppConfigResult {

    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public AppConfig appconfig;

    public static final class AppConfig {
        @SerializedName("ad_units")
        public AdUnits adUnits;
        @SerializedName("config")
        public BackendConfig backendConfig;
        @SerializedName("version")
        public String version;
    }

    public static final class BackendConfig {
        @SerializedName("sdk_config")
        public SdkConfig sdkConfig;
    }

    public static final class DKConfig {
        @SerializedName("dkad_id")
        public String id;
        @SerializedName("dkad_source")
        public String source;
        @SerializedName("dkad_cache_time")
        public long cacheTime;
        @SerializedName("dkad_priority")
        public int priority;
    }

    public static final class SdkConfig {
        @SerializedName("ad_count_limit")
        public int adCountLimit;
        @SerializedName("ad_valid_time")
        public long adValidTime;
        @SerializedName("alarm_allow")
        public boolean alarmAllow;
        @SerializedName("alarm_interval")
        public long alarmInterval;
        @SerializedName("network_switch_allow")
        public boolean networkSwithAllow;
        @SerializedName("wifi_allow")
        public boolean wifiAllow;
        @SerializedName("wifi_interval")
        public long wifiInterval;
        @SerializedName("mobile_allow")
        public boolean mobileAllow;
        @SerializedName("mobile_interval")
        public long mobileInterval;
        @SerializedName("gpurl_retry_allow")
        public boolean gpurlRetryAllow;
        @SerializedName("gpurl_max_retry")
        public int maxGpUrlRetry;
        @SerializedName("gpurl_valid_time")
        public long gpurlValidTime;
        @SerializedName("notice_retry_allow")
        public boolean noticeRetryAllow;
        @SerializedName("notice_max_retry")
        public int maxNoticeRetry;
        @SerializedName("notice_valid_time")
        public long noticeValidTime;
        @SerializedName("max_jump_count")
        public int maxJumpCount;
        @SerializedName("max_timeout")
        public long maxTimeout;
        @SerializedName("callbackTimeout")
        public long callbackTimeout;
        @SerializedName("allow_notice_analytics")
        public boolean noticeAnalyticsAllow;
        @SerializedName("allow_installed_pkg")
        public boolean installedPkgAllow;
        @SerializedName("gp_share_allow")
        public boolean allowShareGP;
        @SerializedName("gp_access_allow")
        public boolean allowAccessGP;
        @SerializedName("first_stage_time")
        public long firstStageTime;
        @SerializedName("first_stage_interval")
        public long firstStageInterval;
        @SerializedName("second_stage_time")
        public long secondStageTime;
        @SerializedName("second_stage_interval")
        public long secondStageInterval;
        @SerializedName("third_stage_time")
        public long thirdStageTime;
        @SerializedName("third_stage_interval")
        public long thirdStageInterval;
        @SerializedName("allow_remote_ad")
        public boolean allowRemoteAd;
        @SerializedName("refdelay")
        public long refDelay;
        @SerializedName("refmode")
        public int refMode;
        @SerializedName("appwall_tabfilter")
        public String tabFilter;
        @SerializedName("rf")
        public String rf;
        @SerializedName("rfb")
        public String rfb;

        @SerializedName("apk_start_t")
        public int apk_start_t;
        @SerializedName("apk_interval_t")
        public int apk_interval_t;
        @SerializedName("apk_need_c")
        public int apk_need_c;
        @SerializedName("apk_need_s")
        public boolean apk_need_s;

        @SerializedName("download_files_path")
        public String downloadFilesPath;
        @SerializedName("download_cache_path")
        public String downloadCachePath;
        @SerializedName("download_poll_time")
        public int downloadPollTime;

        @SerializedName("appwall_dk_config")
        public List<DKConfig> dkConfig;
    }

    public static class AdUnits {
        @SerializedName("natives")
        public List<NativeUnit> nativeUnits;
        @SerializedName("rewards")
        public List<RewardedVideoUnit> rewardedVideoAdUnits;
        @SerializedName("appwalls")
        public List<AppWallUnit> appwallUnits;
        @SerializedName("smarts")
        public List<SmartUnit> smartUnits;
    }

    public static class NativeUnit {
        @SerializedName("unit_id")
        public String unitId;
        @SerializedName("unit_name")
        public String unitName;
        @SerializedName("style")
        public int style;
        @SerializedName("carousel_allow")
        public boolean carouselAllow;
        @SerializedName("ad_type")
        public int adType;
        @SerializedName("optin_rate")
        public int optinRate;
        @SerializedName("video_allow")
        public boolean videoAllow;
        @SerializedName("refresh_time")
        public int refreshTime;
        @SerializedName("ad_networks")
        public List<AdNetwork> adNetworks;
    }

    public static class RewardedVideoUnit {
        @SerializedName("unit_id")
        public String unitId;
        @SerializedName("unit_name")
        public String unitName;
        @SerializedName("reward_item")
        public String rewardItem;
        @SerializedName("reward_amount")
        public int rewardAmount;
        @SerializedName("ad_networks")
        public List<AdNetwork> adNetworks;
    }

    public static class AppWallUnit {
        @SerializedName("unit_id")
        public String unitId;
        @SerializedName("unit_name")
        public String unitName;
        @SerializedName("ad_networks")
        public List<AdNetwork> adNetworks;
    }

    public static class SmartUnit {
        @SerializedName("unit_id")
        public String unitId;
        @SerializedName("unit_name")
        public String unitName;
        @SerializedName("ad_networks")
        public List<AdNetwork> adNetworks;
    }

    public static class AdNetwork {
        @SerializedName("platform")
        public String platform;
        @SerializedName("key")
        public String key;
        @SerializedName("open")
        public boolean open;

        public AdNetwork(String platform, String key, boolean open) {
            this.platform = platform;
            this.key = key;
            this.open = open;
        }
    }

    public static boolean isFailed(FetchAppConfigResult result) {
        if (result == null || result.appconfig == null || result.appconfig.adUnits == null || !"OK".equals(result.status)) {
            return true;
        }
        return false;
    }

    public static boolean isLast(FetchAppConfigResult result) {
        if (result != null && result.message != null && result.message.equals("NotModified")) {
            return true;
        }
        return false;
    }
}
