package com.ereader.client.entities.json;


import com.ereader.client.entities.AddBuy;

public class AddBuyResp extends BaseResp {
	private AddBuy data;

	/**
	 * @return the data
	 */
	public AddBuy getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(AddBuy data) {
		this.data = data;
	}

}
