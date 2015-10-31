package com.ereader.client.entities.json;

public class OrderListResp extends BaseResp {
	private OrderData data;

	/**
	 * @return the data
	 */
	public OrderData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(OrderData data) {
		this.data = data;
	}
}
