package com.example.degus.accesspedia.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.view.menu.MenuBuilder;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.degus.accesspedia.R;
import com.example.degus.accesspedia.SharedPreferencesManager;
import com.example.degus.accesspedia.activity.AboutActivity;

import java.util.Locale;

/**
 * Created by Dominik Nowak on 09/03/2018.
 */

public abstract class ContextMenuMainActivity extends AbstractMainActivity {

    private boolean isMuted = false;
    private SharedPreferencesManager preferences;
    private Menu menu;


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        preferences = new SharedPreferencesManager(this);
        return true;
    }

    protected void setPredefinedMuteSettings() {
        MenuItem item = menu.findItem(R.id.action_mute);
        isMuted = preferences.getMutePref();
        MainActivity mainActivity = (MainActivity) this;
        if (isMuted) {
            handleMute(mainActivity, item);
        } else {
            handleUnMute(mainActivity, item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mute:
                handleMuteValue(item);
                break;

            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;

            case R.id.lang_en:
                setLocale("en");
                break;

            case R.id.lang_pl:
                setLocale("pl");
                break;

            case R.id.lang_it:
                setLocale("it");
                break;

            default:
                break;
        }
        return true;
    }

    private void handleMuteValue(MenuItem item) {
        MainActivity mainActivity = (MainActivity) this;
        if (isMuted) {
            handleUnMute(mainActivity, item);
        } else {
            handleMute(mainActivity, item);
        }
        isMuted = !isMuted;
        preferences.saveMutePref(isMuted);
    }

    private void handleUnMute(MainActivity mainActivity, MenuItem item) {
        mainActivity.unMuteTextToSpeech();
        item.setTitle(getString(R.string.mute));
        item.setIcon(R.drawable.ic_unmuted);
    }

    private void handleMute(MainActivity mainActivity, MenuItem item) {
        mainActivity.muteTextToSpeech();
        item.setTitle(getString(R.string.un_mute));
        item.setIcon(R.drawable.ic_muted);
    }

    public void setLocale(String lang) {
        preferences.saveLangPref(lang);
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        ((MainActivity) this).setLocale(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}
