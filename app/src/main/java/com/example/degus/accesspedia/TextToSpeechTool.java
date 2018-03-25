package com.example.degus.accesspedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.example.degus.accesspedia.content.Splitter;

import java.util.Locale;

/**
 * Created by Dominik Nowak on 28/02/2018.
 */

public class TextToSpeechTool extends TextToSpeech {

    public static final int CHECK_TTS_DATA = 1000;
    private boolean isMuted = false;

    public TextToSpeechTool(final Context context) {
        super(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        this.setLanguage(Locale.getDefault());
    }

    public void speak(String toSay) {
        if (!isMuted) {
            if (toSay.length() > TextToSpeech.getMaxSpeechInputLength()) {
                toSay = Splitter.abbreviate(toSay, TextToSpeech.getMaxSpeechInputLength());
            }
            this.setLanguage(Locale.getDefault());
            Log.d("speak", toSay);
            this.speak(toSay, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public static void installTextToSpeech(Context context) {
        Intent installIntent = new Intent();
        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        context.startActivity(installIntent);
    }

    public static void startCheckingIsTextToSpeechAvailable(Activity activity) {
        Intent ttsIntent = new Intent();
        ttsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        activity.startActivityForResult(ttsIntent, TextToSpeechTool.CHECK_TTS_DATA);
    }

    public void mute() {
        isMuted = true;
        this.stop();
    }

    public void unMute() {
        isMuted = false;
    }
}
