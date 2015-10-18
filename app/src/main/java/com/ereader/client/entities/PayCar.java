package com.ereader.client.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/10/17 16:21
 ***************************************/
public class PayCar {

    private String product_id;
    private String quantity = "1";
    private String product_discount_id = "0";

    public PayCar(String id){
        product_id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_discount_id() {
        return product_discount_id;
    }

    public void setProduct_discount_id(String product_discount_id) {
        this.product_discount_id = product_discount_id;
    }
}
