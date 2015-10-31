package com.ereader.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;


import java.io.File;

/******************************************
 * 类描述： UI 工具类 
 * 类名称：UIUtils
 * @version: 1.0
 * @author: why
 * @time: 2014-5-7 下午4:50:55
 ******************************************/
@SuppressLint("NewApi") public class UIUtils {


	/**
	  * 方法描述：设置listView 的高度 等于所有元素相加
	  * @param lv
	  * @param la
	  * @author: why
	  * @time: 2014-5-7 下午4:51:40
	 */
	public static void setListViewHeight(ListView lv, BaseAdapter la) {
		// calculate height of all items.
		int h = 0;
		final int cnt = la.getCount();
		for (int i = 0; i < cnt; i++) {
			View item = la.getView(i, null, lv);
			item.measure(0, 0);
			h += item.getMeasuredHeight();
			LogUtil.Log("h",h+"");
		}
		// reset ListView height
		ViewGroup.LayoutParams lp = lv.getLayoutParams();
		lp.height = h + (lv.getDividerHeight() * (cnt - 1));
		lv.setLayoutParams(lp);
	}
	
	/**
	 * 
	  * 方法描述：获取屏幕宽度
	  * @param act
	  * @return
	  * @author: qm
	  * @time: 2014-12-2 下午3:54:24
	 */

	public static int getScreenWidth(Context mContext) {
		int width=0;
		if (Build.VERSION.SDK_INT < 13) {
			DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
			width=dm.widthPixels;
//			Display display = (((Activity) mContext).getWindowManager()).getDefaultDisplay();//activity才有
//			width = display.getWidth();  // deprecated
		} else {
			WindowManager wm = (WindowManager) mContext
					.getSystemService(Context.WINDOW_SERVICE);
			Point point = new Point();
			wm.getDefaultDisplay().getSize(point);
			width=point.x;
		}
		return width;
	}
	
	/**
	 * 
	  * 方法描述：获取屏幕高度
	  * @param act
	  * @return
	  * @author: qm
	  * @time: 2014-12-2 下午3:54:24
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context mContext) {
		int height=0;
		if (Build.VERSION.SDK_INT < 13) {
			DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
			height=dm.widthPixels;
//			Display display = (((Activity) mContext).getWindowManager()).getDefaultDisplay();
//			height = display.getHeight();  // deprecated
		} else {
			WindowManager wm = (WindowManager) mContext
					.getSystemService(Context.WINDOW_SERVICE);
			Point point = new Point();
			wm.getDefaultDisplay().getSize(point);
			height=point.y;
		}
		return height;
	}

	/**
	 * 获取sd 卡的图片
	 * pathString  图片路径
	 */
	public static Bitmap getDiskBitmap(String pathString) {
		Bitmap bitmap = null;
		try {
			File file = new File(pathString);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(pathString);
			}
		} catch (Exception e) {
		}
		return bitmap;
	}

	/**
	 * dip to pixel
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * pixel to dip
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	/**
	 *
	 * 方法描述：sd卡是否存在
	 *
	 * @return
	 * @author: GHZ
	 * @time: 2014-6-23 下午7:12:34
	 */
	public static boolean existSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}
}
