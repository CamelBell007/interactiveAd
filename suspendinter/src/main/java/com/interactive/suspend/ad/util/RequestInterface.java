//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.util;

import com.interactive.suspend.ad.http.BaseResponse;
public interface RequestInterface {
    void requestSuccess(BaseResponse var1);

    void requestFail(String var1);

    void preExecute();
}
