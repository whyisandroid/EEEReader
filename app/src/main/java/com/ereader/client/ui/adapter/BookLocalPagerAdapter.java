package com.ereader.client.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.common.constant.Constant;
import com.ereader.common.util.ToastUtil;
import com.ereader.reader.activity.ReaderActivity;
import com.ereader.reader.model.StoreBook;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class BookLocalPagerAdapter extends PagerAdapter {

	private Context context;
	private List<Book> list;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = null;
	private ImageLoaderConfiguration configuration = null;

	private EReaderApplication app;
	private DisplayImageOptions options;

	private int size=0;
	public BookLocalPagerAdapter(Context context, List<Book> list) {
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = ImageLoader.getInstance();
		getImageOptions();
		if (!this.imageLoader.isInited()) {
			this.imageLoader.init(configuration);
		}
		size=list.size();
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

			final ImageView imageView1= (ImageView) imageLayout.findViewById(R.id.imageView1);
			final ImageView imageView2= (ImageView) imageLayout.findViewById(R.id.imageView2);
			final RelativeLayout rl_index1=(RelativeLayout)imageLayout.findViewById(R.id.rl_index1);
			RelativeLayout rl_index2=(RelativeLayout)imageLayout.findViewById(R.id.rl_index2);

//			imageLayout.findViewById(R.id.tv_recommend_read1).setVisibility(View.VISIBLE);
//			imageLayout.findViewById(R.id.tv_recommend_read2).setVisibility(View.VISIBLE);
//			LogUtil.LogError("图片地址"+position,list.get(0).getInfo().getImage_url()+";;"+list.get(1).getInfo().getImage_url());
			final Book bookLeft=position*2<size?list.get(position*2):null;
			final Book bookRight=position*2+1<size?list.get(position*2+1):null;
			if(null!=bookLeft){
				imageLoader.displayImage(bookLeft.getInfo().getImage_url(), imageView1, options);
			}else{
				imageView1.setImageResource(R.drawable.b1_03);
			}
			rl_index1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != bookLeft) {
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("detailBook", bookLeft);
//						IntentUtil.intent(context, bundle, BookDetailActivity.class, false);
						ToastUtil.showToast(context,"推荐试读～", Toast.LENGTH_SHORT);
					}

				}
			});
			if(null!=bookRight){
				imageLoader.displayImage(bookRight.getInfo().getImage_url(), imageView2, options);
			}else{
				imageView2.setImageResource(R.drawable.b1_03);
			}
			rl_index2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					if(null!=bookRight){
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("detailBook", bookRight);
//						IntentUtil.intent(context, bundle, BookDetailActivity.class, false);
						ToastUtil.showToast(context,"推荐试读～", Toast.LENGTH_SHORT);
					}
				}
			});
		if(list.size()%2==0){
			((ViewPager) view).addView(imageLayout, 0);
		}

		return imageLayout;
	}

	//打开书
	private void openBook(int position){
		//TODO 只是demo
		Intent intent = new Intent(context, ReaderActivity.class);
		StoreBook book=new StoreBook();
		book.book_id=position+10000;
		book.name="test"+position;
		book.type="epub";
		book.file=Constant.BOOKS+"book.epub";
		book.presetFile=Constant.BOOKS+"book.epub";
		intent.putExtra("storeBook", book);
		context.startActivity(intent);
	}
}
