package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ereader.client.R;
import com.ereader.client.entities.BookShow;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ShelfSearchAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<BookShow> mList;

    private boolean isShowDelete=false;


    private ImageLoader imageLoader = null;
    private ImageLoaderConfiguration configuration = null;
    private DisplayImageOptions options;

    public ShelfSearchAdapter(Context context, List<BookShow> list) {
        this.mContext = context;
        mList = list;

        inflater = LayoutInflater.from(mContext);
        this.imageLoader = ImageLoader.getInstance();
        getImageOptions();
        if (!this.imageLoader.isInited()) {
            this.imageLoader.init(configuration);
        }
    }

    public void setData(List<BookShow> list) {
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
    public BookShow getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        BookShow book = (BookShow) getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_shelf_item, null);
            holder = new ViewHolder();
            holder.findView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(book.getCover_front_url(), holder.iv_book_shelf, options);
        if (isShowDelete) {
            holder.iv_book_delete.setVisibility(View.VISIBLE);
        } else {
            holder.iv_book_delete.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_book_shelf;

        private ImageView iv_book_delete;

        private ImageView iv_book_status;

        public void findView(View view) {
            iv_book_shelf = (ImageView) view.findViewById(R.id.iv_book_shelf);
            iv_book_delete = (ImageView) view.findViewById(R.id.book_delete);
            iv_book_status = (ImageView) view.findViewById(R.id.book_status);

        }
    }

}
