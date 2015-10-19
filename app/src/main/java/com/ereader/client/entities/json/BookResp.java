package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.Page;

public class BookResp extends BaseResp {
	private BookData data;

	/**
	 * @return the data
	 */
	public BookData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(BookData data) {
		this.data = data;
	}
}
