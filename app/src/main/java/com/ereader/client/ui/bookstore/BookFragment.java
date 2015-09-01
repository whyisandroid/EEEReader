package com.ereader.client.ui.bookstore;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.Category;
import com.ereader.client.entities.DisCategory;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.json.BookResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BookAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

@SuppressLint("ValidFragment")
public class BookFragment extends Fragment implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_book;
	private PullToRefreshView pull_refresh_book;
	private List<Book> mList = new ArrayList<Book>();
	private BookAdapter adapter;
	private Category mCate;
	private DisCategory mDisCate;
	private Page page;
	private int categroy = 0;
	
	
	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BookActivity.BOOK:
				// 更新页面数据
				BookResp bookResp =  (BookResp)controller.getContext().getBusinessData("BookFeaturedResp");
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
			case REFRESH_DOWN_OK:
				ToastUtil.showToast(mContext, "刷新成功！", ToastUtil.LENGTH_LONG);
				adapter.notifyDataSetChanged();
				pull_refresh_book.onHeaderRefreshComplete();
				pull_refresh_book.onFooterRefreshComplete();
				break;
			case REFRESH_UP_OK:
				adapter.notifyDataSetChanged();
				pull_refresh_book.onHeaderRefreshComplete();
				pull_refresh_book.onFooterRefreshComplete();
				break;
			case BookActivity.REFRESH_ERROR:
				pull_refresh_book.onHeaderRefreshComplete();
				pull_refresh_book.onFooterRefreshComplete();
				break;
			default:
				break;
			}
		};
	};
	
	
	public BookFragment(Category mCate) {
		this.mCate = mCate;
	}
	
	public BookFragment(DisCategory mDisCate,int categroy) {
		this.mDisCate = mDisCate;
		this.categroy = categroy;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.book_store_new_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		onHeaderRefresh(pull_refresh_book);
		return view;
	}

	private void findView() {
		lv_book= (ListView)view.findViewById(R.id.lv_book);
		pull_refresh_book = (PullToRefreshView)view.findViewById(R.id.pull_refresh_book);
	}
	private void initView() {
		pull_refresh_book.setOnHeaderRefreshListener(this);
		pull_refresh_book.setOnFooterRefreshListener(this);
		adapter = new BookAdapter(mContext, mList);
		lv_book.setAdapter(adapter);
		lv_book.setOnItemClickListener(bookItemListener);
	}
	
	private OnItemClickListener bookItemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Book book = mList.get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("detailBook", book);
			IntentUtil.intent(mContext, bundle,BookDetailActivity.class,false);
		}
	};
	private void getBookList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.bookList(mhandler,mCate.getParent_id());
			}
		}).start();
	}
	
	
	private void getDisBookList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.booDiskList(mhandler,mDisCate);
			}
		}).start();
	}
	
	@Override
	public void onClick(View v) {
		
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		if(categroy == 0){
			getBookList();
		}else{
			getDisBookList();
		}
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		if(categroy == 0){
			getBookList();
		}else{
			getDisBookList();
		}
	}
}
