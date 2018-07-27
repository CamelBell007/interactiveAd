package com.interactive.suspend.ad.manager;

import android.app.AlarmManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.interactive.suspend.ad.BuildConfig;
import com.interactive.suspend.ad.constant.AsyncStatus;
import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.html.IAdSdkListener;
import com.interactive.suspend.ad.model.AppConfig;
import com.interactive.suspend.ad.model.FetchAppConfigResult;
import com.interactive.suspend.ad.task.AppConfigLoadTask;
import com.interactive.suspend.ad.util.LogUtil;
import com.interactive.suspend.ad.util.PreferenceUtils;

import static com.interactive.suspend.ad.util.LogUtil.TAG;


/**
 * Created by VC on 5/31/17.
 */

public class ConfigLoader implements AppConfigTaskListener {

    private Context mContext;
    private AppConfigLoadTask mAppConfigTask;
    private int mRetryTimes = 0;
    private String mAppid;
    private IAdSdkListener mIAdSdkListener;

    private boolean mIsHasCache;

    public ConfigLoader(Context context, String appid, IAdSdkListener listener) {
        mContext = context.getApplicationContext();
        mAppid = appid;
        mIAdSdkListener = listener;
    }
    
    public void loadAppConfig() {
        if (TextUtils.isEmpty(mAppid)) {
            throw new IllegalArgumentException("your appid is empty!!");
        }
//        mIsHasCache = SdkInternal.isInited(mContext);
        if(mIsHasCache&&mIAdSdkListener != null){
            mIAdSdkListener.onInitSuccess();
            mIAdSdkListener = null;
        }

        if (Constants.DEBUG) { // debug mode
            if (mAppConfigTask != null && mAppConfigTask.getStatus().equals(AsyncStatus.RUNNING)) {
                Log.d(TAG, "Already loading app config, do nothing!");
                return;
            }
            mAppConfigTask = new AppConfigLoadTask(mContext, mAppid, this);
            mAppConfigTask.execute();
        } else {
            if (mAppConfigTask != null && mAppConfigTask.getStatus().equals(AsyncStatus.RUNNING)) {
                Log.d(TAG, "Already loading app config, do nothing!");
                return;
            }

            long now = System.currentTimeMillis();
            long lastSuccessTime = PreferenceUtils.getLastFetchAppConfigSuccessTime(mContext);
            long lastSdkVersion = PreferenceUtils.getCurSdkVersion(mContext);
            String storedAppid = PreferenceUtils.getAppId(mContext);
            if (!mAppid.equals(storedAppid) || BuildConfig.VERSION_CODE > lastSdkVersion
                    || now - lastSuccessTime > AlarmManager.INTERVAL_HOUR) {
                if (mAppConfigTask != null) {
                    mAppConfigTask.cancel(true);
                }
                mAppConfigTask = new AppConfigLoadTask(mContext, mAppid, this);
                mAppConfigTask.execute();
            } else {
                Log.d(TAG,  "App config already loaded!");
                onLoadAppConfigSuccessInternal();
            }
        }
    }

    @Override
    public void onLoadAppConfigSuccess(FetchAppConfigResult.AppConfig config) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // init config after loading succeed.
                AppConfig.getInstance(mContext);
            }
        }).start();

        Log.d(TAG, "succeed get ad unit config");

            // native
            if (config.adUnits.nativeUnits != null && config.adUnits.nativeUnits.size() != 0) {
                // only use one native source id
                if (config.adUnits.nativeUnits.get(0).adNetworks != null && config.adUnits.nativeUnits.get(0).adNetworks.size() != 0) {
                    for (FetchAppConfigResult.AdNetwork adNetwork : config.adUnits.nativeUnits.get(0).adNetworks) {
                        if (adNetwork.platform.equals(Constants.AdNetworks.APX)) {
                            Log.d(TAG, "init apx native unit");
                            PreferenceUtils.setNativeSourceId(mContext, adNetwork.key);
                            if (TextUtils.isEmpty(PreferenceUtils.getCoreSourceId(mContext))) {
                                PreferenceUtils.setCoreSourceId(mContext, adNetwork.key);
                            }
                        }
                    }
                }
            } else {
                PreferenceUtils.setNativeSourceId(mContext, null);
            }

        onLoadAppConfigSuccessInternal();
    }

    private void onLoadAppConfigSuccessInternal() {
//        startService();
        if (!mIsHasCache && mIAdSdkListener != null) {
            mIAdSdkListener.onInitSuccess();
            mIAdSdkListener = null;
        }
    }

    @Override
    public void onLoadAppConfigStart() {}

    @Override
    public void onLoadAppConfigFail(Error error) {
        mRetryTimes++;
        Log.e(LogUtil.TAG,"Failed to get app config, retry: " + mRetryTimes);
        if (mRetryTimes < 3) {
            mAppConfigTask = null;
            loadAppConfig();
        } else {
            if (mIAdSdkListener != null) {
                String errorMessage = "Please check your network state...";
                if(error != null){
                    errorMessage = errorMessage +"errorMessage: "+ error.getMessage();
                }
                mIAdSdkListener.onInitFailed(errorMessage);
                mIAdSdkListener = null;
            }
//            startService();
        }
    }

    private void startService() {
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e(TAG, "startService");
//                Intent service = new Intent(mContext, AdPreloadService.class);
//                service.setAction(Constants.AdAction.ACTION_SETUP_ALARM);
//                try {
//                    mContext.startService(service);
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 10000);
    }
}
