package com.ereader.client.entities.json;


/******************************************
 * 类描述： 网络返回对象基类
 * 类名称：BaseResp  
 * @version: 1.0
 * @author: why
 * @time: 2013-12-9 下午2:39:25 
 ******************************************/
public class BaseResp {
	
	private String status;
	private String message;
	
	public static final String SUCCESS = "1"; //返回结果 成功标志

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseResp [status=" + status + ", message=" + message + "]";
	}
	
}
