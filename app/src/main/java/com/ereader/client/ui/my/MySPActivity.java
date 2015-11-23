package com.ereader.client.ui.my;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.SpComment;
import com.ereader.client.entities.json.SPResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.SPAdapter;
import com.ereader.common.util.ProgressDialogUtil;

// 我的书评
public class MySPActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private ListView lv_sp;
	private List<SpComment> mList = new ArrayList<SpComment>();
	private Page page;
	private SPAdapter adapter;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mList.clear();
				SPResp sp = (SPResp)controller.getContext().getBusinessData("SPResp");
				mList.addAll(sp.getData().getData());
				page = sp.getData().getPage();
				adapter.notifyDataSetChanged();
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
		getSP();
	}
	/**
	 * 
	  * 方法描述：FindView
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		lv_sp = (ListView)findViewById(R.id.lv_sp);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("我的书评");
		 adapter = new SPAdapter(this, mList);
		 lv_sp.setAdapter(adapter);
	}
	
	private void getSP() {
		ProgressDialogUtil.showProgressDialog(this, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getSP(mHandler);
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
