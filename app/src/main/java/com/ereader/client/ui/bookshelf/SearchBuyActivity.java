package com.ereader.client.ui.bookshelf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.BookShow;
import com.ereader.client.entities.BookShowInfo;
import com.ereader.client.entities.BookShowWithDownloadInfo;
import com.ereader.client.entities.json.BookShowResp;
import com.ereader.client.service.AppController;
import com.ereader.client.service.download.DownloadInfo;
import com.ereader.client.service.download.DownloadManager;
import com.ereader.client.service.download.DownloadService;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.ShelfSearchAdapter;
import com.ereader.client.ui.bookshelf.epubread.CustomFont;
import com.ereader.client.ui.bookshelf.epubread.SkySetting;
import com.ereader.client.ui.bookshelf.epubread.SkyUtility;
import com.ereader.client.ui.dialog.DialogUtil;
import com.ereader.common.constant.Constant;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.skytree.epub.Setting;

public class SearchBuyActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private AppController controller;
    private GridView gridv_book_search;
    private EditText main_top_title;
    private ShelfSearchAdapter adapter;
    private DownloadManager downloadManager;

    private EReaderApplication app;
    public final static int _OK = 1000;
    public final static int _DELETE = 1001;

    public final static int OPERATION_CHOOSE = 10;//选择添加图书／删除
    public final static int OPERATION_CUSTOM = 11;//下载／删除

    private static int operation = OPERATION_CUSTOM;//默认OPERATION_CUSTOM

    private DbUtils db;
    private SkyUtility  st;

    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case _OK:
                    LogUtil.LogError("_OK=","获取列表成功");
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
        LogUtil.LogError("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_search_layout);
        app=EReaderApplication.getInstance();
        controller = AppController.getController(this);
        downloadManager = DownloadService.getDownloadManager(SearchBuyActivity.this);
        findView();
//        init();
        initView();



    }
    //初始化阅读设置
    private void init(){

        if (SkySetting.getStorageDirectory()==null) {

            SkySetting.setStorageDirectory(Constant.ROOT_OUTPATH,Constant.FOLDER_NAME);
        }
        st = new SkyUtility(this);
        st.makeSetup();
        this.registerFonts();
//        this.makeLayout();
        this.reload();
        Setting.prepare();

    }
    public void reload() {
        app.reloadBookInformations();
        //TODO   清除数据重新加载
        //

    }
    public void registerFonts() {
        this.registerCustomFont("Underwood","uwch.ttf");
        this.registerCustomFont("Mayflower","Mayflower Antique.ttf");
    }

    public void registerCustomFont(String fontFaceName,String fontFileName) {
        st.copyFontToDevice(fontFileName);
        app.customFonts.add(new CustomFont(fontFaceName,fontFileName));
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
        List<BookShowWithDownloadInfo> datas = null;
        //获取下载数据库里的内容
        int downinfoCount = downloadManager.getDownloadInfoListCount();
        LogUtil.LogError("downinfoCount=",downinfoCount+"");
        if (null != booksGet && null != booksGet.getData()) {
            list = booksGet.getData();
            //=============================
            datas=new ArrayList<BookShowWithDownloadInfo>();
            BookShowWithDownloadInfo data=null;
            DownloadInfo downinfo = null;
            for (int i = 0; i <list.size() ; i++) {
                data=new BookShowWithDownloadInfo(list.get(i));
                for (int j = 0; j < downinfoCount; j++) {
                    downinfo = downloadManager.getDownloadInfo(j);
                    if (String.valueOf(downinfo.getId()).equals(data.getBook_id())) {
                        data.setDownloadInfo(downinfo);
                        if (downinfo.getState() == HttpHandler.State.SUCCESS) {
                            data.setIsDownloaded(true);

                        } else if ((downinfo.getState() == HttpHandler.State.LOADING ||
                                downinfo.getState() == HttpHandler.State.WAITING ||
                                downinfo.getState() == HttpHandler.State.STARTED)) {
                            data.setIsDownloading(true);
                        }
                    }
                }
                datas.add(data);
            }
        }
        if (null == datas) {
            datas = new ArrayList<BookShowWithDownloadInfo>();
        }
        if (null == adapter) {
            adapter = new ShelfSearchAdapter(SearchBuyActivity.this, datas,downloadManager);
            adapter = new ShelfSearchAdapter(SearchBuyActivity.this, datas,downloadManager);
            gridv_book_search.setAdapter(adapter);
        } else {
            adapter.setData(datas);
        }
    }


    private void handlerDatas(){

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
        BookShowWithDownloadInfo book = adapter.getItem(position);
        if (adapter.isShowDelete()) {//删除
            controller.getContext().addBusinessData("delete.book_id", book.getBook_id());
            DialogUtil.delBookById(SearchBuyActivity.this, book, mHandler);

        } else {
            if (operation == OPERATION_CHOOSE) {//添加图书
                //Todo 添加本地图书
                try {
                    db = DbUtils.create(SearchBuyActivity.this, Constant.OUTPATH, Constant.DBNAME);
                    db.configAllowTransaction(true);
                    db.configDebug(true);
                    db.save(book);
                } catch (DbException e) {
                    LogUtil.LogError("添加本地图书-DbException", e.toString());
                } finally {
                    db.close();
                }
//
                setResult(100);
                SearchBuyActivity.this.finish();
            } else {
                if (book.isDownloaded()) {//已经下载
                    Intent it = new Intent();
                    it.setClass(SearchBuyActivity.this, ReadActivity.class);
                    String path = book.getDownloadInfo().getFileSavePath();
                    ToastUtil.showToast(SearchBuyActivity.this, "position=" + position + ";path=" + path, ToastUtil.LENGTH_LONG);
                    LogUtil.Log("position=" + position + ";path=" + path);
                    it.putExtra(getString(R.string.bpath), path);
                    startActivity(it);

                } else {//未下载

//                    adapter.startDown(controller, position);
                    //ToastUtil.showToast(SearchBuyActivity.this,"下载...", Toast.LENGTH_SHORT);
                    if (book.isDownloading()) {//正在下载之取消下载
                        //TODO
                        adapter.setDownloadStatusNById(position, false);
                        adapter.stopDownload(position);

                    } else {//正在下载之开始下载
                        adapter.setDownloadStatusNById(position, true);
                        adapter.startDown(controller, position);
                    }
                }
            }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        BookShowWithDownloadInfo book = adapter.getItem(position);
        if (null != book && !book.isDownloading()) {
            adapter.setIsShowDelete(!adapter.isShowDelete());
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != db) {
            db.close();
            db = null;
        }
    }

}

