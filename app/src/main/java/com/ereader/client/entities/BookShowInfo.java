package com.ereader.client.entities;

import java.util.List;

/**
 * Created by ghf on 15/10/13.
 */
public class BookShowInfo {

    private List<BookShow> data;
    private Page page;

    public List<BookShow> getData() {
        return data;
    }

    public void setData(List<BookShow> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
