//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.interactive.suspend.ad.task.e;

public class h {
    private com.interactive.suspend.ad.model.c a;
    private com.interactive.suspend.ad.g b;
    private com.interactive.suspend.ad.f c;
    private com.interactive.suspend.ad.model.f.a d;
    private String e = "";
    private String f = "";
    private Context g;
    private com.interactive.suspend.ad.model.a h;
    private com.interactive.suspend.ad.e i;

    public h(com.interactive.suspend.ad.model.f.a var1, com.interactive.suspend.ad.g var2, Context var3) {
        this.b = var2;
        this.d = var1;
        this.g = var3;
        this.h = new com.interactive.suspend.ad.model.a(var3);
    }

    public void a(com.interactive.suspend.ad.model.c var1) {
        this.a = var1;
        if(this.a == null) {
            com.interactive.suspend.ad.task.g.a().a("request is null");
            throw new IllegalArgumentException("ad request must be set");
        } else if(this.b == null) {
            throw new IllegalArgumentException("callback must be set");
        } else if(this.d == null) {
            throw new IllegalArgumentException("builder must be set");
        } else {
            this.c = new f();
            this.c.a(this.c());
            this.c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new com.interactive.suspend.ad.model.c[]{this.a});
        }
    }

    private com.interactive.suspend.ad.e.a b() {
        return new com.interactive.suspend.ad.e.a() {
            public void a(Object var1) {
                if(var1 instanceof com.interactive.suspend.ad.model.f) {
                    com.interactive.suspend.ad.model.f var2 = (com.interactive.suspend.ad.model.f)var1;
                    if(var2.isSucess()) {
                        com.interactive.suspend.ad.task.f.a(h.this.g, "failureTimes", "exposure_fail", "click_fail", "req_fail");
                        h.this.b.a();
                    }
                } else if(var1 instanceof Exception) {
                    Throwable var4 = ((Exception)var1).getCause();
                    String var3 = var4 != null?var4.getMessage():((Exception)var1).getMessage();
                    h.this.b.a(var3);
                }

            }

            public void a() {
            }

            public void b(Object var1) {
                h.this.b.a("Ad request canceled");
            }
        };
    }

    private com.interactive.suspend.ad.f.a c() {
        return new com.interactive.suspend.ad.f.a() {
            public void a(Object var1) {
                String var3;
                if(var1 instanceof com.interactive.suspend.ad.model.f) {
                    if(com.interactive.suspend.ad.task.f.b(h.this.g, "failureTimes", "exposure_fail", "click_fail", "req_fail") && h.this.a.h().equals("0")) {
                        h.this.i = new com.interactive.suspend.ad.e();
                        h.this.i.a(h.this.b());
                        h.this.i.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new com.interactive.suspend.ad.model.a[]{h.this.h});
                    }

                    com.interactive.suspend.ad.model.f var2 = (com.interactive.suspend.ad.model.f)var1;
                    if(var2.isSucess()) {
                        Log.i("exposure_click", "Reported success!");
                        h.this.b.a();
                        h.this.e = h.this.a.f();
                        h.this.f = h.this.a.g();
                        if(h.this.e != null && !h.this.e.equals("")) {
                            var3 = com.interactive.suspend.ad.task.k.c(h.this.a.b());
                            com.interactive.suspend.ad.task.j.a(var3, System.currentTimeMillis() + "");
                        }

                        if(h.this.f != null && !h.this.f.equals("")) {
                            var3 = com.interactive.suspend.ad.task.k.c(h.this.a.b.a());
                            com.interactive.suspend.ad.task.j.b(var3, System.currentTimeMillis() + "");
                        }
                    }
                } else if(var1 instanceof Exception) {
                    Log.i("exposure_click", "Reported failed   " + ((Exception)var1).getMessage());
                    if(!(var1 instanceof  com.interactive.suspend.ad.model.g)) {
                        if(h.this.a.h() != null && h.this.a.h().equals("0")) {
                            com.interactive.suspend.ad.task.f.a(h.this.g, "failureTimes", "exposure_fail");
                        } else if(h.this.a.h() != null && h.this.a.h().equals("1")) {
                            com.interactive.suspend.ad.task.f.a(h.this.g, "failureTimes", "click_fail");
                        }
                    }

                    Throwable var4 = ((Exception)var1).getCause();
                    var3 = var4 != null?var4.getMessage():((Exception)var1).getMessage();
                    h.this.b.a(var3);
                }

            }

            public void a() {
            }

            public void b(Object var1) {
                h.this.b.a("Ad request canceled");
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
