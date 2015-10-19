package com.ereader.client.ui.my;

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
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

public class PwdActivity extends BaseActivity implements OnClickListener {
	private EditText mPwd;
	private EditText mNewPwd1;
	private EditText mNewPwd2;
	private AppController controller;
	private Button main_top_right;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_pwd_layout);
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
		mPwd = (EditText)findViewById(R.id.pwd_et_pwd);
		mNewPwd1 = (EditText)findViewById(R.id.pwd_et_pwd1);
		mNewPwd2 = (EditText)findViewById(R.id.pwd_et_pwd2);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("密码");
		main_top_right.setText("保存");
		main_top_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.main_top_right:
			String mPwdValue = mPwd.getText().toString();
			String value = StringUtil.pwd(mPwdValue);
			if(!TextUtils.isEmpty(value)){
				ToastUtil.showToast(PwdActivity.this, value, ToastUtil.LENGTH_LONG);
				return;
			}else{
				controller.getContext().addBusinessData("mPwd",mPwdValue);
			}

			String pwd1 = mNewPwd1.getText().toString();
			String pwd1Value = StringUtil.pwd(pwd1);
			if(!TextUtils.isEmpty(pwd1Value)){
				ToastUtil.showToast(PwdActivity.this, pwd1Value, ToastUtil.LENGTH_LONG);
				return;
			}
			String pwd2 = mNewPwd2.getText().toString();
			String pwd2Value = StringUtil.pwd(pwd2);
			if(!TextUtils.isEmpty(pwd2Value)){
				ToastUtil.showToast(PwdActivity.this,pwd2Value,ToastUtil.LENGTH_LONG);
				return;
			}

			if(!pwd1.equals(pwd2)){
				ToastUtil.showToast(PwdActivity.this,"两次输入密码不一置",ToastUtil.LENGTH_LONG);
				return;
			}else{
				controller.getContext().addBusinessData("mNewPwd",pwd2);
			}
			ProgressDialogUtil.showProgressDialog(this, "", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.updatePwd();
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
			break;
		default:
			break;
		}
	}

}
