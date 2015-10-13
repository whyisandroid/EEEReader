package com.ereader.client.ui.pay;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.json.WalletData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
// 充值
public class RechargeActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView mEcoin;
	private Button main_top_right;
	private Button bt_recharge;
	private RadioButton mPaybao;
	private RadioButton mPayCard;
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


				Alipay pay = new Alipay(this	);
				pay.pay();
				break;
		default:
			break;
		}
	}
}
