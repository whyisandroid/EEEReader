package com.ereader.client.entities.json;

public class PointResp extends BaseResp {
	private PointData data;

	/**
	 * @return the data
	 */
	public PointData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(PointData data) {
		this.data = data;
	}
}
