package com.chenhaowang.booklist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.chenhaowang.booklist.R.id.authors;

public class BookListAdapter extends ArrayAdapter<BookInfo> {

    public BookListAdapter(Context context, ArrayList<BookInfo> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        ViewHolder holder; // to reference the child views for later actions
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            // cache view fields into the holder
            holder = new ViewHolder();
            holder.title = (TextView) listView.findViewById(R.id.title);
            holder.authors = (TextView) listView.findViewById(authors);
            // associate the holder with the view for later lookup
            listView.setTag(holder);
        } else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) listView.getTag();
        }
        BookInfo curtBook = getItem(position);
        holder.title.setText(curtBook.getTitle());
        holder.authors.setText(curtBook.getAuthors());

        return listView;
    }

    static class ViewHolder {
        TextView title;
        TextView authors;
    }
}
