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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.Comment;
import com.ereader.client.entities.json.CommentData;
import com.ereader.client.entities.json.CommentResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BookSPAdapter;
// 书评
@SuppressLint("ValidFragment")
public class BookSPFragment extends Fragment implements OnClickListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_book_sp;
	private List<Comment> mList = new ArrayList<Comment>();
	private Book book;
	private BookSPAdapter adapter;
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mList.clear();
				CommentData resp = (CommentData)controller.getContext().getBusinessData("CommentResp");
				mList.addAll(resp.getData());
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};
	public BookSPFragment(Book book) {
		this.book = book;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.book_sp_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		getComment();
	}

	private void getComment() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getComment(mHandler,book.getInfo().getProduct_id());
			}
		}).start();
	
	}

	private void findView() {
		lv_book_sp = (ListView)view.findViewById(R.id.lv_book_sp);
	}
	private void initView() {
		adapter = new BookSPAdapter(mContext, mList);
		lv_book_sp.setAdapter(adapter);
		lv_book_sp.setOnItemClickListener(bookItemListener);
		
	}
	
	private OnItemClickListener bookItemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		}
	};
	
	
	
	
	@Override
	public void onClick(View v) {
		
	}
}
