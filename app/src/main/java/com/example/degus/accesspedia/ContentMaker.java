package com.example.degus.accesspedia;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dominik Nowak on 25/02/2018.
 */

public class ContentMaker {

    public String getContent(List<String> words) throws ExecutionException, InterruptedException, JSONException {
        ApiAdapter apiAdapter = new ApiAdapter();
        MessageHandler messageHandler = new MessageHandler(words);
        String json = apiAdapter.execute(messageHandler.convertMessageToURL()).get();
        return JSONParser.parse(json);
    }
}
