package com.ereader.client.entities.json;

import com.ereader.client.entities.Comment;
import com.ereader.client.entities.Gift;
import com.ereader.client.entities.Page;

import java.util.List;

public class GiftResp extends BaseResp {
	private GiftData data;

	/**
	 * @return the data
	 */
	public GiftData getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(GiftData data) {
		this.data = data;
	}
}
