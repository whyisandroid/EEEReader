package com.ereader.client.entities.json;


import com.ereader.client.entities.CardInfo;

import java.util.List;

public class CardInfoResp extends BaseResp {
	private CardInfo data;

	/**
	 * @return the data
	 */
	public CardInfo getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(CardInfo data) {
		this.data = data;
	}

}
