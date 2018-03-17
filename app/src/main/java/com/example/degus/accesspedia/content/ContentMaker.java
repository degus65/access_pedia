package com.example.degus.accesspedia.content;

import android.content.Context;

import com.example.degus.accesspedia.ApiAdapter;
import com.example.degus.accesspedia.JSONParser;
import com.example.degus.accesspedia.MessageHandler;
import com.example.degus.accesspedia.R;

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

    public ContentModel getContent(List<String> words) throws ExecutionException, InterruptedException, JSONException {
        while (!words.isEmpty()) {
            String url = MessageHandler.convertMessageToURL(words.remove(0));
            ApiAdapter apiAdapter = new ApiAdapter();
            String json = apiAdapter.execute(url).get();
            if (JSONParser.hasJSONValidData(json)) {
                String content = JSONParser.parse(json);
                if (ContentStandardizer.isAmbiguous(content)) {
                    return this.getContent(ContentStandardizer.disambiguate(content));
                }
                ContentModel contentModel = splitHeaderAndContent(content);
                //TODO add image to model
                return contentModel;
            }
        }
        return new ContentModel(appContext.getString(R.string.understand));
    }

    public ContentModel splitHeaderAndContent(String extractedArticle) {
        ContentModel result = new ContentModel();
        int indexOfSplit = extractedArticle.indexOf('\n');
        result.setHeader(extractedArticle.substring(0, indexOfSplit));
        result.setContent(extractedArticle.substring(indexOfSplit, extractedArticle.length() - 1));
        return result;
    }
}
