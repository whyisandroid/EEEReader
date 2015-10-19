package com.ereader.client.entities.json;

import com.ereader.client.entities.Page;
import com.ereader.client.entities.SpComment;

import java.util.List;

/**
 * Created by wanghy on 2015/10/14.
 */
public class SPdata {
    private List<SpComment> data;
    private Page page;

    public List<SpComment> getData() {
        return data;
    }

    public void setData(List<SpComment> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
