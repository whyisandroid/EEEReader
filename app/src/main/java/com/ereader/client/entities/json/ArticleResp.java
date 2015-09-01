package com.ereader.client.entities.json;


public class ArticleResp extends BaseResp {
	private ArticleData data;

	/**
	 * @return the data
	 */
	public ArticleData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArticleData data) {
		this.data = data;
	}

}
