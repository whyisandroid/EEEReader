package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Comment;
import com.ereader.client.entities.Page;

public class CommentResp extends BaseResp {
	private List<Comment> data;
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
	public List<Comment> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<Comment> data) {
		this.data = data;
	}
}
