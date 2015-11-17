package com.ereader.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.Login;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.entities.json.SubCategoryResp;
import com.ereader.client.ui.bookshelf.epubread.BookViewActivity;
import com.ereader.client.ui.bookshelf.epubread.CustomFont;
import com.ereader.client.ui.bookshelf.epubread.MagazineActivity;
import com.ereader.client.ui.bookshelf.epubread.SkyDatabase;
import com.ereader.client.ui.bookshelf.epubread.SkySetting;
import com.ereader.client.ui.bookshelf.epubread.SkyUtility;
import com.ereader.common.net.AppSocketInterface;
import com.ereader.common.net.XUtilsSocketImpl;
import com.ereader.common.util.LogUtil;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.skytree.epub.BookInformation;
import com.skytree.epub.SkyProvider;

/**
 * ***************************************
 * 类描述： 程序入口类 类名称：CreditWealthApplication
 *
 * @version: 1.0
 * @author: why
 * @time: 2014-2-13 下午2:09:22
 * ****************************************
 */
public class EReaderApplication extends Application {

    /**
     * 实例化 *
     */
    private static EReaderApplication instance;
    /**
     * 网络链接 *
     */
    private static AppSocketInterface appSocket;


    public int curVersionCode; // 版本号
    public String curVersionName; // 版本名字

    private boolean login;// 登录情况
    public static ImageLoader imageLoader = null;
    private ImageLoaderConfiguration configuration = null;
    public static DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 方法描述：初始化
     *
     * @author: why
     * @time: 2014-2-14 下午3:46:04
     */
    private void init() {
        instance = this;
        appSocket = new XUtilsSocketImpl();
        getCurrentVersion();
        this.imageLoader = ImageLoader.getInstance();
        getImageOptions();
    }

    private void getImageOptions() {
        // TODO Auto-generated method stub
        this.options = new DisplayImageOptions.Builder()
				/*.showStubImage(R.drawable.b1_03) // 设置图片下载期间显示的图片
						.showImageForEmptyUri(R.drawable.b1_03) // 设置图片Uri为空或是错误的时候显示的图片
						.showImageOnFail(R.drawable.b1_03) // 设置图片加载或解码过程中发生错误显示的图片*/
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();

        this.configuration = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        if (!this.imageLoader.isInited()) {
            this.imageLoader.init(configuration);
        }
    }


    /**
     * @return login : return the property login.
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * @param login : set the property login.
     */
    public void setLogin(boolean login) {
        this.login = login;
        if (!login) {
            //AppController.getController().getContext().clearBusinessData();
        }
    }

    /**
     * 方法描述: 获取网络通信实例
     *
     * @return
     * @author: why
     * @time: 2013-10-21 下午3:32:02
     */
    public static AppSocketInterface getAppSocket() {
        return appSocket;
    }

    /**
     * 方法描述：获取实例
     *
     * @return
     * @author: why
     * @time: 2013-10-21 下午2:52:44
     */
    public static EReaderApplication getInstance() {
        return instance;
    }

