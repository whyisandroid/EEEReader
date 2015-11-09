package com.ereader.client.ui.my;

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
import com.ereader.client.entities.MessageFriends;
import com.ereader.client.entities.json.MessageData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.MessageFriendsApplyAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class MessageFriendApplyFragment extends Fragment implements OnClickListener,

OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_message_friends;
	private PullToRefreshView pull_refresh_message_friends;
	private List<MessageFriends> mList = new ArrayList<MessageFriends>();
	private MessageFriendsApplyAdapter adapter;

	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	public static final int 	AGREE = 3;  //好友添加成功
	public static final int 	REFUSE = 4;  //拒绝成功
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_DOWN_OK:
				MessageData messageData = (MessageData)controller.getContext().getBusinessData("MessageApplyResp");
				mList.clear();
				mList.addAll(messageData.getData());
				pull_refresh_message_friends.onHeaderRefreshComplete();
				adapter.notifyDataSetChanged();
				break;
			case REFRESH_UP_OK:
				adapter.notifyDataSetChanged();
				pull_refresh_message_friends.onFooterRefreshComplete();
				break;
				case AGREE:
					int positon = (int)msg.obj;
					mList.get(positon).setStatus("2");
					adapter.notifyDataSetChanged();
					break;
				case REFUSE:
					int positon2 = (int)msg.obj;
					mList.get(positon2).setStatus("3");
					adapter.notifyDataSetChanged();
					break;
			default:
				break;
			}
		};
	};


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
		adapter = new MessageFriendsApplyAdapter(mContext, mList,mhandler);
		lv_message_friends.setAdapter(adapter);
		getFriendsApply();
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
		getFriendsApply();
	}

	/**
	 * 方法描述：TODO
	 * @author: why
	 * @time: 2014-10-21 上午11:17:14
	 */
	private void getFriendsApply() {
		ProgressDialogUtil.showProgressDialog(mContext, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getFriendsApply(mhandler);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}
}
