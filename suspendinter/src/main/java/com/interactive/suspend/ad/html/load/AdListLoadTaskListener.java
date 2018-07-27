package com.interactive.suspend.ad.html.load;

import java.util.List;

/**
 * Created by csc on 15/11/4.
 */
public interface AdListLoadTaskListener<T> {
    void onLoadAdListSuccess(List<T> data);
    void onLoadAdListStart();
    void onLoadAdListFail(Error error);
}
