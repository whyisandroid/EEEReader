package com.ereader.client.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.login.RegisterActivity.RegisterCountDownTimer;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

public class FindPwdActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView tv_login_findpwd;
	private EditText et_findpwd_code;
	private EditText et_findpwd_phone;
	private Button bt_register;
	
	private RegisterCountDownTimer timer;
	private boolean is_validate_tip = true;
	public static final int CODE_OK = 5;  //验证码  成功
	
	private Handler mHandler = new Handler(){
			
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case CODE_OK:
					timer = new RegisterCountDownTimer(60000, 1000);
					timer.cancel();
					timer.onTick(59000);
					timer.start();
					break;
				default:
					break;
				}
			}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_findpwd_layout);
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
		tv_login_findpwd = (TextView)findViewById(R.id.tv_login_findpwd);
		et_findpwd_code = (EditText)findViewById(R.id.et_findpwd_code);
		et_findpwd_phone = (EditText)findViewById(R.id.et_findpwd_phone);
		bt_register = (Button)findViewById(R.id.bt_register);
	}
	

	/**
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("找回密码");
		tv_login_findpwd.setOnClickListener(this);
		bt_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.tv_login_findpwd:
			final String phone = et_findpwd_phone.getText().toString();
			if(!TextUtils.isEmpty(StringUtil.moblie(phone))){
				ToastUtil.showToast(FindPwdActivity.this,StringUtil.moblie(phone),ToastUtil.LENGTH_LONG);
				return;
			}
			ProgressDialogUtil.showProgressDialog(this, "", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.sendCode(mHandler,phone,"rest_password");
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
			break;
		case  R.id.bt_register:
			// 验证 验证码 是否正确
			if(TextUtils.isEmpty(et_findpwd_code.getText().toString())){
				ToastUtil.showToast(FindPwdActivity.this,"验证码不能为空",ToastUtil.LENGTH_LONG);
				return;
			}
			controller.getContext().addBusinessData("phone",et_findpwd_phone.getText().toString());
			controller.getContext().addBusinessData("vcode",et_findpwd_code.getText().toString());
			Bundle bundle = new Bundle();
			bundle.putString("phone", et_findpwd_phone.getText().toString());
			bundle.putString("code",et_findpwd_code.getText().toString());
			IntentUtil.intent(this,bundle, FindPwd2Activity.class,false);
			break;
		default:
			break;
		}
	}
	
	/* 定义一个倒计时的内部类 */
	class RegisterCountDownTimer extends CountDownTimer {
		public RegisterCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			is_validate_tip = true;
			tv_login_findpwd.setEnabled(true);
			tv_login_findpwd.setText("获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			is_validate_tip = false;
			tv_login_findpwd.setEnabled(false);
			tv_login_findpwd.setText("倒计时"+millisUntilFinished / 1000 + "s");
		}
	}
}
