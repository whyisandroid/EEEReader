package com.ereader.client.ui.my;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ereader.client.R;
import com.ereader.client.entities.Category;
import com.ereader.client.entities.Message;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.MessageFriendsAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageFriendsFragment extends Fragment implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_message_friends;
	private PullToRefreshView pull_refresh_message_friends;
	private List<Message> mList = new ArrayList<Message>();
	private MessageFriendsAdapter adapter;
	private Category category;
	
	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_DOWN_OK:
				ToastUtil.showToast(mContext, "刷新成功！", ToastUtil.LENGTH_LONG);
				pull_refresh_message_friends.onHeaderRefreshComplete();
				break;
			case REFRESH_UP_OK:
				adapter.notifyDataSetChanged();
				pull_refresh_message_friends.onFooterRefreshComplete();
				break;

			default:
				break;
			}
		};
	};

	public MessageFriendsFragment(Category category){
		this.category = category;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.my_message_friends_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}
	private void findView() {
		lv_message_friends= (ListView)view.findViewById(R.id.lv_message_friends);
		pull_refresh_message_friends = (PullToRefreshView)view.findViewById(R.id.pull_refresh_message_friends);
	}
	private void initView() {
		pull_refresh_message_friends.setOnHeaderRefreshListener(this);
		pull_refresh_message_friends.setOnFooterRefreshListener(this);
		adapter = new MessageFriendsAdapter(mContext, mList);
		lv_message_friends.setAdapter(adapter);

	}
	
	
	@Override
	public void onClick(View v) {
		
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mhandler.sendEmptyMessageDelayed(REFRESH_UP_OK, 3000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		message();
	}

	/**
	 * 方法描述：TODO
	 * @author: why
	 * @time: 2014-10-21 上午11:17:14
	 */
	private void message() {
		ProgressDialogUtil.showProgressDialog(mContext, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getMessage(mhandler,category.getCategory_id());
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}
}
