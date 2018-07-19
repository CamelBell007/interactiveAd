package com.interactive.suspend.ad.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by VC on 2018/6/28.
 *
 */

public class ApplicationUtil {

    public  static void installApk(Context context,File file) {
        if(context == null){
            return;
        }
        if(file == null){
            return;
        }
        Intent intent  = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        String dataAndType = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(file), dataAndType);
        context.startActivity(intent);
    }
}
