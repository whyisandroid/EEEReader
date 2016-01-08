package com.ereader.reader.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.ereader.client.R;

public class MenuDialog extends Dialog implements View.OnClickListener {
	
	View mAbout = null;

	public MenuDialog(Context context) {
		super(context, R.style.MenuDialogTheme);
		setCanceledOnTouchOutside(true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		getWindow().getDecorView().setPadding(0, 0, 0, 0);
		
		setContentView(R.layout.layout_menu_dialog);
		mAbout = findViewById(R.id.menu_about);
		mAbout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			dismiss();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

}
