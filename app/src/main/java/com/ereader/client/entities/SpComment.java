package com.ereader.client.entities;

import java.io.Serializable;

public class SpComment implements Serializable {
	 /* "comment_id": 1,
      "user_id": 1,
      "product_id": 2,
      "title": "",
      "content": "",
      "score": 3,
      "like": 0,
      "created_at": "2015-07-08 20:43:38",
      "updated_at": "2015-07-08 20:43:38",
      "nickname": "",
      "realname": ""*/
	private String comment_id;
	private String user_id;
	private String product_id;
	private String product_name;
	private String title;
	private String score;
	private String content;
	private String like;
	private String created_at;
	private String updated_at;
	private String nickname;
	private String realname;

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return the comment_id
	 */
	public String getComment_id() {
		return comment_id;
	}
	/**
	 * @param comment_id the comment_id to set
	 */
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	 * @return the score
	 */
	public String getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}
	/**
	 * @return the like
	 */
	public String getLike() {
		return like;
	}
	/**
	 * @param like the like to set
	 */
	public void setLike(String like) {
		this.like = like;
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
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}
	/**
	 * @param realname the realname to set
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}
}
