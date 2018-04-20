package com.example.degus.accesspedia;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

/**
 * Created by Dominik Nowak on 14/04/2018.
 */
public class SharedPreferencesManager {

    private SharedPreferences preferences;
    private final static String PREF_KEY = "pref_key";
    private final static String PREF_MUTE = "mute_key";
    private final static String PREF_LANG = "lang_key";
    private final static String PREF_VOICE_INPUT = "input_key";

    public SharedPreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    public void saveMutePref(boolean isMuted) {
        preferences.edit().putBoolean(PREF_MUTE, isMuted).apply();
    }

    public void saveLangPref(String lang) {
        preferences.edit().putString(PREF_LANG, lang).apply();
    }

    public boolean getMutePref() {
        return preferences.getBoolean(PREF_MUTE, false);
    }

    public String getLangPref() {
        return preferences.getString(PREF_LANG, Locale.getDefault().getLanguage());
    }

    public void saveInputPref(boolean isVoice) {
        preferences.edit().putBoolean(PREF_VOICE_INPUT, isVoice).apply();
    }

    public boolean getIsVoiceInputPref() {
        return preferences.getBoolean(PREF_VOICE_INPUT, false);
    }
}
