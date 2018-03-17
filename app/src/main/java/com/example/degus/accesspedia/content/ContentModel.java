package com.example.degus.accesspedia.content;

import android.graphics.Bitmap;

/**
 * Created by Dominik Nowak on 11/03/2018.
 */

public class ContentModel {

    private String header = "";
    private Bitmap image;
    private String content = "";

    public ContentModel() {
    }

    public ContentModel(String header) {
        this.header = header;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
