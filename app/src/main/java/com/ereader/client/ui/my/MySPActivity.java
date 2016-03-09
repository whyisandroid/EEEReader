package com.ereader.client.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.SpComment;
import com.ereader.client.entities.json.OrderData;
import com.ereader.client.entities.json.SPResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.SPAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

// 我的书评
public class MySPActivity extends BaseActivity implements OnClickListener,
		OnHeaderRefreshListener, OnFooterRefreshListener {
	private AppController controller;
	private ListView lv_sp;
	private List<SpComment> mList = new ArrayList<SpComment>();
	private Page page;
	private SPAdapter adapter;
	private PullToRefreshView pull_refresh_sp;

	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	public static final int REFRESH_ERROR = 3;

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case REFRESH_DOWN_OK:
					mList.clear();
					SPResp sp = (SPResp)controller.getContext().getBusinessData("SPResp");
					for (int i = 0; i < sp.getData().getData().size(); i++) {
						boolean flag = true;

						for (int j = 0; j < mList.size(); j++) {
							if(sp.getData().getData().get(i).getComment_id().equals(mList.get(j).getComment_id())) {
								flag = false;
							}
						}
						if(flag){
							mList.add(sp.getData().getData().get(i));
						}
					}
					page = sp.getData().getPage();
					adapter.notifyDataSetChanged();
					pull_refresh_sp.onHeaderRefreshComplete();
					break;
				case REFRESH_UP_OK:
					adapter.notifyDataSetChanged();
					pull_refresh_sp.onFooterRefreshComplete();
					break;
				case REFRESH_ERROR:
					pull_refresh_sp.onHeaderRefreshComplete();
					pull_refresh_sp.onFooterRefreshComplete();
					break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_sping_layout);
		controller = AppController.getController(this);
		findView();
		initView();
		onHeaderRefresh(pull_refresh_sp);
	}
	/**
	 * 
	  * 方法描述：FindView
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		lv_sp = (ListView)findViewById(R.id.lv_sp);
		pull_refresh_sp = (PullToRefreshView)findViewById(R.id.pull_refresh_sp);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	 * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {

		pull_refresh_sp.setOnHeaderRefreshListener(this);
		pull_refresh_sp.setOnFooterRefreshListener(this);
		((TextView) findViewById(R.id.tv_main_top_title)).setText("我的书评");
		adapter = new SPAdapter(this, mList);
		 lv_sp.setAdapter(adapter);
	}
	
	private void getSP(final PageRq mPageRq ) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getSP(mHandler,mPageRq);
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case  R.id.textView1:
			break;
		default:
			break;
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		PageRq mPageRq = new PageRq();
		if(page.getCurrent_page() == page.getLast_page()){
			ToastUtil.showToast(MySPActivity.this, "没有更多了", ToastUtil.LENGTH_LONG);
			pull_refresh_sp.onFooterRefreshComplete();
			return;
		}
		mPageRq.setPage(page.getCurrent_page() + 1);
		getSP(mPageRq);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		PageRq mPageRq = new PageRq();
		getSP(mPageRq);
	}
}
