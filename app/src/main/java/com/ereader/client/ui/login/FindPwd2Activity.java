package com.ereader.client.ui.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ToastUtil;

public class FindPwd2Activity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private Button bt_findpwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_findpwd2_layout);
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
		bt_findpwd = (Button)findViewById(R.id.bt_findpwd);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("找回密码");
		bt_findpwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.bt_findpwd:
			ToastUtil.showToast(this, "密码修改成功", ToastUtil.LENGTH_LONG);
			IntentUtil.intent(this, LoginActivity.class);
			break;
		default:
			break;
		}
	}
}
