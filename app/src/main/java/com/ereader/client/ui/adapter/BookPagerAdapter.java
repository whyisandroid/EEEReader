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

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.ui.adapter.BookAdapter.ViewHolder;
import com.ereader.client.ui.bookshelf.ReadActivity;
import com.ereader.client.ui.bookshelf.epubread.BookViewActivity;
import com.ereader.client.ui.bookshelf.epubread.MagazineActivity;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.skytree.epub.BookInformation;
import com.skytree.epub.SkyProvider;

public class BookPagerAdapter extends PagerAdapter {

	private Context context;
	private List<String> list;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = null;
	private ImageLoaderConfiguration configuration = null;

	private EReaderApplication app;
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
		app=EReaderApplication.getInstance();
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
				String path = context.getFilesDir().getAbsolutePath() + "/book.epub";
				it.putExtra(context.getString(R.string.bpath), path);
				context.startActivity(it);
			}
		});
		rl_index2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Intent it = new Intent();
				it.setClass(context, ReadActivity.class);
				//getResources().openRawResource(R.raw.book0);
				String path = context.getFilesDir().getAbsolutePath() + "/book.epub";
				//(String) listItem.get(0).get("path");
//           ToastUtil.showToast(mContext, "position=" + position + ";path=" + path, ToastUtil.LENGTH_LONG);
				//it.putExtra("aaa", path);getString(R.string.bpath)
				it.putExtra(context.getString(R.string.bpath), path);
				context.startActivity(it);*/
				openBookViewer();
			}
		});
		((ViewPager) view).addView(imageLayout, 0);
		return imageLayout;
	}

	//打开书
	private void openBookViewer(){
		BookInformation bi=new BookInformation("book.epub",context.getFilesDir().getAbsolutePath(),new SkyProvider());
		bi.isFixedLayout=false;
		bi.isDownloaded=true;
		bi.code=1;
		app.sd.updateBook(bi);
		if (!bi.isDownloaded) return;
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
		intent.putExtra("THEMEINDEX",app.setting.theme);//app.setting.theme
		intent.putExtra("DOUBLEPAGED",app.setting.doublePaged);//app.setting.doublePaged
		intent.putExtra("transitionType",app.setting.transitionType);//app.setting.transitionType
		intent.putExtra("GLOBALPAGINATION",app.setting.globalPagination);//app.setting.globalPagination
		intent.putExtra("RTL",bi.isRTL);
		intent.putExtra("VERTICALWRITING",bi.isVerticalWriting);

		intent.putExtra("SPREAD", bi.spread);
		intent.putExtra("ORIENTATION", bi.orientation);

		context.startActivity(intent);
	}
}
