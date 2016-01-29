package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Article;
import com.ereader.client.entities.Page;

public class ArticleData{
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
