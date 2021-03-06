package com.ereader.client.ui.pay;

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
import com.ereader.client.entities.Category;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.json.PointData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BillAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.ToastUtil;
@SuppressLint("ValidFragment")
public class BillFragment extends Fragment implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView lv_my_bill;
	private PullToRefreshView pull_refresh_bill;
	private List<Bill> mList = new ArrayList<Bill>();
	private BillAdapter adapter;
	private Category mCate;

	private Page page;
	
	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_ERROR = 3;
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_DOWN_OK:
				PointData pointData = (PointData)controller.getContext().getBusinessData("PointResp"+mCate.getCategory_id()+"ecoin");
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
				pull_refresh_bill.onHeaderRefreshComplete();
				pull_refresh_bill.onFooterRefreshComplete();
				case REFRESH_ERROR:
					pull_refresh_bill.onHeaderRefreshComplete();
					pull_refresh_bill.onFooterRefreshComplete();
				break;

			default:
				break;
			}
		};
	};

	@SuppressLint("ValidFragment")
	public BillFragment(Category cate){
		this.mCate = cate;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.my_bill_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}
	private void findView() {
		lv_my_bill= (ListView)view.findViewById(R.id.lv_my_bill);
		pull_refresh_bill = (PullToRefreshView)view.findViewById(R.id.pull_refresh_bill);
	}
	private void initView() {
		pull_refresh_bill.setOnHeaderRefreshListener(this);
		pull_refresh_bill.setOnFooterRefreshListener(this);
		adapter = new BillAdapter(mContext, mList);
		lv_my_bill.setAdapter(adapter);
	}
	@Override
	public void onResume() {
		super.onResume();
		onHeaderRefresh(pull_refresh_bill);
	}

	public void getPointList(final PageRq pageRq) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getPointList(mhandler, mCate.getCategory_id(),"ecoin",pageRq);
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
			pull_refresh_bill.onFooterRefreshComplete();
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
