package com.ereader.client.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.BookSearch;

import java.util.List;

public class BookSearchAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<BookSearch> mList;

    public BookSearchAdapter(Context mContext, List<BookSearch> list) {
        inflater = LayoutInflater.from(mContext);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookSearch book = mList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_item, null);
            holder = new ViewHolder();
            holder.findView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_book_author.setText(book.getAuthor());
        holder.tv_book_name.setText(book.getName());
        holder.tv_book_info.setText(book.getDescription());


        return convertView;
    }

    class ViewHolder {
        private TextView tv_book_name;
        private TextView tv_book_author;
        private TextView tv_book_info;

        public void findView(View view) {
            tv_book_name = (TextView) view.findViewById(R.id.tv_book_name);
            tv_book_author = (TextView) view.findViewById(R.id.tv_book_author);
            tv_book_info = (TextView) view.findViewById(R.id.tv_book_info);
        }
    }

}
