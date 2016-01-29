package com.ereader.client.entities.json;

import com.ereader.client.entities.Article;
import com.ereader.client.entities.ArticleDetail;


public class ArticleDetailResp extends BaseResp {
	private ArticleDetail data;

	/**
	 * @return the data
	 */
	public ArticleDetail getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArticleDetail data) {
		this.data = data;
	}

}
