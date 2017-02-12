package com.teddydev.abnd_7_book_api;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.teddydev.abnd_7_book_api.Model.Book;
import com.teddydev.abnd_7_book_api.Util.NetworkUtil;

import java.util.List;

/**
 * Created by Matu on 12.02.2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    String url;

    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<Book> loadInBackground() {
        if (url == null) {
            return null;
        }
        return NetworkUtil.fetchBookData(url);
    }
}
