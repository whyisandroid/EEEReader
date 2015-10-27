package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.ui.adapter.BookAdapter.ViewHolder;
import com.ereader.client.ui.bookshelf.ReadActivity;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BookPagerAdapter extends PagerAdapter {

	private Context context;
	private List<String> list;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = null;
	private ImageLoaderConfiguration configuration = null;
	private DisplayImageOptions options;
	public BookPagerAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = ImageLoader.getInstance();
		getImageOptions();
		if (!this.imageLoader.isInited()) {
			this.imageLoader.init(configuration);
		}
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
		this.options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.b1_03) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.b1_03) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.b1_03) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.build();

		this.configuration = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache())
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup view, final int position) {
		View imageLayout = inflater.inflate(R.layout.book_shelf_pager_item, view,false);
		final TextView textView1= (TextView) imageLayout.findViewById(R.id.textView1);
		final RelativeLayout rl_index1=(RelativeLayout)imageLayout.findViewById(R.id.rl_index1);
		RelativeLayout rl_index2=(RelativeLayout)imageLayout.findViewById(R.id.rl_index2);
		rl_index1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(context, ReadActivity.class);
				//getResources().openRawResource(R.raw.book0);
				String path = context.getFilesDir().getAbsolutePath() + "/book.epub";
				//(String) listItem.get(0).get("path");
//           ToastUtil.showToast(mContext, "position=" + position + ";path=" + path, ToastUtil.LENGTH_LONG);
				//it.putExtra("aaa", path);getString(R.string.bpath)
				it.putExtra(context.getString(R.string.bpath), path);
				context.startActivity(it);
			}
		});
		rl_index2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(context, ReadActivity.class);
				//getResources().openRawResource(R.raw.book0);
				String path = context.getFilesDir().getAbsolutePath() + "/book.epub";
				//(String) listItem.get(0).get("path");
//           ToastUtil.showToast(mContext, "position=" + position + ";path=" + path, ToastUtil.LENGTH_LONG);
				//it.putExtra("aaa", path);getString(R.string.bpath)
				it.putExtra(context.getString(R.string.bpath), path);
				context.startActivity(it);
			}
		});
		((ViewPager) view).addView(imageLayout, 0);
		return imageLayout;
	}
}
