package com.ereader.client.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Bill;
import com.ereader.client.entities.json.ArticleList;

import java.util.List;

public class HelpAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ArticleList> mList ;

	public HelpAdapter(Context mContext, List<ArticleList> list) {
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
		ArticleList articleList = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.help_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_help_type.setText(articleList.getCategory());
		return convertView;
	}
	class ViewHolder{
		private TextView tv_help_type;

		public void findView(View view){
			tv_help_type = (TextView)view.findViewById(R.id.tv_help_type);
		}
	}
}
