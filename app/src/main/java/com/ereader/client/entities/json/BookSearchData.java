package com.ereader.client.entities.json;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.BookSearch;
import com.ereader.client.entities.Page;

import java.util.List;

/**
 * Created by wanghy on 2015/10/14.
 */
public class BookSearchData {
    private List<BookSearch> data;
    private Page page;

    public List<BookSearch> getData() {
        return data;
    }

    public void setData(List<BookSearch> data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
