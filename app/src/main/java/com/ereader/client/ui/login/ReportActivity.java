package com.ereader.client.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

public class ReportActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView tv_report;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_register_report_layout);
		controller = AppController.getController(this);
		findView();
		initView();
	}

	/**
	 * 
	 * 方法描述：FindView
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		tv_report = (TextView) findViewById(R.id.tv_report);
	}

	/**
	 * 
	 * 方法描述：初始化 View
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("用户注册协议");
	}

	private void getList() {
		ProgressDialogUtil.showProgressDialog(this, "努力加载中…", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.featuredList(mHandler);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_report:
			break;
		default:
			break;
		}
	}
}
