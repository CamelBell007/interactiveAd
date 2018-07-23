package com.interactive.suspend.ad.model;

import java.util.List;

/**
 * Created by drason on 2018/7/20.
 */

public class AdConfigBean {

    /**
     * version : 2018061409550115630924
     * app_wall_status : false
     * refresh_time : 3600000
     * appwall_appkey : ff83i709765432b
     * k_status : true
     * k_id : 497612
     * i_status : true
     * i_id : com.hot.new
     * i_key : 5af2c5f0adae5754f51325c0
     * InterAd_Config : {"enable":false,"interval_time":14400000,"max_show_time":2,"inter_flow":[{"inter_platform":"facebook","inter_wight":0,"inter_slotId":""},{"inter_platform":"admob","inter_wight":0,"inter_slotId":""},{"inter_platform":"mopub","inter_wight":0,"inter_slotId":""}]}
     * ADSlot_Config : [{"slot_id":"100028","slot_name":"hot_native1","open_status":true,"flow":[[{"platform":"appwall","open_status":true,"type":"native","key":"9f734fdj765ed2b"}]]}]
     */

    private String version;
    private long refresh_time;
    private List<ADSlotConfigBean> ADSlot_Config;
    private boolean appwall_status;
    private String appwall_key;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getRefresh_time() {
        return refresh_time;
    }

    public void setRefresh_time(long refresh_time) {
        this.refresh_time = refresh_time;
    }

    public List<ADSlotConfigBean> getADSlot_Config() {
        return ADSlot_Config;
    }

    public void setADSlot_Config(List<ADSlotConfigBean> ADSlot_Config) {
        this.ADSlot_Config = ADSlot_Config;
    }

    public boolean isAppwall_status() {
        return appwall_status;
    }

    public void setAppwall_status(boolean appwall_status) {
        this.appwall_status = appwall_status;
    }

    public String getAppwall_key() {
        return appwall_key;
    }

    public void setAppwall_key(String appwall_key) {
        this.appwall_key = appwall_key;
    }

    public static class ADSlotConfigBean {
        /**
         * slot_id : 100028
         * slot_name : hot_native1
         * open_status : true
         * flow : [[{"platform":"appwall","open_status":true,"type":"native","key":"9f734fdj765ed2b"}]]
         */

        private String slot_id;
        private String slot_name;
        private boolean open_status;
        private List<List<FlowBean>> flow;

        public String getSlot_id() {
            return slot_id;
        }

        public void setSlot_id(String slot_id) {
            this.slot_id = slot_id;
        }

        public String getSlot_name() {
            return slot_name;
        }

        public void setSlot_name(String slot_name) {
            this.slot_name = slot_name;
        }

        public boolean isOpen_status() {
            return open_status;
        }

        public void setOpen_status(boolean open_status) {
            this.open_status = open_status;
        }

        public List<List<FlowBean>> getFlow() {
            return flow;
        }

        public void setFlow(List<List<FlowBean>> flow) {
            this.flow = flow;
        }

        public static class FlowBean {
            /**
             * platform : appwall
             * open_status : true
             * type : native
             * key : 9f734fdj765ed2b
             */

            private String platform;
            private boolean open_status;
            private String type;
            private String key;

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public boolean isOpen_status() {
                return open_status;
            }

            public void setOpen_status(boolean open_status) {
                this.open_status = open_status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }

    @Override
    public String toString() {
        return "version:  " + version + ", slotname: " + ADSlot_Config.get(0).slot_name;
    }
}
