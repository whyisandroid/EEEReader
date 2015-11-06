package com.ereader.client.entities.json;

import com.ereader.client.entities.MessageFriends;
import com.ereader.client.entities.MessageSystem;
import com.ereader.client.entities.Page;

import java.util.List;

public class MessageSystemData {
	private List<MessageSystem> data;
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
	public List<MessageSystem> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<MessageSystem> data) {
		this.data = data;
	}
}
