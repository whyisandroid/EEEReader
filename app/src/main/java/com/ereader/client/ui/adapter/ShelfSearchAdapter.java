package com.ereader.client.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ereader.client.R;
import com.ereader.client.entities.BookShowWithDownloadInfo;
import com.ereader.client.service.AppController;
import com.ereader.client.service.download.DownloadInfo;
import com.ereader.client.service.download.DownloadManager;
import com.ereader.common.constant.Constant;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ToastUtil;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

public class ShelfSearchAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<BookShowWithDownloadInfo> mList;

    private boolean isShowDelete = false;


    private ImageLoader imageLoader = null;
    private ImageLoaderConfiguration configuration = null;
    private DisplayImageOptions options;
    private DownloadManager downloadManager;

    public ShelfSearchAdapter(Context context, List<BookShowWithDownloadInfo> list,DownloadManager downloadManager) {
        this.mContext = context;
        mList = list;
        this.downloadManager=downloadManager;
        inflater = LayoutInflater.from(mContext);
        this.imageLoader = ImageLoader.getInstance();
        getImageOptions();
        if (!this.imageLoader.isInited()) {
            this.imageLoader.init(configuration);
        }
    }

    //开始下载
    public void startDown( AppController controller, int position) {
        BookShowWithDownloadInfo book = mList.get(position);
        controller.getContext().addBusinessData("download.book_id", book.getBook_id());
        String target = Constant.DOWNLOAD + book.getName() + Constant.SUFFIX_EPUB;
        String downUrl = controller.getDownUrl();
        if (!TextUtils.isEmpty(downUrl)) {
            try {
                DownloadRequestCallBack callback =new DownloadRequestCallBack();
                int bookiId=Integer.parseInt(book.getBook_id());
                downloadManager.addNewDownload(bookiId,//book_id
                        downUrl,//下载的URL
                        book.getName(),//文件名字
                        target,
                        true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                        false, //如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                        callback);
                DownloadInfo info=downloadManager.getDownloadInfobyBookId(bookiId);

                if(null!=info){
                    book.setDownloadInfo(info);
                    notifyDataSetChanged();
                }else{
                    LogUtil.LogError("下载数据","下载未访问到数据");
                }
            } catch (DbException e) {
                LogUtil.LogError("下载－DbException", e.toString());
            } catch (Exception ee){
                LogUtil.LogError("下载－ee", ee.toString());
            }
        }
    }
    //停止下载
    public void stopDownload(int position){
        BookShowWithDownloadInfo book = mList.get(position);
        DownloadInfo down=book.getDownloadInfo();
        if(null!=down){
            HttpHandler.State state = down.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                case LOADING:
                    try {
                        downloadManager.stopDownload(down);
                    } catch (DbException e) {
                        LogUtil.LogError("DbException-stopDownload", e.getMessage());
                    }
                    book.setDownloadInfo(down);
                    notifyDataSetChanged();
                    break;
                case CANCELLED:
                case FAILURE:
                    try {
                        downloadManager.resumeDownload(down, new DownloadRequestCallBack());
                    } catch (DbException e) {
                        LogUtil.LogError("DbException-stopDownload", e.getMessage());
                    }
                    book.setDownloadInfo(down);
                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }
    //移除
    public void remove(BookShowWithDownloadInfo s) {
        try {
            if(null!=s.getDownloadInfo()){
                downloadManager.removeDownload(s.getDownloadInfo());
            }
            mList.remove(s);
           notifyDataSetChanged();
        } catch (DbException e) {
            LogUtil.LogError("remove-DbException", e.getMessage());
        }
    }
//    //更新
//    public void update(DownloadInfo downloadInfo) {
//        this.downloadInfo = downloadInfo;
//        refresh();
//    }
//
//    public void refresh() {
//
//    }

    public void setDownloadStatusNById(int position, boolean status) {
        mList.get(position).setIsDownloading(status);
        notifyDataSetChanged();

    }

    public void deleteByPostion(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void setData(List<BookShowWithDownloadInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public boolean isShowDelete() {
        return isShowDelete;
    }

    public void setIsShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation")
    private void getImageOptions() {
        // TODO Auto-generated method stub
        this.options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.b1_03) // 设置图片下载期间显示的图片
                        //	.showImageForEmptyUri(R.drawable.community_bag) // 设置图片Uri为空或是错误的时候显示的图片
                        //	.showImageOnFail(R.drawable.community_bag) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();

        this.configuration = new ImageLoaderConfiguration.Builder(mContext)
                .threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public BookShowWithDownloadInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        BookShowWithDownloadInfo book = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_shelf_item, null);
            holder = new ViewHolder(book);
            holder.findView(convertView);
            convertView.setTag(holder);
            holder.refresh();
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.update(book);
        }
        imageLoader.displayImage(book.getCover_front_url(), holder.iv_book_shelf, options);
        if (isShowDelete) {
            holder.iv_book_delete.setVisibility(View.VISIBLE);
        } else {
            holder.iv_book_delete.setVisibility(View.GONE);
        }
        if (book.isDownloaded()) {
            holder.iv_book_status_ok.setVisibility(View.VISIBLE);
        } else{
            holder.iv_book_status_ok.setVisibility(View.GONE);
            if (book.isDownloading()) {
                holder.ll_item.setVisibility(View.VISIBLE);
            } else {
                holder.ll_item.setVisibility(View.GONE);
            }
        }
        if(null!=book.getDownloadInfo()){
            HttpHandler<File> handler = book.getDownloadInfo().getHandler();
            if (handler != null) {
                RequestCallBack callBack = handler.getRequestCallBack();
                if (callBack instanceof DownloadManager.ManagerCallBack) {
                    DownloadManager.ManagerCallBack managerCallBack = (DownloadManager.ManagerCallBack) callBack;
                    if (managerCallBack.getBaseCallBack() == null) {
                        managerCallBack.setBaseCallBack(new DownloadRequestCallBack());
                    }
                }
                callBack.setUserTag(new WeakReference<ViewHolder>(holder));
            }
        }


        return convertView;
    }

    class ViewHolder {
        private ImageView iv_book_shelf;

        private ImageView iv_book_delete;//取消下载

        private ImageView iv_book_status_ing;

        private ImageView iv_book_status_ok;//状态：下载完成

        private RelativeLayout ll_item;

        private ProgressBar progressBar;//进度条

        private BookShowWithDownloadInfo book;//
        private DownloadInfo downInfo;

        public ViewHolder (BookShowWithDownloadInfo mBook){
            this.book=mBook;
            this.downInfo=book.getDownloadInfo();
        }

        public void findView(View view) {
            iv_book_shelf = (ImageView) view.findViewById(R.id.iv_book_shelf);
            iv_book_delete = (ImageView) view.findViewById(R.id.book_delete);
            iv_book_status_ing = (ImageView) view.findViewById(R.id.book_status);
            iv_book_status_ok = (ImageView) view.findViewById(R.id.book_status_ok);
            ll_item = (RelativeLayout) view.findViewById(R.id.ll_item);
            progressBar=(ProgressBar)view.findViewById(R.id.download_pb);
        }
        public void update(BookShowWithDownloadInfo mBook){
            this.book=mBook;
            this.downInfo=book.getDownloadInfo();
            refresh();
        }
        public void refresh() {

            if(null!=downInfo){
                HttpHandler.State state = downInfo.getState();
                if(null!=state){
                    switch (state) {
                        case WAITING:
                        case STARTED:
                        case LOADING:
                            book.setIsDownloaded(true);
                            iv_book_status_ok.setVisibility(View.GONE);
                            ll_item.setVisibility(View.VISIBLE);
                            if (downInfo.getFileLength() > 0) {
                                progressBar.setProgress((int) (downInfo.getProgress() * 100 / downInfo.getFileLength()));
                            } else {
                                progressBar.setProgress(0);
                            }
                            break;
                        case CANCELLED:
                            book.setIsDownloaded(false);
                            book.setIsDownloading(false);
                            iv_book_status_ok.setVisibility(View.GONE);
                            ll_item.setVisibility(View.GONE);
                            break;
                        case SUCCESS:
                            book.setIsDownloaded(true);
                            iv_book_status_ok.setVisibility(View.VISIBLE);
                            ll_item.setVisibility(View.GONE);
                            break;
                        case FAILURE:
                            book.setIsDownloaded(false);
                            book.setIsDownloading(false);
                            iv_book_status_ok.setVisibility(View.GONE);
                            ll_item.setVisibility(View.GONE);
                            LogUtil.LogError("debug", "debug:下载图书<" + book.name + ">失败！");
                            break;
                        default:
                            iv_book_status_ok.setVisibility(View.GONE);
                            ll_item.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        }
    }

    private class DownloadRequestCallBack extends RequestCallBack<File> {

        @SuppressWarnings("unchecked")
        private void refreshListItem() {
            if (userTag == null) return;
            WeakReference<ViewHolder> tag = (WeakReference<ViewHolder>) userTag;
            ViewHolder holder = tag.get();
            if (holder != null) {
                holder.refresh();
            }
        }

        @Override
        public void onStart() {
           ToastUtil.showToast(mContext, "开始下载", ToastUtil.LENGTH_SHORT);
            refreshListItem();
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {
            //进度条在这里处理
            refreshListItem();
        }

        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
            //ToastUtil.showToast(mContext, "debug:下载成功", ToastUtil.LENGTH_SHORT);
            refreshListItem();
        }

        @Override
        public void onFailure(HttpException error, String msg) {
//            ToastUtil.showToast(mContext,"debug:下载失败～原因:"+msg,ToastUtil.LENGTH_SHORT);
            LogUtil.LogError("error","debug:下载失败～原因:"+msg);
            refreshListItem();
        }

        @Override
        public void onCancelled() {
//            ToastUtil.showToast(mContext,"取消下载",ToastUtil.LENGTH_SHORT);
            refreshListItem();
        }
    }


}
