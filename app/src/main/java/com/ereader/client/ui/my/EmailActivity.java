package com.ereader.client.ui.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.Login;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

public class EmailActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private Button main_top_right;
	private EditText mNewEmail;
	private EditText mPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_email_layout);
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
		main_top_right = (Button)findViewById(R.id.main_top_right);
		mNewEmail = (EditText)findViewById(R.id.et_account_email);
		mPwd = (EditText)findViewById(R.id.email_et_pwd);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("邮箱");
		main_top_right.setText("保存");
		main_top_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.main_top_right:
			final String email1 = mNewEmail.getText().toString();
			String email1Value = StringUtil.email(email1);
			if(!TextUtils.isEmpty(email1Value)){
				ToastUtil.showToast(EmailActivity.this, email1Value, ToastUtil.LENGTH_LONG);
				return;
			}
			final String mPwdValue = mPwd.getText().toString();
			String value = StringUtil.pwd(mPwdValue);
			if(!TextUtils.isEmpty(value)){
				ToastUtil.showToast(EmailActivity.this, value, ToastUtil.LENGTH_LONG);
				return;
			}

			ProgressDialogUtil.showProgressDialog(this, "", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.updateEmail(email1,mPwdValue);
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
			break;
		default:
			break;
		}
	}

}
