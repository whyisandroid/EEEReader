package com.ereader.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ereader.client.R;
import com.ereader.client.service.AppManager;
import com.ereader.client.ui.MainFragmentActivity;

/******************************************
 * 类描述： 跳转处理
 *  类名称：IntentUtil
 * 
 * @version: 1.0
 * @time: 2014-2-19 下午5:35:38
 ******************************************/
public class IntentUtil {
	
	
	public static final String app_home_page="android.intent.action.APP.HOMEPAGE";
	
	/**
	  * 方法描述： 不finish当前页 跳转到指定页面  
	  * @param loginActivity
	  * @param class1
	  * @author: why
	  * @time: 2014-4-3 上午11:49:44
	  */
	public static void AppHomePage(Activity activity) {
		/*activity.startActivity(new Intent(activity));
		pushFromRight(activity);*/
		intent(activity, MainFragmentActivity.class);
	}
	public static void AppHomePageByIndex(Context activity,int indexTab) {
		/*activity.startActivity(new Intent(activity));
		pushFromRight(activity);*/
		MainFragmentActivity.setCurrentTab(indexTab);
		intent(activity, MainFragmentActivity.class);
	}
	/**
	  * 方法描述： 不finish当前页 跳转到指定页面  
	  * @param loginActivity
	  * @param class1
	  * @author: why
	  * @time: 2014-4-3 上午11:49:44
	  */
	@SuppressWarnings("rawtypes")
	public static void intent(Context context,
			Class class1) {
		intent(context,class1,false);
	}
	
	/**
	 * 
	 * 方法描述： finish当前页 跳转到指定页面  
	 * 
	 * @author: Administrator
	 * @time: 2014-2-19 下午5:37:37
	 */
	@SuppressWarnings("rawtypes")
	public static void intent(Context context,Class class1,boolean flag) {
		intent(context,null,class1,flag);
	}
	
	/**
	 * 
	  * 方法描述：startActivityForResult
	  * @param activity
	  * @param class1
	  * @param requestCode
	  * @author: Administrator
	  * @time: 2014-11-26 下午1:32:49
	 */
	public static void intentForResult(Activity activity,
			@SuppressWarnings("rawtypes") Class class1,int requestCode){
		/*Intent intent = new Intent(activity, class1);
		activity.startActivityForResult(intent, requestCode);
		pushFromRight(activity);*/
		intentForResult(activity, class1,null, requestCode);
	}
	/**
	 * 
	  * 方法描述：带参数startActivityForResult
	  * @param activity
	  * @param class1
	  * @param bundle
	  * @param requestCode
	  * @author: Administrator
	  * @time: 2014-11-26 下午1:32:49
	 */
	public static void intentForResult(Activity activity,
			@SuppressWarnings("rawtypes") Class class1, Bundle bundle,int requestCode){
		Intent intent = new Intent(activity, class1);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		activity.startActivityForResult(intent, requestCode);
		pushFromRight(activity);
	}
	
	
	
	
	/**
	 * 
	 * 方法描述：跳转到指定页面
	 * 
	 * @author: Administrator
	 * @param isFinish是否finish掉activity
	 * @time: 2014-2-19 下午5:37:37
	 */
	public static void intent(Context context, Bundle bundle,
			@SuppressWarnings("rawtypes") Class class1, boolean isFinish) {
		try {
			Intent intent = new Intent(context, class1);
			if (bundle != null) {
				intent.putExtras(bundle);
			}
			context.startActivity(intent);
			if (isFinish) {
				AppManager.getAppManager().finishActivity((Activity) context);
			}
			pushFromRight((Activity) context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 

	/**
	 * 
	 * 界面前进动画效果
	 * 
	 * @param activity
	 */
	public static void pushFromRight(Activity activity) {
		activity.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);
	}

	/**
	 * 
	 * 界面返回动画效果
	 * 
	 * @param activity
	 */
	public static void popFromLeft(Activity activity) {
		activity.overridePendingTransition(R.anim.push_right_out,
				R.anim.push_right_in);
	}
	
	
}
