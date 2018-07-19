//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.model;

public class f {
    private String mData;
    protected int error_code = -1;
    protected String message;

    public int getError_code() {
        return this.error_code;
    }

    public void setError_code(int var1) {
        this.error_code = var1;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String var1) {
        this.message = var1;
    }

    public f(String var1) {
        this.mData = var1;
    }

    public String getRawData() {
        return this.mData;
    }

    public boolean isSucess() {
        return this.error_code == 0;
    }

    public interface a {
        f b(String var1);
    }
}
