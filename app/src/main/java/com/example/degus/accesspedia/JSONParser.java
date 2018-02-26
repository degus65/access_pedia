package com.example.degus.accesspedia;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dominik Nowak on 26/02/2018.
 */

public class JSONParser {

    public static String parse(String json) throws JSONException {
        JSONObject allJSONObject = new JSONObject(json);
        JSONObject pages = allJSONObject.getJSONObject("query").getJSONObject("pages");
        String firstPageID = pages.keys().next();
        JSONObject firstPage = pages.getJSONObject(firstPageID);
        return firstPage.getString("extract");
    }
}
