package com.interactive.suspend.ad.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.interactive.suspend.ad.BuildConfig;
import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.http.NetworkUtils;
import com.interactive.suspend.ad.manager.AppConfigTaskListener;
import com.interactive.suspend.ad.model.AppConfig;
import com.interactive.suspend.ad.model.FetchAppConfigResult;
import com.interactive.suspend.ad.util.FileUtil;
import com.interactive.suspend.ad.util.LogUtil;
import com.interactive.suspend.ad.util.PreferenceUtils;


/**
 * Created by hongwu on 11/14/16.
 */

public class AppConfigLoadTask extends AsyncTask<Void, Void, FetchAppConfigResult> {

    private Context mContext;
    private String mAppId;
    private AppConfigTaskListener mListener;
    private static final String TAG = "AppConfigLoadTask";


    public AppConfigLoadTask(Context context, String appId, AppConfigTaskListener listener) {
        this.mContext = context.getApplicationContext();
        this.mAppId = appId;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (mListener != null) {
            mListener.onLoadAppConfigStart();
        }
    }

    @Override
    protected FetchAppConfigResult doInBackground(Void... params) {
        FetchAppConfigResult result = NetworkUtils.fetchAppConfig(mContext, mAppId);
        if (FetchAppConfigResult.isLast(result)) {
            return result; // config no modified
        }
        if (!FetchAppConfigResult.isFailed(result) && AppConfig.isConfigValid(result.appconfig)) {
            try {
                String path = mContext.getFilesDir() + "/" + FileUtil.APP_CONFIG;
                Gson gson = new Gson();
                String config = gson.toJson(result.appconfig, FetchAppConfigResult.AppConfig.class);
                Log.d("appconfig: " , gson.toJson(result.appconfig, FetchAppConfigResult.AppConfig.class));
                FileUtil.writeToFile(path, config);
            } catch (Exception e) {
                Log.e(LogUtil.TAG,e.getMessage());
            } catch (Error e) {
                Log.e(LogUtil.TAG,e.getMessage());// toJson() maybe leads to incompatiableClassChangeError
            }
            return result;
        }
        return null;
    }

    @Override
    protected void onPostExecute(FetchAppConfigResult result) {
        // store appid
        PreferenceUtils.setAppId(mContext.getApplicationContext(), mAppId);

        if (FetchAppConfigResult.isLast(result) || !FetchAppConfigResult.isFailed(result)) {
            try {
                SharedPreferences sp = mContext.getSharedPreferences(Constants.Preference.PREF_NAME,
                        mContext.MODE_PRIVATE);
                sp.edit().putLong(Constants.Preference.LAST_GET_APP_CONFIG_TASK_SUCCESS_TIME, System.currentTimeMillis()).apply();
                if (BuildConfig.VERSION_CODE > PreferenceUtils.getCurSdkVersion(mContext)) {
                    Log.d("set cur sdk version: " ,""+ BuildConfig.VERSION_CODE);
                    PreferenceUtils.setCurSdkVersion(mContext, BuildConfig.VERSION_CODE);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (mListener != null) {
                if (FetchAppConfigResult.isLast(result)) {
                    Log.d(TAG, "app config no update");
                    mListener.onLoadAppConfigSuccess(AppConfig.getInstance(mContext).getAppconfig());
                } else {
                    Log.d(TAG, "load app config success");
                    mListener.onLoadAppConfigSuccess(result.appconfig);
                }
            }
            mListener = null;
            return;
        }
        // Failed
        if (FetchAppConfigResult.isFailed(result)) {
            Log.e(TAG, "load app config failed");
            String errorMessage = "fetch app config error:";
            if(result != null){
                Log.d("Camera360","Result Message:"+result.message);
                errorMessage = errorMessage +result.message;
            }
            Error mError = new Error(errorMessage);
            if (mListener != null) {
                mListener.onLoadAppConfigFail(mError);
            }
            mListener = null;
        }
    }
}
