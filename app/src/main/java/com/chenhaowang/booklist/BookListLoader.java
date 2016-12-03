package com.chenhaowang.booklist;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import static com.chenhaowang.booklist.MainActivity.LOG_TAG;

public class BookListLoader extends AsyncTaskLoader<List<BookInfo>> {

    private String mKeyWord;

    public BookListLoader(Context context, String keyWord) {
        super(context);
        mKeyWord = keyWord;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: onStartLoading() called...");
        forceLoad();
    }

    @Override
    public List<BookInfo> loadInBackground() {
        Log.i(LOG_TAG, "TEST: loadInBackground() called...");

        if (mKeyWord == null) {
            return null;
        }
        String url = "https://www.googleapis.com/books/v1/volumes?q=" +
                mKeyWord.toLowerCase().replaceAll(" ", "%20") +
                "&maxResults=15";
        List<BookInfo> bookList = QueryUtils.fetchBookListData(url);
        return bookList;
    }
}
