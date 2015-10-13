package com.ereader.client.entities.json;

import com.ereader.client.entities.Article;


public class WalletResp extends BaseResp {
	private WalletData data;

	/**
	 * @return the data
	 */
	public WalletData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(WalletData data) {
		this.data = data;
	}

}
