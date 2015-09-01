package com.ereader.common.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.ereader.common.util.LogUtil;

/**
 * ****************************************
	 * 类描述： 网络帮助类
	 * 类名称：NetWorkHelper  
 	 * @version: 1.0
	 * @author: ly
	 * @time: 2013-12-3 上午11:54:52 
*****************************************
 */
public class NetWorkHelper {
	private static String TAG = "NetWorkHelper";

	/**
	 * 判断是否有网络连接
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity == null) {
			LogUtil.Log(TAG, "couldn't get connectivity manager");
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						LogUtil.Log(TAG, "network is available");
						return true;
					}
				}
			}
		}
		LogUtil.Log(TAG, "network is not available");
		return false;
	}
	
	/**
	 * 判断wifi 是否可用
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isWifiDataEnable(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isWifiDataEnable = false;
		isWifiDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		return isWifiDataEnable;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isMobileDataEnable(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isMobileDataEnable = false;

		isMobileDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		return isMobileDataEnable;
	}


	/**
	 * 判断网络是否为漫游
	 */
	public static boolean isNetworkRoaming(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			LogUtil.Log(TAG, "couldn't get connectivity manager");
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null
					&& info.getType() == ConnectivityManager.TYPE_MOBILE) {
				TelephonyManager tm = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				if (tm != null && tm.isNetworkRoaming()) {
					LogUtil.Log(TAG, "network is roaming");
					return true;
				} else {
					LogUtil.Log(TAG,  "network is not roaming");
				}
			} else {
				LogUtil.Log(TAG, "not using mobile network");
			}
		}
		return false;
	}
	
	/**
	 * 方法描述：TODO
	 * @author: why
	 * @throws SocketException 
	 * @time: 2015-6-8 下午8:25:11
	 */
	public static String getIP() throws SocketException {
		String ipaddress = "";
		for (Enumeration<NetworkInterface> en = NetworkInterface
				.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			if (intf.getName().toLowerCase().equals("eth0")
					|| intf.getName().toLowerCase().equals("wlan0")) {
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ipaddress = inetAddress.getHostAddress().toString();
						if (!ipaddress.contains("::")) {// ipV6的地址
							return ipaddress;
						}
					}
				}
			} else {
				continue;
			}
		}
		return ipaddress;

	}


}
