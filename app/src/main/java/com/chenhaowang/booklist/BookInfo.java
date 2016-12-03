package com.chenhaowang.booklist;

public class BookInfo {
    private String mTitle;
    private String mAuthors;
    private String mUrl;

    public BookInfo(String title, String authors, String url) {
        mTitle = title;
        mAuthors = authors;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public String getUrl() {
        return mUrl;
    }
}
