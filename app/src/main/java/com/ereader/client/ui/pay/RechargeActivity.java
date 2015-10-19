package com.ereader.client.ui.pay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.RechargeOrder;
import com.ereader.client.entities.json.WalletData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

// 充值
public class RechargeActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView mEcoin;
	private Button main_top_right;
	private Button bt_recharge;
	private RadioButton mPaybao;
	private RadioButton mPayCard;
	private EditText mRechMoney;
	private EditText mRechCard;
	public static final int ORDER_SUCCESS = 0;

	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case ORDER_SUCCESS:
					RechargeOrder order = (RechargeOrder)controller.getContext().getBusinessData("OrderRechargeResp");
					Alipay pay = new Alipay(RechargeActivity.this,order);
					pay.pay();
					break;
				default:
					break;
			}



		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_recharge_layout);
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
		mEcoin = (TextView)findViewById(R.id.recharge_tv_ecoin);
		main_top_right = (Button)findViewById(R.id.main_top_right);
		bt_recharge = (Button)findViewById(R.id.bt_recharge);
		mPaybao = (RadioButton)findViewById(R.id.rb_pay_bao);
		mPayCard = (RadioButton)findViewById(R.id.rb_pay_card);
		mRechMoney = (EditText)findViewById(R.id.recharge_et_bao);
		mRechCard = (EditText)findViewById(R.id.recharge_et_card);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("充值");
		main_top_right.setText("账单");
		main_top_right.setOnClickListener(this);
		WalletData wallet = (WalletData)controller.getContext().getBusinessData("WalletResp");
		mEcoin.setText("当前余额：￥"+wallet.getEcoin().getTotal());
		mPaybao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mPayCard.setChecked(false);
				}
			}
		});
		mPayCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mPaybao.setChecked(false);
				}
			}
		});
		bt_recharge.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.main_top_right:
			IntentUtil.intent(RechargeActivity.this, BillActivity.class);
			break;
			case R.id.bt_recharge:
				if(mPaybao.isChecked()){

					final String money = mRechMoney.getText().toString();
					if(TextUtils.isEmpty(money)){
						ToastUtil.showToast(RechargeActivity.this,"充值金额不能为空",ToastUtil.LENGTH_LONG);
						return;
					}

					// 先生成订单
					ProgressDialogUtil.showProgressDialog(RechargeActivity.this, "", false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							controller.getRechargeOrder(mHander,money);
							ProgressDialogUtil.closeProgressDialog();
						}
					}).start();
				}

				if(mPayCard.isChecked()){
					final String card = mRechCard.getText().toString();
					if(TextUtils.isEmpty(card)){
						ToastUtil.showToast(RechargeActivity.this,"充值卡不能为空",ToastUtil.LENGTH_LONG);
						return;
					}
					ProgressDialogUtil.showProgressDialog(RechargeActivity.this, "", false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							controller.useCard(card);
							ProgressDialogUtil.closeProgressDialog();
						}
					}).start();

				}
				break;
		default:
			break;
		}
	}
}
