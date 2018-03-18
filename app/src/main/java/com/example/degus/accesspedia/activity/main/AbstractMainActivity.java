package com.example.degus.accesspedia.activity.main;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

;

/**
 * Created by Dominik Nowak on 25/02/2018.
 */

public abstract class AbstractMainActivity extends AppCompatActivity implements RecognitionListener {

    @Override
    public void onBeginningOfSpeech() {
        Log.i("speech", "begin");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i("speech", "buf");
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i("speech", "onpart");
    }

    @Override
    public void onEndOfSpeech() {
        Log.i("speech", "end");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i("speech", "onEvent");
    }
}
