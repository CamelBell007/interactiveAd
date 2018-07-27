package com.interactive.suspend.ad.constant;

/**
 * Created by Administrator on 2018/7/26.
 */

public enum AsyncStatus {
        /**
         * Indicates that the task has not been executed yet.
         */
        PENDING,
        /**
         * Indicates that the task is running.
         */
        RUNNING,
        /**
         * Indicates that {@link android.os.AsyncTask#onPostExecute} has finished.
         */
        FINISHED,
    }
