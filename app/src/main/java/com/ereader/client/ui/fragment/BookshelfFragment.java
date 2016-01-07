package com.ereader.client.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import com.ereader.client.entities.BookShow;
import com.ereader.client.entities.BookShowWithDownloadInfo;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BookLocalPagerAdapter;
import com.ereader.client.ui.adapter.BookShelfAdapter;
import com.ereader.client.ui.bookshelf.SearchBuyActivity;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.view.LoopViewPager;
import com.ereader.client.ui.view.PointView;
import com.ereader.common.constant.Constant;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ToastUtil;
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

    private List<BookShowWithDownloadInfo> list = new ArrayList<BookShowWithDownloadInfo>();

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

    //初始化阅读设置
    private void init() {

//        if (SkySetting.getStorageDirectory() == null) {
//
//            SkySetting.setStorageDirectory(Constant.ROOT_OUTPATH, Constant.FOLDER_NAME);
//        }
//        st = new SkyUtility(mContext);
//        st.makeSetup();
//        this.registerFonts();
//        this.reload();
//        Setting.prepare();

    }

//    public void reload() {
//
//    }
//
//    public void registerFonts() {
//        this.registerCustomFont("Underwood", "uwch.ttf");
//        this.registerCustomFont("Mayflower", "Mayflower Antique.ttf");
//    }
//
//    public void registerCustomFont(String fontFaceName, String fontFileName) {
//        st.copyFontToDevice(fontFileName);
////        app.customFonts.add(new CustomFont(fontFaceName, fontFileName));
//    }

    private void initRead() {
        //阅读的设置
//        init();
        sp = mContext.getSharedPreferences("reader", Context.MODE_PRIVATE);
        isInit=sp.getBoolean("isInit", false);
        //sample图书
        if (!isInit) {
            showLoading();
            new AsyncSetApprove().execute("");
        }


    /*    // 读取名为"mark"的sharedpreferences
        sp = mContext.getSharedPreferences("mark", mContext.MODE_PRIVATE);
//        localbook = new LocalBook(mContext, "localbook");
        map2 = new HashMap<String, Integer[]>();
        String[] bookids = getResources().getStringArray(R.array.bookid);
        for (int i = 0; i < bookids.length; i++) {
            map2.put(bookids[i], new Integer[]{R.drawable.book0 + i});
        }*/

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

        initBannerPager();
    }

    private void initBannerPager() {
        // 最近阅读的信息
//        ArrayList<BookInformation> listPager = app.bis;
//        LogUtil.LogError("listPager",listPager.size()+"");
//        if (null != listPager && listPager.size() > 0) {
//            BookPagerAdapter pageAdapter = new BookPagerAdapter(mContext, listPager,app);
//            viewpager.setAdapter(pageAdapter);
//            viewpager.setCurrentItem(0);
//            viewpager.setOnPageChangeListener(viewpagerListener);
//
//            pointView = new PointView(getActivity(), (listPager.size()+1)/2);
//            pointlayout.removeAllViews();
//            pointlayout.addView(pointView);
//            pointView.setPosition(0);
//            pointlayout.postInvalidate();
//        } else {//最近阅读－没有数据：
            List<String> localListPager = new ArrayList<String>();
            localListPager.add("");
            BookLocalPagerAdapter pageAdapter = new BookLocalPagerAdapter(mContext, localListPager);
            viewpager.setAdapter(pageAdapter);
            viewpager.setCurrentItem(0);
            viewpager.setOnPageChangeListener(viewpagerListener);

            pointView = new PointView(getActivity(), localListPager.size());
            pointlayout.removeAllViews();
            pointlayout.addView(pointView);
            pointView.setPosition(0);
            pointlayout.postInvalidate();
//        }

    }

    @Override
    public void onResume() {
        super.onResume();
        localCustom();

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
                BookShowWithDownloadInfo book = adapter.getItem(position);
                if (null == book) {
                    //TODO 无效的显示
                    return;
                }
                if (adapter.isShowDelete()) {//删除
                    //TODO:删除数据库／本地文件

                    if (DbDeleteBook(book)) {
                        if (book.isDownloaded() && null != book.getDownloadInfo()) {//已经下载
                            new File(book.getDownloadInfo().getFileSavePath()).delete();
                        }
                        adapter.deleteByPostion(position);
                        ToastUtil.showToast(getActivity(), "删除《" + book.getName() + "》成功！", ToastUtil.LENGTH_SHORT);
                    } else {
                        ToastUtil.showToast(getActivity(), "删除《" + book.getName() + "》失败！", ToastUtil.LENGTH_SHORT);
                    }

                } else {

                    if (book.isDownloaded()) {//已经下载
//                        Intent it = new Intent();
//                        it.setClass(mContext, ReadActivity.class);
//                        //          getResources().openRawResource(R.raw.book0);
//                        String path = getActivity().getFilesDir().getAbsolutePath() + "/book.epub";
//                        //(String) listItem.get(0).get("path");
//                        ToastUtil.showToast(mContext, "position=" + position + ";path=" + path, ToastUtil.LENGTH_LONG);
//                        //it.putExtra("aaa", path);getString(R.string.bpath)
//                        it.putExtra(getString(R.string.bpath), path);
//                        startActivity(it);

                    } else {//未下载

                        if (book.isDownloading()) {//正在下载之取消下载
                            adapter.setDownloadStatusNById(position, false);
                        } else {//正在下载之开始下载
                            adapter.setDownloadStatusNById(position, true);
                        }
                    }

                }
            }
        }
    };

    private AdapterView.OnItemLongClickListener longListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == parent.getAdapter().getCount() - 1) {

            } else {
                BookShowWithDownloadInfo book = (BookShowWithDownloadInfo) parent.getAdapter().getItem(position);
                if (null != book && !book.isDownloading()) {
                    adapter.setIsShowDelete(!adapter.isShowDelete());
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
                list = db.findAll(BookShowWithDownloadInfo.class);
                setupData(list);
            } catch (DbException e) {
                LogUtil.LogError("获取数据－DbException", e.toString());
            } finally {
                db.close();
            }
        }

    }

    private void setupData(List<BookShowWithDownloadInfo> list) {
        if (null == list) {
            list = new ArrayList<BookShowWithDownloadInfo>();
        }
        adapter = new BookShelfAdapter(mContext, list);
        gridv_book.setAdapter(adapter);
    }



