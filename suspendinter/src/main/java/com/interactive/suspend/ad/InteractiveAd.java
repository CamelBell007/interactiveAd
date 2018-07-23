package com.interactive.suspend.ad;

import android.content.Context;
import android.text.TextUtils;

import com.interactive.suspend.ad.model.AdConfigBean;
import com.interactive.suspend.ad.model.ApxAdConfigBean;
import com.interactive.suspend.ad.net.ApxConfigRequestManager;
import com.interactive.suspend.ad.net.RequestApxConfigListener;
import com.interactive.suspend.ad.net.RequestDopConfigListener;
import com.interactive.suspend.ad.net.RequestManager;

import java.util.HashMap;
import java.util.List;

import static com.interactive.suspend.ad.constant.AdConstants.DRIVER_LINE;

/**
 * Created by VC on 2018/6/26.
 */

public class InteractiveAd {

    private Context mContext;
    private InitListener mInitListener;

    private static final String TAG = "InteractiveAd";

    public static volatile InteractiveAd INTERACTIVE_AD;
    private HashMap<String,String> slotSourceIdMap;
    private String errorMsg = "init failed!";

    private InteractiveAd() {
    }

    public static InteractiveAd getInstance() {
        if (INTERACTIVE_AD == null) {
            synchronized (InteractiveAd.class) {
                if (INTERACTIVE_AD == null) {
                    INTERACTIVE_AD = new InteractiveAd();
                }
            }
        }
        return INTERACTIVE_AD;
    }

    /**
     * init interactive ad sdk
     * get ad configuration and do some init
     */
    public void init(final Context context, String appId, InitListener listener){
        if (context == null || context.getApplicationContext() == null)
            throw new IllegalArgumentException("context argument illegal");
        if (TextUtils.isEmpty(appId)) throw new IllegalArgumentException("appid is not valid");
        mContext = context.getApplicationContext();
        mInitListener = listener;
        final String url = "http://c.open.dotcunitedgroup.com" + DRIVER_LINE + appId;

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadConfig(mContext,url);
            }
        }).start();

    }

    private void loadConfig(final Context context, String url) {
        if (context == null) return;
        RequestManager.requestAdConfig(context, url, new RequestDopConfigListener() {
            @Override
            public void onRequestSuccess(AdConfigBean dopBean) {
                //dop 配置请求成功, 初始化apx
//                Log.i(TAG, "load dop success!! " + dopBean.getAppwall_key());
                if (!TextUtils.isEmpty(dopBean.getAppwall_key())) {
                    initApx(context, dopBean.getAppwall_key());
                } else {
                    if (mInitListener != null) {
                        mInitListener.onInitFailed("config not valide: 502");
                    }
                }
            }

            @Override
            public void onRequestFailed(String errorMsg) {
                //dop 配置请求失败
//                Log.i(TAG, "load dop: " + errorMsg);
                if (mInitListener != null) {
                    mInitListener.onInitFailed(errorMsg);
                }
            }
        });
    }

    private void initApx(Context context, String appwallKey) {
//        Log.i(TAG, " start to init apx!!");
        ApxConfigRequestManager.requestAdConfig(context, appwallKey, new RequestApxConfigListener() {
            @Override
            public void onRequestSuccess(ApxAdConfigBean apxAdConfigBean) {
                //初始化成功...
                initSlotSourceMap(apxAdConfigBean);
                if (mInitListener != null) {
                    mInitListener.onInitSuccess();
                }
//                Log.i(TAG, "load apx success!!");
            }

            @Override
            public void onRequestFailed(String errorMsg) {
                //初始化失败...
//                Log.i(TAG, "load apx failed!!");
                if (mInitListener != null) {
                    mInitListener.onInitFailed(errorMsg);
                }
            }
        });
    }

    private void initSlotSourceMap(ApxAdConfigBean apxAdConfigBean) {
        if (null == apxAdConfigBean) return;
        try {
            List<ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean> nativesBeans = apxAdConfigBean.getData().getAd_units().getNatives();
            slotSourceIdMap = new HashMap<>();
            for (ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean nativesBean : nativesBeans) {
                if (nativesBean != null) {
                    String unitId = nativesBean.getUnit_id();
                    if (TextUtils.isEmpty(unitId)) continue;
                    ApxAdConfigBean.DataBean.AdUnitsBean.NativesBean.AdNetworksBean adNetworksBean = nativesBean.getAd_networks().get(0);
                    if (adNetworksBean != null) {
                        String key = adNetworksBean.getKey();
                        if (!TextUtils.isEmpty(key)) slotSourceIdMap.put(unitId,key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSourceIdBySlotId(String slotId) {
        if (TextUtils.isEmpty(slotId)) throw new IllegalArgumentException("slotid can not be null");
        if (slotSourceIdMap != null) {
            return slotSourceIdMap.get(slotId);
        }
        return "";
    }

    public void showFlatAd(){

    }

    public void hideFloatAd(){

    }
}
