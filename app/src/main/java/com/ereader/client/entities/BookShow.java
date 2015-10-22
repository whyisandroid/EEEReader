package com.ereader.client.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;
import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;

/**
 * Created by ghf on 15/10/10.
 */
@Table(name="shelfbooks")
public class BookShow implements Serializable {
    @Id(column = "book_id")
    private String book_id;

    @Column(column = "name")
    private String name;
    @Transient
    private String author;
    // Transient使这个列被忽略，不存入数据库
    @Transient
    private String version;

    @Column(column = "cover_front_url")
    private String cover_front_url;
    @Column(column = "isDownloading")
    private boolean isDownloading = false;//是否正在下载
    @Column(column = "isDownloaded")
    private boolean isDownloaded = false;
    @Column(column = "localpath")
    private String localpath;


    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setIsDownloaded(boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
    }

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

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setIsDownloading(boolean isDownloading) {
        this.isDownloading = isDownloading;
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
