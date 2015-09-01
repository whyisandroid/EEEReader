package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.Category;
import com.ereader.client.entities.Page;

public class CategoryResp extends BaseResp {
	private List<Category> data;

	/**
	 * @return the data
	 */
	public List<Category> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<Category> data) {
		this.data = data;
	}
}
