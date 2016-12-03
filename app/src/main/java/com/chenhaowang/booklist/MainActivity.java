package com.chenhaowang.booklist;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookInfo>> {
    public static final String LOG_TAG = MainActivity.class.getName();
    private BookListAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private ProgressBar mLoadingIndicator;
    private String mKeyWord;
    private static final int BOOKLIST_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: MainActivity onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.book_list);

        // Create a new {@link ArrayAdapter} of book list
        mAdapter = new BookListAdapter(this, new ArrayList<BookInfo>());
        bookListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        /** check internet connection*/
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            Log.i(LOG_TAG, "TEST: calling initLoader()...");
            loaderManager.initLoader(BOOKLIST_LOADER_ID, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookInfo curtBook = mAdapter.getItem(position);
                Uri webpage = Uri.parse(curtBook.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** check internet connection*/
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    getLoaderManager().restartLoader(BOOKLIST_LOADER_ID, null, MainActivity.this);
                } else {
                    mAdapter.clear();
                    mLoadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setText(R.string.no_internet);
                }
            }
        });
        /**check internet connectivity*/

    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "TEST: onCreateLoader() called...");
        EditText inputKeyWord = (EditText) findViewById(R.id.search_input_edit_text);
        mKeyWord = inputKeyWord.getText().toString();
        return new BookListLoader(this, mKeyWord);
    }

    @Override
    public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> bookList) {
        Log.i(LOG_TAG, "TEST: onLoadFinished() called...");
        mLoadingIndicator.setVisibility(View.GONE);

        mAdapter.clear();
        if (bookList != null && !bookList.isEmpty()) {
            mAdapter.addAll(bookList);
        }

        // Set empty state text to display "No book list found."
        mEmptyStateTextView.setText(R.string.no_booklist);
    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {
        Log.i(LOG_TAG, "TEST: onLoaderReset() called...");
        mAdapter.clear();
    }
}
