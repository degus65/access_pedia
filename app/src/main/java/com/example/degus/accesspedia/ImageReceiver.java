package com.example.degus.accesspedia;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Dominik Nowak on 11/03/2018.
 */

public class ImageReceiver {

    private final static String URI_IMAGE = "https://en.wikipedia.org/w/api.php?format=json&" +
            "action=query&prop=pageimages&pithumbsize=600&titles=";

    public static Bitmap getImage(String title) throws JSONException, ExecutionException, InterruptedException {
        ApiAdapter apiAdapter = new ApiAdapter();
        String json = apiAdapter.execute(URI_IMAGE + MessageHandler.handleWhiteSpaces(title)).get();
        if (JSONParser.hasJSONValidData(json) && JSONParser.hasJsonThumbnailUrl(json)) {
            String thumbnailURI = getImageUrlFrom(json);
            ImageAdapter imageAdapter = new ImageAdapter();
            return imageAdapter.execute(thumbnailURI).get();
        }
        return null;
    }

    private static String getImageUrlFrom(String json) throws JSONException {
        JSONObject pages = JSONParser.extractPagesJSONObject(json);
        String firstPageID = pages.keys().next();
        JSONObject firstPage = pages.getJSONObject(firstPageID);
        JSONObject thumbnail = firstPage.getJSONObject("thumbnail");
        return thumbnail.getString("source");
    }
}
