package com.ereader.client.entities;

/**
 * Created by wanghy on 2015/11/2.
 */
public class Bill {
 /*   "id": 1,
            "user_id": 1,
            "balance": "outlay",
            "carry": "5.0000",
            "type": "ecoin",
            "remain": "95.0000",
            "last_remain": "100.0000",
            "out_trade_no": "3",
            "remark": "",
            "created_at": "2015-07-08 19:50:32",
            "updated_at": "2015-07-08 19:50:32"*/

    private String id;
    private String user_id;
    private String balance;
    private String carry;
    private String type;
    private String remain;
    private String last_remain;
    private String out_trade_no;
    private String created_at;
    private String remark;
    private String updated_at;

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCarry() {
        return carry;
    }

    public void setCarry(String carry) {
        this.carry = carry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getLast_remain() {
        return last_remain;
    }

    public void setLast_remain(String last_remain) {
        this.last_remain = last_remain;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
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
}
