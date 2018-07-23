package com.interactive.suspend.ad.net;

import com.interactive.suspend.ad.model.AdConfigBean;

/**
 * Created by drason on 2018/5/9.
 */

public interface RequestDopConfigListener {
    void onRequestSuccess(AdConfigBean dopBean);
    void onRequestFailed(String errorMsg);
}
