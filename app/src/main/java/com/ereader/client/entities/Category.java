package com.ereader.client.entities;

public class Category {
	/*"category_id": 7,
    "parent_id": 2,
    "name": "小说"*/
	private String category_id;
	private String parent_id;
	private String name;
	
	public Category(String name,String id) {
		this.name = name;
		this.category_id = id;
	}
	
	
	/**
	 * @return the category_id
	 */
	public String getCategory_id() {
		return category_id;
	}
	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	/**
	 * @return the parent_id
	 */
	public String getParent_id() {
		return parent_id;
	}
	/**
	 * @param parent_id the parent_id to set
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
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
}
