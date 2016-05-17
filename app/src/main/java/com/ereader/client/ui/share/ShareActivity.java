package com.ereader.client.ui.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ereader.client.R;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ToastUtil;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by ghf on 16/3/10.
 *  @see this.initPlatformConfig()
 */
public class ShareActivity extends Activity implements View.OnClickListener{
    private TextView mShareWechant,mShareWechantC,mShareQQ,mShareQQC,mShareSina;
    UMImage image;
    ShareParams shareParam;

    private ImageView mBtnTopRight;
    public  static String SHARE_KEY="share.data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        setFinishOnTouchOutside(false);//
        initPlatformConfig();
        init();
        getExtentIntentData();

    }
    //分享
    public static void share(Context c,ShareParams p){
        Bundle b=new Bundle();
        b.putSerializable(SHARE_KEY, p);
        IntentUtil.intent(c, b, ShareActivity.class, false);
    }
    //分享
    public static void share(Context c,String title,String content,String shareUrl,String imageUrl){

        ShareParams shareParams=new ShareParams();
        shareParams.setTitle(title);
        shareParams.setContent(content);
        shareParams.setShareUrl(shareUrl);
        shareParams.setImageUrl(imageUrl);
        Bundle b=new Bundle();
        b.putSerializable(SHARE_KEY,shareParams);
        IntentUtil.intent(c, b, ShareActivity.class, false);

    }
    //分享
    /**
     * @param isLocalFlag 图片链接是否为本地？：true是；false否
     * */
    public static void share(Context c,String title,String content,String shareUrl,String imageUrl,Boolean isLocalFlag){

        ShareParams shareParams=new ShareParams();
        shareParams.setTitle(title);
        shareParams.setContent(content);
        shareParams.setShareUrl(shareUrl);
        shareParams.setImageUrl(imageUrl);
        shareParams.setIsLocalImage(isLocalFlag);
        Bundle b=new Bundle();
        b.putSerializable(SHARE_KEY,shareParams);
        IntentUtil.intent(c, b, ShareActivity.class, false);

    }

    private void getExtentIntentData() {

        Intent it=getIntent();
        if(null!=it&&null!=it.getExtras()){
            shareParam=(ShareParams)it.getExtras().getSerializable(SHARE_KEY);
        }else{
            LogUtil.LogError("shareParam","null:无数据");
        }
        if(null!=shareParam&& !TextUtils.isEmpty(shareParam.getImageUrl())){
            if(shareParam.isLocalImage()){
                Bitmap bitmap = BitmapFactory.decodeFile(shareParam.getImageUrl());
                if(null==bitmap){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                }
                image = new UMImage(ShareActivity.this, bitmap);
//                image = new UMImage(ShareActivity.this, shareParam.getImageUrl());
            }else{
                image = new UMImage(ShareActivity.this, shareParam.getImageUrl());
            }

        }else{
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            image = new UMImage(ShareActivity.this, bitmap);
        }
//
    }

    //初始化配置
    private void initPlatformConfig(){
        //微信
        PlatformConfig.setWeixin("wx4405ef9cbc57cf6d", "3fb604561cd23f4a87ce02e6a2cf0b18");
        //新浪微博
        PlatformConfig.setSinaWeibo("588777842", "4582c6ac64e47fb2c28ee594cfdf1bc7");
        //qq
        PlatformConfig.setQQZone("1104687383", "2YnGZLzwfKE4yArM");

    }

    private void init(){

        mBtnTopRight=(ImageView)findViewById(R.id.share_top_right);
        mBtnTopRight.setVisibility(View.VISIBLE);
        mBtnTopRight.setOnClickListener(this);

        ((TextView) findViewById(R.id.share_top_title)).setText("分享");
        mShareWechant = (TextView) findViewById(R.id.share_wechant);
        mShareWechantC= (TextView) findViewById(R.id.share_wechant_c);
        mShareQQ= (TextView) findViewById(R.id.share_qq);
        mShareQQC= (TextView) findViewById(R.id.share_qq_c);
        mShareSina= (TextView) findViewById(R.id.share_sina);

        mShareWechant.setOnClickListener(this);
        mShareWechantC.setOnClickListener(this);
        mShareQQ.setOnClickListener(this);
        mShareQQC.setOnClickListener(this);
        mShareSina.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(null==shareParam){
            ToastUtil.showToast(ShareActivity.this,"分享无数据！",Toast.LENGTH_SHORT);
            return;
        }
        switch (v.getId()){
            case R.id.share_top_right:
                ShareActivity.this.finish();
                break;
            case R.id.share_wechant:
                new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withMedia(image)
                        .withText(shareParam.getContent())
                        .withTitle(shareParam.getTitle())
                        .withTargetUrl(shareParam.getShareUrl())
                                //.withMedia(new UMEmoji(ShareActivity.this,"http://img.newyx.net/news_img/201306/20/1371714170_1812223777.gif"))
                        .share();

                break;
            case R.id.share_wechant_c:
                new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                        .withText(shareParam.getContent())
                        .withTitle(shareParam.getTitle())
                        .withMedia(image)
                        .withTargetUrl(shareParam.getShareUrl())
                        .share();
                break;
            case R.id.share_qq:
                new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                        .withText(shareParam.getContent())
                        .withMedia(image)
                        .withTitle(shareParam.getTitle())
                        .withTargetUrl(shareParam.getShareUrl())
                        .share();

                break;
            case R.id.share_qq_c:
                new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
                        .withTitle(shareParam.getTitle())
                        .withText(shareParam.getContent())
                        .withTargetUrl(shareParam.getShareUrl())
                        .withMedia(image)
                        .share();
                break;
            case R.id.share_sina:
                /** shareaction need setplatform , callbacklistener,and content(text,image).then share it **/
                new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                        .withTitle(shareParam.getTitle())
                        .withText(shareParam.getContent())
                        .withTargetUrl(shareParam.getShareUrl())
                        .withMedia(image)
                        .share();

                break;
            default:
                break;
        }
    }
    //回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
//            Toast.makeText(ShareActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

//            Toast.makeText(ShareActivity.this,platform + " 分享失败啦"+t.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(ShareActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(ShareActivity.this).onActivityResult(requestCode, resultCode, data);
    }


}
