package com.ereader.client.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.RechargeOrder;
import com.ereader.client.entities.Recommend;
import com.ereader.client.entities.json.RecommendData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.RecommendAdapter;
import com.ereader.client.ui.pay.RechargeActivity;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
// 推荐
public class RecommendActivity extends BaseActivity implements OnClickListener ,
		PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private AppController controller;
	private  List<Recommend> mList = new ArrayList<Recommend>();
	private ListView lv_my_recommend;
	private RecommendAdapter adapter;
	public static final int SUCCESS = 1;
	private PullToRefreshView pull_refresh_book;
	private Page page;

	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	public static final int REFRESH_ERROR = 3;
	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case SUCCESS:
					RecommendData reData = (RecommendData)controller.getContext().getBusinessData("RecommendResp");
					for (int i = 0; i < reData.getData().size(); i++) {
						boolean flag = true;

						for (int j = 0; j < mList.size(); j++) {
							if(reData.getData().get(i).getProduct_name().equals(mList.get(j).getProduct_name())) {
								flag = false;
							}
						}
						if(flag){
							mList.add(reData.getData().get(i));
						}
					}
					page = reData.getPage();
					adapter.notifyDataSetChanged();
					pull_refresh_book.onHeaderRefreshComplete();
					pull_refresh_book.onFooterRefreshComplete();
					break;
				case REFRESH_UP_OK:
					adapter.notifyDataSetChanged();
					pull_refresh_book.onFooterRefreshComplete();
					break;
				case REFRESH_ERROR:
					pull_refresh_book.onHeaderRefreshComplete();
					pull_refresh_book.onFooterRefreshComplete();
					break;
				default:
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_recommend_layout);
		controller = AppController.getController(this);
		findView();
		initView();
		onHeaderRefresh(pull_refresh_book);
	}
	/**
	 * 
	  * 方法描述：FindView
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		lv_my_recommend = (ListView)findViewById(R.id.lv_my_recommend);
		pull_refresh_book = (PullToRefreshView) findViewById(R.id.pull_refresh_book);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("我的推荐");
		pull_refresh_book.setOnHeaderRefreshListener(this);
		pull_refresh_book.setOnFooterRefreshListener(this);
		adapter = new RecommendAdapter(this, mList);
		lv_my_recommend.setAdapter(adapter);
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
			ToastUtil.showToast(RecommendActivity.this, "没有更多了", ToastUtil.LENGTH_LONG);
			pull_refresh_book.onFooterRefreshComplete();
			return;
		}
		mPageRq.setPage(page.getCurrent_page() + 1);
		getDate(mPageRq);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		PageRq mPageRq = new PageRq();
		getDate(mPageRq);
	}

	private void getDate(final PageRq mPageRq) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.myRecommend(mHander,mPageRq);
			}
		}).start();
	}
}
