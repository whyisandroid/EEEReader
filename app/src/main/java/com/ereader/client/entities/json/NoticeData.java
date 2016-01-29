package com.ereader.client.entities.json;

import com.ereader.client.entities.Notice;
import com.ereader.client.entities.Page;

import java.util.List;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2016/1/29 15:47
 ***************************************/
public class NoticeData {
    private List<Notice> data;
    private Page page;

    public List<Notice> getData() {
        return data;
    }

    public void setData(List<Notice> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
