package com.ereader.client.ui.more;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.webkit.WebView;

public class MyWebview extends WebView {

	public MyWebview(Context context) {
		super(context);
	}

	public MyWebview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyWebview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private ViewGroup parentView;

	public void setParentView(ViewGroup parentView) {
		this.parentView = parentView;
	}

	float mPosX, mPosY, mCurrentPosX, mCurrentPosY;
	boolean flag = true;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		// 按下
		case MotionEvent.ACTION_DOWN:
			flag = true;
			mPosX = event.getX();
			mPosY = event.getY();
			break;
		// 移动
		case MotionEvent.ACTION_MOVE:

			mCurrentPosX = event.getX();
			mCurrentPosY = event.getY();
			if (mCurrentPosX - mPosX > 0 && Math.abs(mCurrentPosY - mPosY) < 10) {
				flag = true;
			} else if (mCurrentPosX - mPosX < 0
					&& Math.abs(mCurrentPosY - mPosY) < 10) {
				flag = true;
			} else if ((mCurrentPosY - mPosY > 0 && Math.abs(mCurrentPosX
					- mPosX) < 10)
					|| (mCurrentPosY - mPosY < 0 && Math.abs(mCurrentPosX
					- mPosX) < 10)) {
				flag = false;
				// parentView.requestDisallowInterceptTouchEvent(false);
				// System.out.println("0000000000");
				// return false;
			}
			break;
		// 拿起
		case MotionEvent.ACTION_UP:
			break;
		default:
			flag = false;
			break;
		}
		/*
		 * System.out.println("flag:" + flag); if (!flag) {
		 * parentView.requestDisallowInterceptTouchEvent(false); return false; }
		 */
		return super.onTouchEvent(event);
	}
}
