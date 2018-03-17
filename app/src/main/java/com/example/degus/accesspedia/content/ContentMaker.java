package com.example.degus.accesspedia.content;

import android.content.Context;

import com.example.degus.accesspedia.ApiAdapter;
import com.example.degus.accesspedia.JSONParser;
import com.example.degus.accesspedia.MessageHandler;
import com.example.degus.accesspedia.R;
import com.example.degus.accesspedia.image.ImageReceiver;

import org.json.JSONException;

import java.util.Arrays;
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
            String json = readJsonFromArticleWith(tempTitle);
            if (!JSONParser.hasJSONValidData(json)) {
                continue;
            }
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
        return new ContentModel(appContext.getString(R.string.understand));
    }

    private String readJsonFromArticleWith(String title) throws ExecutionException, InterruptedException {
        String url = MessageHandler.convertMessageToURL(title);
        ApiAdapter apiAdapter = new ApiAdapter();
        return apiAdapter.execute(url).get();
    }

    private ContentModel splitHeaderAndContent(String extractedArticle) {
        ContentModel result = new ContentModel();
        int indexOfSplit = getIndexOfSplit(extractedArticle);
        result.setHeader(extractedArticle.substring(0, indexOfSplit));
        if (indexOfSplit < extractedArticle.length() - 1) {
            result.setContent(extractedArticle.substring(indexOfSplit, extractedArticle.length() - 1));
        }
        return result;
    }

    private int getIndexOfSplit(String extractedArticle) {
        List<String> possibleDividers
                = Arrays.asList(".", "\n", "</p>", "</span>", "</h1>", "</h2>", "</h3>");
        for (String divider : possibleDividers) {
            int indexOfSplit = extractedArticle.indexOf(divider);
            if (indexOfSplit != -1) {
                return indexOfSplit + divider.length();
            }
        }
        return extractedArticle.length();
    }
}
