package com.ereader.client.service;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * 该类用来缓存客户端数据，对外部提供访问这些数据的方法。
 * 
 * @author why
 * @time 2011-9-22
 */
public class AppContext {
	/** 缓存运行时业务数据 **/
	private Map<String, Object> businessData;

	public AppContext() {
		businessData = new Hashtable<String, Object>();
		// /** 初始化测试数据 **/
		// User user = new User();
	}

	public Map<String, Object> getBusinessData() {
		return businessData;
	}

	public Object getBusinessData(String key) {
		return businessData.get(key);
	}

	public void addBusinessData(String name, Object value) {
		businessData.put(name, value);
	}

	public void deleteBusinessData(String key) {
		businessData.remove(key);
	}

	public String getStringData(String str) {
		return (String)getBusinessData(str);
	}

}
