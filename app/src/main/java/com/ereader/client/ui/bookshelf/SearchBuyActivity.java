package com.ereader.client.ui.bookshelf;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.ereader.client.R;
import com.ereader.client.entities.BookShow;
import com.ereader.client.entities.BookShowInfo;
import com.ereader.client.entities.json.BookShowResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.ShelfSearchAdapter;
import com.ereader.client.ui.dialog.DialogUtil;
import com.ereader.common.constant.Constant;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class SearchBuyActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private AppController controller;
    private GridView gridv_book_search;
    private EditText main_top_title;
    private ShelfSearchAdapter adapter;


    public final static int _OK = 1000;
    public final static int _DELETE = 1001;

    public final static int OPERATION_CHOOSE = 10;//选择添加图书／删除
    public final static int OPERATION_CUSTOM = 11;//下载／删除

    private static int operation = OPERATION_CUSTOM;//默认OPERATION_CUSTOM

    private DbUtils db;

    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case _OK:
                    setupData();
                    break;
                case _DELETE:
                    delshelfBuyBooks();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public static int getOperation() {
        return operation;
    }

    public static void setOperation(int pOperation) {
        operation = pOperation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_search_layout);
        controller = AppController.getController(this);
        findView();
        initView();
    }

    private void findView() {
        main_top_title = (EditText) findViewById(R.id.et_book_search);
        gridv_book_search = (GridView) findViewById(R.id.gridv_book_search);
    }

    private void initView() {

        main_top_title.setHint("请输入你要搜索的关键词");

        gridv_book_search.setOnItemClickListener(this);
        gridv_book_search.setOnItemLongClickListener(this);
        getshelfBuyBooks();

    }

    private void setupData() {

        BookShowInfo booksGet = (BookShowInfo) controller.getContext().getBusinessData("BookShowResp");
        List<BookShow> list = null;
        if (null != booksGet && null != booksGet.getData()) {
            list = booksGet.getData();
        }
        if (null == list) {
            list = new ArrayList<BookShow>();
        }
        if (null == adapter) {
            adapter = new ShelfSearchAdapter(SearchBuyActivity.this, list);
            gridv_book_search.setAdapter(adapter);
        } else {
            adapter.setData(list);
        }
    }

    private void getshelfBuyBooks() {
        ProgressDialogUtil.showProgressDialog(SearchBuyActivity.this, "获取中...", false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.shelfBuyBooks(mHandler);
                ProgressDialogUtil.closeProgressDialog();
            }
        }).start();


    }

    private void delshelfBuyBooks() {
        ProgressDialogUtil.showProgressDialog(SearchBuyActivity.this, "获取中...", false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.shelfDelBuyBooks(mHandler);
                ProgressDialogUtil.closeProgressDialog();
            }
        }).start();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BookShow book = adapter.getItem(position);
        if (adapter.isShowDelete()) {//删除
            controller.getContext().addBusinessData("delete.book_id", book.getBook_id());
            DialogUtil.delBookById(SearchBuyActivity.this, book, mHandler);

        } else {
            if (operation == OPERATION_CHOOSE) {//添加图书
                //Todo 添加本地图书
                try {

                    db= DbUtils.create(SearchBuyActivity.this, Constant.OUTPATH, Constant.DBNAME);
                    db.configAllowTransaction(true);
                    db.configDebug(true);

                    db.save(book);
                } catch (DbException e) {
                    LogUtil.LogError("添加本地图书-DbException", e.toString());
                }finally {
                    db.close();
                }
//
                setResult(100);
                SearchBuyActivity.this.finish();
            } else {
                if (book.isDownloaded()) {//已经下载
                    Intent it = new Intent();
                    it.setClass(SearchBuyActivity.this, ReadActivity.class);
                    String path = book.getLocalpath();
                    ToastUtil.showToast(SearchBuyActivity.this, "position=" + position + ";path=" + path, ToastUtil.LENGTH_LONG);
                    it.putExtra(getString(R.string.bpath), path);
                    startActivity(it);

                } else {//未下载
                    //ToastUtil.showToast(SearchBuyActivity.this,"下载...", Toast.LENGTH_SHORT);
                    if (book.isDownloading()) {//正在下载之取消下载
                        adapter.setDownloadStatusNById(position, false);
                    } else {//正在下载之开始下载
                        adapter.setDownloadStatusNById(position, true);
                    }
                }
            }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        BookShow book =adapter.getItem(position);
        if(null!=book&&!book.isDownloading()){
            adapter.setIsShowDelete(!adapter.isShowDelete());
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=db){
            db.close();
            db=null;
        }
    }
}

