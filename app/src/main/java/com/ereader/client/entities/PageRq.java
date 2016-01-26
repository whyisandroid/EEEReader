package com.ereader.client.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2016/1/26 14:51
 ***************************************/
public class PageRq {
    private int page;
    private int per_page;

    public PageRq() {
        page = 1;
        per_page = 10;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }
}
