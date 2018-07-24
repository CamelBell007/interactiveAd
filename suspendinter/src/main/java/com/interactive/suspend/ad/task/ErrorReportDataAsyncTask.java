package com.interactive.suspend.ad.task;

import android.os.AsyncTask;

import com.interactive.suspend.ad.http.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class ErrorReportDataAsyncTask extends AsyncTask<ErrorReportData, Void, Object> {
    private ErrorDataReportInterface mErrorDataReportInterface;

    @Override
    protected Object doInBackground(ErrorReportData... params) {
        ErrorReportData locala = params[0];
        HttpURLConnection localHttpURLConnection = null;
        String str = locala.a();
        try {
            URL localURL = new URL(str);
            localHttpURLConnection = (HttpURLConnection) localURL.openConnection();
            localHttpURLConnection.setRequestMethod("POST");
            localHttpURLConnection.setReadTimeout(5000);
            localHttpURLConnection.setConnectTimeout(5000);

            TmResponse localObject1 = null;
            Object localObject2;
            if (localHttpURLConnection.getResponseCode() == 200) {
                localObject2 = new BufferedInputStream(localHttpURLConnection.getInputStream());
                StringBuilder localStringBuilder = new StringBuilder();
                byte[] arrayOfByte = new byte['?'];
                int i;
                while ((i = ((BufferedInputStream) localObject2).read(arrayOfByte)) != -1) {
                    localStringBuilder.append(new String(arrayOfByte, 0, i, "UTF-8"));
                    if (isCancelled()) {
                        return null;
                    }
                }
                String localObject3 = localStringBuilder.toString();

                localObject1 = new TmResponse.a().a(localObject3);
                return localObject1;
            }
            return localObject1;
        } catch (IOException localIOException) {
            return localIOException;
        } catch (InterException localg) {
            return localg;
        } finally {
            if (localHttpURLConnection != null) {
                localHttpURLConnection.disconnect();
            }
        }
    }


    protected void onPreExecute() {
        if (this.mErrorDataReportInterface != null) {
            this.mErrorDataReportInterface.preExecute();
        }
    }

    protected void onCancelled(Object paramObject) {
        if (this.mErrorDataReportInterface != null) {
            this.mErrorDataReportInterface.cancel(paramObject);
        }
    }

    protected void onPostExecute(Object paramObject) {
        if (this.mErrorDataReportInterface != null) {
            this.mErrorDataReportInterface.postExecute(paramObject);
        }
    }

    public void a(ErrorDataReportInterface parama) {
        this.mErrorDataReportInterface = parama;
    }

    static abstract interface ErrorDataReportInterface {
        public abstract void postExecute(Object paramObject);

        public abstract void preExecute();

        public abstract void cancel(Object paramObject);
    }
}
