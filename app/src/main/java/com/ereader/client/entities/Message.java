package com.ereader.client.entities;

/**
 * Created by wanghy on 2015/9/5.
 */
public class Message {

/*    message_id": 13,
            "from_user_id": 115,
            "to_user_id": 1,
            "type": 1,
            "content": "邀请您成为好友！",
            "json": "{\"friend_id\":1}",
            "status": 3,
            "updated_at": "2015-10-20 17:17:04",
            "created_at": "2015-10-17 16:08:59"*/
    private String message_id;
    private String from_user_id;
    private String to_user_id;
    private String type;
    private String content;
    private String updated_at;
    private String created_at;
    private String status;
    private String  json;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

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

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
