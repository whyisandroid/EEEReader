package com.ereader.client.ui.pay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ereader.client.R;
import com.ereader.client.entities.CardInfo;
import com.ereader.client.entities.RechargeOrder;
import com.ereader.client.entities.json.WalletData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.my.CouponsFragment;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

// 充值
public class RechargeActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView mEcoin;
	private TextView recharge_tv_money;
	private Button main_top_right;
	private Button bt_recharge;
	private RadioButton mPaybao;
	private RadioButton mPayCard;
	private EditText mRechMoney;
	private EditText mRechCard;
	public static final int GET_ORDER = 1;
	public static final int ORDER_SUCCESS = 2;
	public static final int SUCCESS = 3;
	public static final int FAILE = 4;

	private  Alipay pay;

	/** 输入框小数的位数*/
	private static final int DECIMAL_DIGITS = 2;

	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case Alipay.PAY_SUCCESS:
					// 充值成功 修改金额
					WalletData wallet = (WalletData)controller.getContext().getBusinessData("WalletResp");
					String sumMoney = StringUtil.addMoney(wallet.getEcoin().toString(),mRechMoney.getText().toString());
					wallet.setEcoin(sumMoney);
					controller.getContext().addBusinessData("WalletResp", wallet);
					mEcoin.setText("￥" + sumMoney);
					break;
				case ORDER_SUCCESS:
					RechargeOrder order = (RechargeOrder)controller.getContext().getBusinessData("OrderRechargeResp");
					pay.setmOrder(order);
					pay.pay();
					break;
				case GET_ORDER:
					// 先生成订单
					ProgressDialogUtil.showProgressDialog(RechargeActivity.this, "", false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							controller.getRechargeOrder(mHander,mRechMoney.getText().toString());
							ProgressDialogUtil.closeProgressDialog();
						}
					}).start();
					break;
				case SUCCESS:
					CardInfo cardInfo = (CardInfo)controller.getContext().getBusinessData("CardInfoResp");
					recharge_tv_money.setText("￥" + cardInfo.total);
					break;
				case CouponsFragment.INPUT_OK:  // 充值卡 充值成功 修改余额
					CardInfo cardInfo1 = (CardInfo)controller.getContext().getBusinessData("CardInfoResp");
					WalletData wallet1 = (WalletData)controller.getContext().getBusinessData("WalletResp");
					String sumMoney1 = StringUtil.addMoney(wallet1.getEcoin().toString(),cardInfo1.total);
					wallet1.setEcoin(sumMoney1);
					controller.getContext().addBusinessData("WalletResp", wallet1);
					mEcoin.setText("￥" + sumMoney1);
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
		recharge_tv_money = (TextView)findViewById(R.id.recharge_tv_money);
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
		pay = new Alipay(RechargeActivity.this,mHander);
		((TextView) findViewById(R.id.tv_main_top_title)).setText("充值");
		main_top_right.setText("账单");
		main_top_right.setOnClickListener(this);
		WalletData wallet = (WalletData)controller.getContext().getBusinessData("WalletResp");
		mEcoin.setText("￥" + wallet.getEcoin());
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
				if (isChecked) {
					mPaybao.setChecked(false);
				}
			}
		});

		/*if(mPaybao.isChecked()){
			pay.check();
		}*/
		bt_recharge.setOnClickListener(this);


		mRechMoney.setFilters(new InputFilter[]{lengthfilter});
		mRechMoney.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().startsWith("00")){
					mRechMoney.setText("0");
					return;
				}

				if(s.toString().startsWith(".")){
					mRechMoney.setText("");
					return;
				}


				recharge_tv_money.setText("￥" + s);
			}
		});


		mRechCard.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.length() == 12){
					getRechCard(s.toString());
				}
			}
		});
	}


	/**
	 *  设置小数位数控制
	 */
	InputFilter lengthfilter = new InputFilter() {
		public CharSequence filter(CharSequence source, int start, int end,
								   Spanned dest, int dstart, int dend) {
			// 删除等特殊字符，直接返回
			if ("".equals(source.toString())) {
				return null;
			}
			String dValue = dest.toString();
			String[] splitArray = dValue.split("//.");
			if (splitArray.length > 1) {
				String dotValue = splitArray[1];
				int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
				if (diff > 0) {
					return source.subSequence(start, end - diff);
				}
			}
			return null;
		}
	};


	private void getRechCard(final String card){
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getRechCard(mHander,card);
			}
		}).start();
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
						ToastUtil.showToast(RechargeActivity.this, "充值金额不能为空", ToastUtil.LENGTH_LONG);
						return;
					}

					String[] moneyArr = money.split("\\.");
					if(moneyArr.length > 1){
						if(moneyArr[1].length()>2){
							ToastUtil.showToast(RechargeActivity.this, "充值金额不能多余两位小数", ToastUtil.LENGTH_LONG);
							return;
						}
					}
					// 检测 支付环境
					pay.check(Alipay.SDK_CHECK_FLAG);
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
							controller.useCard(card,mHander,-1,"C");
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
