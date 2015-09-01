package com.ereader.client.entities;

import java.io.Serializable;

public class BookInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * "product_id": 2, "name": "这是第二本书", "image": "", "meta_keywords":
	 * "这是第二本书1", "meta_description": "这是第二本书2", "description": "这是第二本书3"
	 */
	private String product_id;
	private String name;
	private String image;
	private String meta_keywords;
	private String meta_description;
	private String description;
	private String image_url;
	
	
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	public String getImage_url() {
		return image_url;
	}
	
	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the meta_keywords
	 */
	public String getMeta_keywords() {
		return meta_keywords;
	}
	/**
	 * @param meta_keywords the meta_keywords to set
	 */
	public void setMeta_keywords(String meta_keywords) {
		this.meta_keywords = meta_keywords;
	}
	/**
	 * @return the meta_description
	 */
	public String getMeta_description() {
		return meta_description;
	}
	/**
	 * @param meta_description the meta_description to set
	 */
	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
