package com.ereader.client.entities;

public class Article {
/*	"article_id": 1,
    "title": "公告001",
    "parent_id": 10,
    "status": 1,
    "created_at": "-0001-11-30 00:00:00",
    "updated_at": "2015-08-24 18:00:45"*/
    private String article_id; 
	private String title; 
	private String parent_id; 
	private String status; 
	private String created_at; 
	private String updated_at;
	private String content;// 内容
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the article_id
	 */
	public String getArticle_id() {
		return article_id;
	}
	/**
	 * @param article_id the article_id to set
	 */
	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	} 
}
