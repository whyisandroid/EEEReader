package com.ereader.client.entities.json;

import com.ereader.client.entities.Login;

public class LoginResp extends BaseResp {
	private Login data;
	private String _token_;
	/**
	 * @return the data
	 */
	public Login getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Login data) {
		this.data = data;
	}
	/**
	 * @return the _token_
	 */
	public String get_token_() {
		return _token_;
	}
	/**
	 * @param _token_ the _token_ to set
	 */
	public void set_token_(String _token_) {
		this._token_ = _token_;
	}

	
	
}
