package com.ereader.client.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ereader.client.R;
import com.ereader.client.entities.BookShowWithDownloadInfo;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class BookShelfAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<BookShowWithDownloadInfo> mList;

    private boolean isShowDelete = false;


    private ImageLoader imageLoader = null;
    private ImageLoaderConfiguration configuration = null;
    private DisplayImageOptions options;
    public BookShelfAdapter(Context mContext, List<BookShowWithDownloadInfo> list) {
        this.mContext=mContext;
        this.mList = list;

        inflater = LayoutInflater.from(mContext);
        this.imageLoader = ImageLoader.getInstance();
        getImageOptions();
        if (!this.imageLoader.isInited()) {
            this.imageLoader.init(configuration);
        }
    }
    public void setDownloadStatusNById(int position, boolean status) {
        mList.get(position).setIsDownloading(status);
        notifyDataSetChanged();
    }

    public void deleteByPostion(int position){
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
        return mList.size() + 1;
    }

    @Override
    public BookShowWithDownloadInfo getItem(int position) {
        if(position==getCount()-1){
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        BookShowWithDownloadInfo book = (BookShowWithDownloadInfo) getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_shelf_item, null);
            holder = new ViewHolder();
            holder.findView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null!=book){
            imageLoader.displayImage(book.getCover_front_url(), holder.iv_book_shelf, options);
            if (isShowDelete) {
                holder.iv_book_delete.setVisibility(View.VISIBLE);
            } else {
                holder.iv_book_delete.setVisibility(View.GONE);
            }
            if (book.isDownloaded()) {
                holder.iv_book_status_ok.setVisibility(View.VISIBLE);
            } else {
                holder.iv_book_status_ok.setVisibility(View.GONE);
                if (book.isDownloading()) {
                    holder.ll_item.setVisibility(View.VISIBLE);
                } else {
                    holder.ll_item.setVisibility(View.GONE);
                }
            }
        }
        if (position == getCount()-1) {//加号
            holder.iv_book_delete.setVisibility(View.GONE);
            holder.iv_book_status_ok.setVisibility(View.GONE);
            holder.iv_book_shelf.setImageResource(R.drawable.s3_20);
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_book_shelf;

        private ImageView iv_book_delete;

        private ImageView iv_book_status_ing;

        private ImageView iv_book_status_ok;

        private RelativeLayout ll_item;


        public void findView(View view) {
            iv_book_shelf = (ImageView) view.findViewById(R.id.iv_book_shelf);
            iv_book_delete = (ImageView) view.findViewById(R.id.book_delete);
            iv_book_status_ing = (ImageView) view.findViewById(R.id.book_status);
            iv_book_status_ok = (ImageView) view.findViewById(R.id.book_status_ok);
            ll_item = (RelativeLayout) view.findViewById(R.id.ll_item);
        }
    }

}
