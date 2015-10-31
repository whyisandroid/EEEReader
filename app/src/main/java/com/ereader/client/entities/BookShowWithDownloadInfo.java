package com.ereader.client.entities;

import com.ereader.client.service.download.DownloadInfo;

/**
 * Created by ghf on 15/10/31.
 */
public class BookShowWithDownloadInfo extends BookShow{
    private DownloadInfo downloadInfo;

    public BookShowWithDownloadInfo(){
        super();
    }
    public BookShowWithDownloadInfo(BookShow book){
        setBook_id(book.getBook_id());
        setAuthor(book.getAuthor());
        setCover_front_url(book.getCover_front_url());
        setName(book.getName());
        setVersion(book.getVersion());
        setIsDownloading(book.isDownloading());
        setIsDownloaded(book.isDownloaded());
    }
    public BookShowWithDownloadInfo(BookShow book,DownloadInfo down){
        this(book);
        this.downloadInfo=down;
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }
}
