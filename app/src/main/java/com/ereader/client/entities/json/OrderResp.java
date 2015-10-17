package com.ereader.client.entities.json;

import com.ereader.client.entities.Order;

public class OrderResp extends BaseResp {
	private Order data;

	/**
	 * @return the data
	 */
	public Order getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Order data) {
		this.data = data;
	}
}
