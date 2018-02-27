package com.example.degus.accesspedia.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degus.accesspedia.ContentMaker;
import com.example.degus.accesspedia.R;
import com.example.degus.accesspedia.SpeechUtils;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

/**
 * Created by Dominik Nowak on 26.07.2017.
 */
public class MainActivity extends AbstractMainActivity {

    private TextView text;
    private ImageButton microphoneButton;
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
        microphoneButton = (ImageButton) findViewById(R.id.micro);

        microphoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    speechRecognizer.startListening(speechUtils.getRecognizerIntent(getPackageName()));

                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), getString(R.string.speech_not_allowed),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Toast.makeText(getBaseContext(), getString(R.string.recording_started), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int error) {
        Log.e("speech", String.valueOf(error));
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
        String result = "";
        try {
            result = contentMaker.getContent(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), getString(R.string.could_not_get_data), Toast.LENGTH_SHORT).show();
        }
        text.setText(Html.fromHtml(result));
    }

    @Override
    protected void onDestroy() {
        speechRecognizer.destroy();
        super.onDestroy();
    }
}
