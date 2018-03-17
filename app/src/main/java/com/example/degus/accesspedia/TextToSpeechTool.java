package com.example.degus.accesspedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Dominik Nowak on 28/02/2018.
 */

public class TextToSpeechTool {

    public static final int CHECK_TTS_DATA = 1000;
    private final TextToSpeech textToSpeech;
    private boolean isMuted = false;

    public TextToSpeechTool(final Context context) {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.getDefault());
                Toast.makeText(context, "TextToSpeechInited", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void speak(String toSay) {
        if (!isMuted) {
            Log.d("speak", "speak");
            textToSpeech.speak(toSay, TextToSpeech.QUEUE_ADD, null, null);
        }
    }

    public void stopAndShutdown() {
        textToSpeech.stop();
        textToSpeech.shutdown();
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
        textToSpeech.stop();
    }

    public void unMute() {
        isMuted = false;
    }
}
