package com.teddydev.abnd_7_book_api;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.teddydev.abnd_7_book_api.Model.Book;
import com.teddydev.abnd_7_book_api.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private List<Book> bookList = new ArrayList<>();

    private ListView listView;
    private EditText searchEditText;
    private SimpleImageListAdapter bookAdapter;

    private String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=android";

    private EditText.OnEditorActionListener searchOnEditActionListener = new EditText.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Bundle bundle = new Bundle();
                bundle.putString("URL", buildUrl(searchEditText.getText().toString()));
                getLoaderManager().restartLoader(0, bundle, MainActivity.this).forceLoad();
                Util.hideKeyboard(MainActivity.this);
                return true;
            }
            return false;
        }
    };

    private String buildUrl(String parameter) {
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL);
        url.append(parameter);
        return url.toString();
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

    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, bundle.getString("URL"));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        this.bookList.clear();
        if (books != null && !books.isEmpty()) {
            this.bookList.addAll(books);
            bookAdapter.notifyDataSetChanged();
        }
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.clear();
    }
}
