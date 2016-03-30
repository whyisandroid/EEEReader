package com.ereader.client.ui.bookstore;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.BookSearch;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.SubCategory;
import com.ereader.client.entities.json.BookData;
import com.ereader.client.entities.json.BookSearchData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.BookAdapter;
import com.ereader.client.ui.adapter.BookSearchAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

// 搜索 
public class BookSearchActivity extends BaseActivity implements
		OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private AppController controller;
	private ListView lv_book_search;
	private List<Book> mList = new ArrayList<Book>();
	private BookSearchAdapter adapter;
	private EditText et_book_search;
	private Page page;
	private PullToRefreshView pull_refresh_book;
	private String mSearch;
	private boolean mClear = false;

	public static final int REFRESH_ERROR = 3; // 刷新失败
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if(mClear){
					mList.clear();
				}

				BookSearchData bookResp = (BookSearchData) controller.getContext().getBusinessData("SearchBookResp"+mSearch);
				for (int i = 0; i < bookResp.getData().size(); i++) {
					boolean flag = true;

					for (int j = 0; j < mList.size(); j++) {
						if(bookResp.getData().get(i).getInfo().getProduct_id().equals(mList.get(j).getInfo().getProduct_id())){
							flag = false;
						}
					}
					if(flag){
						mList.add(bookResp.getData().get(i));
					}
				}
				page = bookResp.getPage();
				adapter.notifyDataSetChanged();
				pull_refresh_book.onHeaderRefreshComplete();
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
		setContentView(R.layout.book_search_layout);
		controller = AppController.getController(this);
		findView();
		initView();
	}

	/**
	 * 
	 * 方法描述：FindView
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		lv_book_search = (ListView)findViewById(R.id.lv_book_search);
		et_book_search = (EditText)findViewById(R.id.et_book_search);
		pull_refresh_book = (PullToRefreshView) findViewById(R.id.pull_refresh_book);
	}

	/**
	 * 
	 * 方法描述：初始化 View
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		adapter = new BookSearchAdapter(BookSearchActivity.this, mList);
		lv_book_search.setAdapter(adapter);
		lv_book_search.setOnItemClickListener(bookItemListener);
		pull_refresh_book.setOnHeaderRefreshListener(this);
		pull_refresh_book.setOnFooterRefreshListener(this);
		et_book_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				mSearch = s.toString();
				mClear = true;
				search(new PageRq());
			}
		});
	}
	
	private void search(final PageRq mPageRq) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.search(mPageRq,mSearch,mHandler);
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
			IntentUtil.intent(BookSearchActivity.this, bundle,BookDetailActivity.class,false);
		}
	};
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView1:
			break;
		default:
			break;
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		PageRq mPageRq = new PageRq();
		if(page.getCurrent_page() == page.getLast_page()){
			ToastUtil.showToast(BookSearchActivity.this, "没有更多了", ToastUtil.LENGTH_LONG);
			pull_refresh_book.onFooterRefreshComplete();
			return;
		}
		mPageRq.setPage(page.getCurrent_page() + 1);
		mClear = false;
		search(mPageRq);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mClear = false;
		PageRq mPageRq = new PageRq();
		search(mPageRq);
	}
}
