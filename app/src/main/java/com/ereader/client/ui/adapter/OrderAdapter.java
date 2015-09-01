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
import com.ereader.client.ui.my.SPActivity;
import com.ereader.common.util.IntentUtil;

public class OrderAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<String> mList;
	private Context mContext;

	public OrderAdapter(Context mContext,List<String>  list) {
		inflater=LayoutInflater.from(mContext);
		this.mContext = mContext;
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
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_order_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_order_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IntentUtil.intent(mContext,SPActivity.class);
			}
		});
		
		return convertView;
	}
	class ViewHolder{
		private Button tv_order_right;
		
		public void findView(View view){
			tv_order_right = (Button)view.findViewById(R.id.tv_order_right);
		}
	}

}
