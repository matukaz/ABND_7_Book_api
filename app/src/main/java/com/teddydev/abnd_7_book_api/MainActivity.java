package com.teddydev.abnd_7_book_api;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.teddydev.abnd_7_book_api.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private List<Book> bookList = new ArrayList<>();

    private ListView listView;
    private SimpleImageListAdapter bookAdapter;

    private String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookAdapter = new SimpleImageListAdapter(this, bookList);

        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(bookAdapter);

        getLoaderManager().initLoader(0, null, this).forceLoad();

    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        this.bookList.clear();
        if (books != null && !books.isEmpty()) {
            this.bookList.addAll(books);
            bookAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.clear();
    }
}
