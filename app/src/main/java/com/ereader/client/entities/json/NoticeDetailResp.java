package com.ereader.client.entities.json;

import com.ereader.client.entities.Notice;

public class NoticeDetailResp extends BaseResp {
	private Notice data;

	/**
	 * @return the data
	 */
	public Notice getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Notice data) {
		this.data = data;
	}
}
