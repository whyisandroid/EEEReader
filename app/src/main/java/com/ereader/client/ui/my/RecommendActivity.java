package com.ereader.client.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.RechargeOrder;
import com.ereader.client.entities.Recommend;
import com.ereader.client.entities.json.RecommendData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.RecommendAdapter;
import com.ereader.client.ui.pay.RechargeActivity;
import com.ereader.common.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;
// 推荐
public class RecommendActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private  List<Recommend> mList = new ArrayList<Recommend>();
	private ListView lv_my_recommend;
	private RecommendAdapter adapter;
	public static final int SUCCESS = 1;


	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case SUCCESS:
					RecommendData reData = (RecommendData)controller.getContext().getBusinessData("RecommendResp");
					mList.addAll(reData.getData());
					adapter.notifyDataSetChanged();
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
	}
	/**
	 * 
	  * 方法描述：FindView
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		lv_my_recommend = (ListView)findViewById(R.id.lv_my_recommend);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("我的推荐");
		adapter = new RecommendAdapter(this, mList);
		lv_my_recommend.setAdapter(adapter);

		ProgressDialogUtil.showProgressDialog(RecommendActivity.this, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.myRecommend(mHander);
				ProgressDialogUtil.closeProgressDialog();
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
}
