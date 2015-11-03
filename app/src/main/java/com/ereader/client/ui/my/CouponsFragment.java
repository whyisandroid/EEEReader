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
import com.ereader.client.entities.Gift;
import com.ereader.client.entities.json.GiftData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.CouponsAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.ProgressDialogUtil;

@SuppressLint("ValidFragment")
public class CouponsFragment extends Fragment implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_coupons;
	private PullToRefreshView pull_refresh_coupons;
	private List<Gift> mList = new ArrayList<Gift>();
	private CouponsAdapter adapter;
	private Category cate;
	
	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	public static final int INPUT_OK = 33;  //充值成功

	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_DOWN_OK:
				GiftData giftData = (GiftData)controller.getContext().getBusinessData("GiftResp"+cate.getCategory_id());
				mList.clear();
				mList.addAll(giftData.getData());
				pull_refresh_coupons.onHeaderRefreshComplete();
				adapter.notifyDataSetChanged();
				break;
			case REFRESH_UP_OK:
				adapter.notifyDataSetChanged();
				pull_refresh_coupons.onFooterRefreshComplete();
				break;
				case INPUT_OK:
					if("0".equals(cate.getCategory_id())){
						int position = (int)msg.obj;
						mList.get(position).setStatus("2");
					}else if("1".equals(cate.getCategory_id())){
					int position = (int)msg.obj;
					mList.remove(position);
					}
					adapter.notifyDataSetChanged();
					break;
			default:
				break;
			}
		};
	};

	@SuppressLint("ValidFragment")
	public CouponsFragment(Category cate) {
		this.cate = cate;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.my_coupons_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}
	private void findView() {
		lv_coupons = (ListView)view.findViewById(R.id.lv_coupons);
		pull_refresh_coupons = (PullToRefreshView)view.findViewById(R.id.pull_refresh_coupons);
	}
	private void initView() {
		pull_refresh_coupons.setOnHeaderRefreshListener(this);
		pull_refresh_coupons.setOnFooterRefreshListener(this);
		adapter = new CouponsAdapter(mContext, mList,mhandler);
		lv_coupons.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		getCoupons();
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
		getCoupons();
	}

	private void getCoupons() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getCoupons(mhandler, cate.getCategory_id());
			}
		}).start();
	}
}
