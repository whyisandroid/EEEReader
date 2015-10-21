package com.ereader.client.entities;

/**
 * Created by wanghy on 2015/10/20.
 */
public class RechargeOrder {
    private String  out_trade_no;// 交易订单号
    private String total_fee;// 交易金额
    private String notify_url;// 异步回调地址

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
