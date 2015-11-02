package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Gift;
import com.ereader.client.service.AppController;
import com.ereader.common.util.ProgressDialogUtil;

public class CouponsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Gift> mList;
	private Context mContext;

	public CouponsAdapter(Context mContext,List<Gift>  list) {
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
		final Gift gift = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_coupons_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_coupons_key.setText("优惠码："	+gift.getCode());
		holder.tv_coupons_endtime.setText("有效期："+gift.getCreated_at()+"至"+gift.getExpire_at());
		holder.tv_coupons_money.setText(gift.getTotal());
		//status=1 可用，2已用，3已过期
		if("1".equals(gift.getStatus())){
			holder.tv_coupons_status.setText("可用");
			holder.tv_coupons_status.setTextColor(Color.GREEN);
			holder.bt_coupons_add.setVisibility(View.VISIBLE);
		}else if("2".equals(gift.getStatus())){
			holder.tv_coupons_status.setText("已用");
			holder.tv_coupons_status.setTextColor(Color.RED);
			holder.bt_coupons_add.setVisibility(View.GONE);
		}else if("3".equals(gift.getStatus())){
			holder.tv_coupons_status.setText("已过期");
			holder.bt_coupons_add.setVisibility(View.GONE);
			holder.tv_coupons_status.setTextColor(Color.GRAY);
		}

		holder.bt_coupons_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialogUtil.showProgressDialog(mContext, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						AppController.getController().useCard(gift.getCode());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			}
		});
		return convertView;
	}
	class ViewHolder{
		private TextView tv_coupons_key;
		private TextView tv_coupons_endtime;
		private TextView tv_coupons_status;
		private TextView tv_coupons_money;
		private Button bt_coupons_add;

		public void findView(View view){
			tv_coupons_key = (TextView)view.findViewById(R.id.tv_coupons_key);
			tv_coupons_endtime = (TextView)view.findViewById(R.id.tv_coupons_endtime);
			tv_coupons_status = (TextView)view.findViewById(R.id.tv_coupons_status);
			tv_coupons_money = (TextView)view.findViewById(R.id.tv_coupons_money);
			bt_coupons_add = (Button)view.findViewById(R.id.bt_coupons_add);
		}
	}
}
