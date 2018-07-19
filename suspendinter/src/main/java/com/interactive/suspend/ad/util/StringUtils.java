package com.interactive.suspend.ad.util;

/**
 * Created by Administrator on 2018/6/26.
 */

public class StringUtils {

    /**
     * judge string is empty.
     * @param string
     * @return
     */
    public static boolean isEmpty(String string){
        if(string == null){
            return true;
        }
        if(string.trim().length() == 0){
            return true;
        }
        return false;
    }
}
