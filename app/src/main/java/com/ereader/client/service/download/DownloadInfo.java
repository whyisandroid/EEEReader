package com.ereader.client.service.download;

import com.ereader.client.entities.BookShow;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.http.HttpHandler;

import java.io.File;
/**
 * Author: Xutils
 * Date: 13-11-10
 * Time: 下午8:11
 */
@Table(name="downloadbooks")
public class DownloadInfo extends BookShow{

    public DownloadInfo() {
    }

    public DownloadInfo(String bookid) {
        setBook_id(bookid+"");
        this.id=Integer.parseInt(bookid);
    }
    public int id;
//    @Transient
    public HttpHandler<File> handler;

    public HttpHandler.State state;

    public String downloadUrl;

    public String fileName;

    public String fileSavePath;

    public long progress;

    public long fileLength;

    public boolean autoResume;

    public boolean autoRename;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HttpHandler<File> getHandler() {
        return handler;
    }

    public void setHandler(HttpHandler<File> handler) {
        this.handler = handler;
    }

    public HttpHandler.State getState() {
        return state;
    }

    public void setState(HttpHandler.State state) {
        this.state = state;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public boolean isAutoResume() {
        return autoResume;
    }

    public void setAutoResume(boolean autoResume) {
        this.autoResume = autoResume;
    }

    public boolean isAutoRename() {
        return autoRename;
    }

    public void setAutoRename(boolean autoRename) {
        this.autoRename = autoRename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DownloadInfo)) return false;

        DownloadInfo that = (DownloadInfo) o;

        if (!book_id.equals(that.book_id) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
