package com.ereader.client.entities;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*"product_id": 2,
    "sku": "本",
    "quantity": 100,
    "upc": "BOOK002",
    "ean": "BOOK002",
    "isbn": "002",
    "mpn": "002",
    "manufacturer_id": 1,
    "price": "5.00",
    "cost": "5.00",
    "available_at": "0000-00-00 00:00:00",
    "onsale_status": "1",
    "stock_status": "1",
    "created_at": "-0001-11-30 00:00:00",
    "updated_at": "2015-06-23 20:07:17",
    "extra": [],
    "info": {*/
	private String product_id;
	private String sku;
	private String quantity;
	private String upc;
	private String ean;
	private String isbn;
	private String mpn;
	private String manufacturer_id;
	private String price;
	private String cost;
	private String available_at;
	private String onsale_status;
	private String stock_status;
	private String created_at;
	private String updated_at;
	private String comment_star;
	private BookExtra extra;
	private BookInfo info;
	private String image_url;
	
	private boolean select; // 购物车用

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getComment_star() {
		return comment_star;
	}

	public void setComment_star(String comment_star) {
		this.comment_star = comment_star;
	}

	public boolean isSelect() {
		return select;
	}
	
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
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
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}
	/**
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the upc
	 */
	public String getUpc() {
		return upc;
	}
	/**
	 * @param upc the upc to set
	 */
	public void setUpc(String upc) {
		this.upc = upc;
	}
	/**
	 * @return the ean
	 */
	public String getEan() {
		return ean;
	}
	/**
	 * @param ean the ean to set
	 */
	public void setEan(String ean) {
		this.ean = ean;
	}
	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	/**
	 * @return the mpn
	 */
	public String getMpn() {
		return mpn;
	}
	/**
	 * @param mpn the mpn to set
	 */
	public void setMpn(String mpn) {
		this.mpn = mpn;
	}
	/**
	 * @return the manufacturer_id
	 */
	public String getManufacturer_id() {
		return manufacturer_id;
	}
	/**
	 * @param manufacturer_id the manufacturer_id to set
	 */
	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}
	/**
	 * @return the available_at
	 */
	public String getAvailable_at() {
		return available_at;
	}
	/**
	 * @param available_at the available_at to set
	 */
	public void setAvailable_at(String available_at) {
		this.available_at = available_at;
	}
	/**
	 * @return the onsale_status
	 */
	public String getOnsale_status() {
		return onsale_status;
	}
	/**
	 * @param onsale_status the onsale_status to set
	 */
	public void setOnsale_status(String onsale_status) {
		this.onsale_status = onsale_status;
	}
	/**
	 * @return the stock_status
	 */
	public String getStock_status() {
		return stock_status;
	}
	/**
	 * @param stock_status the stock_status to set
	 */
	public void setStock_status(String stock_status) {
		this.stock_status = stock_status;
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
	 * @return the extra
	 */
	public BookExtra getExtra() {
		return extra;
	}

	/**
	 * @param extra the extra to set
	 */
	public void setExtra(BookExtra extra) {
		this.extra = extra;
	}

	/**
	 * @return the info
	 */
	public BookInfo getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(BookInfo info) {
		this.info = info;
	}
}
