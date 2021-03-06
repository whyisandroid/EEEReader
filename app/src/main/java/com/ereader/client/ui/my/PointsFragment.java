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
import com.ereader.client.entities.Bill;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.json.OrderData;
import com.ereader.client.entities.json.PointData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.PointsAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.ToastUtil;

@SuppressLint("ValidFragment")
public class PointsFragment extends Fragment implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_points;
	private PullToRefreshView pull_refresh_points;
	private List<Bill> mList = new ArrayList<Bill>();
	private PointsAdapter adapter;
	private String balance;
	private Page page;
	
	public static final int REFRESH_DOWN_OK = 1; // 刷新
	public static final int REFRESH_ERROR = 3;
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_DOWN_OK:

				PointData pointData = (PointData)controller.getContext().getBusinessData("PointResp"+balance+"point");
				for (int i = 0; i < pointData.getData().size(); i++) {
					boolean flag = true;

					for (int j = 0; j < mList.size(); j++) {
						if(pointData.getData().get(i).getId().equals(mList.get(j).getId())) {
							flag = false;
						}
					}
					if(flag){
						mList.add(pointData.getData().get(i));
					}
				}
				page = pointData.getPage();
				adapter.notifyDataSetChanged();
				pull_refresh_points.onHeaderRefreshComplete();
				pull_refresh_points.onFooterRefreshComplete();
				break;
			case REFRESH_ERROR:
				pull_refresh_points.onHeaderRefreshComplete();
				pull_refresh_points.onFooterRefreshComplete();
				break;

			default:
				break;
			}
		};
	};


	@SuppressLint("ValidFragment")
	public PointsFragment(String balance){
		this.balance = balance;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.my_points_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}
	private void findView() {
		lv_points= (ListView)view.findViewById(R.id.lv_points);
		pull_refresh_points = (PullToRefreshView)view.findViewById(R.id.pull_refresh_points);
	}
	private void initView() {
		pull_refresh_points.setOnHeaderRefreshListener(this);
		pull_refresh_points.setOnFooterRefreshListener(this);
		adapter = new PointsAdapter(mContext, mList);
		lv_points.setAdapter(adapter);
	}
	@Override
	public void onResume() {
		super.onResume();
		onHeaderRefresh(pull_refresh_points);
	}

	public void getPointList(final PageRq pageRq) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getPointList(mhandler, balance,"point",pageRq);
			}
		}).start();
	}
	
	@Override
	public void onClick(View v) {
		
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		PageRq mPageRq = new PageRq();
		if(page.getCurrent_page() == page.getLast_page()){
			ToastUtil.showToast(getActivity(), "没有更多了", ToastUtil.LENGTH_LONG);
			pull_refresh_points.onFooterRefreshComplete();
			return;
		}
		mPageRq.setPage(page.getCurrent_page() + 1);
		getPointList(mPageRq);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		PageRq mPageRq = new PageRq();
		getPointList(mPageRq);
	}
}
