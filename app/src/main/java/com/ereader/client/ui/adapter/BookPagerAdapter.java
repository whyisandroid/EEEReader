package com.ereader.client.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.common.util.LogUtil;
import com.ereader.reader.activity.ReaderActivity;
import com.ereader.reader.model.StoreBook;

import java.util.List;
import java.util.WeakHashMap;

public class BookPagerAdapter extends PagerAdapter {

	private Context context;
	private List<StoreBook> list;
	private LayoutInflater inflater;
	WeakHashMap<Object, Bitmap> mCovers = new WeakHashMap<Object, Bitmap>();
//	private ImageLoader imageLoader = null;
//	private ImageLoaderConfiguration configuration = null;

//	private DisplayImageOptions options;
	public BookPagerAdapter(Context context, List<StoreBook> list) {
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
//		this.imageLoader = ImageLoader.getInstance();
//		getImageOptions();
//		if (!this.imageLoader.isInited()) {
//			this.imageLoader.init(configuration);
//		}
	}

	/**
	 * 
	 * 方法描述：设置imageloader的options
	 * 
	 * @author: qm
	 * @time: 2014-12-5 上午11:54:04
	 */
	@SuppressWarnings("deprecation")
	private void getImageOptions() {
		// TODO Auto-generated method stub
//		this.options = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.b1_03) // 设置图片下载期间显示的图片
//				.showImageForEmptyUri(R.drawable.b1_03) // 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.b1_03) // 设置图片加载或解码过程中发生错误显示的图片
//				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//				.build();
//
//		this.configuration = new ImageLoaderConfiguration.Builder(context)
//				.threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
//				.memoryCache(new WeakMemoryCache())
//				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//				.build();
	}

	@Override
	public int getCount() {

		return (list.size()+1)/2;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup view, final int position) {
		View imageLayout = inflater.inflate(R.layout.book_shelf_pager_item, view,false);
		int index_left=position*2;
		int index_right=position*2+1;
		LogUtil.LogError("", "position=" + position + ";total_size=" + list.size() + ";index_right=" + index_right);
		//1
		final StoreBook book_left=list.get(index_left);
		final TextView textView1= (TextView) imageLayout.findViewById(R.id.textView1);
		if(!TextUtils.isEmpty(book_left.cover)){
			LogUtil.LogError("book_left.cover",book_left.cover );
			final ImageView imageView1= (ImageView) imageLayout.findViewById(R.id.imageView1);
//			imageLoader.displayImage(book_left.cover, imageView1, options);
			setCover(imageView1,book_left.cover);
		}


		final RelativeLayout rl_index1=(RelativeLayout)imageLayout.findViewById(R.id.rl_index1);
		rl_index1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openBookViewer(book_left);
			}
		});


		//2

		RelativeLayout rl_index2=(RelativeLayout)imageLayout.findViewById(R.id.rl_index2);
		if(index_right<=list.size()){
			final StoreBook book_right=list.get(index_left);
			if(!TextUtils.isEmpty(book_left.cover)){
				final ImageView imageView2= (ImageView) imageLayout.findViewById(R.id.imageView2);
//				imageLoader.displayImage(book_right.cover, imageView2, options);
				setCover(imageView2,book_right.cover);
			}

			rl_index2.setVisibility(View.VISIBLE);
			rl_index2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openBookViewer(book_right);
				}
			});
		}else{
			rl_index2.setVisibility(View.INVISIBLE);
		}

		((ViewPager) view).addView(imageLayout, 0);
		return imageLayout;
	}

	//打开书
	private void openBookViewer(StoreBook book){
		Intent intent = new Intent(context, ReaderActivity.class);
		intent.putExtra("storeBook", book);
		context.startActivity(intent);
	}


	private void setCover(ImageView view,String cover){
		Bitmap bmp = null;
		if (cover != null) {
			bmp = mCovers.get(cover);
			if ( bmp == null) {
				bmp = BitmapFactory.decodeFile(cover);
			}
		}
		if (bmp != null) {
			view.setImageBitmap(bmp);

		} else {
			view.setImageResource(R.drawable.b1_03);
		}


	}
}
