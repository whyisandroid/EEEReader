package com.ereader.client.ui.bookstore;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.SubCategory;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.CategroyAdapter;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

public class CategroyActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private Button main_top_right;
	private ExpandableListView el_book_caregroy;
	private List<SubCategory> sList = new ArrayList<SubCategory>();
	private CategroyAdapter adapter;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				sList.clear();
				sList.addAll((ArrayList<SubCategory>) controller.getContext().getBusinessData("SubCategoryResp"));
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_categroy_layout);
		controller = AppController.getController(this);
		findView();
		initView();
		getCategory(mHandler);
	}

	private void getCategory(final Handler mHandler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getCategory(mHandler);
			}
		}).start();
	}

	/**
	 * 
	 * 方法描述：FindView
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		main_top_right = (Button) findViewById(R.id.main_top_right);
		el_book_caregroy = (ExpandableListView) findViewById(R.id.el_book_caregroy);
		
		sList.addAll(EReaderApplication.getInstance().getCategroy().getData());
		adapter = new CategroyAdapter(this, sList);
		el_book_caregroy.setAdapter(adapter);
	}

	/**
	 * 
	 * 方法描述：初始化 View
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("全部分类");
		main_top_right.setText("购物车");
		main_top_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.main_top_right:
			IntentUtil.intent(this, BuyCarActivity.class);
			break;
		default:
			break;
		}
	}
}
