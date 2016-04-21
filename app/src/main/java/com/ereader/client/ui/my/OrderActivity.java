package com.ereader.client.ui.my;

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
import com.ereader.client.ui.adapter.BookTabsAdapter;
import com.ereader.client.ui.adapter.ForTabsAdapter;
import com.ereader.client.ui.adapter.OrderFragsAdapter;
import com.ereader.client.ui.view.ScrollingTabsView;
import com.ereader.client.ui.view.TabsView;

// 订单
public class OrderActivity extends BaseFragmentActivity implements OnClickListener {
	private AppController controller;
	private TabsView st_order;
	private ViewPager vp_order;
	private Button main_top_right;
	private List<Category> mListTitle;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order_layout);
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
		st_order = (TabsView)findViewById(R.id.st_order);
		vp_order = (ViewPager)findViewById(R.id.vp_order);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("我的订单");
		mListTitle = new ArrayList<Category>();
		mListTitle.add(new Category("全部", ""));
		mListTitle.add(new Category("已完成", "1"));
		mListTitle.add(new Category("未支付", "0"));
		mListTitle.add(new Category("已取消", "2"));
		OrderFragsAdapter orderAdapter = new OrderFragsAdapter(getSupportFragmentManager(), mListTitle);
		vp_order.setAdapter(orderAdapter);

		// 设置缓存fragment的数量
		vp_order.setOffscreenPageLimit(2);
		vp_order.setCurrentItem(0);
		vp_order.setPageMargin(4);

		List<String> mlist = new ArrayList<String>();
		mlist.add("全部");
		mlist.add("已完成");
		mlist.add("未支付");
		mlist.add("已取消");
		ForTabsAdapter adapter = new ForTabsAdapter(this, mlist);
		st_order.setAdapter(adapter);
		st_order.setViewPager(vp_order);
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
