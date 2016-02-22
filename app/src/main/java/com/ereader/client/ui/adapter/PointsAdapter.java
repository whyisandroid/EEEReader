package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Bill;
import com.ereader.client.ui.my.SPActivity;
import com.ereader.common.util.IntentUtil;

public class PointsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Bill> mList;
	private Context mContext;

	public PointsAdapter(Context mContext,List<Bill>  list) {
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
		Bill bill = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_points_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}

		holder.tv_points_name.setText(bill.getRemark());
		holder.tv_point_time.setText(bill.getCreated_at());
		holder.tv_points_num.setText(bill.getCarry());
		if("outlay".equals(bill.getBalance())){
			holder.tv_points_num.setText("-"+bill.getCarry());
			holder.tv_points_num.setTextColor(Color.RED);
		}else{
			holder.tv_points_num.setTextColor(Color.GREEN);
			holder.tv_points_num.setText("+"+bill.getCarry());
		}

		return convertView;
	}
	class ViewHolder{
		private TextView tv_points_name;
		private TextView tv_point_time;
		private TextView tv_points_num;

		public void findView(View view){
			tv_points_name = (TextView)view.findViewById(R.id.tv_points_name);
			tv_point_time = (TextView)view.findViewById(R.id.tv_point_time);
			tv_points_num = (TextView)view.findViewById(R.id.tv_points_num);
		}
	}
}
