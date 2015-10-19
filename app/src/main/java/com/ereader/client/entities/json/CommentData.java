package com.ereader.client.entities.json;

import com.ereader.client.entities.Comment;
import com.ereader.client.entities.Page;

import java.util.List;

/**
 * Created by wanghy on 2015/10/14.
 */
public class CommentData {
    private List<Comment> data;
    private Page page;

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
