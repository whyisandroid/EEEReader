package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.Order;
import com.ereader.client.entities.OrderList;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.my.SPActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

public class OrderAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<OrderList> mOrderList;
	private Context mContext;
	private Handler mHandler;

	public OrderAdapter(Context mContext,List<OrderList> mList,Handler mHandler) {
		inflater=LayoutInflater.from(mContext);
		this.mContext = mContext;
		mOrderList = mList;
		this.mHandler = mHandler;
	}

	@Override
	public int getCount() {
		return mOrderList.size();
	}

	@Override
	public Object getItem(int position) {
		return mOrderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final OrderList order = mOrderList.get(position);

		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_order_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.lv_order_item.setAdapter(new OrderItemAdapter(mContext,order));
		holder.tv_order_id.setText("订单编号   "+order.getOrder_id());
		//订单状态 未支付0 已支付1  已取消2
        if("0".equals(order.getPay_status())){
			holder.tv_order_right.setVisibility(View.VISIBLE);
			holder.tv_order_left.setVisibility(View.VISIBLE);

		}else if("1".equals(order.getPay_status())){
			holder.tv_order_right.setVisibility(View.GONE);
			holder.tv_order_left.setVisibility(View.GONE);
		}else if("2".equals(order.getPay_status())){
			holder.tv_order_right.setVisibility(View.GONE);
			holder.tv_order_left.setVisibility(View.GONE);
		}


		holder.tv_order_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					ProgressDialogUtil.showProgressDialog(mContext, "", false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							AppController.getController().cancelOrder(mHandler,order.getOrder_id(),position);
							ProgressDialogUtil.closeProgressDialog();
						}
					}).start();
			}
		});

		holder.tv_order_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ProgressDialogUtil.showProgressDialog(mContext, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						Order orderTrue = new Order();
						orderTrue.setOrder_id(order.getOrder_id());
						orderTrue.setPay_total(order.getPay_total());
						AppController.getController().getContext().addBusinessData("OrderResp", orderTrue);
						AppController.getController().wallet(mHandler);
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();

			}
		});
		
		return convertView;
	}
	class ViewHolder{
		private Button tv_order_right;
		private Button tv_order_left;

		private TextView tv_order_id;
		private ListView lv_order_item;
		
		public void findView(View view){
			tv_order_right = (Button)view.findViewById(R.id.tv_order_right);
			tv_order_left = (Button)view.findViewById(R.id.tv_order_left);
			tv_order_id = (TextView)view.findViewById(R.id.tv_order_id);
			lv_order_item = (ListView)view.findViewById(R.id.lv_order_item);
		}
	}
}
