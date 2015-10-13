package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.SpComment;

public class SPResp extends BaseResp {
	private SPdata data;


	/**
	 * @return the data
	 */
	public SPdata getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(SPdata data) {
		this.data = data;
	}
}
