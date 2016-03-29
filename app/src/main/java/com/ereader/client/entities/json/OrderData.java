package com.ereader.client.entities.json;

import com.ereader.client.entities.OrderList;
import com.ereader.client.entities.Page;

import java.util.List;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/10/31 14:38
 ***************************************/
public class OrderData {

    private List<OrderList> data;
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<OrderList> getData() {
        return data;
    }

    public void setData(List<OrderList> data) {
        this.data = data;
    }
}
