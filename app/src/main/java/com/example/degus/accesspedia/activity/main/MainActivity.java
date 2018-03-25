package com.example.degus.accesspedia.activity.main;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degus.accesspedia.R;
import com.example.degus.accesspedia.SpeechRecognitionUtils;
import com.example.degus.accesspedia.TextToSpeechTool;
import com.example.degus.accesspedia.content.ContentMaker;
import com.example.degus.accesspedia.content.ContentModel;

import org.json.JSONException;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dominik Nowak on 26.07.2017.
 */
public class MainActivity extends ContextMenuMainActivity {

    private TextView header;
    private ImageView articleImage;
    private TextView content;
    private ImageButton microphoneButton;
    private SpeechRecognizer speechRecognizer;
    private TextToSpeechTool textToSpeechTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpeechRecognizer();
        findViews();
        TextToSpeechTool.startCheckingIsTextToSpeechAvailable(this);
    }

    private void findViews() {
        header = (TextView) findViewById(R.id.header);
        articleImage = (ImageView) findViewById(R.id.articleImage);
        content = (TextView) findViewById(R.id.textContent);
        microphoneButton = (ImageButton) findViewById(R.id.micro);
        microphoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    microphoneButton.animate().alpha(1.0f).setDuration(100).start();
                    speechRecognizer.startListening(SpeechRecognitionUtils.getRecognizerIntent(getPackageName()));

                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), getString(R.string.speech_not_allowed),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                microphoneButton.animate().alpha(0.1f).setDuration(1000).start();
            }
        });
    }

    private void initSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        textToSpeechTool.stop();
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
        ContentMaker contentMaker = new ContentMaker(this);
        ContentModel contentModel;
        try {
            contentModel = contentMaker.getContent(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
            setContent(contentModel);
            if (textToSpeechTool != null) {
                textToSpeechTool.speak(Html.fromHtml(contentModel.getHeader() + " " + contentModel.getContent()).toString());
            } else {
                Toast.makeText(getBaseContext(), getString(R.string.text_to_speech_failed), Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), getString(R.string.could_not_get_data), Toast.LENGTH_SHORT).show();
        }
    }

    private void setContent(ContentModel contentModel) {
        header.setText(Html.fromHtml(contentModel.getHeader()));
        content.setText(Html.fromHtml(contentModel.getContent()));
        if (contentModel.getImage() != null) {
            articleImage.setImageBitmap(contentModel.getImage());
        } else {
            articleImage.setImageResource(android.R.color.transparent);
        }
    }

    @Override
    protected void onDestroy() {
        speechRecognizer.destroy();
        if (textToSpeechTool != null) {
            textToSpeechTool.stop();
            textToSpeechTool.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TextToSpeechTool.CHECK_TTS_DATA) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                textToSpeechTool = new TextToSpeechTool(this);
            } else {
                TextToSpeechTool.installTextToSpeech(this);
            }
        }
    }

    public void muteTextToSpeech() {
        textToSpeechTool.mute();
    }

    public void unMuteTextToSpeech() {
        textToSpeechTool.unMute();
    }

    public void setLocale(Locale locale) {
        int result = textToSpeechTool.isLanguageAvailable(locale);
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(getBaseContext(), getString(R.string.unavaible_language), Toast.LENGTH_SHORT).show();
        } else {
            textToSpeechTool.setLanguage(locale);
        }
    }
}
