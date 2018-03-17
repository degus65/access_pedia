package com.example.degus.accesspedia;

import android.content.Context;

import com.example.degus.accesspedia.content.ContentMaker;
import com.example.degus.accesspedia.content.ContentModel;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Dominik Nowak on 03/03/2018.
 */

public class ContentSplittingTest {

    @Test
    public void contentShouldBeCorrectlySplitted() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String html = "<p>A <b>smartphone</b> is a handheld personal computer</p> with a mobile operating system and an integrated";
        ContentMaker contentMaker = new ContentMaker(Mockito.mock(Context.class));
        Method method = ContentMaker.class.getDeclaredMethod("splitHeaderAndContent", String.class);
        method.setAccessible(true);
        ContentModel result = (ContentModel) method.invoke(contentMaker, html);
        assertEquals(result.getHeader(), "<p>A <b>smartphone</b> is a handheld personal computer</p>");
    }
}
