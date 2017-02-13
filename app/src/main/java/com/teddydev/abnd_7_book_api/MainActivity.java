package com.teddydev.abnd_7_book_api;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teddydev.abnd_7_book_api.Model.Book;
import com.teddydev.abnd_7_book_api.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private List<Book> bookList = new ArrayList<>();

    private ListView listView;
    private EditText searchEditText;
    private TextView msgTextView;
    private ProgressBar progressbar;
    private SimpleImageListAdapter bookAdapter;

    private String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    private EditText.OnEditorActionListener searchOnEditActionListener = new EditText.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Bundle bundle = new Bundle();
                bundle.putString(Const.EXTRA_URL, buildUrl(searchEditText.getText().toString()));
                if (!Util.isInternetConnection(MainActivity.this)) {
                    msgTextView.setText(getString(R.string.no_internet));
                } else {
                    getLoaderManager().restartLoader(0, bundle, MainActivity.this).forceLoad();
                    Util.hideKeyboard(MainActivity.this);
                    searchEditText.setFocusable(false);
                }
                return true;
            }
            return false;
        }
    };
    private String buildUrl(String searchParameter) {
        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", searchParameter);
        return uriBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookAdapter = new SimpleImageListAdapter(this, bookList);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(bookAdapter);

        searchEditText = (EditText) findViewById(R.id.search_edittext);
        searchEditText.setOnEditorActionListener(searchOnEditActionListener);

        msgTextView = (TextView) findViewById(R.id.msg_textview);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        progressbar.setVisibility(View.VISIBLE);
        msgTextView.setVisibility(View.GONE);
        return new BookLoader(this, bundle.getString(Const.EXTRA_URL));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        this.bookList.clear();
        if (books != null && !books.isEmpty()) {
            this.bookList.addAll(books);
            bookAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
        } else {
            msgTextView.setText(getString(R.string.fetching_error));
        }

        searchEditText.setFocusableInTouchMode(true);
        progressbar.setVisibility(View.GONE);
        listView.setEmptyView(msgTextView);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.clear();
    }
}
