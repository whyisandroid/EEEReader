package com.ereader.client.entities.json;

import com.ereader.client.entities.Gift;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.Recommend;

import java.util.List;

/**
 * Created by wanghy on 2015/10/14.
 */
public class RecommendData {
    private List<Recommend> data;
    private Page page;

    public List<Recommend> getData() {
        return data;
    }

    public void setData(List<Recommend> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
