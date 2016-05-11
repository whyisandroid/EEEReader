package com.ereader.client.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/10/17 16:55
 ***************************************/
public class Order {
    private String user_id;
    private String pay_total;
    private String pay_need;
    private String pay_point;
    private String pay_point_money;
    private String updated_at;
    private String created_at;
    private String order_id;

    public void setPay_point_money(String pay_point_money) {
        this.pay_point_money = pay_point_money;
    }

    public String getPay_point_money() {
        return pay_point_money;
    }

    public void setPay_point(String pay_point) {
        this.pay_point = pay_point;
    }

    public String getPay_point() {
        return pay_point;
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

    public String getPay_need() {
        return pay_need;
    }

    public void setPay_need(String pay_need) {
        this.pay_need = pay_need;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
