package com.ereader.client.entities.json;

import java.util.List;

import com.ereader.client.entities.Category;
import com.ereader.client.entities.DisCategory;

public class DisCategoryResp extends BaseResp {
	private List<DisCategory> data;

	/**
	 * @return the data
	 */
	public List<DisCategory> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<DisCategory> data) {
		this.data = data;
	}
}
