package com.ereader.client.ui.pay;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
// 充值
public class RechargeActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView textView1;
	private Button main_top_right;
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
		textView1 = (TextView)findViewById(R.id.textView1);
		main_top_right = (Button)findViewById(R.id.main_top_right);
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
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.main_top_right:
			IntentUtil.intent(RechargeActivity.this, BillActivity.class);
			break;
		default:
			break;
		}
	}
}
