package com.ereader.client.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.ereader.client.entities.OrderBook;
import com.ereader.client.entities.OrderList;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.my.SPActivity;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

public class OrderItemAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private OrderList mOrderList;
	private Context mContext;

	public OrderItemAdapter(Context mContext, OrderList mList) {
		inflater=LayoutInflater.from(mContext);
		this.mContext = mContext;
		mOrderList = mList;
	}

	@Override
	public int getCount() {
		if(mOrderList.getOrder_products()==null){
			LogUtil.Log("orderID"+mOrderList.getOrder_id(),0+"");
		}else {
			LogUtil.Log("orderID"+mOrderList.getOrder_id(),mOrderList.getOrder_products().size()+"");
		}

		return  mOrderList.getOrder_products()==null?0:mOrderList.getOrder_products().size();
	}

	@Override
	public Object getItem(int position) {
		return mOrderList.getOrder_products().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final OrderBook orderBook = mOrderList.getOrder_products().get(position);

		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_order_item_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}

		//订单状态 未支付0 已支付1  已取消2
        if("0".equals(mOrderList.getPay_status())){
			holder.tv_order_type.setText("尚未支付");
			holder.tv_order_type.setTextColor(Color.RED);
			holder.tv_order_right.setVisibility(View.INVISIBLE);
			holder.tv_order_left.setVisibility(View.INVISIBLE);

		}else if("1".equals(mOrderList.getPay_status())){
			holder.tv_order_type.setText("交易成功");
			holder.tv_order_type.setTextColor(Color.GREEN);
			holder.tv_order_right.setVisibility(View.VISIBLE);
			if("1".equals(orderBook.getIsComment())){
				holder.tv_order_right.setText("已评论");
				holder.tv_order_right.setEnabled(false);
			}else{
				holder.tv_order_right.setText("写书评");
			}
			holder.tv_order_left.setText("立即阅读");
		}else if("2".equals(mOrderList.getPay_status())){
			holder.tv_order_type.setText("已取消 ");
			holder.tv_order_type.setTextColor(Color.GRAY);
			holder.tv_order_right.setVisibility(View.INVISIBLE);
			holder.tv_order_left.setVisibility(View.INVISIBLE);
		}
		holder.tv_book_price.setText("¥"+orderBook.getPrice());
		holder.tv_book_time.setText(mOrderList.getUpdated_at());
		holder.tv_book_name.setText(orderBook.getInfo() == null ?"":orderBook.getInfo().getName());

		if(orderBook.getInfo() != null && !TextUtils.isEmpty(orderBook.getInfo().getImage_url())){
			EReaderApplication.imageLoader.displayImage(orderBook.getInfo().getImage_url(), holder.iv_book, EReaderApplication.options);
		}

		if(mOrderList.getTo_user() != null && !TextUtils.isEmpty(mOrderList.getTo_user().getNickname())){
			holder.tv_book_friend.setVisibility(View.VISIBLE);
			holder.tv_book_friend.setText("送给好友："+mOrderList.getTo_user().getNickname());
			holder.tv_order_left.setVisibility(View.GONE);
		}


		holder.tv_order_right.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,SPActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("name",orderBook.getInfo() == null ?"":orderBook.getInfo().getName());
				bundle.putString("id",orderBook.getInfo() == null ?"":orderBook.getInfo().getProduct_id());
				bundle.putString("orderId", mOrderList.getOrder_id());
				intent.putExtras(bundle);
				((Activity)mContext).startActivityForResult(intent, -1);
			}
		});

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialogUtil.showProgressDialog(mContext, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						AppController.getController().getBookDetail(orderBook.getProduct_id());
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
		private TextView tv_book_price;
		private TextView tv_book_name;
		private TextView tv_book_time;
		private TextView tv_order_type;
		private TextView tv_book_friend;
		private ImageView iv_book;

		public void findView(View view){
			iv_book = (ImageView)view.findViewById(R.id.iv_book);
			tv_order_right = (Button)view.findViewById(R.id.tv_order_right);
			tv_order_left = (Button)view.findViewById(R.id.tv_order_left);
			tv_book_name = (TextView)view.findViewById(R.id.tv_book_name);
			tv_book_price = (TextView)view.findViewById(R.id.tv_book_price);
			tv_book_friend = (TextView)view.findViewById(R.id.tv_book_friend);
			tv_book_time = (TextView)view.findViewById(R.id.tv_book_time);
			tv_order_type = (TextView)view.findViewById(R.id.tv_order_type);
		}
	}
}
