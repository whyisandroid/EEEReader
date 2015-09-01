package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ereader.client.R;

public class BookShelfAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<String> mList ;

	public BookShelfAdapter(Context mContext,List<String>  list) {
		inflater=LayoutInflater.from(mContext);
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size()+1;
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
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.book_shelf_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}	
		if(position == mList.size()){
			holder.iv_book_shelf.setImageResource(R.drawable.s3_20);
		}
		
		return convertView;
	}
	class ViewHolder{
		private ImageView iv_book_shelf;
		public void findView(View view){
			iv_book_shelf = (ImageView)view.findViewById(R.id.iv_book_shelf);
		}
	}

}
