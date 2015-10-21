package com.ereader.client.entities;

import java.io.Serializable;

/**
 * Created by ghf on 15/10/10.
 */
public class BookShow implements Serializable {

    private  String book_id;
    private String name;
    private String author;
    private String version;
    private String cover_front_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getCover_front_url() {
        return cover_front_url;
    }

    public void setCover_front_url(String cover_front_url) {
        this.cover_front_url = cover_front_url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BookShow{" +
                "author='" + author + '\'' +
                ", book_id='" + book_id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", cover_front_url='" + cover_front_url + '\'' +
                '}';
    }
}
