package com.interactive.suspend.ad.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hongwu on 11/14/16.
 */

public class FileUtil {

    public static final String APP_CONFIG = "app.config";

    public static String readFromFile(String path) {
        if (path == null) {
            return null;
        }
        StringBuffer sb = null;
        String line;
        BufferedReader reader = null;

        try {
            sb = new StringBuffer();
            File file = new File(path);
            if (!file.exists()) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            Log.e(LogUtil.TAG,e.getMessage());
        } catch (Error e) {
            Log.e(LogUtil.TAG,e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                Log.e(LogUtil.TAG,e.getMessage());
            }
        }
        return sb == null ? null : sb.toString();
    }

    public static void writeToFile(String path, String str) {
        if (path == null || str == null) {
            return;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(str.getBytes());
        } catch (Exception e) {
            Log.e(LogUtil.TAG,e.getMessage());
        } catch (Error e) {
            Log.e(LogUtil.TAG,e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                Log.e(LogUtil.TAG,e.getMessage());
            }
        }
    }

    public static String getSuffix(String name) {
        String end = "";
        if (name == null || name == "") {
            return end;
        }

        if (name.endsWith("apk")) {
            end = "apk";
        }

        if (name.endsWith("bin")) {
            end = "bin";
        }

        return end;
    }

    public static void Copy(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isFolderExists(String strFolder) {
        if (TextUtils.isEmpty(strFolder)) {
            return false;
        }
        try {
            File file = new File(strFolder);
            return file.exists() && file.isDirectory();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isFileExists(String strFile) {
        if (TextUtils.isEmpty(strFile)) {
            return false;
        }
        try {
            File file = new File(strFile);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isFileEmpty(String strFile) {
        if (!isFileExists(strFile)) {
            return true;
        }
        try {
            File file = new File(strFile);
            return file.length() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isFileCanRead(String strFile) {
        if (!isFileExists(strFile)) {
            return false;
        }
        try {
            File file = new File(strFile);
            return file.canRead();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getFileSize(String strFile) {
        if (!isFileExists(strFile)) {
            return 0;
        }
        try {
            File file = new File(strFile);
            return file.length();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static String getExternalPkgFiles(Context context, String pkgname) {
        if (context == null || TextUtils.isEmpty(pkgname) || !ExistSDCard()) {
            return "";
        }
        File sdFile = context.getExternalCacheDir();
        if (sdFile == null) {
            return "";
        }
        String excache = sdFile.getPath();
        if (TextUtils.isEmpty(excache)) {
            return "";
        }
        int findex = excache.substring(0, excache.length() - 1).lastIndexOf('/');
        if (findex > 0) {
            findex = excache.substring(0, findex).lastIndexOf('/');
            return excache.substring(0, findex) + "/" + pkgname + "/files";     //默认真实路径是 files
        }
        return "";
    }

}
