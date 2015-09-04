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
import com.ereader.client.entities.Message;
import com.ereader.client.ui.my.FriendsActivity;
import com.ereader.client.ui.my.SPActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ToastUtil;

public class MessageFriendsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Message> mList;
	private Context mContext;

	public MessageFriendsAdapter(Context mContext,List<Message>  list) {
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
			convertView =inflater.inflate(R.layout.my_message_friends_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_message_friends_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ToastUtil.showToast(mContext, "添加成功", ToastUtil.LENGTH_LONG);
			}
		});
		
	holder.tv_message_friends_no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ToastUtil.showToast(mContext, "拒绝好友", ToastUtil.LENGTH_LONG);
			}
		});
		
		return convertView;
	}
	class ViewHolder{
		private Button tv_message_friends_ok;
		private Button tv_message_friends_no;
		
		public void findView(View view){
			tv_message_friends_ok = (Button)view.findViewById(R.id.tv_message_friends_ok);
			tv_message_friends_no = (Button)view.findViewById(R.id.tv_message_friends_no);
		}
	}

}
