package com.ereader.client.ui.my;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
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
import com.ereader.common.util.ToastUtil;

public class PhoneActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private Button main_top_right;
	private TextView tv_pwd_get_code;
	private EditText et_account_phone;
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
		setContentView(R.layout.my_phone_layout);
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
		tv_pwd_get_code = (TextView)findViewById(R.id.tv_pwd_get_code);
		et_account_phone = (EditText)findViewById(R.id.et_account_phone);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("手机号");
		main_top_right.setText("保存");
		Login login = EReaderApplication.getInstance().getLogin();
		if(login != null){
			et_account_phone.setText("手机号： "+login.getPhone());
		}
		main_top_right.setOnClickListener(this);
		tv_pwd_get_code.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.tv_login_findpwd:
			mHandler.obtainMessage(CODE_OK).sendToTarget();
			break;
		case  R.id.main_top_right:
			ToastUtil.showToast(this, "保存成功", ToastUtil.LENGTH_LONG);
			this.finish();
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
			tv_pwd_get_code.setText("获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			is_validate_tip = false;
			tv_pwd_get_code.setText("倒计时"+millisUntilFinished / 1000 + "s");
		}
	}

}
