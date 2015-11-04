package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.Recommend;
import com.ereader.common.util.ToastUtil;

public class RecommendAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Recommend> mList ;
	private Context mContext;

	public RecommendAdapter(Context mContext,List<Recommend>  list) {
		inflater=LayoutInflater.from(mContext);
		mList = list;
		this.mContext = mContext;
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
		Recommend recommend = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_recommend_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_recommend_name.setText(recommend.getProduct_name());
		holder.tv_recommend_user.setText("推荐给的好友："+recommend.getUser_nickname());
		EReaderApplication.imageLoader.displayImage(recommend.getImage_url() == null ?"":recommend.getImage_url(), holder.iv_recommend, EReaderApplication.options);

		return convertView;
	}
	class ViewHolder{
		private ImageView iv_recommend;
		private TextView tv_recommend_name;
		private TextView tv_recommend_user;
		public void findView(View view){
			iv_recommend = (ImageView)view.findViewById(R.id.iv_recommend);
			tv_recommend_user = (TextView)view.findViewById(R.id.tv_recommend_user);
			tv_recommend_name = (TextView)view.findViewById(R.id.tv_recommend_name);
		}
	}
}
