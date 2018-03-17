package com.example.degus.accesspedia.content;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dominik Nowak on 03/03/2018.
 */

public class ContentStandardizer {

    private static final String MAY_REFER_PHRASE = "may refer to:";
    private static final String COMMON_REFER_PHRASE = "most commonly refers to:";

    public static boolean isAmbiguous(String content) {
        return content.contains(MAY_REFER_PHRASE) || content.contains(COMMON_REFER_PHRASE);
    }

    public static List<String> disambiguate(String content) {
        return new LinkedList<>(Arrays.asList(extractFirstRelatedTherm(content)));
    }

    private static String extractFirstRelatedTherm(String html) {
        Document doc = Jsoup.parse(html).normalise();
        Elements listsElements = doc.select("ul").select("li");
        String elementText = listsElements.first().text();
        Log.d("ambiguousTherm", elementText);
        if (elementText.contains(",")) {
            return elementText.substring(0, elementText.indexOf(','));
        } else {
            return elementText;
        }
    }
}
