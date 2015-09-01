package com.ereader.client.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.ereader.client.EReaderApplication;
import com.ereader.client.service.AppController;
import com.ereader.client.service.AppManager;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.common.util.IntentUtil;

/**
 * 应用程序Activity的基类
 * @author
 * @version 
 * @created 
 */
public class BaseFragmentActivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}
	
	public void goBack(View view) {
		this.finish();  
		IntentUtil.popFromLeft(this);
	}
	
	public void goBuy(View view) {
		if(((Button)view).getText().toString().startsWith("购物车")){
			if(!EReaderApplication.getInstance().isLogin()){
				IntentUtil.intent(AppController.getController().getCurrentActivity(), LoginActivity.class);
			}
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		AppController.getController(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
}
