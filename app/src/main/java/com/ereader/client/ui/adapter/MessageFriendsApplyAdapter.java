package com.ereader.client.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.MessageFriends;
import com.ereader.client.service.AppController;
import com.ereader.common.util.ProgressDialogUtil;

import java.util.List;

public class MessageFriendsApplyAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<MessageFriends> mList;
	private Context mContext;
	private Handler mHandler;

	public MessageFriendsApplyAdapter(Context mContext, List<MessageFriends> list, Handler mHandler) {
		inflater=LayoutInflater.from(mContext);
		this.mContext = mContext;
		mList = list;
		this.mHandler = mHandler;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final MessageFriends message = mList.get(position);
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_message_friends_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_message_friends_name.setText(message.getFrom_user_nickname());
		holder.tv_message_friends_time.setText(message.getCreated_at());
		//status 消息状态 1等待同意 2已同意 3已拒绝
		if("1".equals(message.getStatus())){
			holder.tv_message_friends_ok.setVisibility(View.VISIBLE);
			holder.tv_message_friends_no.setVisibility(View.VISIBLE);
			holder.tv_message_friends_status.setVisibility(View.GONE);
		}else if("2".equals(message.getStatus())){
			holder.tv_message_friends_ok.setVisibility(View.GONE);
			holder.tv_message_friends_no.setVisibility(View.GONE);
			holder.tv_message_friends_status.setVisibility(View.VISIBLE);
			holder.tv_message_friends_status.setText("已同意");
			holder.tv_message_friends_status.setTextColor(Color.parseColor("#43a8d7"));
		}else if("3".equals(message.getStatus())){
			holder.tv_message_friends_ok.setVisibility(View.GONE);
			holder.tv_message_friends_no.setVisibility(View.GONE);
			holder.tv_message_friends_status.setVisibility(View.VISIBLE);
			holder.tv_message_friends_status.setText("已拒绝");
			holder.tv_message_friends_status.setTextColor(Color.parseColor("#CCCCCC"));
		}



		holder.tv_message_friends_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProgressDialogUtil.showProgressDialog(mContext, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						AppController.getController().agreeFriends(mHandler, message.getFrom_user_id(), position);
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			}
		});
		
		holder.tv_message_friends_no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProgressDialogUtil.showProgressDialog(mContext, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						AppController.getController().disagreeFriends(mHandler, message.getFrom_user_id(), position);
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
				}
			});

			return convertView;
		}
	class ViewHolder{
		private Button tv_message_friends_ok;
		private Button tv_message_friends_no;
		private TextView tv_message_friends_name;
		private TextView tv_message_friends_time;
		private TextView tv_message_friends_status;



		public void findView(View view){
			tv_message_friends_ok = (Button)view.findViewById(R.id.tv_message_friends_ok);
			tv_message_friends_no = (Button)view.findViewById(R.id.tv_message_friends_no);
			tv_message_friends_name = (TextView)view.findViewById(R.id.tv_message_friends_name);
			tv_message_friends_time = (TextView)view.findViewById(R.id.tv_message_friends_time);
			tv_message_friends_status = (TextView)view.findViewById(R.id.tv_message_friends_status);
		}
	}
}
