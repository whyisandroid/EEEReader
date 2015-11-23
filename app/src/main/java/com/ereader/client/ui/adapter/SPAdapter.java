package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.SpComment;
import com.ereader.client.ui.my.MySPDetailActivity;
import com.ereader.common.util.IntentUtil;

public class SPAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<SpComment> mList ;
	private Context mContext;

	public SPAdapter(Context mContext,List<SpComment> list) {
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
		final SpComment sp = mList.get(position);
		
		final ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_sping_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}	
		holder.tv_sp_name.setText(sp.getTitle());
		//String value = "您的好友玥儿给您推荐《永远不要找别人要安全感》，点击书名立即查看您的好友玥儿给您推荐《永远不要找别人要安全感》，点击书名立即查看您的好友玥儿给您推荐《永远不要找别人要安全感》，点击书名立即查看您的好友玥儿给您推荐《永远不要找别人要安全感》您的好友玥儿给您推荐《永远不要找别人要安全感》，点击书名立即查看您的好友玥儿给您推荐《永远不要找别人要安全感》您的好友玥儿给您推荐《永远不要找别人要安全感》，点击书名立即查看您的好友玥儿给您推荐《永远不要找别人要安全感》";
		//sp.setContent(value);
		holder.tv_book_content.setText(sp.getContent());



		ViewTreeObserver vto = holder.tv_book_content.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				int lineCount = holder.tv_book_content.getLineCount();
				if(lineCount >= 5){
					holder.iv_sp_max.setVisibility(View.VISIBLE);
					holder.tv_book_content.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Bundle bundle = new Bundle();
							bundle.putSerializable("sp",sp);
							IntentUtil.intent(mContext,bundle, MySPDetailActivity.class,false);
						}
					});
				}else{
					holder.iv_sp_max.setVisibility(View.GONE);
				}
				return true;
			}
		});

		holder.tv_book_date.setText(sp.getCreated_at());
		holder.tv_book_name.setText(sp.getProduct_name());
		holder.rbar_sp_star.setRating(Float.valueOf(sp.getScore()));
		return convertView;
	}
	class ViewHolder{
		private TextView tv_sp_name;
		private TextView tv_book_content;
		private TextView tv_book_date;
		private TextView tv_book_name;
		private RatingBar rbar_sp_star;
		private ImageView iv_sp_max;
		public void findView(View view){
			tv_sp_name = (TextView)view.findViewById(R.id.tv_sp_name);
			tv_book_content = (TextView)view.findViewById(R.id.tv_book_content);
			tv_book_date = (TextView)view.findViewById(R.id.tv_book_date);
			tv_book_name = (TextView)view.findViewById(R.id.tv_book_name);
			rbar_sp_star = (RatingBar)view.findViewById(R.id.rbar_sp_star);
			iv_sp_max = (ImageView)view.findViewById(R.id.iv_sp_max);
		}
	}

}
