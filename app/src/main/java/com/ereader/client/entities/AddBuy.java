package com.ereader.client.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/11/9 21:48
 ***************************************/
public class AddBuy {
    private String total_product_count;
    private String total_price;

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_product_count() {
        return total_product_count;
    }

    public void setTotal_product_count(String total_product_count) {
        this.total_product_count = total_product_count;
    }
}
