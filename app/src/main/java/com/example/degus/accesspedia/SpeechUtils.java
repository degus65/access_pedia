package com.example.degus.accesspedia;

import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.Locale;

/**
 * Created by Dominik Nowak on 06.08.2017.
 */

public class SpeechUtils {

    public Intent getRecognizerIntent(String packageName) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        return intent;
    }

}
