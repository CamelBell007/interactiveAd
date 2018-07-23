//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad;

import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.text.TextUtils;

import com.interactive.suspend.ad.http.BaseResponse;
import com.interactive.suspend.ad.http.FloatAdParams;
import com.interactive.suspend.ad.http.TmResponse;
import com.interactive.suspend.ad.taskObject.SharedPreferenceUtil;
import com.interactive.suspend.ad.taskObject.ThreadStackLog;

public class FloatAdRequestAction {
    private FloatAdParams mFloatAdParams;
    private RequestInterface mRequestInterface;
    private FloatAdAsyncTask mFloatAdAsyncTask;
    private BaseResponse.loadFinish mLoadFinishInterface;
    private com.interactive.suspend.ad.taskObject.a e;
    private Context mContext;

    public FloatAdRequestAction(BaseResponse.loadFinish var1, RequestInterface var2, Context var3) {
        this.mRequestInterface = var2;
        this.mLoadFinishInterface = var1;
        this.e = com.interactive.suspend.ad.taskObject.a.a(var3);
        this.mContext = var3;
    }



    private FloatAdAsyncTask.FloatAdTaskCallBack createFloatAdTaskCallBack() {
        return new FloatAdAsyncTask.FloatAdTaskCallBack() {
            public void postExecute(Object responseObject) {
                if(responseObject == null) {
                    FloatAdRequestAction.this.mRequestInterface.requestFail("Ad request failed");
                } else if(responseObject instanceof TmResponse) {
                    TmResponse response = (TmResponse)responseObject;
                    if(response.isSucess()) {
                        FloatAdRequestAction.this.mRequestInterface.requestSuccess(response);
                        int expireTime = (int)response.getExpire();
                        if(expireTime > 0 && FloatAdRequestAction.this.e != null) {
                            FloatAdRequestAction.this.e.a(FloatAdRequestAction.this.mFloatAdParams.b(), response.getRawData(), expireTime);
                        }
                    } else {
                        FloatAdRequestAction.this.mRequestInterface.requestFail(response.getMessage());
                    }
                } else if(responseObject instanceof Exception) {
                    SharedPreferenceUtil.a(FloatAdRequestAction.this.mContext, "failureTimes", "req_fail");
                    Throwable throwable = ((Exception)responseObject).getCause();
                    String var6 = throwable != null?throwable.getMessage():((Exception)responseObject).getMessage();
                    FloatAdRequestAction.this.mRequestInterface.requestFail(var6);
                } else if(responseObject instanceof BaseResponse) {
                    BaseResponse baseResponse = (BaseResponse)responseObject;
                    if(!baseResponse.isSucess()) {
                        FloatAdRequestAction.this.mRequestInterface.requestFail(baseResponse.getMessage());
                    }
                }

            }

            public void preExecute() {
                FloatAdRequestAction.this.mRequestInterface.preExecute();
            }

            public void cancel(Object var1) {
                FloatAdRequestAction.this.mRequestInterface.requestFail("Ad request canceled");
            }
        };
    }

    public void requestAdInfo(FloatAdParams floatAdParams) {
        this.mFloatAdParams = floatAdParams;
        if(this.mFloatAdParams == null) {
            ThreadStackLog.a().a("request is null");
            throw new IllegalArgumentException("ad params request must be set");
        } else if(this.mRequestInterface == null) {
            throw new IllegalArgumentException("callback must be set");
        } else if(this.mLoadFinishInterface == null) {
            throw new IllegalArgumentException("builder must be set");
        } else {
            String var2 = "";
            if(this.e != null) {
                var2 = this.e.a(this.mFloatAdParams.b());
            }

            if(TextUtils.isEmpty(var2)) {
                this.mFloatAdAsyncTask = new FloatAdAsyncTask();
                this.mFloatAdAsyncTask.setFloatAdTaskCallBack(this.createFloatAdTaskCallBack());
                this.mFloatAdAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new FloatAdParams[]{this.mFloatAdParams});
            } else {
                BaseResponse var3 = this.mLoadFinishInterface.getResponse(var2);
                if(var3.isSucess()) {
                    this.mRequestInterface.requestSuccess(var3);
                } else {
                    this.mRequestInterface.requestFail(var3.getMessage());
                }
            }

        }
    }

    public void releseResource() {
        if(this.mFloatAdAsyncTask != null) {
            this.mFloatAdAsyncTask.setFloatAdTaskCallBack(null);
        }

        if(this.mFloatAdAsyncTask != null && !this.mFloatAdAsyncTask.isCancelled() && this.mFloatAdAsyncTask.getStatus() == Status.RUNNING) {
            this.mFloatAdAsyncTask.cancel(true);
            this.mFloatAdAsyncTask = null;
        }

    }
}
