package com.example.degus.accesspedia;

import android.util.Log;

import java.util.List;

/**
 * Created by Dominik Nowak on 25/02/2018.
 */

public class MessageHandler {

    private List<String> words;
    private final static String URI_TITLE="https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&titles=";
    private final static String SPACE="%20";

    public MessageHandler(List<String> words) {
        this.words = words;
    }

    public String convertMessageToURL(){
        Log.i("speech", words.toString());
        if(!words.isEmpty()) {
            return URI_TITLE + handleWhiteSpaces(words.remove(0));
        }
        return "";
    }

    private String handleWhiteSpaces(String message){
        return message.trim().replaceAll(" ", SPACE);
    }
}
