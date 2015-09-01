package com.ereader.client.ui.my;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.ToastUtil;
// 写书评
public class SPActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private Button bt_sp_submint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_sp_layout);
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
		bt_sp_submint = (Button)findViewById(R.id.bt_sp_submint);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("写书评");
		bt_sp_submint.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.bt_sp_submint:
			
			ToastUtil.showToast(SPActivity.this, "提交成功", ToastUtil.LENGTH_LONG);
			this.finish();
			break;
		default:
			break;
		}
	}
}
