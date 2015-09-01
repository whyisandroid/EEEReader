package com.ereader.client.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.MyBookAdapter;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.more.MoreActivity;
import com.ereader.client.ui.my.AccountActivity;
import com.ereader.client.ui.my.CollectionActivity;
import com.ereader.client.ui.my.CouponsActivity;
import com.ereader.client.ui.my.FriendsActivity;
import com.ereader.client.ui.my.MessageActivity;
import com.ereader.client.ui.my.MySPActivity;
import com.ereader.client.ui.my.OrderActivity;
import com.ereader.client.ui.my.PointsActivity;
import com.ereader.client.ui.my.RecommendActivity;
import com.ereader.client.ui.pay.RechargeActivity;
import com.ereader.common.util.IntentUtil;

public class MyBookFragment extends Fragment implements OnClickListener {
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_mybook;
	private TextView tv_main_top_title;
	private Button main_top_right;
	private Button main_top_left;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.book_my_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}

	private void findView() {
		lv_mybook = (ListView) view.findViewById(R.id.lv_mybook);
		main_top_right = (Button) view.findViewById(R.id.main_top_right);
		main_top_left = (Button) view.findViewById(R.id.main_top_left_text);
		tv_main_top_title = (TextView)view.findViewById(R.id.tv_main_top_title);
	}

	private void initView() {
		tv_main_top_title.setText("用户名");
		main_top_right.setText("充值");
		main_top_left.setText("更多");
		String[] mList = getResources().getStringArray(R.array.myBook);
		MyBookAdapter adapter = new MyBookAdapter(mContext, mList);
		lv_mybook.setAdapter(adapter);
		main_top_left.setOnClickListener(this);
		main_top_right.setOnClickListener(this);
		tv_main_top_title.setOnClickListener(this);
		lv_mybook.setOnItemClickListener(bookItemListener);
	}
	
	
	
	@Override
	public void onResume() {
		super.onResume();
		if(EReaderApplication.getInstance().isLogin()){
			tv_main_top_title.setText(EReaderApplication.getInstance().getLogin().getNickname());
		}else{
			tv_main_top_title.setText("未登录");
		}
	}
	
	
	

	private OnItemClickListener bookItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			if(!EReaderApplication.getInstance().isLogin()){
				IntentUtil.intent(mContext, LoginActivity.class);
				return;
			}
			switch (position) {
			case 0:
				IntentUtil.intent(mContext, OrderActivity.class);
				break;
			case 1:
				IntentUtil.intent(mContext, CollectionActivity.class);
				break;
			case 2:
				IntentUtil.intent(mContext, RecommendActivity.class);
				break;
			case 3:
				IntentUtil.intent(mContext, MySPActivity.class);
				break;
			case 4:
				IntentUtil.intent(mContext, CouponsActivity.class);
				break;
			case 5:
				IntentUtil.intent(mContext, PointsActivity.class);
				break;
			case 6:
				IntentUtil.intent(mContext, AccountActivity.class);
				break;
			case 7:
				IntentUtil.intent(mContext, MessageActivity.class);
				break;
			case 8:
				IntentUtil.intent(mContext, FriendsActivity.class);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.main_top_left_text:
			IntentUtil.intent(mContext, MoreActivity.class);
			break;
		case R.id.tv_main_top_title:
			IntentUtil.intent(mContext, LoginActivity.class);
			break;
		case R.id.main_top_right:
			IntentUtil.intent(mContext, RechargeActivity.class);
			break;
		default:
			break;
		}
	}

}
