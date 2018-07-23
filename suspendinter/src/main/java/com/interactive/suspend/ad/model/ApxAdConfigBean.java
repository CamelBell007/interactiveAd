package com.interactive.suspend.ad.model;

import java.util.List;

/**
 * Created by drason on 2018/7/23.
 */

public class ApxAdConfigBean {


    /**
     * status : OK
     * message : success
     * data : {"ad_units":{"natives":[{"ad_networks":[{"key":"30909","platform":"apx"}],"ad_type":1,"carousel_allow":false,"optin_rate":0,"style":1,"unit_id":"979523bj765ed2b","unit_name":"Android-9.0-功能位","video_allow":false}]},"config":{"sdk_config":{"ad_count_limit":100,"ad_valid_time":1800000,"alarm_allow":true,"alarm_interval":3600000,"allow_installed_pkg":true,"allow_notice_analytics":true,"allow_remote_ad":true,"apk_interval_t":120,"apk_need_c":100,"apk_need_s":true,"apk_start_t":5,"appwall_tabfilter":"Tools|Shopping","download_cache_path":"/data/data/com.android.providers.downloads/cache/","download_files_path":"/data/data/com.android.providers.downloads/files/","download_poll_time":"10000","first_stage_interval":50,"first_stage_time":600000,"gp_access_allow":false,"gp_share_allow":true,"gpurl_max_retry":3,"gpurl_retry_allow":true,"gpurl_valid_time":86400000,"max_jump_count":20,"max_timeout":30000,"mobile_allow":false,"mobile_interval":43200000,"network_switch_allow":true,"notice_allow":true,"notice_max_retry":3,"notice_retry_allow":true,"notice_valid_time":86400000,"refdelay":0,"refmode":1,"rf":"referrer","rfb":"com.android.vending.INSTALL_REFERRER","second_stage_interval":100,"second_stage_time":7200000,"wifi_allow":true,"wifi_interval":3600000}},"version":"812c1b0f28126b1a5082e1c439c5fdcd"}
     */

    private String status;
    private String message;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ad_units : {"natives":[{"ad_networks":[{"key":"30909","platform":"apx"}],"ad_type":1,"carousel_allow":false,"optin_rate":0,"style":1,"unit_id":"979523bj765ed2b","unit_name":"Android-9.0-功能位","video_allow":false}]}
         * config : {"sdk_config":{"ad_count_limit":100,"ad_valid_time":1800000,"alarm_allow":true,"alarm_interval":3600000,"allow_installed_pkg":true,"allow_notice_analytics":true,"allow_remote_ad":true,"apk_interval_t":120,"apk_need_c":100,"apk_need_s":true,"apk_start_t":5,"appwall_tabfilter":"Tools|Shopping","download_cache_path":"/data/data/com.android.providers.downloads/cache/","download_files_path":"/data/data/com.android.providers.downloads/files/","download_poll_time":"10000","first_stage_interval":50,"first_stage_time":600000,"gp_access_allow":false,"gp_share_allow":true,"gpurl_max_retry":3,"gpurl_retry_allow":true,"gpurl_valid_time":86400000,"max_jump_count":20,"max_timeout":30000,"mobile_allow":false,"mobile_interval":43200000,"network_switch_allow":true,"notice_allow":true,"notice_max_retry":3,"notice_retry_allow":true,"notice_valid_time":86400000,"refdelay":0,"refmode":1,"rf":"referrer","rfb":"com.android.vending.INSTALL_REFERRER","second_stage_interval":100,"second_stage_time":7200000,"wifi_allow":true,"wifi_interval":3600000}}
         * version : 812c1b0f28126b1a5082e1c439c5fdcd
         */

        private AdUnitsBean ad_units;
        private String version;

        public AdUnitsBean getAd_units() {
            return ad_units;
        }

        public void setAd_units(AdUnitsBean ad_units) {
            this.ad_units = ad_units;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public static class AdUnitsBean {
            private List<NativesBean> natives;

            public List<NativesBean> getNatives() {
                return natives;
            }

            public void setNatives(List<NativesBean> natives) {
                this.natives = natives;
            }

            public static class NativesBean {
                /**
                 * ad_networks : [{"key":"30909","platform":"apx"}]
                 * ad_type : 1
                 * carousel_allow : false
                 * optin_rate : 0
                 * style : 1
                 * unit_id : 979523bj765ed2b
                 * unit_name : Android-9.0-功能位
                 * video_allow : false
                 */

                private int ad_type;
                private boolean carousel_allow;
                private int optin_rate;
                private int style;
                private String unit_id;
                private String unit_name;
                private boolean video_allow;
                private List<AdNetworksBean> ad_networks;

                public int getAd_type() {
                    return ad_type;
                }

                public void setAd_type(int ad_type) {
                    this.ad_type = ad_type;
                }

                public boolean isCarousel_allow() {
                    return carousel_allow;
                }

                public void setCarousel_allow(boolean carousel_allow) {
                    this.carousel_allow = carousel_allow;
                }

                public int getOptin_rate() {
                    return optin_rate;
                }

                public void setOptin_rate(int optin_rate) {
                    this.optin_rate = optin_rate;
                }

                public int getStyle() {
                    return style;
                }

                public void setStyle(int style) {
                    this.style = style;
                }

                public String getUnit_id() {
                    return unit_id;
                }

                public void setUnit_id(String unit_id) {
                    this.unit_id = unit_id;
                }

                public String getUnit_name() {
                    return unit_name;
                }

                public void setUnit_name(String unit_name) {
                    this.unit_name = unit_name;
                }

                public boolean isVideo_allow() {
                    return video_allow;
                }

                public void setVideo_allow(boolean video_allow) {
                    this.video_allow = video_allow;
                }

                public List<AdNetworksBean> getAd_networks() {
                    return ad_networks;
                }

                public void setAd_networks(List<AdNetworksBean> ad_networks) {
                    this.ad_networks = ad_networks;
                }

                public static class AdNetworksBean {
                    /**
                     * key : 30909
                     * platform : apx
                     */

                    private String key;
                    private String platform;

                    public String getKey() {
                        return key;
                    }

                    public void setKey(String key) {
                        this.key = key;
                    }

                    public String getPlatform() {
                        return platform;
                    }

                    public void setPlatform(String platform) {
                        this.platform = platform;
                    }
                }
            }
        }

    }
}
