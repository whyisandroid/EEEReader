package com.ereader.client.entities;

import java.util.List;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/10/31 14:39
 ***************************************/
public class OrderList {
    /*"order_id": 128,
            "order_type": 1,
            "user_id": 1,
            "pay_total": "10.00",
            "pay_discount": "0.00",
            "pay_point": "0.00",
            "pay_need": "10.00",
            "pay_type": "",
            "pay_status": "0",
            "created_at": "2015-08-04 23:27:19",
            "updated_at": "2015-09-01 18:17:01",
            "order_products": [*/

    private String order_id;
    private String order_type;
    private String user_id;
    private String pay_total;
    private String pay_discount;
    private String pay_point;
    private String pay_need;
    private String pay_type;
    private String pay_status;
    private String created_at;
    private String updated_at;
    private ToUser to_user;
    private List<OrderBook> order_products;

    public void setTo_user(ToUser to_user) {
        this.to_user = to_user;
    }

    public ToUser getTo_user() {
        return to_user;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPay_total() {
        return pay_total;
    }

    public void setPay_total(String pay_total) {
        this.pay_total = pay_total;
    }

    public String getPay_discount() {
        return pay_discount;
    }

    public void setPay_discount(String pay_discount) {
        this.pay_discount = pay_discount;
    }

    public String getPay_point() {
        return pay_point;
    }

    public void setPay_point(String pay_point) {
        this.pay_point = pay_point;
    }

    public String getPay_need() {
        return pay_need;
    }

    public void setPay_need(String pay_need) {
        this.pay_need = pay_need;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<OrderBook> getOrder_products() {
        return order_products;
    }

    public void setOrder_products(List<OrderBook> order_products) {
        this.order_products = order_products;
    }
}
