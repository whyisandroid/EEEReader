package com.ereader.client.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ereader.client.R;

public class BookStoreAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private String[] mList ;

	public BookStoreAdapter(Context mContext,String[]  list) {
		inflater=LayoutInflater.from(mContext);
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.length;
	}

	@Override
	public Object getItem(int position) {
		return mList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		String name = mList[position];
		if(convertView == null){
			convertView =inflater.inflate(R.layout.book_store_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}	
		holder.tv_mybook_cate.setText(name);
		return convertView;
	}
	class ViewHolder{
		private TextView tv_mybook_cate;
		
		public void findView(View view){
			tv_mybook_cate = (TextView)view.findViewById(R.id.tv_mybook_cate);
		}
	}
}
