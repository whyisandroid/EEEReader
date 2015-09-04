package com.ereader.client.entities;

/**
 * Created by wanghy on 2015/9/5.
 */
public class Message {
   /* "message_id": 1,
            "from_user_id": 1,
            "to_user_id": 1,
            "type": 2,
            "content": "您的好友给您推荐《神笔马良》，点击书名立即查看",
            "json": "{\"product_id\":\"1\"}",
            "updated_at": "2015-09-01 15:11:56",
            "created_at": "2015-09-01 14:06:49"*/
    private String message_id;
    private String from_user_id;
    private String to_user_id;
    private String type;
    private String content;
    private String updated_at;
    private String created_at;
    private MessageType json;

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

    public MessageType getJson() {
        return json;
    }

    public void setJson(MessageType json) {
        this.json = json;
    }
}
