package com.ereader.client.entities;

/**
 * Created by wanghy on 2015/9/5.
 */
public class Gift {
   /* "giftcard_id": 3,
            "code": "123451",
            "total": "100.00",
            "belong_user_id": 1,
            "is_use": "1",
            "use_user_id": 1,
            "expire_at": "2015-12-31 23:59:59",
            "created_at": "-0001-11-30 00:00:00",
            "updated_at": "2015-07-20 14:39:42",
            "is_expire": 0*/
    private String giftcard_id;
    private String code;
    private String total;
    private String belong_user_id;
    private String is_use;
    private String use_user_id;
    private String expire_at;
    private String created_at;
    private String updated_at;
    private String is_expire;

    public String getGiftcard_id() {
        return giftcard_id;
    }

    public void setGiftcard_id(String giftcard_id) {
        this.giftcard_id = giftcard_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBelong_user_id() {
        return belong_user_id;
    }

    public void setBelong_user_id(String belong_user_id) {
        this.belong_user_id = belong_user_id;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getUse_user_id() {
        return use_user_id;
    }

    public void setUse_user_id(String use_user_id) {
        this.use_user_id = use_user_id;
    }

    public String getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(String expire_at) {
        this.expire_at = expire_at;
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

    public String getIs_expire() {
        return is_expire;
    }

    public void setIs_expire(String is_expire) {
        this.is_expire = is_expire;
    }
}
