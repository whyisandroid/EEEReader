package com.ereader.client.entities.json;

import com.ereader.client.entities.Bill;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.Page;

import java.util.List;

/**
 * Created by wanghy on 2015/10/14.
 */
public class PointData {
    private List<Bill> data;
    private Page page;

    public List<Bill> getData() {
        return data;
    }

    public void setData(List<Bill> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
