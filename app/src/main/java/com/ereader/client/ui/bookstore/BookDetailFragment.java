package com.ereader.client.ui.bookstore;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;

@SuppressLint("ValidFragment")
public class BookDetailFragment extends Fragment implements OnClickListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private List<String> mList = new ArrayList<String>();
	private String value ;
	private TextView tv_book_detail;
	
	public BookDetailFragment(String value) {
		this.value = value;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.book_detail_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}
	private void findView() {
		tv_book_detail = (TextView)view.findViewById(R.id.tv_book_detail);
	}
	
	private void initView() {
		tv_book_detail.setText(value.trim());
	}
	
	
	
	@Override
	public void onClick(View v) {
		
	}
}
