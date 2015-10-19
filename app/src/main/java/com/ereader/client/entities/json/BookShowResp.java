package com.ereader.client.entities.json;

import com.ereader.client.entities.BookShow;
import com.ereader.client.entities.BookShowInfo;
import com.ereader.client.entities.Page;

import java.util.List;

/**
 * Created by ghf on 15/10/12.
 */
public class BookShowResp extends BaseResp{

 private BookShowInfo data;

    public BookShowInfo getData() {
        return data;
    }

    public void setData(BookShowInfo data) {
        this.data = data;
    }
}
