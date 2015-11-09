package com.ereader.client.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/11/6 10:19
 ***************************************/
public class MessageFriends {
   /* "message_id": 24,
            "from_user_id": 78,
            "from_user_nickname": "张三",
            "status": 0,
            "created_at": "2015-10-19 16:30:31"*/

/*
    "message_id": 9,
            "from_user_id": 1,
            "from_user_nickname": "xxx",    //推荐者姓名
            "product_id": "1",               //产品id
            "product_name": "《一本电子书》", //产品名称
            "created_at": "2015-09-01 15:43:09"  //推荐时间*/

    private String message_id;
    private String from_user_id;
    private String from_user_nickname;
    private String status;
    private String created_at;

    private String product_id;
    private String product_name;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_nickname() {
        return from_user_nickname;
    }

    public void setFrom_user_nickname(String from_user_nickname) {
        this.from_user_nickname = from_user_nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
