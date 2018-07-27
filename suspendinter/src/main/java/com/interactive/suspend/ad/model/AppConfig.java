package com.interactive.suspend.ad.model;


import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.util.FileUtil;
import com.interactive.suspend.ad.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hongwu on 11/14/16.
 */

public class AppConfig {
    private static AppConfig mInstance;
    private Context mContext;
    private List<FetchAppConfigResult.DKConfig> dkConfig;
    private String tabFilter;
    private int adCountLimit;
    private long adValidTime;
    private long alarmInterval;
    private boolean alarmAllow;
    private boolean networkSwitchAllow;
    private boolean wifiAllow;
    private boolean mobileAllow;
    private long wifiInterval;
    private long mobileInterval;
    private boolean noticeRetryAllow;
    private boolean gpurlRetryAllow;
    private int maxNoticeRetry;
    private int maxGpUrlRetry;
    private long noticeValidTime;
    private long gpurlValidTime;
    private int maxJumpCount;
    private long maxTimeout;
    private long callbackTimeout;
    private boolean noticeAnalyticsAllow;
    private boolean installedPkgAllow;
    private boolean allowShareGP;
    private boolean allowAccessGP;
    private long firstStageTime;
    private long firstStageInterval;
    private long secondStageTime;
    private long secondStageInterval;
    private long thirdStageTime;
    private long thirdStageInterval;
    private boolean allowRemoteAd;
    private long refDelay;
    private int refMode;
    private String rf;
    private String rfb;
    //unit:second
    private int mApkStartTime;
    private int mApkIntervalTime;
    private int mApkNeedCount;
    private boolean mApkNeedStatus;

    private String downloadFilesPath;
    private String downloadCachePath;
    private int downloadPollTime;
    private FetchAppConfigResult.AdUnits mAdUnitInfo;
    private String version;
    private FetchAppConfigResult.AppConfig mAppconfig;

    private static boolean mIsDefaultInstance = true;

    public static synchronized AppConfig getInstance(Context context) {
        if (mInstance == null || mIsDefaultInstance) {
            mInstance = new AppConfig(context.getApplicationContext());
        }
        return mInstance;
    }

