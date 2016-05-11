package com.ereader.client.entities.json;


import com.ereader.client.entities.AddBuy;
import com.ereader.client.entities.Favourite;

public class FavouriteResp extends BaseResp {
	private Favourite data;

	/**
	 * @return the data
	 */
	public Favourite getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Favourite data) {
		this.data = data;
	}

}
