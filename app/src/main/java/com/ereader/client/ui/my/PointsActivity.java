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
import com.ereader.client.ui.adapter.PointsFragsAdapter;
import com.ereader.client.ui.view.ScrollingTabsView;

// 我的积分
public class PointsActivity extends BaseFragmentActivity implements OnClickListener {
	private AppController controller;
	private ScrollingTabsView stabs_points;
	private ViewPager vpager_points;
	private Button main_top_right;
	private List<Category> mListTitle;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_points_layout);
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
		stabs_points = (ScrollingTabsView)findViewById(R.id.stabs_points);
		vpager_points = (ViewPager)findViewById(R.id.vpager_points);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("我的积分");
		mListTitle = new ArrayList<Category>();
			mListTitle.add(new Category("全部","1"));
			mListTitle.add(new Category("积分收入","2"));
			mListTitle.add(new Category("积分支出","3"));
		PointsFragsAdapter orderAdapter = new PointsFragsAdapter(getSupportFragmentManager(),mListTitle.size());
		vpager_points.setAdapter(orderAdapter);
		
		// 设置缓存fragment的数量
		vpager_points.setOffscreenPageLimit(2);
		vpager_points.setCurrentItem(0);
		vpager_points.setPageMargin(4);
		
		
		BookTabsAdapter adapter = new BookTabsAdapter(this,mListTitle);
		stabs_points.setAdapter(adapter);
		stabs_points.setViewPager(vpager_points);
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