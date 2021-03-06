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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.Category;
import com.ereader.client.entities.DisCategory;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.json.BookResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BookAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

@SuppressLint("ValidFragment")
public class BookFragment extends Fragment implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_book;
	private ImageView iv_book_up;
	private PullToRefreshView pull_refresh_book;
	private List<Book> mList = new ArrayList<Book>();
	private BookAdapter adapter;
	private Category mCate;
	private DisCategory mDisCate;
	private Page page;
	private PageRq mPageRq;
	private int categroy = 0;
	
	
	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BookActivity.BOOK:
				// 更新页面数据
				BookResp bookResp =  (BookResp)controller.getContext().getBusinessData("BookFeaturedResp"+mCate.getCategory_id());
				for (int i = 0; i < bookResp.getData().getData().size(); i++) {
					boolean flag = true;
					
					for (int j = 0; j < mList.size(); j++) {
						if(bookResp.getData().getData().get(i).getInfo().getProduct_id().equals(mList.get(j).getInfo().getProduct_id())){
							flag = false;
						}
					}
					if(flag){
						mList.add(bookResp.getData().getData().get(i));
					}
				}
				
				page = bookResp.getData().getPage();
				adapter.notifyDataSetChanged();
				pull_refresh_book.onHeaderRefreshComplete();
				pull_refresh_book.onFooterRefreshComplete();
				break;
				case BookActivity.BOOK_DIS:
					// 更新页面数据
					BookResp bookResp2 =  (BookResp)controller.getContext().getBusinessData("BookFeaturedResp"+mDisCate.getName());
					for (int i = 0; i < bookResp2.getData().getData().size(); i++) {
						boolean flag = true;

						for (int j = 0; j < mList.size(); j++) {
							if(bookResp2.getData().getData().get(i).getInfo().getProduct_id().equals(mList.get(j).getInfo().getProduct_id())){
								flag = false;
							}
						}
						if(flag){
							mList.add(bookResp2.getData().getData().get(i));
						}
					}

					page = bookResp2.getData().getPage();
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
		iv_book_up= (ImageView)view.findViewById(R.id.iv_book_up);
		lv_book= (ListView)view.findViewById(R.id.lv_book);
		pull_refresh_book = (PullToRefreshView)view.findViewById(R.id.pull_refresh_book);

	}
	private void initView() {
		pull_refresh_book.setOnHeaderRefreshListener(this);
		pull_refresh_book.setOnFooterRefreshListener(this);
		adapter = new BookAdapter(mContext, mList);
		lv_book.setAdapter(adapter);
		lv_book.setOnItemClickListener(bookItemListener);
		iv_book_up.setOnClickListener(this);
		/*lv_book.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem != 0) {
					iv_book_up.setVisibility(View.VISIBLE);
				}else {
					iv_book_up.setVisibility(View.GONE);
				}
			}
		});*/
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
	private void getBookList(final PageRq pageRq) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.bookList(mhandler,mCate.getCategory_id(),pageRq);
			}
		}).start();
	}
	
	
	private void getDisBookList(final PageRq pageRq) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.booDiskList(mhandler,mDisCate,pageRq);
			}
		}).start();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.iv_book_up:
				lv_book.setSelection(0);
				break;
		}
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPageRq = new PageRq();
		if(page.getCurrent_page() == page.getLast_page()){
			ToastUtil.showToast(getActivity(),"没有更多了",ToastUtil.LENGTH_LONG);
			pull_refresh_book.onFooterRefreshComplete();
			return;
		}
		mPageRq.setPage(page.getCurrent_page()+1);
		if(categroy == 0){
			getBookList(mPageRq);
		}else{
			getDisBookList(mPageRq);
		}
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPageRq = new PageRq();
		if(categroy == 0){
			getBookList(mPageRq);
		}else{
			getDisBookList(mPageRq);
		}
	}
}
