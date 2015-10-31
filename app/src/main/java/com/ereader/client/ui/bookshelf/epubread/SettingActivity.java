package com.ereader.client.ui.bookshelf.epubread;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;

public class SettingActivity extends Activity 
{
	public CheckBox networkCheckBox;
	public CheckBox doublePagedCheckBox;
	public CheckBox lockRotationCheckBox;
	public CheckBox globalPaginationCheckBox;
	
	public Button themeWhiteButton;
	public Button themeBrownButton;
	public Button themeBlackButton;
	
	public ImageView themeWhiteImageView;
	public ImageView themeBrownImageView;
	public ImageView themeBlackImageView;
	
	public RadioGroup pageTransitionGroup; 	
	public EReaderApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = (EReaderApplication)getApplication();
		app.loadSetting();
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_setting);
		
		networkCheckBox = (CheckBox)this.findViewById(R.id.networkCheckBox);
		doublePagedCheckBox = (CheckBox)this.findViewById(R.id.doublePagedCheckBox);
		lockRotationCheckBox = (CheckBox)this.findViewById(R.id.lockRotationCheckBox);
		globalPaginationCheckBox = (CheckBox)this.findViewById(R.id.globalPaginationCheckBox);
		
		themeWhiteButton = (Button)this.findViewById(R.id.themeWhiteButton);
		themeWhiteButton.setOnClickListener(onClickListener);
		themeBrownButton = (Button)this.findViewById(R.id.themeBrownButton);
		themeBrownButton.setOnClickListener(onClickListener);		
		themeBlackButton = (Button)this.findViewById(R.id.themeBlackButton);
		themeBlackButton.setOnClickListener(onClickListener);
		
		themeWhiteImageView = (ImageView)this.findViewById(R.id.themeWhiteImageView);
		themeWhiteImageView.setScaleType(ScaleType.FIT_CENTER);
		themeWhiteImageView.setAdjustViewBounds(true);
		
		themeBrownImageView = (ImageView)this.findViewById(R.id.themeBrownImageView);
		themeBrownImageView.setScaleType(ScaleType.FIT_CENTER);
		themeBrownImageView.setAdjustViewBounds(true);

		themeBlackImageView = (ImageView)this.findViewById(R.id.themeBlackImageView);
		themeBlackImageView.setScaleType(ScaleType.FIT_CENTER);
		themeBlackImageView.setAdjustViewBounds(true);
		
		
		pageTransitionGroup = (RadioGroup) this.findViewById(R.id.pageTransitionGroup);
	}
	
	private void showToast(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void markTheme(int themeIndex) {
		int markColor = 0xAABFFF00;
		themeWhiteImageView.setBackgroundColor(Color.TRANSPARENT);
		themeBrownImageView.setBackgroundColor(Color.TRANSPARENT);
		themeBlackImageView.setBackgroundColor(Color.TRANSPARENT);
		
		if (themeIndex==0) {
			themeWhiteImageView.setBackgroundColor(markColor);
		}else if (themeIndex==1) {
			themeBrownImageView.setBackgroundColor(markColor);
		}else {
			themeBlackImageView.setBackgroundColor(markColor);
		}
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View arg) {
			int themeIndex = 0;
			if (arg==themeWhiteButton) {
				themeIndex = 0;
			}else if (arg==themeBrownButton) {
				themeIndex = 1;
			}else {
				themeIndex = 2;
			}
			app.setting.theme = themeIndex;
			markTheme(app.setting.theme);
		}
	};
	
	public void loadValues() {
		networkCheckBox.setChecked(app.setting.allow3G);
		doublePagedCheckBox.setChecked(app.setting.doublePaged);
		lockRotationCheckBox.setChecked(app.setting.lockRotation);
		globalPaginationCheckBox.setChecked(app.setting.globalPagination);
		
		int index = app.setting.transitionType;
		if (index==0) pageTransitionGroup.check(R.id.noneRadio);
		else if (index==1) pageTransitionGroup.check(R.id.slideRadio);
		else pageTransitionGroup.check(R.id.curlRadio);
		
		markTheme(app.setting.theme);
	}
	
	public void saveValues() {
		app.setting.allow3G = networkCheckBox.isChecked();
		app.setting.doublePaged = doublePagedCheckBox.isChecked();
		app.setting.lockRotation = lockRotationCheckBox.isChecked();
		app.setting.globalPagination = globalPaginationCheckBox.isChecked();
		
		int index = pageTransitionGroup.indexOfChild(findViewById(pageTransitionGroup.getCheckedRadioButtonId()));
		app.setting.transitionType = index;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		app.loadSetting();
		loadValues();		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saveValues();
		app.saveSetting();		
	}

}
