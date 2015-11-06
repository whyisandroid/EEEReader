package com.ereader.client.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Message;
import com.ereader.client.entities.MessageSystem;

import java.util.List;

public class MessageSystemAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<MessageSystem> mList;
	private Context mContext;

	public MessageSystemAdapter(Context mContext, List<MessageSystem> list) {
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
		MessageSystem message = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_message_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_message_name.setText(message.getContent());
		holder.tv_message_time.setText(message.getCreated_at());
		return convertView;
	}
	class ViewHolder{
		private TextView tv_message_name;
		private TextView tv_message_time;

		public void findView(View view){
			tv_message_name = (TextView)view.findViewById(R.id.tv_message_name);
			tv_message_time = (TextView)view.findViewById(R.id.tv_message_time);
		}
	}

}
