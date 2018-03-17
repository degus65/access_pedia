package com.example.degus.accesspedia;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dominik Nowak on 26/02/2018.
 */

public class JSONParser {

    public static String parse(String json) throws JSONException {
        JSONObject pages = extractPagesJSONObject(json);
        String firstPageID = pages.keys().next();
        JSONObject firstPage = pages.getJSONObject(firstPageID);
        return firstPage.getString("extract");
    }

    public static boolean hasJSONValidData(String json) throws JSONException {
        JSONObject pages = extractPagesJSONObject(json);
        String firstPageID = pages.keys().next();
        return Integer.parseInt(firstPageID) > 0;
    }

    public static JSONObject extractPagesJSONObject(String json) throws JSONException {
        JSONObject allJSONObject = new JSONObject(json);
        return allJSONObject.getJSONObject("query").getJSONObject("pages");
    }

    public static boolean hasJsonThumbnailUrl(String json) throws JSONException {
        JSONObject pages = extractPagesJSONObject(json);
        String firstPageID = pages.keys().next();
        JSONObject firstPage = pages.getJSONObject(firstPageID);
        return firstPage.has("thumbnail");
    }
}
