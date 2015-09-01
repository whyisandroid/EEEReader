package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Article;
import com.ereader.client.entities.Page;

public class ArticleData{
	private List<Article> data;
	private Page page;
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	public Page getPage() {
		return page;
	}
	/**
	 * @return the data
	 */
	public List<Article> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<Article> data) {
		this.data = data;
	}
}
