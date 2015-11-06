package com.ereader.client.entities.json;


import com.ereader.client.entities.Message;

public class MessageSystemResp extends BaseResp {
	private MessageSystemData data;

	/**
	 * @return the data
	 */
	public MessageSystemData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(MessageSystemData data) {
		this.data = data;
	}

}
