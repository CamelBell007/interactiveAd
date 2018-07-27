package com.interactive.suspend.ad.util;

import android.content.Context;

import com.interactive.suspend.ad.db.AdInfo;
import com.interactive.suspend.ad.db.AvDatabaseUtils;
import com.interactive.suspend.ad.model.AppConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guojia on 2016/11/3.
 */

public class AdUtils {
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<AdInfo> getValidAdInfo(Context context, List<AdInfo> adInfoList, String log){
//        L.e("VC--DB-->getValidAdInfo", log);
        if(context == null || adInfoList == null){
//            L.e("VC--DB-->getValidAdInfo", "context == null || adInfoList == null");
            return null;
        }
        if(adInfoList.size() == 0){
//            L.e("VC--DB-->getValidAdInfo", "adInfoList.size() == 0");
            return null;
        }
        ArrayList<AdInfo> avalidInfos = new ArrayList<>();
        ArrayList<AdInfo> expiredInfos = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        for(AdInfo adInfo:adInfoList){
//            L.e("VC--DB-->getValidAdInfo", "PKG-NAME:"+adInfo.pkgname+"     "+"currentTime："+currentTime);
//            L.e("VC--DB-->getValidAdInfo",  "PKG-NAME:"+adInfo.pkgname+"     "+"loadingTime："+adInfo.loadingTime);
//            L.e("VC--DB-->getValidAdInfo",  "PKG-NAME:"+adInfo.pkgname+"     "+"AdValidTime："+AppConfig.getInstance(context).getAdValidTime());
            if(currentTime - adInfo.loadingTime <  AppConfig.getInstance(context).getAdValidTime()
                    &&AppConfig.getInstance(context).getAdValidTime()> 0 ){
//                L.e("VC--DB-->getValidAdInfo",  "PKG-NAME:"+adInfo.pkgname+"     "+"ad is valid,keep and use");
                avalidInfos.add(adInfo);
            }else{
//                L.e("VC--DB-->getValidAdInfo", "PKG-NAME:"+adInfo.pkgname+"     "+"ad is expired,remove");
                expiredInfos.add(adInfo);
            }
        }
        deleteExpiredAdInfoFromDB(context,expiredInfos);
        return avalidInfos;
    }

    public static void deleteExpiredAdInfoFromDB(final Context context, final List<AdInfo>  ExpiredAdInfos){
//        L.e("VC--DB-->delete expireTime AdInfo FromDB", "" );
        if(context == null || ExpiredAdInfos == null){
            return;
        }
        if(ExpiredAdInfos.size() == 0){
            return;
        }
//        L.e("VC--DB-->delete expireTime AdInfo FromDB", "ExpiredAdInfos" );
        new Thread(new Runnable() {
            @Override
            public void run() {
                AvDatabaseUtils.removeDataCollection(context,ExpiredAdInfos);
            }
        }).start();

    }

}
