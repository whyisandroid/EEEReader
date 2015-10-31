package com.ereader.client;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.ereader.client.entities.Book;
import com.ereader.client.entities.Login;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.entities.json.SubCategoryResp;
import com.ereader.client.ui.bookshelf.epubread.CustomFont;
import com.ereader.client.ui.bookshelf.epubread.SkyDatabase;
import com.ereader.client.ui.bookshelf.epubread.SkySetting;
import com.ereader.client.ui.bookshelf.epubread.SkyUtility;
import com.ereader.common.net.AppSocketInterface;
import com.ereader.common.net.XUtilsSocketImpl;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.skytree.epub.BookInformation;

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
}