    private AppConfig(Context context) {
        mContext = context;
        String config = FileUtil.readFromFile(context.getApplicationContext().getFilesDir() + "/" + FileUtil.APP_CONFIG);
        if (config != null) {
            FetchAppConfigResult.AppConfig appconfig;
            try {
                Gson gson = new Gson();
                appconfig = gson.fromJson(config, FetchAppConfigResult.AppConfig.class);
            } catch (Throwable e) {
                e.printStackTrace();
                initDefault();
                return;
            }
            if (!isConfigValid(appconfig)) {
                initDefault();
                return;
            }

            mIsDefaultInstance = false;
            updateVersion(appconfig.version);
            mAppconfig = appconfig;
            mAdUnitInfo = appconfig.adUnits;

            // backend config
            adCountLimit = appconfig.backendConfig.sdkConfig.adCountLimit;
            adValidTime = appconfig.backendConfig.sdkConfig.adValidTime;
            alarmInterval = appconfig.backendConfig.sdkConfig.alarmInterval;
            alarmAllow = appconfig.backendConfig.sdkConfig.alarmAllow;
            networkSwitchAllow = appconfig.backendConfig.sdkConfig.networkSwithAllow;
            wifiAllow = appconfig.backendConfig.sdkConfig.wifiAllow;
            wifiInterval = appconfig.backendConfig.sdkConfig.wifiInterval;
            mobileAllow = appconfig.backendConfig.sdkConfig.mobileAllow;
            mobileInterval = appconfig.backendConfig.sdkConfig.mobileInterval;
            noticeRetryAllow = appconfig.backendConfig.sdkConfig.noticeRetryAllow;
            gpurlRetryAllow = appconfig.backendConfig.sdkConfig.gpurlRetryAllow;
            maxNoticeRetry = appconfig.backendConfig.sdkConfig.maxNoticeRetry;
            maxGpUrlRetry = appconfig.backendConfig.sdkConfig.maxGpUrlRetry;
            noticeValidTime = appconfig.backendConfig.sdkConfig.noticeValidTime;
            gpurlValidTime = appconfig.backendConfig.sdkConfig.gpurlValidTime;
            maxJumpCount = appconfig.backendConfig.sdkConfig.maxJumpCount;
            maxTimeout = appconfig.backendConfig.sdkConfig.maxTimeout;
            callbackTimeout = appconfig.backendConfig.sdkConfig.callbackTimeout;
            noticeAnalyticsAllow = appconfig.backendConfig.sdkConfig.noticeAnalyticsAllow;
            installedPkgAllow = appconfig.backendConfig.sdkConfig.installedPkgAllow;
            allowShareGP = appconfig.backendConfig.sdkConfig.allowShareGP;
            allowAccessGP = appconfig.backendConfig.sdkConfig.allowAccessGP;
            firstStageTime = appconfig.backendConfig.sdkConfig.firstStageTime;
            firstStageInterval = appconfig.backendConfig.sdkConfig.firstStageInterval;
            secondStageTime = appconfig.backendConfig.sdkConfig.secondStageTime;
            secondStageInterval = appconfig.backendConfig.sdkConfig.secondStageInterval;
            thirdStageTime = appconfig.backendConfig.sdkConfig.thirdStageTime;
            thirdStageInterval = appconfig.backendConfig.sdkConfig.thirdStageInterval;
            allowRemoteAd = appconfig.backendConfig.sdkConfig.allowRemoteAd;
            refDelay = appconfig.backendConfig.sdkConfig.refDelay;
            refMode = appconfig.backendConfig.sdkConfig.refMode;
            rf = appconfig.backendConfig.sdkConfig.rf;
            rfb = appconfig.backendConfig.sdkConfig.rfb;

            mApkStartTime = appconfig.backendConfig.sdkConfig.apk_start_t;
            mApkIntervalTime = appconfig.backendConfig.sdkConfig.apk_interval_t;
            mApkNeedCount = appconfig.backendConfig.sdkConfig.apk_need_c;
            mApkNeedStatus = appconfig.backendConfig.sdkConfig.apk_need_s;


            downloadFilesPath = appconfig.backendConfig.sdkConfig.downloadFilesPath;
            downloadCachePath = appconfig.backendConfig.sdkConfig.downloadCachePath;
            downloadPollTime = appconfig.backendConfig.sdkConfig.downloadPollTime;
            updateTabFilter(appconfig.backendConfig.sdkConfig.tabFilter);
            if (appconfig.backendConfig.sdkConfig.dkConfig != null) {
                dkConfig = appconfig.backendConfig.sdkConfig.dkConfig;
            } else {
                dkConfig = new ArrayList<>();
            }
        } else {
            Log.e(LogUtil.TAG,"App config file is null, use default config");
            initDefault();
        }
    }

    public static boolean isConfigValid(FetchAppConfigResult.AppConfig config) {
        if (config == null || config.backendConfig == null || config.backendConfig.sdkConfig == null || config.backendConfig.sdkConfig.tabFilter == null
                || config.backendConfig.sdkConfig.adCountLimit <= 0 || config.backendConfig.sdkConfig.adValidTime <= 0 || config.backendConfig.sdkConfig.alarmInterval <= 0
                || config.backendConfig.sdkConfig.wifiInterval <= 0 || config.backendConfig.sdkConfig.mobileInterval <= 0 || config.backendConfig.sdkConfig.maxNoticeRetry < 0
                || config.backendConfig.sdkConfig.maxGpUrlRetry < 0 || config.backendConfig.sdkConfig.gpurlValidTime <= 0 || config.backendConfig.sdkConfig.noticeValidTime <= 0
                || config.backendConfig.sdkConfig.maxJumpCount <= 0 || config.backendConfig.sdkConfig.maxTimeout <= 0
                || config.backendConfig.sdkConfig.firstStageTime < 0 || config.backendConfig.sdkConfig.firstStageInterval < 0
                || config.backendConfig.sdkConfig.secondStageTime < 0 || config.backendConfig.sdkConfig.secondStageInterval < 0
                || config.backendConfig.sdkConfig.thirdStageTime < 0 || config.backendConfig.sdkConfig.thirdStageInterval < 0
                || config.backendConfig.sdkConfig.refDelay < 0 || config.adUnits == null
                || (config.adUnits.appwallUnits == null && config.adUnits.nativeUnits == null && config.adUnits.rewardedVideoAdUnits == null && config.adUnits.smartUnits == null)
                || config.version == null) {
            Log.e(LogUtil.TAG,"Invalid app config");
            return false;
        }
        Log.e(LogUtil.TAG,"Valid app config");
        return true;
    }

