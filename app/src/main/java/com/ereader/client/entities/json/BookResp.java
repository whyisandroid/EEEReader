package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.Page;

public class BookResp extends BaseResp {
	private List<Book> data;
	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * @return the data
	 */
	public List<Book> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<Book> data) {
		this.data = data;
	}
}
