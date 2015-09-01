package com.ereader.common.net;


/******************************************
 * 类描述：  cookie 值 
 * 类名称：UserCookies  
 * @version: 1.0
 * @author: why
 * @time: 2015-6-2 下午3:30:26 
 ******************************************/
public class UserCookies {

	private String clientType    ;//客户端系统(IOS/Android)                                                                        
	private String screenSize;//分辨率                                                                      
	private String clientVersion;//客户端版本号(2.0,2.1等)                                    
	private String operateId;//客户端口生成的唯一流水号（可用UUID）  
	private String deviceId;//设备号                                                                      
	private String  location;//地理位置                                                                  
	private String  networkType;//网络（wifi/4G/3G/2G）                                       
	private String token;//用户token                                                                  
	private String ip;//ip地址                                                                         
	private String clientTime;//客户端时间            
	/**
	 * @return clientType : return the property clientType.
	 */
	public String getClientType() {
		return clientType;
	}
	/**
	 * @param clientType : set the property clientType.
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	/**
	 * @return screenSize : return the property screenSize.
	 */
	public String getScreenSize() {
		return screenSize;
	}
	/**
	 * @param screenSize : set the property screenSize.
	 */
	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	/**
	 * @return clientVersion : return the property clientVersion.
	 */
	public String getClientVersion() {
		return clientVersion;
	}
	/**
	 * @param clientVersion : set the property clientVersion.
	 */
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	/**
	 * @return operateId : return the property operateId.
	 */
	public String getOperateId() {
		return operateId;
	}
	/**
	 * @param operateId : set the property operateId.
	 */
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	/**
	 * @return deviceId : return the property deviceId.
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId : set the property deviceId.
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @return location : return the property location.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location : set the property location.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return networkType : return the property networkType.
	 */
	public String getNetworkType() {
		return networkType;
	}
	/**
	 * @param networkType : set the property networkType.
	 */
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	/**
	 * @return token : return the property token.
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token : set the property token.
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return ip : return the property ip.
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip : set the property ip.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return clientTime : return the property clientTime.
	 */
	public String getClientTime() {
		return clientTime;
	}
	/**
	 * @param clientTime : set the property clientTime.
	 */
	public void setClientTime(String clientTime) {
		this.clientTime = clientTime;
	}

}
