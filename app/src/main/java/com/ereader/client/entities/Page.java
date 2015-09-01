package com.ereader.client.entities;

public class Page {
/*	"total": 1,
    "per_page": 15,
    "current_page": 1,
    "last_page": 1,
    "next_page_url": null,
    "prev_page_url": null,
    "from": 1,
    "to": 1*/
	
	private String total;
	private String per_page;
	private String current_page;
	private String last_page;
	private String next_page_url;
	private String prev_page_url;
	private String from;
	private String to;
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the per_page
	 */
	public String getPer_page() {
		return per_page;
	}
	/**
	 * @param per_page the per_page to set
	 */
	public void setPer_page(String per_page) {
		this.per_page = per_page;
	}
	/**
	 * @return the current_page
	 */
	public String getCurrent_page() {
		return current_page;
	}
	/**
	 * @param current_page the current_page to set
	 */
	public void setCurrent_page(String current_page) {
		this.current_page = current_page;
	}
	/**
	 * @return the last_page
	 */
	public String getLast_page() {
		return last_page;
	}
	/**
	 * @param last_page the last_page to set
	 */
	public void setLast_page(String last_page) {
		this.last_page = last_page;
	}
	/**
	 * @return the next_page_url
	 */
	public String getNext_page_url() {
		return next_page_url;
	}
	/**
	 * @param next_page_url the next_page_url to set
	 */
	public void setNext_page_url(String next_page_url) {
		this.next_page_url = next_page_url;
	}
	/**
	 * @return the prev_page_url
	 */
	public String getPrev_page_url() {
		return prev_page_url;
	}
	/**
	 * @param prev_page_url the prev_page_url to set
	 */
	public void setPrev_page_url(String prev_page_url) {
		this.prev_page_url = prev_page_url;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	
}
