package com.example.degus.accesspedia.content;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Dominik Nowak on 17/03/2018.
 */

public class Splitter {

    private final static List<String> POSSIBLE_DIVIDERS = Arrays.asList(".", "\n", "</p>", "</span>");


    public static ContentModel splitHeaderAndContent(String extractedArticle) {
        ContentModel result = new ContentModel();
        int indexOfSplit = getIndexOfSplit(extractedArticle);
        result.setHeader(extractedArticle.substring(0, indexOfSplit));
        if (indexOfSplit < extractedArticle.length() - 1) {
            result.setContent(extractedArticle.substring(indexOfSplit, extractedArticle.length() - 1));
        }
        return result;
    }

    private static int getIndexOfSplit(String extractedArticle) {
        for (String divider : POSSIBLE_DIVIDERS) {
            int indexOfSplit = extractedArticle.indexOf(divider);
            if (indexOfSplit != -1) {
                return indexOfSplit + divider.length();
            }
        }
        return extractedArticle.length();
    }

    public static String abbreviate(String text, int maxInputLength) {
        String allowedLengthText = text.substring(0, maxInputLength);
        return allowedLengthText.substring(0, getLastIndexOfSplit(allowedLengthText));
    }

    private static int getLastIndexOfSplit(String extractedArticle) {
        ListIterator<String> reverseIterator = POSSIBLE_DIVIDERS.listIterator(POSSIBLE_DIVIDERS.size());
        while (reverseIterator.hasPrevious()) {
            int indexOfSplit = extractedArticle.lastIndexOf(reverseIterator.previous());
            if (indexOfSplit != -1) {
                return indexOfSplit - reverseIterator.previous().length();
            }
        }
        return 0;
    }
}
