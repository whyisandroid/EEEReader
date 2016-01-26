package com.ereader.client.ui.bookstore;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.json.BookResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.BookAdapter;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

public class BookActivity extends BaseActivity implements OnClickListener,
        OnHeaderRefreshListener, OnFooterRefreshListener {
    private AppController controller;
    private Button main_top_right;
    private ListView lv_book;
    private ImageView iv_book_up;
    private PullToRefreshView pull_refresh_book;
    private List<Book> mList = new ArrayList<Book>();
    private BookAdapter adapter;
    private Page page;
    private String title;// 类别

    public static final int BOOK = 0; // 更新页面数据 书本
    public static final int BOOK_CATE = 10; // 更新页面数据 书本
    public static final int BOOK_DIS = -1; // 更新页面数据 书本 ....... 多个
    public static final int REFRESH_DOWN_OK = 1; // 向下刷新
    public static final int REFRESH_UP_OK = 2;  //向上拉
    public static final int REFRESH_ERROR = 3; // 刷新失败
    private Handler mhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case BOOK:
                    // 更新页面数据
                    BookResp bookResp = (BookResp) controller.getContext().getBusinessData("BookFeaturedResp");
                    for (int i = 0; i < bookResp.getData().getData().size(); i++) {
                        boolean flag = true;

                        for (int j = 0; j < mList.size(); j++) {
                            if(bookResp.getData().getData().get(i).getInfo().getProduct_id().equals(mList.get(j).getInfo().getProduct_id())){
                                flag = false;
                            }
                        }
                        if(flag){
                            mList.add(bookResp.getData().getData().get(i));
                        }
                    }
                    page = bookResp.getData().getPage();
                    adapter.notifyDataSetChanged();
                    pull_refresh_book.onHeaderRefreshComplete();
                    pull_refresh_book.onFooterRefreshComplete();
                    break;
                case BOOK_CATE:
                    // 更新页面数据
                    String id = getIntent().getExtras().getString("categroyItem_id");
                    BookResp bookResp2 = (BookResp) controller.getContext().getBusinessData("BookFeaturedResp" + id);
                    for (int i = 0; i < bookResp2.getData().getData().size(); i++) {
                        boolean flag = true;

                        for (int j = 0; j < mList.size(); j++) {
                            if(bookResp2.getData().getData().get(i).getInfo().getProduct_id().equals(mList.get(j).getInfo().getProduct_id())){
                                flag = false;
                            }
                        }
                        if(flag){
                            mList.add(bookResp2.getData().getData().get(i));
                        }
                    }
                    page = bookResp2.getData().getPage();
                    adapter.notifyDataSetChanged();
                    pull_refresh_book.onHeaderRefreshComplete();
                    pull_refresh_book.onFooterRefreshComplete();
                    break;
                case REFRESH_DOWN_OK:
                    ToastUtil.showToast(BookActivity.this, "刷新成功！", ToastUtil.LENGTH_LONG);
                    pull_refresh_book.onHeaderRefreshComplete();
                    break;
                case REFRESH_UP_OK:
                    adapter.notifyDataSetChanged();
                    pull_refresh_book.onFooterRefreshComplete();
                    break;
                case REFRESH_ERROR:
                    pull_refresh_book.onHeaderRefreshComplete();
                    pull_refresh_book.onFooterRefreshComplete();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);
        controller = AppController.getController(this);
        findView();
        initView();
        onHeaderRefresh(pull_refresh_book);
    }

    /**
     * 方法描述：FindView
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void findView() {
        main_top_right = (Button) findViewById(R.id.main_top_right);
        lv_book = (ListView) findViewById(R.id.lv_book);
        pull_refresh_book = (PullToRefreshView) findViewById(R.id.pull_refresh_book);
        iv_book_up = (ImageView) findViewById(R.id.iv_book_up);
    }


    /**
     * 方法描述：初始化 View
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {

        title = getIntent().getExtras().getString("title");
        ((TextView) findViewById(R.id.tv_main_top_title)).setText(title);
        main_top_right.setText("购物车");
        main_top_right.setOnClickListener(this);
        pull_refresh_book.setOnHeaderRefreshListener(this);
        pull_refresh_book.setOnFooterRefreshListener(this);
        adapter = new BookAdapter(BookActivity.this, mList);
        lv_book.setAdapter(adapter);
        lv_book.setOnItemClickListener(bookItemListener);

        iv_book_up.setOnClickListener(this);
        lv_book.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0) {
                    iv_book_up.setVisibility(View.VISIBLE);
                } else {
                    iv_book_up.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getDate(PageRq pageRq) {
        if ("经典热销".equals(title)) {
            featuredList(pageRq);
        } else if ("推荐阅读".equals(title)) {
            recommend(pageRq);
        } else if ("好评榜".equals(title)) {
            //缺失
            recommend(pageRq);
        } else if ("热销榜".equals(title)) {
            //缺失
            recommend(pageRq);
        } else {
            // 分类列表
            String id = getIntent().getExtras().getString("categroyItem_id");
            categroyItem(id,pageRq);
        }
    }

    private void categroyItem(final String id,final PageRq pageRq) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.categroyItem(mhandler, id,pageRq);
            }
        }).start();
    }

    private void featuredList(final PageRq pageRq) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.featuredList(mhandler,pageRq);
            }
        }).start();
    }

    private void recommend(final PageRq pageRq) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.recommend(mhandler,pageRq);
            }
        }).start();
    }

    private OnItemClickListener bookItemListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Book book = mList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("detailBook", book);
            IntentUtil.intent(BookActivity.this, bundle, BookDetailActivity.class, false);
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.main_top_right:
                IntentUtil.intent(this, BuyCarActivity.class);
                break;
            case R.id.iv_book_up:
                lv_book.setSelection(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        PageRq mPageRq = new PageRq();
        if(page.getCurrent_page() == page.getLast_page()){
            ToastUtil.showToast(BookActivity.this,"没有更多了",ToastUtil.LENGTH_LONG);
            pull_refresh_book.onFooterRefreshComplete();
            return;
        }
        mPageRq.setPage(page.getCurrent_page() + 1);
        getDate(mPageRq);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        PageRq mPageRq = new PageRq();
        getDate(mPageRq);
    }
}
