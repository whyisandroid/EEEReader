package com.ereader.client.entities.json;

import java.util.ArrayList;
import java.util.List;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.Page;

public class BookOnlyResp extends BaseResp {
	private List<Book> data = new ArrayList<Book>();


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
