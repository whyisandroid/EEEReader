package com.ereader.client.ui.my;

import android.os.Bundle;
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

public class NameActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private Button main_top_right;
	private EditText et_account_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_name_layout);
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
		et_account_name = (EditText)findViewById(R.id.et_account_name);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("姓名");
		Login login = EReaderApplication.getInstance().getLogin();
		if(login != null){
			et_account_name.setText(login.getRealname());
		}
		main_top_right.setText("保存");
		main_top_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case  R.id.main_top_right:
			ToastUtil.showToast(this, "保存成功", ToastUtil.LENGTH_LONG);
			this.finish();
			break;
		default:
			break;
		}
	}

}
