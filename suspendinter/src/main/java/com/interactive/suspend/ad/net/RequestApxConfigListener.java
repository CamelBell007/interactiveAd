package com.interactive.suspend.ad.net;

import com.interactive.suspend.ad.model.ApxAdConfigBean;

/**
 * Created by drason on 2018/5/9.
 */

public interface RequestApxConfigListener {
    void onRequestSuccess(ApxAdConfigBean dopBean);
    void onRequestFailed(String errorMsg);
}
