package com.ereader.client.entities.json;

import com.ereader.client.entities.Article;
import com.ereader.client.entities.Message;
import com.ereader.client.entities.MessageFriends;
import com.ereader.client.entities.Page;

import java.util.List;

public class MessageData {
	private List<MessageFriends> data;
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
	public List<MessageFriends> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<MessageFriends> data) {
		this.data = data;
	}
}
