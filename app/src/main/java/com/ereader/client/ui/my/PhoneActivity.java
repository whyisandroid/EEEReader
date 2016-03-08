package com.ereader.client.ui.my;

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

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

public class PhoneActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private Button main_top_right;
	private TextView tv_pwd_get_code;
	private EditText mNewPhone;
	private EditText mCode;
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
		mNewPhone = (EditText)findViewById(R.id.et_phone);
		mCode = (EditText)findViewById(R.id.phone_et_code);
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
		main_top_right.setOnClickListener(this);
		tv_pwd_get_code.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case  R.id.tv_pwd_get_code:
				final String phone = mNewPhone.getText().toString();
				if(!TextUtils.isEmpty(StringUtil.moblie(phone))){
					ToastUtil.showToast(PhoneActivity.this,StringUtil.moblie(phone),ToastUtil.LENGTH_LONG);
					return;
				}
				String loginPhone = EReaderApplication.getInstance().getLogin().getPhone();
				if(phone.equals(loginPhone)){
					ToastUtil.showToast(PhoneActivity.this,"该手机号已存在",ToastUtil.LENGTH_LONG);
					return;
				}


				ProgressDialogUtil.showProgressDialog(this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.sendCode(mHandler,phone,"update_phone");
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
				break;
		case  R.id.main_top_right:
			final String mNPhone = mNewPhone.getText().toString();
			if(!TextUtils.isEmpty(StringUtil.moblie(mNPhone))){
				ToastUtil.showToast(PhoneActivity.this,StringUtil.moblie(mNPhone),ToastUtil.LENGTH_LONG);
				return;
			}else{
				controller.getContext().addBusinessData("mNewPhone", mNPhone);
			}

			String mCodeValue = mCode.getText().toString();
			if(TextUtils.isEmpty(mCodeValue)){
				ToastUtil.showToast(PhoneActivity.this,"验证码不能为空",ToastUtil.LENGTH_LONG);
				return;
			}else{
				controller.getContext().addBusinessData("mPhoneCode",mCodeValue);
			}
			ProgressDialogUtil.showProgressDialog(this, "", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.updatePhone();
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
			tv_pwd_get_code.setEnabled(true);
			tv_pwd_get_code.setText("获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			is_validate_tip = false;
			tv_pwd_get_code.setEnabled(false);
			tv_pwd_get_code.setText("倒计时"+millisUntilFinished / 1000 + "s");
		}
	}

}
