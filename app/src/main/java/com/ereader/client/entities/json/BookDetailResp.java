package com.ereader.client.entities.json;

import com.ereader.client.entities.Book;

public class BookDetailResp extends BaseResp {
	private Book data;

	/**
	 * @return the data
	 */
	public Book getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Book data) {
		this.data = data;
	}
}
