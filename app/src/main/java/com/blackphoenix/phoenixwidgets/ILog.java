package com.blackphoenix.phoenixwidgets;

import android.util.Log;

/**
 * Created by Praba on 1/2/2018.
 */

public class ILog {

    public static void print(String title, String message){
        Log.e(""+title,""+message);
    }

    public static void error(String errorTitle, String errorMessage){
        Log.e(""+errorTitle,""+errorMessage);
    }

    /*public void appendLog(String mytag, String value, String filename) {
        File logFile = new File("sdcard/" + filename + ".txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            String con=mytag + "\t" + value;
            buf.append(con);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

}
