package com.ereader.client.entities.json;

public class NoticeResp extends BaseResp {
	private NoticeData data;

	/**
	 * @return the data
	 */
	public NoticeData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(NoticeData data) {
		this.data = data;
	}
}
