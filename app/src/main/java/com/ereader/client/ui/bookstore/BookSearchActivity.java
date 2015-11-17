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
import com.ereader.client.entities.SubCategory;
import com.ereader.client.entities.json.BookData;
import com.ereader.client.entities.json.BookSearchData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.BookAdapter;
import com.ereader.client.ui.adapter.BookSearchAdapter;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

// 搜索 
public class BookSearchActivity extends BaseActivity implements
		OnClickListener {
	private AppController controller;
	private ListView lv_book_search;
	private List<Book> mList = new ArrayList<Book>();
	private BookSearchAdapter adapter;
	private EditText et_book_search;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mList.clear();
				BookSearchData data = (BookSearchData) controller.getContext().getBusinessData("SearchBookResp");
				mList.addAll(data.getData());
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
				search(s.toString());
			}
		});
	}
	
	private void search(final String value) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.search(value,mHandler);
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
}