    /**
     * 获取当前客户端版本信息
     */
    private void getCurrentVersion() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            curVersionName = info.versionName;
            curVersionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
    }

    public SubCategoryResp getCategroy() {
        return AppSharedPref.getInstance(this).getCategroy();
    }

    public void saveCategroy(SubCategoryResp sub) {
        AppSharedPref.getInstance(this).saveCategroy(sub);
    }


    /**
     * 方法描述：通用的方法
     *
     * @param key
     * @author: ghf
     * @time: 2015-6-8 下午7:03:35
     */
    public void saveLocalInfoByKeyValue(String key, String value) {
        AppSharedPref.getInstance(this).saveLocalInfoByKeyValue(key, value);
    }

    /**
     * 方法描述：通用的方法
     *
     * @param key
     * @author: ghf
     * @time: 2015-6-8 下午7:03:35
     */
    public String getLocalInfoByKeyValue(String key) {
        return AppSharedPref.getInstance(this).getLocalInfoByKeyValue(key);
    }


    public void saveLogin(Login data) {
        AppSharedPref.getInstance(this).saveLogin(data);
    }

    public Login getLogin() {
        return AppSharedPref.getInstance(this).getLogin();
    }

    public void saveBuyCar(BookOnlyResp resp) {
        AppSharedPref.getInstance(this).saveBuyCar(resp);

    }

    public BookOnlyResp getBuyCar() {
        return AppSharedPref.getInstance(this).getBuyCar();
    }


    //sky
    public String message = "We are the world.";
    public ArrayList<BookInformation> bis;
    public ArrayList<CustomFont> customFonts = new ArrayList<CustomFont>();
    public SkySetting setting;
    public SkyDatabase sd = null;
    public int sortType=0;
    //阅读的初始化
    public void initReadSettings(){
        sd = new SkyDatabase(this);
        reloadBookInformations();
        loadSetting();
    }

    public void reloadBookInformations() {
        this.bis = sd.fetchBookInformations(sortType,"");
    }

    public void reloadBookInformations(String key) {
        this.bis = sd.fetchBookInformations(sortType,key);
    }

    public void loadSetting() {
        this.setting = sd.fetchSetting();
    }

    public void saveSetting() {
        sd.updateSetting(this.setting);
    }

    public void openEpub(Context context,BookInformation bi){
        initReadSettings();
        if (!bi.isDownloaded) {
            return;
        }
        Intent intent;
        if (!bi.isFixedLayout) {
            intent = new Intent(context,BookViewActivity.class);
        }else {
            intent = new Intent(context,MagazineActivity.class);
        }
        intent.putExtra("BOOKCODE",bi.bookCode);//bi.bookCode
        intent.putExtra("TITLE",bi.title);
        intent.putExtra("AUTHOR", bi.creator);
        intent.putExtra("BOOKNAME",bi.fileName);
        if (bi.isRTL && !bi.isRead) {
            intent.putExtra("POSITION",(double)1);
        }else {
            intent.putExtra("POSITION",bi.position);
        }
        intent.putExtra("THEMEINDEX",setting.theme);//app.setting.theme
        intent.putExtra("DOUBLEPAGED",setting.doublePaged);//app.setting.doublePaged
        intent.putExtra("transitionType",setting.transitionType);//app.setting.transitionType
        intent.putExtra("GLOBALPAGINATION",setting.globalPagination);//app.setting.globalPagination
        intent.putExtra("RTL",bi.isRTL);
        intent.putExtra("VERTICALWRITING",bi.isVerticalWriting);

        intent.putExtra("SPREAD", bi.spread);
        intent.putExtra("ORIENTATION", bi.orientation);

        context.startActivity(intent);
    }

    public boolean isBookDownloaded(String url) {
        int bookCode = getBookCodeByURL(url);
        if (bookCode==-1) return false;

        for (int i=0; i<bis.size(); i++) {
            BookInformation bi = bis.get(i);
            if (bi.bookCode==bookCode) {
                return bi.isDownloaded;
            }
        }
        return false;
    }

    public synchronized void installBook(String url,int code) {
        LogUtil.Log("instalBook start");
        int bookCode = code;
        try {
            if (isBookDownloaded(url)) return;
            String extension = SkyUtility.getFileExtension(url);
            if (!extension.contains("epub")) return;
            String pureName = SkyUtility.getPureName(url);//名字
            LogUtil.Log("instalBook starts real");

            sd.insertEmptyBook(url,"","","",0);
//            String targetName = sd.getFileNameByBookCode(bookCode);
            LogUtil.Log("copy Book from downloads to books");
            copyBookToDevice(url,pureName);

            BookInformation bi;
            String coverPath = sd.getCoverPathByBookCode(bookCode);
            String baseDirectory = SkySetting.getStorageDirectory() + "/books";

            bi = getBookInformation(pureName, baseDirectory, coverPath);
            bi.bookCode = bookCode;
            bi.title = pureName;
            bi.fileSize = -1;
            bi.downSize = -1;
            bi.isDownloaded = true;
            final BookInformation tbi = bi;
            sd.updateBook(bi);
            LogUtil.Log("instalBook ends");
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    reloadBookInformations();//重新获取数据
                }
            },500);
        }catch(Exception e) {
            LogUtil.LogError("e-installBook",e.getMessage());
        }
    }
    public int getBookCodeByURL(String url) {
        for (int i=0; i<bis.size(); i++) {
            BookInformation bi = bis.get(i);
            if (bi.url.equalsIgnoreCase(url)) return bi.bookCode;
            if (bi.url.contains(url)) return bi.bookCode;
            if (url.contains(bi.url)) return bi.bookCode;
        }
        return -1;
    }

    public BookInformation getBookInformation(String fileName,String baseDirectory,String coverPath) {
        LogUtil.Log(fileName);

        BookInformation bi = new BookInformation();
        // SkyProvider is the default epub file handler since 5.0.
        SkyProvider skyProvider = new SkyProvider();
        bi = new BookInformation();
        bi.setFileName(fileName);
        bi.setBaseDirectory(baseDirectory);
        bi.setContentProvider(skyProvider);
        File coverFile = new File(coverPath);
        if (!coverFile.exists()) bi.setCoverPath(coverPath);
        skyProvider.setBook(bi.getBook());
//        skyProvider.setKeyListener(new KeyDelegate());
        bi.makeInformation();
        return bi;
    }

    /**
    * @param targetName 目标的名字
     * @param  filePath 源路径
    * */
    public synchronized void copyBookToDevice(String filePath,String targetName) {
        try {
            InputStream localInputStream = null;

//            if (filePath.contains("asset")) {
//                String fileName = SkyUtility.getFileName(filePath);
//                localInputStream = this.getAssets().open("books/"+fileName);
//            }else {
//                localInputStream = new FileInputStream(filePath);
//            }
            localInputStream = new FileInputStream(filePath);
            String bookDir = SkySetting.getStorageDirectory() + "/books";
            String path = bookDir+"/"+targetName;
            FileOutputStream localFileOutputStream = new FileOutputStream(path);
            byte[] arrayOfByte = new byte[1024];
            int offset;
            while ((offset = localInputStream.read(arrayOfByte))>0)
            {
                localFileOutputStream.write(arrayOfByte, 0, offset);
            }
            localFileOutputStream.flush();
            localFileOutputStream.close();
            localInputStream.close();
        }catch (FileNotFoundException fileNotFoundE){
            LogUtil.LogError("copyBookToDevice-FileNotFoundException",fileNotFoundE.toString());
            return;
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
            return;
        }
    }


    public int isExistByCodeId(List<BookInformation> bis,int code){
        int result=-1;
        for (int i = 0; i <bis.size() ; i++) {
            if(code==bis.get(i).bookCode);
            return i;
        }
        return result;
    }
}
