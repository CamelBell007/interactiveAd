//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad;

import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.text.TextUtils;

import com.interactive.suspend.ad.model.TmResponse;
import com.interactive.suspend.ad.task.g;

public class d {
    private com.interactive.suspend.ad.model.d a;
    private com.interactive.suspend.ad.i b;
    private com.interactive.suspend.ad.c c;
    private com.interactive.suspend.ad.model.f.a d;
    private com.interactive.suspend.ad.task.a e;
    private Context f;

    public d(com.interactive.suspend.ad.model.f.a var1, com.interactive.suspend.ad.i var2, Context var3) {
        this.b = var2;
        this.d = var1;
        this.e = com.interactive.suspend.ad.task.a.a(var3);
        this.f = var3;
    }



    private com.interactive.suspend.ad.c.a b() {
        return new com.interactive.suspend.ad.c.a() {
            public void a(Object var1) {
                if(var1 == null) {
                    d.this.b.a("Ad request failed");
                } else if(var1 instanceof TmResponse) {
                    TmResponse var2 = (TmResponse)var1;
                    if(var2.isSucess()) {
                        d.this.b.a(var2);
                        int var3 = (int)var2.getExpire();
                        if(var3 > 0 && d.this.e != null) {
                            d.this.e.a(d.this.a.b(), var2.getRawData(), var3);
                        }
                    } else {
                        d.this.b.a(var2.getMessage());
                    }
                } else if(var1 instanceof Exception) {
                    com.interactive.suspend.ad.task.f.a(d.this.f, "failureTimes", "req_fail");
                    Throwable var4 = ((Exception)var1).getCause();
                    String var6 = var4 != null?var4.getMessage():((Exception)var1).getMessage();
                    d.this.b.a(var6);
                } else if(var1 instanceof com.interactive.suspend.ad.model.f) {
                    com.interactive.suspend.ad.model.f var5 = (com.interactive.suspend.ad.model.f)var1;
                    if(!var5.isSucess()) {
                        d.this.b.a(var5.getMessage());
                    }
                }

            }

            public void a() {
                d.this.b.a();
            }

            public void b(Object var1) {
                d.this.b.a("Ad request canceled");
            }
        };
    }

    public void a(com.interactive.suspend.ad.model.d var1) {
        this.a = var1;
        if(this.a == null) {
            g.a().a("request is null");
            throw new IllegalArgumentException("ad request must be set");
        } else if(this.b == null) {
            throw new IllegalArgumentException("callback must be set");
        } else if(this.d == null) {
            throw new IllegalArgumentException("builder must be set");
        } else {
            String var2 = "";
            if(this.e != null) {
                var2 = this.e.a(this.a.b());
            }

            if(TextUtils.isEmpty(var2)) {
                this.c = new com.interactive.suspend.ad.c();
                this.c.a(this.b());
                this.c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new com.interactive.suspend.ad.model.d[]{this.a});
            } else {
                com.interactive.suspend.ad.model.f var3 = this.d.b(var2);
                if(var3.isSucess()) {
                    this.b.a(var3);
                } else {
                    this.b.a(var3.getMessage());
                }
            }

        }
    }

    public void a() {
        if(this.c != null) {
            this.c.a(null);
        }

        if(this.c != null && !this.c.isCancelled() && this.c.getStatus() == Status.RUNNING) {
            this.c.cancel(true);
            this.c = null;
        }

    }
}
