package com.ereader.client.entities.json;

import com.ereader.client.entities.OrderList;

import java.util.List;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/10/31 14:38
 ***************************************/
public class OrderData {
   /* "total": 280,
            "per_page": "30",
            "current_page": 1,
            "last_page": 10,
            "next_page_url": "/?page=2",
            "prev_page_url": null,
            "from": 1,
            "to": 30,
            "data"*/

    private String total;
    private String per_page;
    private String current_page;
    private String last_page;
    private String next_page_url;
    private String prev_page_url;
    private String from;
    private String to;
    private List<OrderList> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public String getLast_page() {
        return last_page;
    }

    public void setLast_page(String last_page) {
        this.last_page = last_page;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<OrderList> getData() {
        return data;
    }

    public void setData(List<OrderList> data) {
        this.data = data;
    }
}