//
//    /**
//     * 本地书库载入
//     */
//    public void local() {
//        SQLiteDatabase db = localbook.getReadableDatabase();
//        String col[] = {"path"};
//        Cursor cur = db.query("localbook", col, "type=1", null, null, null,
//                null);
//        Cursor cur1 = db.query("localbook", col, "type=2", null, null, null,
//                null);
//        Integer num = cur.getCount();
//        Integer num1 = cur1.getCount();
//        ArrayList<String> arraylist = new ArrayList<String>();
//        while (cur1.moveToNext()) {
//            String s = cur1.getString(cur1.getColumnIndex("path"));
//            arraylist.add(s);
//        }
//        while (cur.moveToNext()) {
//            String s = cur.getString(cur.getColumnIndex("path"));
//            arraylist.add(s);
//        }
//        db.close();
//        cur.close();
//        cur1.close();
//        if (listItem == null)
//            listItem = new ArrayList<HashMap<String, Object>>();
//        listItem.clear();
//        String[] bookids = getResources().getStringArray(R.array.bookid);
//        String[] booknames = getResources().getStringArray(R.array.bookname);
//        String[] bookauthors = getResources()
//                .getStringArray(R.array.bookauthor);
//        Map<String, String[]> maps = new HashMap<String, String[]>();
//        for (int i = 0; i < bookids.length; i++) {
//            String[] value = new String[2];
//            value[0] = booknames[i];
//            value[1] = bookauthors[i];
//            maps.put(bookids[i], value);
//        }
//        for (int i = 0; i < num + num1; i++) {
//            if (i < num1) {
//                File file1 = new File(arraylist.get(i));
//                String m = file1.getName().substring(0,
//                        file1.getName().length() - 4);
//                if (m.length() > 8) {
//                    m = m.substring(0, 8) + "...";
//                }
//                String id = arraylist.get(i).substring(
//                        arraylist.get(i).lastIndexOf("/") + 1);
//                String[] array = maps.get(id);
//                String auther = array != null && array[1] == null ? "未知"
//                        : array[1];
//                String name = array[0] == null ? m : array[0];
//                map = new HashMap<String, Object>();
//
//                if (i == 0) {
//                    map.put("itemback", R.drawable.itemback);
//                } else if ((i % 2) == 0) {
//                    map.put("itemback", R.drawable.itemback);
//                }
//                map.put("ItemImage",
//                        map2 != null ? map2.get(file1.getName())[0]
//                                : R.drawable.cover);
//                map.put("BookName", "");
//                map.put("ItemTitle", name == null ? m : name);
//                map.put("ItemTitle1", "作者：" + auther);
//                map.put("LastImage", "推荐书目");
//                map.put("path", file1.getPath());
//                map.put("com", 0 + file1.getName());// 单独用于排序
//                listItem.add(map);
//            } else {
//                map = new HashMap<String, Object>();
//
//                File file1 = new File(arraylist.get(i));
//                String m = file1.getName().substring(0,
//                        file1.getName().length() - 4);
//                if (m.length() > 8) {
//                    m = m.substring(0, 8) + "...";
//                }
//                if (i == 0) {
//                    map.put("itemback", R.drawable.itemback);
//                } else if ((i % 2) == 0) {
//                    map.put("itemback", R.drawable.itemback);
//                }
//                map.put("ItemImage", R.drawable.cover);
//                map.put("BookName", m);
//                map.put("ItemTitle", m);
//                map.put("ItemTitle1", "作者：未知");
//                map.put("LastImage", "本地导入");
//                map.put("path", file1.getPath());
//                map.put("com", "1");
//                listItem.add(map);
//            }
//        }
//    }

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
}
