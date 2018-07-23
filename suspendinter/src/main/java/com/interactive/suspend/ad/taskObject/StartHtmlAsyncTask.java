package com.interactive.suspend.ad.taskObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.interactive.suspend.ad.util.ApplicationUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/6/28.
 */

public class StartHtmlAsyncTask extends AsyncTask<String, Integer, String> {
    private Context mContxt;
    private WakeLock mWakeLock;
    public ProgressDialog vProgressDialog;
    private final static String FILE_APK = "file.apk";
    private final static String SDCARD_FILE = "/sdcard/";

    public StartHtmlAsyncTask(Context context, ProgressDialog progressDialog) {
        this.mContxt = context;
        this.vProgressDialog = progressDialog;
    }


    @Override
    protected String doInBackground(String... params) {
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            HttpURLConnection httpURLConnection = null;

            try {
                String message;
                try {
                    URL url = new URL(params[0]);
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.connect();
                    if(httpURLConnection.getResponseCode() != 200) {
                        message = "Server returned HTTP " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage();
                        return message;
                    } else {
                        int contentLength = httpURLConnection.getContentLength();
                        inputStream = httpURLConnection.getInputStream();
                        fileOutputStream = new FileOutputStream(SDCARD_FILE + FILE_APK);
                        byte[] bytes = new byte[8192];

                        int count;
                        for(long length = 0L; (count = inputStream.read(bytes)) != -1; fileOutputStream.write(bytes, 0, count)) {
                            if(this.isCancelled()) {
                                inputStream.close();
                                Object object = null;
                                return (String)object;
                            }

                            length += (long)count;
                            if(contentLength > 0) {
                                publishProgress(new Integer[]{Integer.valueOf((int)(length * 100L / (long)contentLength))});
                            }
                        }

                        if(fileOutputStream != null) {
                            fileOutputStream.close();
                        }

                        if(inputStream != null) {
                            inputStream.close();
                        }

                        ApplicationUtil.installApk(mContxt,new File(SDCARD_FILE + FILE_APK));
                        return null;
                    }
                } catch (Exception exception) {
                    message = exception.toString();
                    return message;
                }
            } finally {
                if(httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }

            }
    }

    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager powerManager = (PowerManager)mContxt.getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(1, this.getClass().getName());
        mWakeLock.acquire();
        vProgressDialog.show();
    }

    protected void progressUpdate(Integer... integers) {
        super.onProgressUpdate(integers);
        vProgressDialog.setIndeterminate(false);
        vProgressDialog.setMax(100);
        vProgressDialog.setProgress(integers[0].intValue());
    }

    protected void progressDismiss(String var1) {
        mWakeLock.release();
        vProgressDialog.dismiss();
    }

}

