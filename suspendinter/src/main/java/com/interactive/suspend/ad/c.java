//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad;

import android.os.AsyncTask;

import com.interactive.suspend.ad.model.TmResponse;
import com.interactive.suspend.ad.model.f;
import com.interactive.suspend.ad.model.g;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class c extends AsyncTask<com.interactive.suspend.ad.model.d, Void, Object> {
    private c.a a;

    public c() {
    }

    @Override
    protected Object doInBackground(com.interactive.suspend.ad.model.d... params) {
        com.interactive.suspend.ad.model.d var2 = params[0];
        HttpURLConnection var3 = null;
        String var4 = var2.c();
        Object var5 = new f("");

        g var7;
        try {
            URL var6 = new URL(var4);
            var3 = (HttpURLConnection)var6.openConnection();
            var3.setRequestMethod("GET");
            var3.setReadTimeout(5000);
            var3.setConnectTimeout(5000);
            if(var3.getResponseCode() != 200) {
                ((f)var5).setError_code(-1);
                ((f)var5).setMessage("网络连接错误， 链接  " + var4 + "，   错误代码" + var3.getResponseCode());
                return var5;
            }

            BufferedInputStream var20 = new BufferedInputStream(var3.getInputStream());
            StringBuilder var8 = new StringBuilder();
            byte[] var9 = new byte[8192];

            String var11;
            do {
                int var10;
                if((var10 = var20.read(var9)) == -1) {
                    var11 = var8.toString();
                    var5 = (new TmResponse.a()).a(var11);
                    return var5;
                }

                var8.append(new String(var9, 0, var10, "UTF-8"));
            } while(!this.isCancelled());

            var11 = null;
            return var11;
        } catch (IOException var16) {
            IOException var19 = var16;
            return var19;
        } catch (g var17) {
            var7 = var17;
        } finally {
            if(var3 != null) {
                var3.disconnect();
            }

        }

        return var7;
    }

    protected void onPostExecute(Object var1) {
        if(this.a != null) {
            this.a.a(var1);
        }

    }

    protected void onPreExecute() {
        if(this.a != null) {
            this.a.a();
        }

    }

    protected void onCancelled(Object var1) {
        if(this.a != null) {
            this.a.b(var1);
        }

    }

    public void a(c.a var1) {
        this.a = var1;
    }

    public interface a {
        void a(Object var1);

        void a();

        void b(Object var1);
    }
}
