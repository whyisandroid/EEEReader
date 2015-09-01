package com.ereader.client.ui.more;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

public class HelpActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private RelativeLayout rl_help_info;
	private RelativeLayout rl_help_buy;
	private RelativeLayout rl_help_pay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help__layout);
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
		rl_help_info = (RelativeLayout) findViewById(R.id.rl_help_info);
		rl_help_buy = (RelativeLayout) findViewById(R.id.rl_help_buy);
		rl_help_pay = (RelativeLayout) findViewById(R.id.rl_help_pay);
	}

	/**
	 * 
	 * 方法描述：初始化 View
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("帮助中心");
		rl_help_info.setOnClickListener(this);
		rl_help_buy.setOnClickListener(this);
		rl_help_pay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_help_info:
			getArticle("10"); 
			break;
		case R.id.rl_help_buy:
			getArticle("20"); 
			break;
		case R.id.rl_help_pay:
			getArticle("30"); 
			break;
		default:
			break;
		}
	}
	private void getArticle(final String id) {
		ProgressDialogUtil.showProgressDialog(this, "通信中…", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getArticle(id);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}
}
