package com.ereader.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ereader.client.entities.Login;
import com.ereader.client.entities.SubCategory;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.entities.json.SubCategoryResp;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.util.Json_U;
import com.ereader.common.util.LogUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * ****************************************
	 * 类描述： 持久化数据层
	 * 类名称：AppSharedPref  
 	 * @version: 1.0
	 * @author: why
	 * @time: 2014-2-18 下午6:04:05 
*****************************************
 */
public class AppSharedPref {
	
	/** SharePreferences名字 */
	private  String SHARE_PREFERENCES_NAME = "CreditPerson";
	
	/** The shared preferences. */
	private static SharedPreferences sharedPreferences = null;
	private static AppSharedPref asp = null;
	
	/**
	 * 构造函数.
	 */
	private AppSharedPref(Context context) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(
					SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
		}
	}

	/**
	 * 单态实例
	 */
	public static AppSharedPref getInstance(Context context) {
		if (asp == null) {
			asp = new AppSharedPref(context);
		}
		return asp;
	}


	/**
	  * 方法描述：TODO
	  * @return
	  * @author: why
	  * @time: 2014-7-1 下午3:18:44
	  */
	public boolean getTokenFlag() {
		return sharedPreferences.getBoolean("tokenFlag", false);
	}

	
	/**
	  * 方法描述：TODO
	  * @param token
	  * @author: why
	  * @time: 2014-7-1 下午4:14:21
	  */
	public void saveToken(String token) {
		Editor e = sharedPreferences.edit();
		e.putString("token",token);
		e.commit();
	}

	
	/**
	  * 方法描述：TODO
	  * @return
	  * @author: why
	  * @time: 2014-7-1 下午4:14:29
	  */
	public String getToken() {
		return sharedPreferences.getString("token", "");
	}
	
	/**
	  * 方法描述：TODO
	  * @param resp
	  * @author: ghf
	  * @time: 2015-6-8 下午7:27:14
	  */

	public void saveLocalInfoByKeyValue(String key, String value) {
		Editor e = sharedPreferences.edit();
		e.putString(key,value);
		e.commit();
	}
	/**
	  * 方法描述：TODO
	  * @param resp
	  * @author: ghf
	  * @time: 2015-6-8 下午7:27:14
	  */
	public String getLocalInfoByKeyValue(String key) {
		return sharedPreferences.getString(key, "");
	}


	/**
	  * 方法描述：TODO
	  * @param mSet
	  * @author: why
	  * @time: 2015-7-14 下午2:49:42
	  */
	public void saveCategroy(SubCategoryResp mSet) {

		Editor e = sharedPreferences.edit();
		try {
			e.putString("Categroy_Sub", Json_U.objToJsonStr(mSet));
		} catch (BusinessException e1) {
			LogUtil.LogError("存账户列表本地失败", e1.toString());
			e1.printStackTrace();
		}
		e.commit();
	}

	
	/**
	  * 方法描述：TODO
	  * @return
	  * @author: why
	  * @time: 2015-7-14 下午2:50:06
	  */
	public SubCategoryResp getCategroy() {

		String  jsonStr = sharedPreferences.getString("Categroy_Sub", "");
		try {
			if(TextUtils.isEmpty(jsonStr)){
				return new  SubCategoryResp();
			}else{
				return Json_U.parseJsonToObj((jsonStr),SubCategoryResp.class);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			return new  SubCategoryResp();
		}
	}

	public void saveLogin(Login data) {


		Editor e = sharedPreferences.edit();
		try {
			e.putString("LoginData", Json_U.objToJsonStr(data));
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
		e.commit();
	}
	

	public Login getLogin() {
		String  jsonStr = sharedPreferences.getString("LoginData", "");
		try {
			if(TextUtils.isEmpty(jsonStr)){
				return null;
			}else{
				return Json_U.parseJsonToObj((jsonStr),Login.class);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveBuyCar(BookOnlyResp resp) {


		Editor e = sharedPreferences.edit();
		try {
			e.putString("BuyCarData", Json_U.objToJsonStr(resp));
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
		e.commit();
	}

	public BookOnlyResp getBuyCar() {
		String  jsonStr = sharedPreferences.getString("BuyCarData", "");
		try {
			if(TextUtils.isEmpty(jsonStr)){
				return null;
			}else{
				return Json_U.parseJsonToObj((jsonStr),BookOnlyResp.class);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
