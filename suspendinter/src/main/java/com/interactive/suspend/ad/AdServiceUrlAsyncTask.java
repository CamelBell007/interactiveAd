package com.interactive.suspend.ad;

import android.os.AsyncTask;

import com.interactive.suspend.ad.http.AdInfo;
import com.interactive.suspend.ad.http.TmResponse;
import com.interactive.suspend.ad.http.InterException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdServiceUrlAsyncTask extends AsyncTask<AdInfo, Void, Object>
{
    private a a;

    protected void onPreExecute()
    {
        if (this.a != null) {
            this.a.a();
        }
    }

    @Override
    protected Object doInBackground(AdInfo... paramVarArgs)
    {
        AdInfo localc = paramVarArgs[0];
        HttpURLConnection localHttpURLConnection = null;
        String str = localc.a();
        try
        {
            URL localURL = new URL(str);
            localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
            localHttpURLConnection.setRequestMethod("POST");
            localHttpURLConnection.setReadTimeout(5000);
            localHttpURLConnection.setConnectTimeout(5000);
            localHttpURLConnection.setDoOutput(true);
            TmResponse localObject1 = null;
            Object localObject2;
            if (localHttpURLConnection.getResponseCode() == 200)
            {
                localObject2 = new BufferedInputStream(localHttpURLConnection.getInputStream());
                StringBuilder localStringBuilder = new StringBuilder();
                byte[] arrayOfByte = new byte['?'];
                int i;
                while ((i = ((BufferedInputStream)localObject2).read(arrayOfByte)) != -1)
                {
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
        }
        catch (IOException localIOException)
        {
            return localIOException;
        }
        catch (InterException localg)
        {
            return localg;
        } finally
        {
            if (localHttpURLConnection != null) {
                localHttpURLConnection.disconnect();
            }
        }
    }

    protected void onPostExecute(Object paramObject)
    {
        if (this.a != null) {
            this.a.a(paramObject);
        }
    }

    protected void onCancelled(Object paramObject)
    {
        if (this.a != null) {
            this.a.b(paramObject);
        }
    }

    public void a(a parama)
    {
        this.a = parama;
    }

    static abstract interface a
    {
        public abstract void a(Object paramObject);

        public abstract void a();

        public abstract void b(Object paramObject);
    }
}
