package com.interactive.suspend.ad.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * Created by csc on 15/11/4.
 */
public class AvDatabaseHelper extends SQLiteOpenHelper {

    private static final String NAME = "com_av_adsdk.db";
    private static final int VERSION = 12;
    private static AvDatabaseHelper sInstance;

    public synchronized static AvDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AvDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    AvDatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AdColumns.CREATE);
        db.execSQL(AdColumns.CREATE_SUBSCRIBE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("VC-DB","onUpgrade");
        db.execSQL(AdColumns.DELETE);
        db.execSQL(AdColumns.CREATE);
        db.execSQL(AdColumns.DELETE_SUBSCRIBE);
        db.execSQL(AdColumns.CREATE_SUBSCRIBE);
//        if(oldVersion <= 11){
//            db.execSQL(AdColumns.INSERT_TABLE_NAME_LOADING_TIME);
//            L.d("VC-DB","onUpgrade-INSERT_TABLE_NAME_LOADING_TIME");
//        }

    }

    public static boolean isAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
