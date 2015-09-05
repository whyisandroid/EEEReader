package com.ereader.client.entities.json;

import com.ereader.client.entities.Comment;
import com.ereader.client.entities.Gift;
import com.ereader.client.entities.Page;

import java.util.List;

public class GiftResp extends BaseResp {
	private List<Gift> data;
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
	public List<Gift> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<Gift> data) {
		this.data = data;
	}
}
