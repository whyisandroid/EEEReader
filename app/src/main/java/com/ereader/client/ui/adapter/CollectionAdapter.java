package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.service.AppController;
import com.ereader.common.util.ProgressDialogUtil;

public class CollectionAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Book> mList ;
	private Context mContext;
	private Handler mHandler;

	public CollectionAdapter(Context mContext,Handler mHandler,List<Book> list) {
		inflater=LayoutInflater.from(mContext);
		mList = list;
		this.mContext = mContext;
		this.mHandler = mHandler;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Book book = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_collection_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.tv_book_name.setText(book.getInfo().getName());
		holder.tv_book_time.setText("收藏时间："+book.getUpdated_at());
		holder.tv_book_price.setText("￥"+book.getPrice());
		
		holder.tv_order_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				ProgressDialogUtil.showProgressDialog(mContext, "请稍等…", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						AppController.getController().deleteCollection(mHandler,position,book.getInfo().getProduct_id());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			}
		});
		
		
		holder.tv_collection_buycar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {


				ProgressDialogUtil.showProgressDialog(mContext, "添加中…", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						AppController.getController().addBuyCar(mHandler,book.getInfo().getProduct_id());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			}
		});
		return convertView;
	}
	class ViewHolder{
		private Button tv_collection_buycar;
		private TextView tv_order_delete;
		private TextView tv_book_name;
		private TextView tv_book_time;
		private TextView tv_book_price;
		public void findView(View view){
			tv_collection_buycar = (Button)view.findViewById(R.id.tv_collection_buycar);
			tv_order_delete = (TextView)view.findViewById(R.id.tv_order_delete);
			tv_book_name = (TextView)view.findViewById(R.id.tv_book_name);
			tv_book_time = (TextView)view.findViewById(R.id.tv_book_time); 
			tv_book_price = (TextView)view.findViewById(R.id.tv_book_price); 
		}
	}

}
