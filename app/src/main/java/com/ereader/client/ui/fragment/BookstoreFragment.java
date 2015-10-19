package com.ereader.client.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BookStoreAdapter;
import com.ereader.client.ui.bookstore.BookActivity;
import com.ereader.client.ui.bookstore.BookSearchActivity;
import com.ereader.client.ui.bookstore.BookTitleActivity;
import com.ereader.client.ui.bookstore.CategroyActivity;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

public class BookstoreFragment  extends Fragment implements OnClickListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_book_store;
	private Button main_top_right;
	private Button main_top_left;
	private String[] mList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.book_store_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}
	private void findView() {
		lv_book_store = (ListView)view.findViewById(R.id.lv_book_store);
		main_top_right= (Button)view.findViewById(R.id.main_top_right);
		main_top_left= (Button)view.findViewById(R.id.main_top_left_text);
	}
	private void initView() {
		((TextView) view.findViewById(R.id.tv_main_top_title)).setText("电子书城");
		main_top_right.setText("购物车");
		main_top_left.setText("搜索");
		main_top_left.setOnClickListener(this);
		main_top_right.setOnClickListener(this);
		
		mList  = getResources().getStringArray(R.array.store);
		BookStoreAdapter adapter = new BookStoreAdapter(mContext, mList);
		lv_book_store.setAdapter(adapter);
		lv_book_store.setOnItemClickListener(storeItemListener);
	}
	
	private OnItemClickListener storeItemListener = new  OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Bundle bundle = new Bundle();
			bundle.putString("title", mList[position]);
			switch (position) {
			case 0:
				latest(bundle);
				break;
			case 1:
				IntentUtil.intent(mContext, bundle,BookActivity.class,false);
				break;
			case 2:
				IntentUtil.intent(mContext, bundle,BookActivity.class,false);
				break;
			case 3:
				discount(bundle);
				break;
			case 4:
				IntentUtil.intent(mContext, bundle,BookActivity.class,false);
				break;
			case 5:
				IntentUtil.intent(mContext, bundle,BookActivity.class,false);
				break;
			case 6:
				IntentUtil.intent(mContext, bundle,CategroyActivity.class,false);
				break;
			default:
				break;
			}
			
		}

		private void discount(final Bundle bundle) {
			ProgressDialogUtil.showProgressDialog(mContext, "", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.discount(bundle);
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
		}

		private void latest(final Bundle bundle) {
			ProgressDialogUtil.showProgressDialog(mContext, "", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.latest(bundle);
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
		
		}
	};
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_top_right:
			IntentUtil.intent(mContext, BuyCarActivity.class);
			break;
		case R.id.main_top_left_text:
			IntentUtil.intent(mContext, BookSearchActivity.class);
			break;
		default:
			break;
		}
	}
}
