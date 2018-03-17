package com.example.degus.accesspedia.content;

/**
 * Created by Dominik Nowak on 11/03/2018.
 */

public class ContentModel {

    String header = "";
    Object image; //IMAGE
    String content = "";

    public ContentModel() {
    }

    public ContentModel(String header) {
        this.header = header;
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
