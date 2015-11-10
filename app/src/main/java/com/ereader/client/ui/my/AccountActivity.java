package com.ereader.client.ui.my;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.MyBookAdapter;
import com.ereader.common.util.IntentUtil;

public class AccountActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private ListView lv_my_account;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_account_layout);
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
		lv_my_account = (ListView)findViewById(R.id.lv_my_account);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("账户设置");
		String[] mList = new String[]{};
		String loginAccount = EReaderApplication.getInstance().getLocalInfoByKeyValue("LoginAccount");
		if(loginAccount.contains("@")){
			mList = getResources().getStringArray(R.array.account2);
		}else{
		  mList = getResources().getStringArray(R.array.account1);
		}
		MyBookAdapter adapter = new MyBookAdapter(this, mList);
		lv_my_account.setAdapter(adapter);
		lv_my_account.setOnItemClickListener(bookItemListener);
	}
	
	private OnItemClickListener bookItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				IntentUtil.intent(AccountActivity.this, NameActivity.class);
				break;
			case 1:
				String loginAccount = EReaderApplication.getInstance().getLocalInfoByKeyValue("LoginAccount");
				if(loginAccount.contains("@")){
					IntentUtil.intent(AccountActivity.this,EmailActivity.class);
				}else{
					IntentUtil.intent(AccountActivity.this, PhoneActivity.class);
				}
				break;
			case 2:
				IntentUtil.intent(AccountActivity.this, PwdActivity.class);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.textView1:
			break;
		default:
			break;
		}
	}

}
