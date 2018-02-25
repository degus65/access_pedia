package com.example.degus.accesspedia.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degus.accesspedia.ContentMaker;
import com.example.degus.accesspedia.R;
import com.example.degus.accesspedia.SpeechUtils;

import java.util.concurrent.ExecutionException;

/**
 * Created by Dominik Nowak on 26.07.2017.
 */
public class MainActivity extends AbstractMainActivity {

    private TextView text;
    private Button button;
    private SpeechUtils speechUtils;
    private SpeechRecognizer speechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speechUtils = new SpeechUtils();
        initSpeechRecognizer();
        findViews();
    }

    private void findViews() {
        text = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    Log.i("speech", "button");
                    speechRecognizer.startListening(speechUtils.getRecognizerIntent(getPackageName()));

                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Speech recognition is not supported in this device.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        Log.i("speech", "create");
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Toast.makeText(getBaseContext(), "Voice recording starts", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int error) {
        Log.i("speech", String.valueOf(error));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String requiredPermission = Manifest.permission.RECORD_AUDIO;
            if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{requiredPermission}, 101);
            }
        }
    }

    @Override
    public void onResults(Bundle results) {
        ContentMaker contentMaker = new ContentMaker();
        String result = null;
        try {
            result = contentMaker.getContent(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Nie udało się pobrać danych. Spróbuj ponownie", Toast.LENGTH_SHORT).show();
        }
        text.setText(result);
    }
}
