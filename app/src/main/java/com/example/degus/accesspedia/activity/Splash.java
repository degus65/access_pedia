package com.example.degus.accesspedia.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.example.degus.accesspedia.SharedPreferencesManager;
import com.example.degus.accesspedia.activity.main.MainActivity;

import java.util.Locale;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(new SharedPreferencesManager(this).getLangPref());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        SystemClock.sleep(1000);
        finish();
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
