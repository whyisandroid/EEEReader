package com.ereader.client.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ereader.client.R;

/**
 * ViewPager 计数点
 */
public class PointView extends View {
	private int with = 120;      
	private int hight = 15;
	private int mSize = 1;
	private int mPosition = 0;
	private static final Paint mOnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static final Paint mOffPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public PointView(Context context) {
		super(context);
		mOffPaint.setColor(context.getResources().getColor(R.color.main_bar));
		mOnPaint.setColor(context.getResources().getColor(R.color.new_color_3));
	}

	public PointView(Context context, int size) {
		this(context);
		mSize = size;
		mOffPaint.setColor(context.getResources().getColor(R.color.main_bar));
		mOnPaint.setColor(context.getResources().getColor(R.color.new_color_3));
	}

	public PointView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mOffPaint.setColor(context.getResources().getColor(R.color.main_bar));
		// mOffPaint.setAlpha(0x7F);
		mOnPaint.setColor(context.getResources().getColor(R.color.new_color_3));
	}

	@Override
	protected void onDraw(Canvas canvas) {

		for (int i = 0; i < mSize; ++i) {

			if (i == mPosition) {
				canvas.drawRect(i * with+hight, 0, (i + 1) * with, hight, mOffPaint);
			} else {
				canvas.drawRect(i * with+hight, 0, (i + 1) * with, hight, mOnPaint);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension((with+hight) * mSize, hight);
	}

	public void setPosition(int id) {
		mPosition = id;
	}

	public void setSize(int size) {
		mSize = size;
	}

	public void setPaints(int onColor, int offColor) {
		mOnPaint.setColor(onColor);
		mOffPaint.setColor(offColor);
	}

	public void setBlack() {
		setPaints(0xE6000000, 0x66000000);
	}
}
