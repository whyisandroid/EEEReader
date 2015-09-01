package com.ereader.client.ui.bookstore;

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
import com.ereader.client.entities.DisCategory;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseFragmentActivity;
import com.ereader.client.ui.adapter.BookTabsAdapter;
import com.ereader.client.ui.adapter.BookFragsAdapter;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.view.ScrollingTabsView;
import com.ereader.common.util.IntentUtil;

public class BookTitleActivity extends BaseFragmentActivity implements OnClickListener {
	private AppController controller;
	private ScrollingTabsView st_book_new;
	private ViewPager vp_book_store;
	private Button main_top_right;
	private List<Category> mListTitle  = new ArrayList<Category>();
	private List<DisCategory> mDisListTitle  = new ArrayList<DisCategory>();	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_store_new_layout);
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
		st_book_new = (ScrollingTabsView)findViewById(R.id.st_book_new);
		vp_book_store = (ViewPager)findViewById(R.id.vp_book_store);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		String title = getIntent().getExtras().getString("title");
		((TextView) findViewById(R.id.tv_main_top_title)).setText(title);
		main_top_right.setText("购物车");
		main_top_right.setOnClickListener(this);
		if("最新上架".equals(title)){
			mListTitle = (List<Category>)controller.getContext().getBusinessData("CategoryResp");
			BookFragsAdapter pageAdapter = new BookFragsAdapter(getSupportFragmentManager(),mListTitle);
			vp_book_store.setAdapter(pageAdapter);
			// 设置缓存fragment的数量
			vp_book_store.setOffscreenPageLimit(2);
			vp_book_store.setCurrentItem(0);
			vp_book_store.setPageMargin(4);
			BookTabsAdapter adapter = new BookTabsAdapter(this,mListTitle);
			st_book_new.setAdapter(adapter);
			st_book_new.setViewPager(vp_book_store);
		
		}else if("特价专区".equals(title)){
			mDisListTitle = (List<DisCategory>)controller.getContext().getBusinessData("DisCategoryResp");
			BookFragsAdapter pageAdapter = new BookFragsAdapter(getSupportFragmentManager(),mDisListTitle,1);
			vp_book_store.setAdapter(pageAdapter);
			// 设置缓存fragment的数量
			vp_book_store.setOffscreenPageLimit(2);
			vp_book_store.setCurrentItem(0);
			vp_book_store.setPageMargin(4);
			BookTabsAdapter adapter = new BookTabsAdapter(this,mDisListTitle,1);
			st_book_new.setAdapter(adapter);
			st_book_new.setViewPager(vp_book_store);
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.main_top_right:
			IntentUtil.intent(this, BuyCarActivity.class);
			break;
		default:
			break;
		}
	}
}
