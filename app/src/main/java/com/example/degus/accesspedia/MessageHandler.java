package com.example.degus.accesspedia;

import android.util.Log;

/**
 * Created by Dominik Nowak on 25/02/2018.
 */

public class MessageHandler {

    private final static String URI_TITLE = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&titles=";
    private final static String SPACE = "%20";


    public static String convertMessageToURL(String message) {
        Log.i("speech", message.toString());
        return URI_TITLE + handleWhiteSpaces(message);
    }

    public static String handleWhiteSpaces(String message) {
        return message.trim().replaceAll(" ", SPACE);
    }
}
