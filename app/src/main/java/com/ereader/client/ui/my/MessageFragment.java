package com.ereader.client.ui.my;

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
import android.widget.ListView;

import com.ereader.client.R;
import com.ereader.client.entities.Category;
import com.ereader.client.entities.Message;
import com.ereader.client.entities.MessageFriends;
import com.ereader.client.entities.json.MessageData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.MessageAdapter;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

@SuppressLint("ValidFragment")
public class MessageFragment extends MesFragment implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_message;
	private PullToRefreshView pull_refresh_message;
	private List<MessageFriends> mList = new ArrayList<MessageFriends>();
	private MessageAdapter adapter;

	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_DOWN_OK:
				MessageData messageData = (MessageData)controller.getContext().getBusinessData("MessageFriendsResp");
				mList.clear();
				mList.addAll(messageData.getData());
				pull_refresh_message.onHeaderRefreshComplete();
				adapter.notifyDataSetChanged();
				break;
			case REFRESH_UP_OK:
				adapter.notifyDataSetChanged();
				pull_refresh_message.onFooterRefreshComplete();
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.my_message_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}

	@Override
	public void delete() {
		mList.clear();
		adapter.notifyDataSetChanged();
		ToastUtil.showToast(getActivity(), "清理成功", ToastUtil.LENGTH_LONG);
	}

	private void findView() {
		lv_message= (ListView)view.findViewById(R.id.lv_message);
		pull_refresh_message = (PullToRefreshView)view.findViewById(R.id.pull_refresh_message);
	}
	private void initView() {
		pull_refresh_message.setOnHeaderRefreshListener(this);
		pull_refresh_message.setOnFooterRefreshListener(this);
		adapter = new MessageAdapter(mContext, mList);
		lv_message.setAdapter(adapter);

	}

	@Override
	public void onResume() {
		super.onResume();
		message();
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
				controller.getFriendsMessage(mhandler);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}
}
