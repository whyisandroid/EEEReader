package com.ereader.client.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.BookShow;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.json.BookResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BookLocalPagerAdapter;
import com.ereader.client.ui.adapter.BookPagerAdapter;
import com.ereader.client.ui.adapter.BookShelfAdapter;
import com.ereader.client.ui.bookshelf.SearchBuyActivity;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.view.LoopViewPager;
import com.ereader.client.ui.view.PointView;
import com.ereader.common.constant.Constant;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ToastUtil;
import com.ereader.reader.db.BookDBHelper;
import com.ereader.reader.model.StoreBook;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookshelfFragment extends Fragment {
    private static final String TAG = "BookshelfFragment";
    private View view;
    private Context mContext;
    private AppController controller;
    private Button main_top_right;
    private GridView gridv_book;
    private LoopViewPager viewpager;
    private LinearLayout pointlayout;

    private ArrayList<HashMap<String, Object>> listItem = null;
    private HashMap<String, Object> map = null;
    private Map<String, Integer[]> map2;// 存放本地推荐目录的小封面图片引用
    private SharedPreferences sp;
//    private LocalBook localbook;
    private boolean isInit = false;
    private PointView pointView;
    private BookShelfAdapter adapter;
    private ProgressDialog mLoadingDialog;
    private DbUtils db;

    public static final int requestCode_addBook = 1000;

    public static final int requestCode_login = 1001;

    private List<StoreBook> list = new ArrayList<StoreBook>();

    public static final int RECOMMEND_BOOK = 1100;

    private Handler mhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case RECOMMEND_BOOK:
                    //推荐阅读
                    BookResp bookResp = (BookResp) controller.getContext().getBusinessData("shelf.RecommendBookResp");
                    EReaderApplication.getInstance().saveRecommend(bookResp);//本地存储
                    setupRecommend();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.book_shelf_fragment, container, false);
        controller = AppController.getController(getActivity());
        mContext = getActivity();
        findView();
        initView();
        initRead();
        return view;
    }


    private void initRead() {
        //阅读的设置
        sp = mContext.getSharedPreferences("reader", Context.MODE_PRIVATE);
        isInit=sp.getBoolean("isInit", false);
        //sample图书
        if (!isInit) {
            showLoading();
            new AsyncSetApprove().execute("");
        }

    }

    private void findView() {
        main_top_right = (Button) view.findViewById(R.id.main_top_right);
        gridv_book = (GridView) view.findViewById(R.id.gridv_book);

        viewpager = (LoopViewPager) view.findViewById(R.id.viewpager);
        pointlayout = (LinearLayout) view.findViewById(R.id.pointlayout);
    }

    private void initView() {
        ((TextView) view.findViewById(R.id.tv_main_top_title)).setText("书架");
        main_top_right.setText("已购");
        main_top_right.setOnClickListener(rightListener);
        gridv_book.setOnItemClickListener(gridItemListener);
        gridv_book.setOnItemLongClickListener(longListener);

//        initBannerPager();
    }

    private void initBannerPager() {
        // 最近阅读的信息
        List<StoreBook> listPager=  BookDBHelper.get(mContext).queryAllBooks();
        int size=listPager.size();
        if (null != listPager && size > 0&&EReaderApplication.getInstance().isLogin()) {
            //限制
            if(size>6){
                listPager=listPager.subList(0,6);//左闭右开
            }
            LogUtil.LogError("banner:",listPager.size()+"");
            BookPagerAdapter pageAdapter = new BookPagerAdapter(mContext, listPager);
            viewpager.setAdapter(pageAdapter);
            viewpager.setCurrentItem(0);
            viewpager.setOnPageChangeListener(viewpagerListener);

            pointView = new PointView(getActivity(), (listPager.size()+1)/2);
            pointlayout.removeAllViews();
            pointlayout.addView(pointView);
            pointView.setPosition(0);
            pointlayout.postInvalidate();
        } else {//最近阅读－没有数据：
            initRecommend();
        }

    }
    private void initRecommend(){
        BookResp recommend =EReaderApplication.getInstance().getRecommend();
        if(null!=recommend&&null!=recommend.getData()&&recommend.getData().getData().size()>0){
            setupRecommend();
        }else{
            recommend();
        }
    }
    //推荐书籍
    private void setupRecommend(){
        List<Book> recommendList=new ArrayList<Book>();

        BookResp recommend =EReaderApplication.getInstance().getRecommend();
        if(null!=recommend){
            recommendList=recommend.getData().getData();
        }
        if(recommendList.size()>0){
//            recommendList=recommendList.subList(0,2);
            LogUtil.LogError("推荐的大小", recommendList.size()+"");
            BookLocalPagerAdapter pageAdapter = new BookLocalPagerAdapter(mContext, recommendList);
            viewpager.setAdapter(pageAdapter);
            viewpager.setCurrentItem(0);
            viewpager.setOnPageChangeListener(viewpagerListener);

            pointView = new PointView(getActivity(), 1);
            pointlayout.removeAllViews();
            pointlayout.addView(pointView);
            pointView.setPosition(0);
            pointlayout.postInvalidate();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        localCustom();
        initBannerPager();

    }

    private OnPageChangeListener viewpagerListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            int size = viewpager.getAdapter().getCount();
            pointView.setPosition(position % size);
            pointView.postInvalidate();

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private OnItemClickListener gridItemListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (position == parent.getAdapter().getCount() - 1) {
                if (EReaderApplication.getInstance().isLogin()) {
                    SearchBuyActivity.setOperation(SearchBuyActivity.OPERATION_CHOOSE);
                    IntentUtil.intentForResult(getActivity(), SearchBuyActivity.class, requestCode_addBook);
                } else {
                    IntentUtil.intentForResult(getActivity(), LoginActivity.class, requestCode_login);
                }

            } else {
                StoreBook book = adapter.getItem(position);
                if (null == book) {
                    return;
                }
                if (book.delete) {//删除
                    //删除数据库不删除本地文件
                    adapter.deleteByPostion(position);
                    ToastUtil.showToast(getActivity(), "删除《" + book.name + "》成功！", ToastUtil.LENGTH_SHORT);
                } else {
                    adapter.open(position);
                }
            }
        }
    };

    private AdapterView.OnItemLongClickListener longListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == parent.getAdapter().getCount() - 1) {

            } else {
                StoreBook book = (StoreBook) parent.getAdapter().getItem(position);
                if (null != book ) {
                    adapter.setIsShowDelete(!book.delete,position);
                }
            }
            return true;
        }
    };
    private OnClickListener rightListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (EReaderApplication.getInstance().isLogin()) {
                SearchBuyActivity.setOperation(SearchBuyActivity.OPERATION_CUSTOM);
                IntentUtil.intent(getActivity(), SearchBuyActivity.class);
            } else {
                IntentUtil.intentForResult(getActivity(), LoginActivity.class, requestCode_login);
            }

        }
    };

    class AsyncSetApprove extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            if (!isInit) {

                File path = new File(Constant.BOOKS);//mContext.getFilesDir();
                if (!path.exists()) {
                    path.mkdirs();
                }
                String[] strings = getResources()
                        .getStringArray(R.array.bookid);// 获取assets目录下的文件列表
                for (int i = 0; i < strings.length; i++) {
                    try {
                        FileOutputStream out = new FileOutputStream(path + "/"
                                + strings[i]);
                        BufferedInputStream bufferedIn = new BufferedInputStream(
                                getResources().openRawResource(R.raw.book + i));
                        BufferedOutputStream bufferedOut = new BufferedOutputStream(
                                out);
                        byte[] data = new byte[2048];
                        int length = 0;
                        while ((length = bufferedIn.read(data)) != -1) {
                            bufferedOut.write(data, 0, length);
                        }
                        // 将缓冲区中的数据全部写出
                        bufferedOut.flush();
                        // 关闭流
                        bufferedIn.close();
                        bufferedOut.close();
                        sp.edit().putBoolean("isInit", true).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    isInit = true;
                    sp.edit().putBoolean("isInit", true).commit();

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // local();
            super.onPostExecute(result);
            cancelLoading();
        }
    }

    /**
     * 获取SD卡根目录
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    private boolean DbDeleteBook(BookShow book) {

        try {
            db = DbUtils.create(getActivity(), Constant.OUTPATH, Constant.DBNAME);
            db.configAllowTransaction(true);
            db.configDebug(true);
            db.delete(book);
            return true;
        } catch (DbException e) {
            LogUtil.LogError("删除数据－DbException", e.toString());
            e.printStackTrace();
            return false;

        } finally {
            db.close();
        }

    }

    private void localCustom() {
        if (!EReaderApplication.getInstance().isLogin()) {
            setupData(list);
        } else {
            LogUtil.LogError("path", Constant.OUTPATH + Constant.DBNAME);
            try {
                db = DbUtils.create(getActivity(), Constant.OUTPATH, Constant.DBNAME);
                db.configAllowTransaction(true);
                db.configDebug(true);
                list = db.findAll(StoreBook.class);
                setupData(list);
            } catch (DbException e) {
                LogUtil.LogError("获取数据－DbException", e.toString());
            } finally {
                db.close();
            }
        }

    }

    private void setupData(List<StoreBook> list) {
        if (null == list) {
            list = new ArrayList<StoreBook>();
        }
        adapter = new BookShelfAdapter(mContext, list);
        gridv_book.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case requestCode_addBook://已购图书回调
                if (resultCode == 100) {
                    //TODO  查询数据库
                    localCustom();
                }
                break;
            case requestCode_login://登陆回调回调

                break;
            default:

                break;

        }
    }
    private void showLoading() {
        cancelLoading();
        mLoadingDialog = new ProgressDialog(mContext);
        mLoadingDialog.setMessage(getResources().getText(R.string.initialing));
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }

    private void cancelLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            try {
                mLoadingDialog.dismiss();
            } catch(Throwable tr) {}
            mLoadingDialog = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelLoading();
        if (null != db) {
            db.close();
            db = null;
        }
    }

    //推荐阅读
    private void recommend() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                PageRq pageRq=new PageRq();
                pageRq.setPage(1);
                pageRq.setPer_page(2);
                controller.shelfRecommend(mhandler, pageRq);
            }
        }).start();
    }
}
