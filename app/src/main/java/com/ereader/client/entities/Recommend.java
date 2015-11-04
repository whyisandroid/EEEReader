package com.ereader.client.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/11/3 20:16
 ***************************************/
public class Recommend {
    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
  /*  "to_user_id": 36,
            "product_name": "一本电子书68041319",
            "user_nickname": "angelipin",
            "created_at": "2015-10-25 23:24:09"*/

    private String  to_user_id;
    private String  product_name;
    private String  user_nickname;
    private String  created_at;
    private String image_url;

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }
}
