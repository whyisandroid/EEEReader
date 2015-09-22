package com.ereader.client.entities;

/**
 * Created by wanghy on 2015/9/22.
 */
public class Friend {
/*    "nickname": "",
            "friend_id": 1,
            "created_at": "2015-07-15 17:02:22",
            "updated_at": "2015-07-15 19:42:33"
    */
    private String nickname;
    private String friend_id;
    private String created_at;
    private String updated_at;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
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
