package com.ereader.client.entities.json;

import com.ereader.client.entities.Order;
import com.ereader.client.entities.RechargeOrder;

public class OrderRechargeResp extends BaseResp {
	private RechargeOrder data;

	/**
	 * @return the data
	 */
	public RechargeOrder getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(RechargeOrder data) {
		this.data = data;
	}
}
