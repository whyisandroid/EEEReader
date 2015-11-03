package com.ereader.client.entities.json;

import com.ereader.client.entities.Login;
import com.ereader.client.entities.Recommend;

public class RecommendResp extends BaseResp {
	private RecommendData data;
	/**
	 * @return the data
	 */
	public RecommendData getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(RecommendData data) {
		this.data = data;
	}
}
