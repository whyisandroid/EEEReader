package com.ereader.client.entities.json;


import java.util.List;

public class ArticleResp extends BaseResp {
	private List<ArticleList> data;

	/**
	 * @return the data
	 */
	public List<ArticleList> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<ArticleList> data) {
		this.data = data;
	}

}
