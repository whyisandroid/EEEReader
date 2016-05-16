package com.ereader.client.ui.share;

import java.io.Serializable;

/**
 * Created by owen on 2015/7/17.
 */
public class ShareParams implements Serializable{

    private String title;
    private String content;
    private String imageUrl;
    private String shareUrl;

    private boolean isLocalImage=false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public boolean isLocalImage() {
        return isLocalImage;
    }

    public void setIsLocalImage(boolean isLocalImage) {
        this.isLocalImage = isLocalImage;
    }

    @Override
    public String toString() {
        return "ShareParams{" +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }
}
