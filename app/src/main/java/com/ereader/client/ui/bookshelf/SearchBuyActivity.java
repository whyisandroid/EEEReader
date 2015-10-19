package com.ereader.client.ui.bookshelf;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.ereader.client.R;
import com.ereader.client.entities.BookShow;
import com.ereader.client.entities.BookShowInfo;
import com.ereader.client.entities.json.BookShowResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.ShelfSearchAdapter;
import com.ereader.client.ui.dialog.DialogUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

public class SearchBuyActivity extends BaseActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
	private AppController controller;
	private GridView gridv_book_search;
	private EditText main_top_title;
	private ShelfSearchAdapter adapter;


	public final static int  _OK=1000;
	public final static int _DELETE=1001;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case _OK:
					setupData();
					break;
				case _DELETE:
					delshelfBuyBooks();
					break;
				default:
					break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shelf_search_layout);
		controller = AppController.getController(this);
		findView();
		initView();
	}
	private void findView() {
		main_top_title = (EditText)findViewById(R.id.et_book_search);
		gridv_book_search = (GridView)findViewById(R.id.gridv_book_search);
	}
	private void initView() {

		main_top_title.setHint("请输入你要搜索的关键词");

		gridv_book_search.setOnItemClickListener(this);
		gridv_book_search.setOnItemLongClickListener(this);
		getshelfBuyBooks();
//		List<String> list = new ArrayList<String>();
//		list.add("");
//		list.add("");
//		list.add("");
//		list.add("");
//		list.add("");
//		list.add("");
//		list.add("");
//		list.add("");
//		list.add("");
//
//		adapter = new ShelfSearchAdapter(this, list);
//		gridv_book_search.setAdapter(adapter);
	}

	private void setupData(){

		BookShowInfo booksGet= (BookShowInfo) controller.getContext().getBusinessData("BookShowResp");
		List<BookShow> list=null;
		if(null!=booksGet&&null!=booksGet.getData()){
			list=booksGet.getData();
		}
		if(null==list){
			list=new ArrayList<BookShow>();
		}
		if(null==adapter){
			adapter = new ShelfSearchAdapter(SearchBuyActivity.this, list);
			gridv_book_search.setAdapter(adapter);
		}else{
			adapter.setData(list);
		}
	}

	private void getshelfBuyBooks(){
		ProgressDialogUtil.showProgressDialog(SearchBuyActivity.this,"获取中...",false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.shelfBuyBooks(mHandler);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();


	}

	private void delshelfBuyBooks(){
		ProgressDialogUtil.showProgressDialog(SearchBuyActivity.this,"获取中...",false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.shelfDelBuyBooks(mHandler);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();


	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(adapter.isShowDelete()){
			BookShow book=adapter.getItem(position);
			controller.getContext().addBusinessData("delete.book_id",book.getBook_id());
			DialogUtil.delBookById(SearchBuyActivity.this,book,mHandler);

		}else{
			ToastUtil.showToast(SearchBuyActivity.this,"下载", Toast.LENGTH_SHORT);
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		adapter.setIsShowDelete(!adapter.isShowDelete());
		return true;
	}
}

