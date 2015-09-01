package com.ereader.client.entities.json;

import com.ereader.client.entities.Article;


public class ArticleDetailResp extends BaseResp {
	private Article data;

	/**
	 * @return the data
	 */
	public Article getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Article data) {
		this.data = data;
	}

}
