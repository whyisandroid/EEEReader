package com.ereader.client.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;

import com.ereader.client.R;
import com.ereader.common.util.IntentUtil;

public class MainActivity extends Activity {

	private static final int MSG_CASE_HOME = 1;//home界面标识

	private final Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MSG_CASE_HOME:
					goHome();
					break;
				default:
					break;
			}
		}
	};

	private void goHome() {
		IntentUtil.intent(MainActivity.this, MainFragmentActivity.class);
		this.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mHandler.sendEmptyMessageDelayed(MSG_CASE_HOME,3000);
	}
}
