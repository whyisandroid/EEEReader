package com.ereader.client.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Friend;
import com.ereader.client.entities.Friends;
import com.ereader.common.util.ToastUtil;

public class FriendsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Friends> mFrList;

	public FriendsAdapter(Context mContext,List<Friend>  list) {
		inflater=LayoutInflater.from(mContext);
		mFrList = getFriends(list);
	}

	private List<Friends> getFriends(List<Friend> list) {
		List<Friends> friends  = new ArrayList<Friends>();
		for (int i = 0; i < list.size(); i++) {
			Friends frds = new Friends();
			frds.setmF1(list.get(i));
			i++;
			if(i != list.size()){
				frds.setmF2(list.get(i));
			}
			friends.add(frds);
		}
		return friends;
	}


	@Override
	public int getCount() {
		return mFrList.size();
	}

	@Override
	public Object getItem(int position) {
		return mFrList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Friends mFriend = mFrList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_friends_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}	
		holder.tv_mybook_cate.setText(mFriend.getmF1().getNickname());
		holder.tv_mybook_cate2.setText(mFriend.getmF2() != null ? mFriend.getmF2().getNickname() : "");
		return convertView;
	}
	class ViewHolder{
		private TextView tv_mybook_cate;
		private TextView tv_mybook_cate2;
		public void findView(View view){
			tv_mybook_cate = (TextView)view.findViewById(R.id.tv_mybook_cate);
			tv_mybook_cate2 = (TextView)view.findViewById(R.id.tv_mybook_cate2);
		}
	}
}
