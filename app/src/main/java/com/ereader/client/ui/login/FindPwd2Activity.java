package com.ereader.client.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

public class FindPwd2Activity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private EditText et_findpwd_code1;
	private EditText et_findpwd_code2;
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
		et_findpwd_code1 = (EditText)findViewById(R.id.et_findpwd_code1);
		et_findpwd_code2 = (EditText)findViewById(R.id.et_findpwd_code2);
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
			String pwd1 = et_findpwd_code1.getText().toString();
			String pwd1Value = StringUtil.pwd(pwd1);
			if(!TextUtils.isEmpty(pwd1Value)){
				ToastUtil.showToast(FindPwd2Activity.this, pwd1Value, ToastUtil.LENGTH_LONG);
				return;
			}
			String pwd2 = et_findpwd_code2.getText().toString();
			String pwd2Value = StringUtil.pwd(pwd2);
			if(!TextUtils.isEmpty(pwd2Value)){
				ToastUtil.showToast(FindPwd2Activity.this,pwd2Value,ToastUtil.LENGTH_LONG);
				return;
			}

			if(!pwd1.equals(pwd2)){
				ToastUtil.showToast(FindPwd2Activity.this,"两次输入密码不一置",ToastUtil.LENGTH_LONG);
				return;
			}else{
				controller.getContext().addBusinessData("pwdCode",pwd2);
			}

			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.findCode();
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
			break;
		default:
			break;
		}
	}
}
