package com.example.degus.accesspedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Dominik Nowak on 11/03/2018.
 */

public class ImageReceiver {

    private final static String URI_IMAGE = "https://en.wikipedia.org/w/api.php?format=json&" +
            "action=query&prop=pageimages&pithumbsize=600&titles=";

    public void getImage(String title) throws JSONException, ExecutionException, InterruptedException { //TODO:change to IMAGE
        ApiAdapter apiAdapter = new ApiAdapter();
        String json = apiAdapter.execute(URI_IMAGE + MessageHandler.handleWhiteSpaces(title)).get();
        if (JSONParser.hasJSONValidData(json)) {
            String thumbnailURI = getImageUrlFrom(json);
        }
    }

    private String getImageUrlFrom(String json) throws JSONException {
        JSONObject pages = JSONParser.extractPagesJSONObject(json);
        String firstPageID = pages.keys().next();
        JSONObject firstPage = pages.getJSONObject(firstPageID);
        JSONObject thumbnail = firstPage.getJSONObject("thumbnail");
        return thumbnail.getString("source");
    }
}
