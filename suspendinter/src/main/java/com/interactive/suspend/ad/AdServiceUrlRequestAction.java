//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.interactive.suspend.ad.http.AdInfo;
import com.interactive.suspend.ad.http.BaseResponse;
import com.interactive.suspend.ad.http.ErrorReportData;
import com.interactive.suspend.ad.http.InterException;
import com.interactive.suspend.ad.taskObject.FileUtils;
import com.interactive.suspend.ad.taskObject.SharedPreferenceUtil;
import com.interactive.suspend.ad.taskObject.StringUtils;
import com.interactive.suspend.ad.taskObject.ThreadStackLog;

public class AdServiceUrlRequestAction {
    private AdInfo a;
    private LoadAdCallback mLoadAdCallback;
    private AdServiceUrlAsyncTask c;
    private BaseResponse.loadFinish d;
    private String e = "";
    private String f = "";
    private Context mContext;
    private ErrorReportData h;
    private ErrorReportDataAsyncTask i;

    public AdServiceUrlRequestAction(BaseResponse.loadFinish var1, LoadAdCallback var2, Context var3) {
        this.mLoadAdCallback = var2;
        this.d = var1;
        this.mContext = var3;
        this.h = new ErrorReportData(var3);
    }

    public void a(AdInfo var1) {
        this.a = var1;
        if(this.a == null) {
            ThreadStackLog.a().a("request is null");
            throw new IllegalArgumentException("ad request must be set");
        } else if(this.mLoadAdCallback == null) {
            throw new IllegalArgumentException("callback must be set");
        } else if(this.d == null) {
            throw new IllegalArgumentException("builder must be set");
        } else {
            this.c = new AdServiceUrlAsyncTask();
            this.c.a(this.c());
            this.c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new AdInfo[]{this.a});
        }
    }

    private ErrorReportDataAsyncTask.ErrorDataReportInterface b() {
        return new ErrorReportDataAsyncTask.ErrorDataReportInterface() {
            public void postExecute(Object var1) {
                if(var1 instanceof BaseResponse) {
                    BaseResponse var2 = (BaseResponse)var1;
                    if(var2.isSucess()) {
                        SharedPreferenceUtil.a(AdServiceUrlRequestAction.this.mContext, "failureTimes", "exposure_fail", "click_fail", "req_fail");
                        AdServiceUrlRequestAction.this.mLoadAdCallback.loadSuccess();
                    }
                } else if(var1 instanceof Exception) {
                    Throwable var4 = ((Exception)var1).getCause();
                    String var3 = var4 != null?var4.getMessage():((Exception)var1).getMessage();
                    AdServiceUrlRequestAction.this.mLoadAdCallback.loadFail(var3);
                }

            }

            public void preExecute() {
            }

            public void cancel(Object var1) {
                AdServiceUrlRequestAction.this.mLoadAdCallback.loadFail("Ad request canceled");
            }
        };
    }

    private AdServiceUrlAsyncTask.a c() {
        return new AdServiceUrlAsyncTask.a() {
            public void a(Object var1) {
                String var3;
                if(var1 instanceof BaseResponse) {
                    if(SharedPreferenceUtil.b(AdServiceUrlRequestAction.this.mContext, "failureTimes", "exposure_fail", "click_fail", "req_fail") && AdServiceUrlRequestAction.this.a.h().equals("0")) {
                        AdServiceUrlRequestAction.this.i = new ErrorReportDataAsyncTask();
                        AdServiceUrlRequestAction.this.i.a(AdServiceUrlRequestAction.this.b());
                        AdServiceUrlRequestAction.this.i.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ErrorReportData[]{AdServiceUrlRequestAction.this.h});
                    }

                    BaseResponse var2 = (BaseResponse)var1;
                    if(var2.isSucess()) {
                        Log.i("exposure_click", "Reported success!");
                        AdServiceUrlRequestAction.this.mLoadAdCallback.loadSuccess();
                        AdServiceUrlRequestAction.this.e = AdServiceUrlRequestAction.this.a.f();
                        AdServiceUrlRequestAction.this.f = AdServiceUrlRequestAction.this.a.g();
                        if(AdServiceUrlRequestAction.this.e != null && !AdServiceUrlRequestAction.this.e.equals("")) {
                            var3 = StringUtils.c(AdServiceUrlRequestAction.this.a.b());
                            FileUtils.a(var3, System.currentTimeMillis() + "");
                        }

                        if(AdServiceUrlRequestAction.this.f != null && !AdServiceUrlRequestAction.this.f.equals("")) {
                            var3 = StringUtils.c(AdServiceUrlRequestAction.this.a.b.a());
                            FileUtils.b(var3, System.currentTimeMillis() + "");
                        }
                    }
                } else if(var1 instanceof Exception) {
                    Log.i("exposure_click", "Reported failed   " + ((Exception)var1).getMessage());
                    if(!(var1 instanceof InterException)) {
                        if(AdServiceUrlRequestAction.this.a.h() != null && AdServiceUrlRequestAction.this.a.h().equals("0")) {
                            SharedPreferenceUtil.a(AdServiceUrlRequestAction.this.mContext, "failureTimes", "exposure_fail");
                        } else if(AdServiceUrlRequestAction.this.a.h() != null && AdServiceUrlRequestAction.this.a.h().equals("1")) {
                            SharedPreferenceUtil.a(AdServiceUrlRequestAction.this.mContext, "failureTimes", "click_fail");
                        }
                    }

                    Throwable var4 = ((Exception)var1).getCause();
                    var3 = var4 != null?var4.getMessage():((Exception)var1).getMessage();
                    AdServiceUrlRequestAction.this.mLoadAdCallback.loadFail(var3);
                }

            }

            public void a() {
            }

            public void b(Object var1) {
                AdServiceUrlRequestAction.this.mLoadAdCallback.loadFail("Ad request canceled");
            }
        };
    }

    public void a() {
        if(this.c != null) {
            this.c.a(null);
        }

        if(this.i != null) {
            this.i.a(null);
        }

        if(this.c != null && !this.c.isCancelled()) {
            this.c.cancel(true);
            this.c = null;
        }

    }
}
