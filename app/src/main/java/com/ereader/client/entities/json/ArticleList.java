package com.ereader.client.entities.json;

import com.ereader.client.entities.Article;

import java.io.Serializable;
import java.util.List;

public class ArticleList implements Serializable{
	private String category;
	private List<Article> articles;

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	/**
	 * @return the data
	 */
	public List<Article> getData() {
		return articles;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<Article> data) {
		this.articles = data;
	}
}
