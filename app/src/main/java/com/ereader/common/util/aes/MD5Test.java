package com.ereader.common.util.aes;

import android.annotation.SuppressLint;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ereader.common.util.Config;


/******************************************
	 * 类描述： TODO
	 * 类名称：Snippet  
 	 * @version: 1.0
	 * @author: why
	 * @time: 2014-10-21 下午2:54:19 
******************************************/
public class MD5Test {
	/**
	 * 签名生成算法
	 * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	@SuppressLint("NewApi")
	public static String getSignature(HashMap<String,String> params, BasicNameValuePair nameValuePair,String secret) throws IOException
	{
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		
		Set<Entry<String, String>> entrys = sortedParams.entrySet();
	 
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		basestring.append(nameValuePair.getName()).append("=").append(URLEncoder.encode(nameValuePair.getValue(),"UTF-8")).append("&");
		for (Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue() == null ?"":param.getValue(),"UTF-8")).append("&");
		}
		basestring.deleteCharAt(basestring.length()-1);
		basestring.append(secret);
		// 使用MD5对待签名串求签
 		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}
	 
		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}
	
	
	@SuppressLint("NewApi")
	public static String getSignature(List<NameValuePair> nameValuePairs, String secret) throws IOException
	{
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (NameValuePair param : nameValuePairs) {
			basestring.append(param.getName()).append("=").append(URLEncoder.encode(param.getValue(),"UTF-8")).append("&");
		}
		if(basestring.length() >0){
			basestring.deleteCharAt(basestring.length()-1);
		}
		basestring.append(secret);
		 
		String value = basestring.toString();
	//	{"signture":"a5878828ae6951bc4b676e5db8a2f325","signstr":"_URI_=%2Fapi%2Findex%2Flogin&loginname=18201017981&password=111111&signature=59867575593ede02f4939888aa65b187test"}}
	
		// 使用MD5对待签名串求签
 		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(value.getBytes("UTF-8"));
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}
	 
		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}
	
	
	
	/**
	  * 方法描述：TODO
	  * @return
	  * @author: why
	 * @throws IOException 
	  * @time: 2014-10-21 下午8:05:52
	  */
	public static String md5Sign(List<NameValuePair> nameValuePairs,BasicNameValuePair basicNameValuePair) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < nameValuePairs.size(); i++) {
			NameValuePair value = nameValuePairs.get(i);
			map.put(value.getName(),value.getValue());
		}
		return MD5Test.getSignature(map,basicNameValuePair, "test");
	}
}

