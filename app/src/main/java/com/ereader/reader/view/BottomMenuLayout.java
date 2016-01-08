package com.ereader.reader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ereader.reader.read.settings.ReadSettings;
import com.glview.view.View;
import com.glview.view.View.OnClickListener;
import com.glview.widget.LinearLayout;
import com.glview.widget.SeekBar;
import com.glview.widget.SeekBar.OnSeekBarChangeListener;
import com.ereader.client.R;
import com.ereader.reader.activity.ChaptersDialog;
import com.glview.widget.TextView;

public class BottomMenuLayout extends LinearLayout implements OnClickListener, View.OnLongClickListener, OnSeekBarChangeListener , View.OnTouchListener {
	
	private View mChapterIcon;//目录
//	private View mChapterPrevious;//上一章
//	private View mChapterNext;//下一章
	private SeekBar mChapterSeek;//进度条
	
	private View mBrightMenuIcon;//亮度调节
	private View mPageStyleMenuIcon;//翻页方式
	private View mFontMenuIcon;//阅读设置

	//=========
	private View mFontSizeMinus;//字号-
	private View mFontSizePlus;//字号+
	private TextView mFontSizeText;//大小

	int mFontSize = -1;

	private LinearLayout read_setting;

	private LinearLayout read_base;
	//=========
	
	ChaptersDialog mChaptersDialog;
	
	ReaderLayout mReaderLayout;
	BookReadView mBookReadView;

	public BottomMenuLayout(Context context) {
		super(context);
	}

	public BottomMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BottomMenuLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BottomMenuLayout(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	
	public void setReaderLayout(ReaderLayout readerLayout) {
		mReaderLayout = readerLayout;
		mBookReadView = readerLayout.getBookReadView();
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mChapterIcon = findViewById(R.id.menu_chapter);
		mChapterIcon.setOnClickListener(this);
//		mChapterPrevious = findViewById(R.id.chapter_previous);
//		mChapterPrevious.setOnClickListener(this);
//		mChapterNext = findViewById(R.id.chapter_next);
//		mChapterNext.setOnClickListener(this);
		mChapterSeek = (SeekBar) findViewById(R.id.chapter_seek);
		mChapterSeek.setOnSeekBarChangeListener(this);
		
		mBrightMenuIcon = findViewById(R.id.menu_bright);
		mBrightMenuIcon.setOnClickListener(this);
		
		mPageStyleMenuIcon = findViewById(R.id.menu_pagestyle);
		mPageStyleMenuIcon.setOnClickListener(this);
		
		mFontMenuIcon = findViewById(R.id.menu_font);
		mFontMenuIcon.setOnClickListener(this);
		//字体
		mFontSizeMinus = findViewById(R.id.font_size_minus);
		mFontSizeMinus.setOnClickListener(this);
		mFontSizeMinus.setOnLongClickListener(this);
		mFontSizeMinus.setOnTouchListener(this);
		mFontSizePlus = findViewById(R.id.font_size_plus);
		mFontSizePlus.setOnClickListener(this);
		mFontSizePlus.setOnLongClickListener(this);
		mFontSizePlus.setOnTouchListener(this);
		mFontSizeText = (TextView) findViewById(R.id.font_size_value);
		read_setting=(LinearLayout)findViewById(R.id.read_setting);
		read_base=(LinearLayout)findViewById(R.id.read_base);

	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		dismissChaptersDialog();
	}
	
	public void show() {
		read_base.setVisibility(View.VISIBLE);
		read_setting.setVisibility(View.GONE);
		Fade.fadeIn(this, Fade.BOTTOM);
		loadCurrentProgress();
	}
	public void showBottomSetting() {
		read_base.setVisibility(View.GONE);
		read_setting.setVisibility(View.VISIBLE);
		Fade.fadeIn(this, Fade.BOTTOM);
		mFontSizeText.setText(String.valueOf(ReadSettings.getFontSize(getContext())));//初始化字体大小
		//loadCurrentProgress();
	}
	
	public void hide() {
		Fade.fadeOut(this, Fade.BOTTOM);
	}
	
	private void loadCurrentProgress() {
		mChapterSeek.setProgress((int) (mBookReadView.getBookPageManager().getCurrentProgress() * mChapterSeek.getMax()));
	}
	
	private void dismissChaptersDialog() {
		postToAndroid(new Runnable() {
			@Override
			public void run() {
				if (mChaptersDialog != null && mChaptersDialog.isShowing()) {
					try {
						mChaptersDialog.dismiss();
					} catch (Throwable tr) {
					}
					mChaptersDialog = null;
				}
			}
		});
	}
	
	private void showChaptersDialog() {
		dismissChaptersDialog();
		postToAndroid(new Runnable() {
			@Override
			public void run() {
				mChaptersDialog = new ChaptersDialog(getContext(), mBookReadView.getBookPageManager());
				mChaptersDialog.show();
			}
		});
		mReaderLayout.hideMenu();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event) || true;
	}
	
	@Override
	public void onClick(View v) {
		if (v == mChapterIcon) {
			showChaptersDialog();
		}
//		else if (v == mChapterPrevious) {
//			mBookReadView.getBookPageManager().seekToPreviousChapter();
//			loadCurrentProgress();
//		} else if (v == mChapterNext) {
//			mBookReadView.getBookPageManager().seekToNextChapter();
//			loadCurrentProgress();
//		}
		else if (v == mBrightMenuIcon) {
			mReaderLayout.showBrightMenu();
		} else if (v == mPageStyleMenuIcon) {
			mReaderLayout.showPageStyleMenu();
		} else if (v == mFontMenuIcon) {
			mReaderLayout.showFontMenu();
		}else if (v == mFontSizeMinus) {
			changeFontSize(false);
		} else if (v == mFontSizePlus) {
			changeFontSize(true);
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int progress = seekBar.getProgress();
		int max = seekBar.getMax();
		float p = ((float) progress) / max;
		mBookReadView.getBookPageManager().seekTo(p);
	}
	@Override
	public boolean onLongClick(View v) {
		if (v == mFontSizeMinus) {
			mFontSizeMinusRunnable.run();
		} else if (v == mFontSizePlus) {
			mFontSizePlusRunnable.run();
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v == mFontSizeMinus || v == mFontSizePlus) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				removeCallbacks(mFontSizeMinusRunnable);
				removeCallbacks(mFontSizePlusRunnable);
			}
		}
		return false;
	}


	private void changeFontSize(boolean plus) {
		if (mFontSize == -1) {
			mFontSize = ReadSettings.getFontSize(getContext());
		}
		mFontSize = ReadSettings.checkFontSize(plus ? mFontSize + 1 : mFontSize - 1);
		mFontSizeText.setText(String.valueOf(mFontSize));
		removeCallbacks(mChangeFontSizeRunnable);
		postDelayed(mChangeFontSizeRunnable, 200);
	}

	Runnable mChangeFontSizeRunnable = new Runnable() {
		@Override
		public void run() {
			ReadSettings.setFontSize(getContext(), mFontSize);
		}
	};
	Runnable mFontSizeMinusRunnable = new Runnable() {
		@Override
		public void run() {
			changeFontSize(false);
			postDelayed(this, 100);
		}
	};

	Runnable mFontSizePlusRunnable = new Runnable() {
		@Override
		public void run() {
			changeFontSize(true);
			postDelayed(this, 100);
		}
	};
}
