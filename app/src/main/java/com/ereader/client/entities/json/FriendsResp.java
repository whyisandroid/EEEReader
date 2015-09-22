package com.ereader.client.entities.json;


import com.ereader.client.entities.Friend;

import java.util.List;

public class FriendsResp extends BaseResp {
	private List<Friend> data;

	/**
	 * @return the data
	 */
	public List<Friend> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<Friend> data) {
		this.data = data;
	}

}
