package com.ereader.client.ui.pay;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Category;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseFragmentActivity;
import com.ereader.client.ui.adapter.BillFragsAdapter;
import com.ereader.client.ui.adapter.BookTabsAdapter;
import com.ereader.client.ui.adapter.ThreeTabsAdapter;
import com.ereader.client.ui.view.ScrollingTabsView;
import com.ereader.client.ui.view.TabsView;


// 我的账单
public class BillActivity extends BaseFragmentActivity implements OnClickListener {
	private AppController controller;
	private TabsView stabs_bill;
	private ViewPager vpager_bill;
	private Button main_top_right;
	private List<Category> mListTitle = new ArrayList<Category>();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_bill_layout);
		controller = AppController.getController(this);
		findView();
		initView();
	}
	/**
	 * 
	  * 方法描述：FindView
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		main_top_right = (Button)findViewById(R.id.main_top_right);
		stabs_bill = (TabsView)findViewById(R.id.stabs_bill);
		vpager_bill = (ViewPager)findViewById(R.id.vpager_bill);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("账单");
			main_top_right.setOnClickListener(this);
			mListTitle.add(new Category("全部","all"));
			mListTitle.add(new Category("购书","outlay"));
			mListTitle.add(new Category("充值","income"));
		
		BillFragsAdapter orderAdapter = new BillFragsAdapter(getSupportFragmentManager(),mListTitle);
		vpager_bill.setAdapter(orderAdapter);
		
		// 设置缓存fragment的数量
		vpager_bill.setOffscreenPageLimit(2);
		vpager_bill.setCurrentItem(0);
		vpager_bill.setPageMargin(4);

		List<String> mlist = new ArrayList<>();
		mlist.add("全部");
		mlist.add("购书");
		mlist.add("充值");
		ThreeTabsAdapter adapter = new ThreeTabsAdapter(this,mlist);
		stabs_bill.setAdapter(adapter);
		stabs_bill.setViewPager(vpager_bill);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case  R.id.main_top_right:
			break;
		default:
			break;
		}
	}
}
