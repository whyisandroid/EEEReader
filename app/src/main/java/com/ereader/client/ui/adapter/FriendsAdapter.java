package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.ereader.client.R;
import com.ereader.common.util.ToastUtil;

public class FriendsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<String> mList ;
	private Context mContext;

	public FriendsAdapter(Context mContext,List<String>  list) {
		inflater=LayoutInflater.from(mContext);
		mList = list;
		this.mContext = mContext;
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
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_friends_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}	
		
		return convertView;
	}
	class ViewHolder{
		public void findView(View view){
		}
	}

}
