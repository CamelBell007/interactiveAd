package com.interactive.suspend.ad.html.load;

import java.util.List;

/**
 * Created by hongwu on 4/6/17.
 */

public interface AdCacheTaskListener<T> {
    void onLoadAdCacheSuccess(List<T> data);
}
