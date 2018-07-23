package com.interactive.suspend.ad.taskObject;

import android.content.Context;
import android.content.pm.PackageManager;

public class PermissionManager {
    public static boolean checkPermissionIsGrant(Context paramContext, String paramString) {
        try {
            return paramContext.checkCallingOrSelfPermission(paramString) == PackageManager.PERMISSION_GRANTED;
        } catch (Exception localException) {
        }
        return false;
    }
}
