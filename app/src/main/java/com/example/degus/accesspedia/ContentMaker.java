package com.example.degus.accesspedia;

import android.content.Context;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dominik Nowak on 25/02/2018.
 */

public class ContentMaker {

    private final Context appContext;

    public ContentMaker(Context appContext) {
        this.appContext = appContext;
    }

    public String getContent(List<String> words) throws ExecutionException, InterruptedException, JSONException {
        while (!words.isEmpty()) {
            String url = MessageHandler.convertMessageToURL(words.remove(0));
            ApiAdapter apiAdapter = new ApiAdapter();
            String json = apiAdapter.execute(url).get();
            if (JSONParser.hasJSONValidData(json)) {
                return JSONParser.parse(json);
            }
        }
        return appContext.getString(R.string.understand);
    }
}
