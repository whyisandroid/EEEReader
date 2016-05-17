package com.ereader.client.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by ghf on 15/10/10.
 */
@Table(name="shelfbooks")
public class BookShow implements Serializable {
    @Id(column = "book_id")
    public String book_id;
    @Column(column = "product_id")
    public String product_id;
    @Column(column = "name")
    public String name;
    @Column(column = "author")
    public String author;
    // Transient使这个列被忽略，不存入数据库
    @Column(column = "version")
    public String version;
    @Column(column = "cover_front_url")
    public String cover_front_url;
    @Column(column = "isDownloading")
    public boolean isDownloading = false;//是否正在下载
    @Column(column = "isDownloaded")
    public boolean isDownloaded = false;
//    @Column(column = "localpath")
//    private String localpath;//在downloadInfo里找

//    //下载的信息   我靠－json 解析不了
//    private DownloadInfo downloadInfo;
//
//    public DownloadInfo getDownloadInfo() {
//        return downloadInfo;
//    }
//
//    public void setDownloadInfo(DownloadInfo downloadInfo) {
//        this.downloadInfo = downloadInfo;
//    }
//    public String getLocalpath() {
//        return localpath;
//    }
//
//    public void setLocalpath(String localpath) {
//        this.localpath = localpath;
//    }

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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "BookShow{" +
                "book_id='" + book_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", version='" + version + '\'' +
                ", cover_front_url='" + cover_front_url + '\'' +
                ", isDownloading=" + isDownloading +
                ", isDownloaded=" + isDownloaded +
                '}';
    }
}
