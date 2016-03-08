package com.ereader.client.ui.more;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.view.MyWebview;
import com.ereader.common.net.NetWorkHelper;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ProgressDialogUtil;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2016/3/8 16:58
 ***************************************/
public class WebActivity extends BaseActivity implements View.OnClickListener {
    private AppController controller;

    private MyWebview mWebViewShow;

    private String mUrl;
    private int mCacheMode;
    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_webview);
        controller = AppController.getController(this);
        findView();
        initView();
    }

    /**
     *
     * 方法描述：FindView
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void findView() {
        mWebViewShow = (MyWebview)findViewById(R.id.web_book);
    }

    /**
     *
     * 方法描述：初始化 View
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {
        String title = getIntent().getExtras().getString("title");
        ((TextView) findViewById(R.id.tv_main_top_title)).setText(title);

        if (NetWorkHelper.isNetworkAvailable(this)) {//network is available
            mCacheMode = WebSettings.LOAD_NO_CACHE;
        } else {//network is not available
            mCacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK;
        }

         mUrl =  getIntent().getExtras().getString("url");
        //mUrl = "www.baidu.com";
        initWebView();
    }

    /**
     * 方法描述：TODO
     *
     * @author: why
     * @time: 2014-10-27 上午11:03:52
     */
    private void initWebView() {
        mWebViewShow.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(WebActivity.this, R.string.web_view_not_find_browser, Toast
                            .LENGTH_SHORT).show();
                }

            }
        });
        if (!TextUtils.isEmpty(mUrl)) {
            loadUrl(mUrl);
        }
    }


    private void loadUrl(String url) {
            mWebViewShow.clearView();
            mWebViewShow.setVisibility(View.VISIBLE);
            initWebViewSetting(mWebViewShow);
            mWebViewShow.loadUrl(url);
            mWebViewShow.setWebViewClient(new WebViewClient() {

                @Override
                public void onReceivedError(WebView view, int errorCode, String description,
                                            String failingUrl) {
                    LogUtil.Log("onReceivedError---------");
                    view.stopLoading();
                    view.clearView();
                    ProgressDialogUtil.closeProgressDialog();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    LogUtil.Log("shouldOverrideUrlLoading---------");
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    LogUtil.Log("onPageStarted---------");
                    super.onPageStarted(view, url, favicon);
                    if (!WebActivity.this.isFinishing()) {
                        ProgressDialogUtil.showProgressDialog(WebActivity.this, R.string
                                .pull_to_refresh_footer_refreshing_label, true);
                    }
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    LogUtil.Log("onPageFinished---------" + url);
                    ProgressDialogUtil.closeProgressDialog();
                }
            });
    }

    private void initWebViewSetting(MyWebview webView) {

        WebSettings mWebSettings = webView.getSettings();
        // 有图 or 无图
        mWebSettings.setLoadsImagesAutomatically(true);
        // 弹窗
        mWebSettings.setSupportMultipleWindows(false);
        // 设置JavaScript有效性
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 开启 HTML5 Web Storage
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        // String databasePath = getContext().getDir("database",
        // Context.MODE_PRIVATE).getPath();
        // mWebSettings.setDatabasePath(databasePath);
        // 支持使用插件
        // mWebSettings.setPluginsEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        // 2.2以上版本支持
        // if(android.os.Build.VERSION.SDK >
        // android.os.Build.VERSION_CODES.ECLAIR_MR1){
        // mWebSettings.setPluginState(PluginState.ON);
        // }
        mWebSettings.setNeedInitialFocus(true);
        // mWebSettings.setUserAgentString(WebSharedPref.USERAGENT);
        // 设置是否保存密码
        mWebSettings.setSavePassword(true);
        mWebSettings.setSaveFormData(true);
        // 保存cookies
        // CookieManager.getInstance().setAcceptCookie(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        // 配合概览模式参数，如果不设置，概览模式将不起作用
        mWebSettings.setUseWideViewPort(true);
        // 设置为概览模式
        mWebSettings.setLoadWithOverviewMode(true);
        // 缓存模式
        mWebSettings.setCacheMode(mCacheMode);
        // if(NetWorkHelper.isNetworkAvailable(ApplyMainFragment.this.getActivity())){
        // mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // }else{
        // mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // }
        // mWebSettings.setUseWideViewPort(true);

        //chrome browser
        webView.setWebChromeClient(new WebChromeClient());
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textView1:
                break;
            default:
                break;
        }
    }
}
