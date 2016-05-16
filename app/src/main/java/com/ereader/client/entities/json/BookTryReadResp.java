package com.ereader.client.entities.json;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.TryRead;

public class BookTryReadResp extends BaseResp {
	private TryRead data;

	/**
	 * @return the data
	 */
	public TryRead getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(TryRead data) {
		this.data = data;
	}
}
