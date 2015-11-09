package com.ereader.client.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.MessageFriends;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<MessageFriends> mList;
	private Context mContext;

	public MessageAdapter(Context mContext,List<MessageFriends>  list) {
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
		MessageFriends message = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_message_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}

		String value = "好友"+"<font color = \"#43a8d7\">"+message.getFrom_user_nickname()+"</font>" +"给您推荐"+"<font color = \"#43a8d7\">"+"《"+message.getProduct_name()+"》"+"</font>";
		holder.tv_message_name.setText(Html.fromHtml(value));
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
