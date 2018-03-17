package com.example.degus.accesspedia.content;

import android.content.Context;

import com.example.degus.accesspedia.ApiAdapter;
import com.example.degus.accesspedia.ImageReceiver;
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
            String tempTitle = words.remove(0);
            String url = MessageHandler.convertMessageToURL(tempTitle);
            ApiAdapter apiAdapter = new ApiAdapter();
            String json = apiAdapter.execute(url).get();
            if (JSONParser.hasJSONValidData(json)) {
                String content = JSONParser.parse(json);
                if (ContentStandardizer.isAmbiguous(content)) {
                    return this.getContent(ContentStandardizer.disambiguate(content));
                }
                if (content != null && content.length() > 0) {
                    ContentModel contentModel = splitHeaderAndContent(content);
                    contentModel.setImage(ImageReceiver.getImage(tempTitle));
                    return contentModel;
                }
            }
        }
        return new ContentModel(appContext.getString(R.string.understand));
    }

    private ContentModel splitHeaderAndContent(String extractedArticle) {
        ContentModel result = new ContentModel();
        int indexOfSplit = getIndexOfSplit(extractedArticle);
        result.setHeader(extractedArticle.substring(0, indexOfSplit));
        result.setContent(extractedArticle.substring(indexOfSplit, extractedArticle.length() - 1));
        return result;
    }

    private int getIndexOfSplit(String extractedArticle) {
        int indexOfSplit = extractedArticle.indexOf('.') + 1;
        if (indexOfSplit != -1) {
            return indexOfSplit;
        }
        indexOfSplit = extractedArticle.indexOf('\n');
        if (indexOfSplit != -1) {
            return indexOfSplit;
        }
        indexOfSplit = extractedArticle.indexOf("</p>");
        if (indexOfSplit != -1) {
            return indexOfSplit;
        }
        indexOfSplit = extractedArticle.indexOf("</span>");
        if (indexOfSplit != -1) {
            return indexOfSplit;
        }
        return extractedArticle.length() - 1;
    }


}
