package com.ereader.client.entities;

/**
 * Created by wanghy on 2015/10/16.
 */
public class BookSearch {
    /*"product_id": 2,
            "name": "这是第二本书",
            "meta_keywords": "这是第二本书1",
            "meta_description": "这是第二本书2",
            "description": "这是第二本书3",
            "author": "神笔马良",
            "image_url": ""*/
    private String product_id;
    private String name;
    private String meta_keywords;
    private String meta_description;
    private String description;
    private String author;
    private String image_url;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
