package com.teddydev.abnd_7_book_api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teddydev.abnd_7_book_api.Model.Book;

import java.util.ArrayList;
import java.util.List;


public class SimpleImageListAdapter extends ArrayAdapter<Book> {
    public SimpleImageListAdapter(Context context, List<Book> arrayList) {
        super(context, 0, arrayList);
    }

    static class ViewHolder {
        private TextView title;
        private TextView author;
    }

    @NonNull
    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {
        ViewHolder holder;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) listItemView.findViewById(R.id.title);
            holder.author = (TextView) listItemView.findViewById(R.id.author);
            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        Book book = getItem(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());

        return listItemView;
    }
}
