package com.ereader.client.ui.bookstore;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseFragmentActivity;
import com.ereader.client.ui.adapter.BookDetailFragsAdapter;
import com.ereader.client.ui.adapter.BookDetailTabsAdapter;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.my.CollectionActivity;
import com.ereader.client.ui.my.FriendsActivity;
import com.ereader.client.ui.view.ScrollingTabsView;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

public class BookDetailActivity extends BaseFragmentActivity implements OnClickListener {
	private ScrollingTabsView st_book_detail;
	private ViewPager vp_book_store;
	private AppController controller;
	private Button main_top_right;
	private Button bt_book_add_buy;
	private Button bt_book_add_friends;
	private List<String> mListTitle;
	private TextView tv_book_collection;
	private TextView tv_book_name;
	private TextView tv_book_author;
	private TextView tv_book_publish;
	private RatingBar rb_book_star;
	private TextView tv_book_price;
	private int buyNum = 0;
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ToastUtil.showToast(BookDetailActivity.this, "收藏成功", ToastUtil.LENGTH_LONG);
				break;
			case 1:
				//TODO  改变购物车的数量
				buyNum++;
				main_top_right.setText("购物车("+buyNum+")");
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_detail_layout);
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
		bt_book_add_buy = (Button)findViewById(R.id.bt_book_add_buy);
		bt_book_add_friends = (Button)findViewById(R.id.bt_book_add_friends);
		st_book_detail = (ScrollingTabsView)findViewById(R.id.st_book_detail);
		vp_book_store = (ViewPager)findViewById(R.id.vp_book_store);
		tv_book_collection = (TextView)findViewById(R.id.tv_book_collection);
		tv_book_name = (TextView)findViewById(R.id.tv_book_name);
		tv_book_author = (TextView)findViewById(R.id.tv_book_author);
		tv_book_publish = (TextView)findViewById(R.id.tv_book_publish);
		tv_book_price = (TextView)findViewById(R.id.tv_book_price);
		rb_book_star = (RatingBar)findViewById(R.id.rb_book_star);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		Book book = (Book)getIntent().getExtras().getSerializable("detailBook");
		tv_book_collection.setTag(book.getInfo().getProduct_id());
		((TextView) findViewById(R.id.tv_main_top_title)).setText("书城");
		BookOnlyResp resp  = (BookOnlyResp)EReaderApplication.getInstance().getBuyCar();
		main_top_right.setText("购物车");
		
		if(resp != null){
			buyNum = resp.getData().size();
			main_top_right.setText("购物车("+buyNum+")");
		}
		Drawable drawable= getResources().getDrawable(R.drawable.b5_03);
		/// 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		main_top_right.setCompoundDrawables(drawable,null,null,null);
		main_top_right.setTextColor(getResources().getColor(R.color.white));
		main_top_right.setOnClickListener(this);
		tv_book_collection.setOnClickListener(this);
		bt_book_add_buy.setOnClickListener(this);
		bt_book_add_friends.setOnClickListener(this);
		mListTitle = new ArrayList<String>();
		mListTitle.add("目录");
		mListTitle.add("内容简介");
		mListTitle.add("作者简介");
		mListTitle.add("书评");
		
		BookDetailFragsAdapter pageAdapter = new BookDetailFragsAdapter(getSupportFragmentManager(),mListTitle.size(),book);
		vp_book_store.setAdapter(pageAdapter);
		
		// 设置缓存fragment的数量
		vp_book_store.setOffscreenPageLimit(2);
		vp_book_store.setCurrentItem(0);
		vp_book_store.setPageMargin(4);
		
		
		BookDetailTabsAdapter adapter = new BookDetailTabsAdapter(this,mListTitle);
		st_book_detail.setAdapter(adapter);
		st_book_detail.setViewPager(vp_book_store);
		setBook(book);
		
		
	}

	private void setBook(Book book) {
		tv_book_name.setText(book.getInfo().getName());
		tv_book_author.setText("作者："+book.getExtra().getAuthor());
		tv_book_publish.setText("出版社："+book.getExtra().getPress());
		rb_book_star.setRating(4);
		tv_book_price.setText("¥ "+book.getPrice());
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_book_add_buy:
			if(!EReaderApplication.getInstance().isLogin()){
				IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
				return;
			}
				ProgressDialogUtil.showProgressDialog(this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.addBuyCar(mHandler, tv_book_collection.getTag().toString());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			break;
		case  R.id.main_top_right:
			IntentUtil.intent(BookDetailActivity.this, BuyCarActivity.class);
			break;
		case R.id.tv_book_collection:
			if(!EReaderApplication.getInstance().isLogin()){
				IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
				return;
			}
				ProgressDialogUtil.showProgressDialog(this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.addCollection(mHandler, tv_book_collection.getTag().toString());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			break;
			case R.id.bt_book_add_friends:
				IntentUtil.intent(BookDetailActivity.this, FriendsActivity.class);
				break;
		default:
			break;
		}
	}
}
