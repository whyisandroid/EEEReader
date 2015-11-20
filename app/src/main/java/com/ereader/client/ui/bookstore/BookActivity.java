package com.ereader.client.ui.bookstore;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.json.BookResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.BookAdapter;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

public class BookActivity extends BaseActivity implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private AppController controller;
	private Button main_top_right;
	private ListView lv_book;
	private PullToRefreshView pull_refresh_book;
	private List<Book> mList = new ArrayList<Book>();
	private BookAdapter adapter;
	private Page page;
	
	public static final int BOOK =  0; // 更新页面数据 书本
	public static final int BOOK_CATE =  10; // 更新页面数据 书本
	public static final int BOOK_DIS =  -1; // 更新页面数据 书本 ....... 多个
	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	public static final int REFRESH_ERROR =  3; // 刷新失败
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BOOK:
				// 更新页面数据
				BookResp bookResp =  (BookResp)controller.getContext().getBusinessData("BookFeaturedResp");
				mList.addAll(bookResp.getData().getData());
				page = bookResp.getData().getPage();
				adapter.notifyDataSetChanged();
				break;
				case BOOK_CATE:
					// 更新页面数据
					mList.clear();
					String id = getIntent().getExtras().getString("categroyItem_id");
					BookResp bookResp2 =  (BookResp)controller.getContext().getBusinessData("BookFeaturedResp"+id);
					mList.addAll(bookResp2.getData().getData());
					page = bookResp2.getData().getPage();
					adapter.notifyDataSetChanged();
					break;
			case REFRESH_DOWN_OK:
				ToastUtil.showToast(BookActivity.this, "刷新成功！", ToastUtil.LENGTH_LONG);
				pull_refresh_book.onHeaderRefreshComplete();
				break;
			case REFRESH_UP_OK:
				adapter.notifyDataSetChanged();
				pull_refresh_book.onFooterRefreshComplete();
				break;
			case REFRESH_ERROR:
				pull_refresh_book.onHeaderRefreshComplete();
				pull_refresh_book.onFooterRefreshComplete();
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_layout);
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
		lv_book= (ListView)findViewById(R.id.lv_book);
		pull_refresh_book = (PullToRefreshView)findViewById(R.id.pull_refresh_book);
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
		if("经典热销".equals(title)){
			featuredList();
		}else if("推荐阅读".equals(title)) {
			recommend();
		}else if("好评榜".equals(title)){
			//缺失
			recommend();
		}else if("热销榜".equals(title)){
			//缺失
			recommend();
		}else{
			// 分类列表
			String id = getIntent().getExtras().getString("categroyItem_id");
			categroyItem(id);

		}
		
		main_top_right.setText("购物车");
		main_top_right.setOnClickListener(this);
		pull_refresh_book.setOnHeaderRefreshListener(this);
		pull_refresh_book.setOnFooterRefreshListener(this);
		adapter = new BookAdapter(BookActivity.this, mList);
		lv_book.setAdapter(adapter);
		lv_book.setOnItemClickListener(bookItemListener);
	}

	private void categroyItem(final String id) {
		ProgressDialogUtil.showProgressDialog(this, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.categroyItem(mhandler,id);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}

	private void featuredList() {
		ProgressDialogUtil.showProgressDialog(this, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.featuredList(mhandler);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}
	private void recommend(){
		ProgressDialogUtil.showProgressDialog(this, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.recommend(mhandler);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}
	private OnItemClickListener bookItemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Book book = mList.get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("detailBook", book);
			IntentUtil.intent(BookActivity.this, bundle,BookDetailActivity.class,false);
		}
	};

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

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		page.getTotal();
		
		mhandler.sendEmptyMessageDelayed(REFRESH_UP_OK, 3000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mhandler.sendEmptyMessageDelayed(REFRESH_DOWN_OK, 3000);
	}
}
