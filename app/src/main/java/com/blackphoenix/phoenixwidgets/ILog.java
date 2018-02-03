package com.blackphoenix.phoenixwidgets;

import android.util.Log;

/**
 * Created by shree on 1/2/2018.
 */
public class ILog {

    public static void print(String title, String message){
        Log.e(""+title,""+message);
    }

    public static void error(String errorTitle, String errorMessage){
        Log.e(""+errorTitle,""+errorMessage);
    }

}
