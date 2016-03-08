package com.ereader.client.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Article;
import com.ereader.client.entities.Notice;

import java.util.List;

public class Notice2Adapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Notice> mList ;

	public Notice2Adapter(Context mContext, List<Notice> list) {
		inflater=LayoutInflater.from(mContext);
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
		Notice notice = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.more_notice_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}	
		holder.tv_notice_title.setText(notice.getTitle());
		return convertView;
	}
	class ViewHolder{
		TextView tv_notice_title;
		public void findView(View view){
			tv_notice_title = (TextView)view.findViewById(R.id.tv_notice_title);
		}
	}

}
