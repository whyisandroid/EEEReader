package com.ereader.client.ui.adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.ereader.client.entities.OrderBook;
import com.ereader.client.entities.OrderList;
import com.ereader.client.ui.my.SPActivity;
import com.ereader.common.util.IntentUtil;

import java.util.List;

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
		return mOrderList.getOrder_products().size();
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
			holder.tv_order_right.setVisibility(View.INVISIBLE);
			holder.tv_order_left.setVisibility(View.INVISIBLE);

		}else if("1".equals(mOrderList.getPay_status())){
			holder.tv_order_type.setText("交易成功");
			holder.tv_order_right.setText("写书评");
			holder.tv_order_left.setText("立即阅读");
		}else if("2".equals(mOrderList.getPay_status())){
			holder.tv_order_type.setText("已取消 ");
			holder.tv_order_right.setVisibility(View.INVISIBLE);
			holder.tv_order_left.setVisibility(View.INVISIBLE);
		}
		holder.tv_book_price.setText(orderBook.getPrice());
		holder.tv_book_time.setText(mOrderList.getUpdated_at());
		holder.tv_book_name.setText(orderBook.getName());
		EReaderApplication.imageLoader.displayImage(orderBook.getInfo().getImage_url(), holder.iv_book, EReaderApplication.options);


		holder.tv_order_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("name",orderBook.getName());
				bundle.putString("id",orderBook.getInfo().getProduct_id());
				IntentUtil.intent(mContext,bundle,SPActivity.class,false);
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
		private ImageView iv_book;

		public void findView(View view){
			iv_book = (ImageView)view.findViewById(R.id.iv_book);
			tv_order_right = (Button)view.findViewById(R.id.tv_order_right);
			tv_order_left = (Button)view.findViewById(R.id.tv_order_left);
			tv_book_name = (TextView)view.findViewById(R.id.tv_book_name);
			tv_book_price = (TextView)view.findViewById(R.id.tv_book_price);
			tv_book_time = (TextView)view.findViewById(R.id.tv_book_time);
			tv_order_type = (TextView)view.findViewById(R.id.tv_order_type);
		}
	}
}