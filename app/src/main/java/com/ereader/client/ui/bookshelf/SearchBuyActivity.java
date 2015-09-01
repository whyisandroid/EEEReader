package com.ereader.client.ui.bookshelf;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.ShelfSearchAdapter;

public class SearchBuyActivity extends BaseActivity {
	private AppController controller;
	private GridView gridv_book_search;
	private EditText main_top_title;
	
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
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		main_top_title.setHint("请输入你要搜索的关键词");
		ShelfSearchAdapter adapter = new ShelfSearchAdapter(this, list);
		gridv_book_search.setAdapter(adapter);  
	}
}

