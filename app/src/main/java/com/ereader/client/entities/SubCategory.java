package com.ereader.client.entities;

import java.util.ArrayList;
import java.util.List;

public class SubCategory {
	/*"category_id": 7,
    "parent_id": 2,
    "name": "小说"*/
	private String category_id;
	private String parent_id;
	private String name;
	private List<Category>sub = new ArrayList<Category>();
	
	public void setSub(List<Category> sub) {
		this.sub = sub;
	}
	
	public List<Category> getSub() {
		return sub;
	}
	
	public SubCategory(String name,String id) {
		this.name = name;
		this.category_id = id;
	}
	
	
	public SubCategory() {
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
