package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.SpComment;

public class SPResp extends BaseResp {
	private List<SpComment> data;
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
	public List<SpComment> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<SpComment> data) {
		this.data = data;
	}
}
