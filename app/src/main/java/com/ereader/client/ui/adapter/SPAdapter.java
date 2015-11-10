package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.SpComment;

public class SPAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<SpComment> mList ;

	public SPAdapter(Context mContext,List<SpComment> list) {
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
		SpComment sp = mList.get(position);
		
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_sping_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}	
		holder.tv_sp_name.setText(sp.getTitle());
		holder.tv_book_content.setText(sp.getContent());
		if(holder.tv_book_content.getLineCount() >=5){
			holder.iv_sp_max.setVisibility(View.VISIBLE);
			holder.tv_book_content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
		}else{
			holder.iv_sp_max.setVisibility(View.GONE);
		}
		
		holder.tv_book_date.setText(sp.getUpdated_at());
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