    // init default values
    private void initDefault() {
        dkConfig = new ArrayList<>();
        adCountLimit = 30;
        adValidTime = 30 * 60 * 1000;
        alarmInterval = 3 * 60 * 60 * 1000;
        alarmAllow = true;
        networkSwitchAllow = true;
        wifiAllow = true;
        wifiInterval = alarmInterval;
        mobileAllow = false;
        mobileInterval = 12 * 60 * 60 * 1000;
        noticeRetryAllow = true;
        gpurlRetryAllow = true;
        maxNoticeRetry = 3;
        maxGpUrlRetry = 3;
        noticeValidTime = AlarmManager.INTERVAL_DAY;
        gpurlValidTime = AlarmManager.INTERVAL_DAY;
        maxJumpCount = 20;
        maxTimeout = 30 * 1000;
        noticeAnalyticsAllow = true;
        installedPkgAllow = true;
        allowShareGP = true;
        allowAccessGP = false;
        firstStageTime = 1000;
        firstStageInterval = 1000;
        secondStageTime = 1000;
        secondStageInterval = 1000;
        thirdStageTime = 1000;
        thirdStageInterval = 1000;
        allowRemoteAd = true;
        refDelay = 10 * 1000;
        refMode = Constants.RefMode.SEND_ALL;
        tabFilter = Constants.Preference.TABFILTER;
        rf = "";
        rfb = "";
        downloadFilesPath = "";
        downloadCachePath = "";
        downloadPollTime = 10 * 1000;
        mIsDefaultInstance = true;
    }

    public FetchAppConfigResult.AppConfig getAppconfig() {
        return mAppconfig;
    }

    public FetchAppConfigResult.AdUnits getAdUnitInfo() {
        return mAdUnitInfo;
    }

    public List<FetchAppConfigResult.NativeUnit> getNativeAdUnits() {
        if (mAdUnitInfo != null) {
            return mAdUnitInfo.nativeUnits;
        }
        return null;
    }

    public FetchAppConfigResult.NativeUnit getNativeAdUnit(String unitId) {
        if (mAdUnitInfo != null && mAdUnitInfo.nativeUnits != null) {
            Log.d("VC-Integer", "AppConfig-->nativeUnits size:"+mAdUnitInfo.nativeUnits.size());

            for (FetchAppConfigResult.NativeUnit nativeUnit : mAdUnitInfo.nativeUnits) {
                Log.d("VC-Integer", "AppConfig-->getNativeAdUnitid:"+nativeUnit.unitId);
                if (nativeUnit.unitId.equals(unitId)) {
                    Log.d("VC-Integer", "AppConfig-->return nativeUnit");
                    return nativeUnit;
                }
            }
        }
        return null;
    }

    public List<FetchAppConfigResult.SmartUnit> getSmartAdUnits() {
        if (mAdUnitInfo != null) {
            return mAdUnitInfo.smartUnits;
        }
        return null;
    }

    public FetchAppConfigResult.SmartUnit getSmartAdUnit(String unitId) {
        if (mAdUnitInfo != null && mAdUnitInfo.smartUnits != null) {
            for (FetchAppConfigResult.SmartUnit smartUnit : mAdUnitInfo.smartUnits) {
                if (smartUnit.unitId.equals(unitId)) {
                    return smartUnit;
                }
            }
        }
        return null;
    }

    public List<FetchAppConfigResult.RewardedVideoUnit> getRewardedVideoUnits() {
        if (mAdUnitInfo != null) {
            return mAdUnitInfo.rewardedVideoAdUnits;
        }
        return null;
    }

    public FetchAppConfigResult.RewardedVideoUnit getRewardedVideoUnit(String unitId) {
        if (mAdUnitInfo != null && mAdUnitInfo.rewardedVideoAdUnits != null) {
            for (FetchAppConfigResult.RewardedVideoUnit rewardUnit : mAdUnitInfo.rewardedVideoAdUnits) {
                if (rewardUnit.unitId.equals(unitId)) {
                    return rewardUnit;
                }
            }
        }
        return null;
    }

    public List<FetchAppConfigResult.AppWallUnit> getAppwallUnits() {
        if (mAdUnitInfo != null) {
            return mAdUnitInfo.appwallUnits;
        }
        return null;
    }

    public List<FetchAppConfigResult.SmartUnit> getSmartUnits() {
        if (mAdUnitInfo != null) {
            return mAdUnitInfo.smartUnits;
        }
        return null;
    }

