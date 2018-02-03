package com.blackphoenix.phoenixwidgets;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by PC on 01-01-2018.
 */
public class TxtSpeech {
    private TextToSpeech tts;
    Context context;

    public TxtSpeech(Context context) {

        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });
    }


    public void Speak(String S_str) {
        tts.speak(S_str, TextToSpeech.QUEUE_FLUSH, null);
    }
}
