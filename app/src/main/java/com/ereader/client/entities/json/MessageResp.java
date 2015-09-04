package com.ereader.client.entities.json;

import com.ereader.client.entities.Article;


public class MessageResp extends BaseResp {
	private MessageData data;

	/**
	 * @return the data
	 */
	public MessageData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(MessageData data) {
		this.data = data;
	}

}
