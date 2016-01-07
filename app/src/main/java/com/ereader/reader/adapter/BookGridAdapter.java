package com.ereader.reader.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.ereader.client.R;
import com.ereader.reader.model.StoreBook;
import com.glview.graphics.Bitmap;
import com.glview.graphics.drawable.BitmapDrawable;
import com.glview.view.LayoutInflater;
import com.glview.view.View;
import com.glview.view.ViewGroup;
import com.glview.widget.BaseAdapter;
import com.glview.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class BookGridAdapter extends BaseAdapter {
	
	Context mContext;
	
	LayoutInflater mLayoutInflater;
	
	List<StoreBook> mData = new ArrayList<StoreBook>();
	
	WeakHashMap<Object, Bitmap> mCovers = new WeakHashMap<Object, Bitmap>();

	public BookGridAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}
	
	public List<StoreBook> getList() {
		return mData;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.book_grid_item, parent, false);
		}
		StoreBook storeBook = (StoreBook) getItem(position);
		View v = convertView.findViewById(R.id.item_back);
		TextView tv = (TextView) convertView.findViewById(R.id.item_text);
		Bitmap cover = null;
		if (storeBook.cover != null) {
			cover = mCovers.get(storeBook.cover);
			if (cover== null) {
				cover = new Bitmap(BitmapFactory.decodeFile(storeBook.cover));
			}
		}
		if (cover != null) {
			v.setBackground(new BitmapDrawable(cover));
			tv.setText(null);
		} else {
			v.setBackgroundResource(storeBook.getIconRes());
			tv.setText(storeBook.name);
		}
		return convertView;
	}

}
