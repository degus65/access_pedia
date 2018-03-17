package com.example.degus.accesspedia;

import com.example.degus.accesspedia.content.ContentModel;
import com.example.degus.accesspedia.content.Splitter;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Dominik Nowak on 03/03/2018.
 */

public class ContentSplittingTest {

    @Test
    public void contentShouldBeCorrectlySplitted() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String html = "<p>A <b>smartphone</b> is a handheld personal computer</p> with a mobile operating system and an integrated";
        ContentModel result = Splitter.splitHeaderAndContent(html);
        assertEquals(result.getHeader(), "<p>A <b>smartphone</b> is a handheld personal computer</p>");
    }
}