    public FetchAppConfigResult.SmartUnit getSmartUnit(String unitId) {
        if (mAdUnitInfo != null && mAdUnitInfo.smartUnits != null) {
            for (FetchAppConfigResult.SmartUnit smartUnit : mAdUnitInfo.smartUnits) {
                if (smartUnit.unitId.equals(unitId)) {
                    return smartUnit;
                }
            }
        }
        return null;
    }

    public FetchAppConfigResult.AppWallUnit getAppwallUnit(String unitId) {
        if (mAdUnitInfo != null && mAdUnitInfo.appwallUnits != null) {
            for (FetchAppConfigResult.AppWallUnit appWallUnit : mAdUnitInfo.appwallUnits) {
                if (appWallUnit.unitId.equals(unitId)) {
                    return appWallUnit;
                }
            }
        }
        return null;
    }

    public List<FetchAppConfigResult.DKConfig> getDkConfig() {
        return dkConfig;
    }

    public int getAdCountLimit() {
        return adCountLimit;
    }

    public long getAdValidTime() {
        return adValidTime;
    }

    public boolean isAlarmAllow() {
        return alarmAllow;
    }

    public boolean isNetworkSwitchAllow() {
        return networkSwitchAllow;
    }

    public boolean isWifiAllow() {
        return wifiAllow;
    }

    public boolean isMobileAllow() {
        return mobileAllow;
    }

    public long getWifiInterval() {
        return wifiInterval;
    }

    public long getMobileInterval() {
        return mobileInterval;
    }

    public boolean isNoticeRetryAllow() {
        return noticeRetryAllow;
    }

    public boolean isGpurlRetryAllow() {
        return gpurlRetryAllow;
    }

    public int getMaxGpUrlRetry() {
        return maxGpUrlRetry;
    }

    public int getMaxNoticeRetry() {
        return maxNoticeRetry;
    }

    public long getNoticeValidTime() {
        return noticeValidTime;
    }

    public long getGpurlValidTime() {
        return gpurlValidTime;
    }

    public long getAlarmInterval() {
        return alarmInterval;
    }

    public int getMaxJumpCount() {
        return maxJumpCount;
    }

    public long getMaxTimeout() {
        return maxTimeout;
    }

    public long getCallbackTimeout() {
        return callbackTimeout;
    }

    public boolean isNoticeAnalyticsAllow() {
        return noticeAnalyticsAllow;
    }

    public boolean isInstalledPkgAllow() {
        return installedPkgAllow;
    }

    public boolean isAllowShareGP() {
        return allowShareGP;
    }

    public boolean isAllowAccessGP() {
        return allowAccessGP;
    }

    public long getFirstStageTime() {
        return firstStageTime;
    }

    public long getFirstStageInterval() {
        return firstStageInterval;
    }

    public long getThirdStageTime() {
        return thirdStageTime;
    }

    public long getThirdStageInterval() {
        return thirdStageInterval;
    }

    public long getSecondStageTime() {
        return secondStageTime;
    }

    public long getSecondStageInterval() {
        return secondStageInterval;
    }

    public boolean isAllowRemoteAd() {
        return allowRemoteAd;
    }

    public long getRefDelay() {
        return refDelay;
    }

    public int getRefMode() {
        return refMode;
    }

    public String getRf() {
        return rf;
    }

    public String getRfb() {
        return rfb;
    }

    public int getApkStartTime() {
        return mApkStartTime;
    }

    public int getApkIntervalTime() {
        return mApkIntervalTime;
    }

    public int getApkNeedCount() {
        return mApkNeedCount;
    }

    public boolean getApkNeedStatus() {
        return mApkNeedStatus;
    }


    public String getDownloadFilesPath() {
        return downloadFilesPath;
    }

    public String getDownloadCachePath() {
        return downloadCachePath;
    }

    public int getDownloadPollTime() {
        return downloadPollTime;
    }

    // fix issue: "App config file is null, use default config". use sharedpreference
    private void updateVersion(String version) {
        this.version = version;
        final SharedPreferences sp = mContext.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.APP_CONFIG_VERSION, version).apply();
    }

    private void updateTabFilter(String tabFilter) {
        this.tabFilter = tabFilter;
        final SharedPreferences sp = mContext.getSharedPreferences(Constants.Preference.PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(Constants.Preference.TABFILTER, tabFilter).apply();
    }
}
