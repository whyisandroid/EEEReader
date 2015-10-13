package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Comment;
import com.ereader.client.entities.Page;

public class CommentResp extends BaseResp {
	private CommentData data;

	/**
	 * @return the data
	 */
	public CommentData getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(CommentData data) {
		this.data = data;
	}
}
