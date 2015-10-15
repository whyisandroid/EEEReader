package com.ereader.client.entities.json;

import com.ereader.client.entities.BookSearch;

public class BookSearchResp extends BaseResp {
	private BookSearchData data;

	/**
	 * @return the data
	 */
	public BookSearchData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(BookSearchData data) {
		this.data = data;
	}
}
