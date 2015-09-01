package com.ereader.client.entities.json;

import java.util.ArrayList;
import java.util.List;

import com.ereader.client.entities.Category;
import com.ereader.client.entities.SubCategory;

public class SubCategoryResp extends BaseResp {
	private List<SubCategory> data = new ArrayList<SubCategory>();

	/**
	 * @return the data
	 */
	public List<SubCategory> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<SubCategory> data) {
		this.data = data;
	}
}
