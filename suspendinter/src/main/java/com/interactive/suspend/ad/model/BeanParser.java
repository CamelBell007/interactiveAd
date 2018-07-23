package com.interactive.suspend.ad.model;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drason on 2018/7/20.
 */

public class BeanParser {

    public static synchronized AdConfigBean parseAdConfig(String json) {
        if (TextUtils.isEmpty(json)) return null;
        try {
            AdConfigBean adConfigBean = new AdConfigBean();
            JSONObject configBeanObject = new JSONObject(json);
            String version = configBeanObject.optString("version");
            adConfigBean.setVersion(version);
            long refresh_time = configBeanObject.optLong("refresh_time");
            adConfigBean.setRefresh_time(refresh_time);
            boolean appWallStatus = configBeanObject.optBoolean("app_wall_status");
            adConfigBean.setAppwall_status(appWallStatus);
            String appwallAppkey = configBeanObject.optString("appwall_appkey");
            adConfigBean.setAppwall_key(appwallAppkey);
            List<AdConfigBean.ADSlotConfigBean> slotConfigBeanList = new ArrayList<>();
            JSONArray adSlotConfig = configBeanObject.optJSONArray("ADSlot_Config");
            if (adSlotConfig != null) {
                for (int i = 0; i < adSlotConfig.length(); i++) {
                    JSONObject slotConfigJson = (JSONObject) adSlotConfig.get(i);
                    if (null != slotConfigJson) {
                        AdConfigBean.ADSlotConfigBean slotConfigBean = parseSlot(slotConfigJson);
                        slotConfigBeanList.add(slotConfigBean);
                    }
                }
            }
            adConfigBean.setADSlot_Config(slotConfigBeanList);
            return adConfigBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized ApxAdConfigBean parseApxConfigBean(String json) {
        try {
            ApxAdConfigBean apxAdConfigBean = new ApxAdConfigBean();
            JSONObject apxBeanO = new JSONObject(json);
            String status = apxBeanO.optString("status");
            apxAdConfigBean.setStatus(status);
            String message = apxBeanO.optString("message");
            apxAdConfigBean.setMessage(message);
            JSONObject dataBeanO = apxBeanO.optJSONObject("data");
            if (dataBeanO != null) {
                ApxAdConfigBean.DataBean dataBean = parseDataBean(dataBeanO);
                apxAdConfigBean.setData(dataBean);
            }
            return apxAdConfigBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    private static synchronized AdConfigBean.ADSlotConfigBean parseSlot(JSONObject jsonObject) {
        try {
            if (jsonObject == null) return null;
            AdConfigBean.ADSlotConfigBean slotConfigBean = new AdConfigBean.ADSlotConfigBean();
            String slotId = jsonObject.optString("slot_id");
            slotConfigBean.setSlot_id(slotId);
            String slotName = jsonObject.optString("slot_name");
            slotConfigBean.setSlot_name(slotName);
            boolean openStatus = jsonObject.optBoolean("open_status");
            slotConfigBean.setOpen_status(openStatus);
            List<List<AdConfigBean.ADSlotConfigBean.FlowBean>> list = new ArrayList<>();
            JSONArray flowJson = jsonObject.optJSONArray("flow");
            if (flowJson != null && flowJson.length() > 0) {
                JSONArray flowJson2 = (JSONArray) flowJson.get(0);
                if (flowJson2 != null && flowJson2.length() > 0) {
                    List<AdConfigBean.ADSlotConfigBean.FlowBean> flowBeanList2 = new ArrayList<>();
                    for (int i = 0; i < flowJson2.length(); i++) {
                        JSONObject flowObject = (JSONObject) flowJson2.get(i);
                        if (null != flowObject) {
                            AdConfigBean.ADSlotConfigBean.FlowBean flowBean = parseFlow(flowObject);
                            flowBeanList2.add(flowBean);
                        }
                    }
                    list.add(flowBeanList2);
                }
            }
            slotConfigBean.setFlow(list);

            return slotConfigBean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static synchronized AdConfigBean.ADSlotConfigBean.FlowBean parseFlow(JSONObject jsonObject) {
        AdConfigBean.ADSlotConfigBean.FlowBean flowBean = new AdConfigBean.ADSlotConfigBean.FlowBean();
        try {
            String platform = jsonObject.optString("platform");
            boolean openStatus = jsonObject.optBoolean("open_status");
            String type = jsonObject.optString("type");
            String key = jsonObject.optString("key");
            flowBean.setPlatform(platform);
            flowBean.setKey(key);
            flowBean.setOpen_status(openStatus);
            flowBean.setType(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flowBean;
    }



    private static synchronized ApxAdConfigBean.DataBean parseDataBean(JSONObject dataBeanObject) {
        try {
            ApxAdConfigBean.DataBean dataBean = new ApxAdConfigBean.DataBean();
            String version = dataBeanObject.optString("version");
            dataBean.setVersion(version);
            JSONObject adUnitsO = dataBeanObject.optJSONObject("ad_units");
            if (adUnitsO != null) {
                ApxAdConfigBean.DataBean.AdUnitsBean adUnitsBean = parseAdUnitsBean(adUnitsO);
                dataBean.setAd_units(adUnitsBean);
            }
            return dataBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static synchronized ApxAdConfigBean.DataBean.AdUnitsBean parseAdUnitsBean(JSONObject adUnitsObject) {
        try {
            ApxAdConfigBean.DataBean.AdUnitsBean adUnitsBean = new ApxAdConfigBean.DataBean.AdUnitsBean();
            JSONArray nativesJsonArray = adUnitsObject.optJSONArray("natives");
            if (nativesJsonArray != null && nativesJsonArray.length() > 0) {
                List<ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean> nativesBeanList = new ArrayList<>();
                for (int i = 0; i < nativesJsonArray.length(); i++) {
                    JSONObject nativeBeanO = (JSONObject) nativesJsonArray.get(i);
                    if (nativeBeanO == null) continue;
                    ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean nativesBean = parseNativeBean(nativeBeanO);
                    nativesBeanList.add(nativesBean);
                }
                adUnitsBean.setNatives(nativesBeanList);
            }
            return adUnitsBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static synchronized ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean parseNativeBean(JSONObject nativeO) {
        try {
            ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean nativesBean = new ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean();
            int adType = nativeO.optInt("ad_type");
            nativesBean.setAd_type(adType);
            String unitId = nativeO.optString("unit_id");
            nativesBean.setUnit_id(unitId);
            String unitName = nativeO.optString("unit_name");
            nativesBean.setUnit_name(unitName);
            JSONArray adNetworks = nativeO.optJSONArray("ad_networks");
            if (adNetworks != null && adNetworks.length() > 0) {
                List<ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean.AdNetworksBean> adNetworksBeanList = new ArrayList<>();
                for (int i = 0; i < adNetworks.length(); i++) {
                    JSONObject adNetworkO = (JSONObject) adNetworks.get(i);
                    if (adNetworkO == null) continue;
                    ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean.AdNetworksBean adNetworksBean = parseAdNetwork(adNetworkO);
                    adNetworksBeanList.add(adNetworksBean);
                }
                nativesBean.setAd_networks(adNetworksBeanList);
            }
            return nativesBean;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static synchronized ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean.AdNetworksBean parseAdNetwork(JSONObject jsonObject) {
        try {
            String key = jsonObject.optString("key");
            String platform = jsonObject.optString("platform");
            ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean.AdNetworksBean adNetworksBean = new ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean.AdNetworksBean();
            adNetworksBean.setKey(key);
            adNetworksBean.setPlatform(platform);
            return adNetworksBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
