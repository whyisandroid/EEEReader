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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.RegExpUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView tv_regisrer_code;
	private Button bt_register;
	private EditText et_register_code;
	private EditText et_register;
	private EditText et_register_pwd1;
	private EditText et_register_pwd2;
	private RadioGroup rg_register;
	private RadioButton rb_register_phone;
	private RadioButton rb_register_email;
	private boolean phoneRegister = true;
	
	private RelativeLayout rl_register_code;
	
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
		setContentView(R.layout.login_register_layout);
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
		bt_register= (Button)findViewById(R.id.bt_register);
		tv_regisrer_code = (TextView)findViewById(R.id.tv_regisrer_code);
		et_register_code = (EditText)findViewById(R.id.et_register_code);
		et_register = (EditText)findViewById(R.id.et_register);
		et_register_pwd1 = (EditText)findViewById(R.id.et_register_pwd1);
		et_register_pwd2 = (EditText)findViewById(R.id.et_register_pwd2);
		
		rg_register = (RadioGroup)findViewById(R.id.rg_register);
		rb_register_phone = (RadioButton)findViewById(R.id.rb_register_phone);
		rb_register_email = (RadioButton)findViewById(R.id.rb_register_email);
		rl_register_code = (RelativeLayout)findViewById(R.id.rl_register_code);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("注册");
		bt_register.setOnClickListener(this);
		tv_regisrer_code.setOnClickListener(this);
		rg_register.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == rb_register_phone.getId()){
					phoneRegister = true;
					et_register.setHint("手机号");
					rl_register_code.setVisibility(View.VISIBLE);
				}else if(checkedId == rb_register_email.getId()){
					et_register.setHint("邮箱");
					phoneRegister = false;
					rl_register_code.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.tv_regisrer_code:
			if(!StringUtil.isMoblieInput(et_register.getText().toString())){
				ToastUtil.showToast(RegisterActivity.this, "手机号码不合法", ToastUtil.LENGTH_LONG);
				return;
			}else{
				controller.getContext().addBusinessData("regisrerPhone",et_register.getText().toString());
			}
			
			ProgressDialogUtil.showProgressDialog(this, "通信中…", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.getCode(mHandler,et_register.getText().toString());
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
			break;
		case R.id.bt_register:
			if(phoneRegister){
				if(!StringUtil.isMoblieInput(et_register.getText().toString())){
					ToastUtil.showToast(RegisterActivity.this, "手机号码不合法", ToastUtil.LENGTH_LONG);
					return;
				}else{
					controller.getContext().addBusinessData("regisrerPhone",et_register.getText().toString());
				}
				if(et_register_code.getText().toString().length() == 6){
					controller.getContext().addBusinessData("code",et_register_code.getText().toString());
				}else{
					ToastUtil.showToast(RegisterActivity.this, "验证码位数不正确", ToastUtil.LENGTH_LONG);
					return;
				}
			}else{
				if(!RegExpUtil.emailValidation(et_register.getText().toString())){
					ToastUtil.showToast(RegisterActivity.this, "邮箱格式不合法", ToastUtil.LENGTH_LONG);
					return;
				}else{
					controller.getContext().addBusinessData("regisrerEmail",et_register.getText().toString());
				}
			}
			
			String pwdVadition = StringUtil.pwd(et_register_pwd1.getText().toString());
			if(!TextUtils.isEmpty(pwdVadition)){
				ToastUtil.showToast(RegisterActivity.this, pwdVadition, ToastUtil.LENGTH_LONG);
				return;
			}
			
			if(!et_register_pwd1.getText().toString().equals(et_register_pwd2.getText().toString())){
				ToastUtil.showToast(RegisterActivity.this, "两次的密码不一样", ToastUtil.LENGTH_LONG);
				return;
			}else{
				controller.getContext().addBusinessData("regisrerPwd",et_register_pwd1.getText().toString());
			}
			
				ProgressDialogUtil.showProgressDialog(this, "通信中…", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.register();
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
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
			tv_regisrer_code.setText("获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			is_validate_tip = false;
			tv_regisrer_code.setText("倒计时"+millisUntilFinished / 1000 + "s");
		}
	}

}
