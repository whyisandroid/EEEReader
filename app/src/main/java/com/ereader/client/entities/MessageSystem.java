package com.ereader.client.entities;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/11/6 10:23
 ***************************************/
public class MessageSystem {
   /* "message_id": 23,
            "content": "系统消息消息消息消息！",
            "created_at": "2015-10-19 16:30:31"*/
    private String message_id ;
    private String content ;
    private String created_at ;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
