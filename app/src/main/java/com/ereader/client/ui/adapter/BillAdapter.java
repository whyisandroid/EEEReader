package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Bill;

public class BillAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Bill> mList ;

	public BillAdapter(Context mContext,List<Bill>  list) {
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
		Bill bill = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_bill_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_bill_name.setText(bill.getRemark());
		holder.tv_bill_time.setText(bill.getCreated_at());
		holder.tv_bill_num.setText(bill.getCarry());
		if("outlay".equals(bill.getBalance())){
			holder.tv_bill_num.setText("-" + bill.getCarry());
			holder.tv_bill_num.setTextColor(Color.RED);
		}else{
			holder.tv_bill_num.setText("+"+bill.getCarry());
			holder.tv_bill_num.setTextColor(Color.GREEN);
		}

		return convertView;
	}
	class ViewHolder{
		private TextView tv_bill_name;
		private TextView tv_bill_time;
		private TextView tv_bill_num;

		public void findView(View view){
			tv_bill_name = (TextView)view.findViewById(R.id.tv_bill_name);
			tv_bill_time = (TextView)view.findViewById(R.id.tv_bill_time);
			tv_bill_num = (TextView)view.findViewById(R.id.tv_bill_num);
		}
	}

}
