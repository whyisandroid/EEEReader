package com.ereader.client.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/10/31 14:45
 ***************************************/
public class OrderBook {
   /* "order_product_id": 69,
            "order_id": 128,
            "product_id": 1,
            "user_id": 1,
            "product_discount_id": 0,
            "price": "10.00",
            "quantity": 1,
            "isComment": 0,
            "cStar": 0,
            "cTitle": "",
            "cContent": null,
            "name": "这是第一本书",
            "image": "",
            "meta_keywords": "这是第一本书1",
            "meta_description": "这是第一本书2",
            "description": "这是第一本书3",
            "extra":
*/

    private String order_product_id;
    private String order_id;
    private String product_id;
    private String user_id;
    private String product_discount_id;
    private String price;
    private String quantity;
    private String is_comment;
    private String cStar;
    private String cTitle;
    private String cContent;
    private String name;
    private String image;
    private String meta_keywords;
    private String meta_description;
    private String description;
    private BookExtra extra;
    private BookInfo info;

    public String getOrder_product_id() {
        return order_product_id;
    }

    public void setOrder_product_id(String order_product_id) {
        this.order_product_id = order_product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_discount_id() {
        return product_discount_id;
    }

    public void setProduct_discount_id(String product_discount_id) {
        this.product_discount_id = product_discount_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIsComment() {
        return is_comment;
    }

    public void setIsComment(String isComment) {
        this.is_comment = isComment;
    }

    public String getcStar() {
        return cStar;
    }

    public void setcStar(String cStar) {
        this.cStar = cStar;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMeta_keywords() {
        return meta_keywords;
    }

    public void setMeta_keywords(String meta_keywords) {
        this.meta_keywords = meta_keywords;
    }

    public String getMeta_description() {
        return meta_description;
    }

    public void setMeta_description(String meta_description) {
        this.meta_description = meta_description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookExtra getExtra() {
        return extra;
    }

    public void setExtra(BookExtra extra) {
        this.extra = extra;
    }

    public BookInfo getInfo() {
        return info;
    }

    public void setInfo(BookInfo info) {
        this.info = info;
    }
}
