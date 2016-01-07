package com.ereader.client.ui.adapter;

/*
public class BookPagerAdapter extends PagerAdapter {

	private Context context;
	private List<BookInformation> list;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = null;
	private ImageLoaderConfiguration configuration = null;

	private EReaderApplication app;
	private DisplayImageOptions options;
	public BookPagerAdapter(Context context, List<BookInformation> list,EReaderApplication app) {
		this.context = context;
		this.list = list;
		this.app=app;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = ImageLoader.getInstance();
		getImageOptions();
		if (!this.imageLoader.isInited()) {
			this.imageLoader.init(configuration);
		}
		BookInformation b=null;
		for (int i = 0; i <list.size() ; i++) {
			b=list.get(i);
			LogUtil.LogError(i+"-is",b.toString());
		}
	}

	*//**
	 * 
	 * 方法描述：设置imageloader的options
	 * 
	 * @author: qm
	 * @time: 2014-12-5 上午11:54:04
	 *//*
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
		int index_left=position*2;
		int index_right=position*2+1;
		LogUtil.LogError("","position="+position+";total_size="+list.size()+";index_right="+index_right);
		//1
		final BookInformation book_left=list.get(index_left);
		final TextView textView1= (TextView) imageLayout.findViewById(R.id.textView1);
		final ImageView imageView1= (ImageView) imageLayout.findViewById(R.id.imageView1);

		final RelativeLayout rl_index1=(RelativeLayout)imageLayout.findViewById(R.id.rl_index1);
		rl_index1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//
				*//*Intent it = new Intent();
				it.setClass(context, ReadActivity.class);
				String path = context.getFilesDir().getAbsolutePath() + "/book.epub";
				it.putExtra(context.getString(R.string.bpath), path);
				context.startActivity(it);*//*
				openBookViewer(book_left);
			}
		});


		//2

		RelativeLayout rl_index2=(RelativeLayout)imageLayout.findViewById(R.id.rl_index2);
		if(index_right<=list.size()){
			final BookInformation book_right=list.get(index_left);
			final ImageView imageView2= (ImageView) imageLayout.findViewById(R.id.imageView2);
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
	private void openBookViewer(BookInformation bi){

		if (SkySetting.getStorageDirectory()==null) {
			SkySetting.setStorageDirectory(Constant.ROOT_OUTPATH,Constant.FOLDER_NAME);
		}

	*//*	SkyProvider sky=new SkyProvider();
		//
		//BookInformation bi=new BookInformation("alice.epub", context.getFilesDir().getAbsolutePath().toString(),sky);
		bi=new BookInformation("book.epub", Constant.BOOKS,sky);
		bi.isFixedLayout=false;
		bi.isDownloaded=true;
		bi.code=0;

		//
		bi.setFileName("book.epub");
		bi.setBaseDirectory(Constant.DOWNLOAD);
		bi.setContentProvider(sky);
		sky.setBook(bi.getBook());
//		sky.setKeyListener(new KeyDelegate());
//		bi.makeInformation();
		//

//		app.initReadSettings();
		app.sd.insertEmptyBook("iii", "uu", "111", "222", 0);
		app.sd.updateBook(bi);*//*
		Log.e("eeeeee",bi.fileName+":::"+ SkySetting.storageDirectory+":::"+bi.source+":::"+bi.getBook().baseDirectory);
		app.openEpub(context,bi);
//		Log.e("eeeeee",bi.fileName+":::"+ SkySetting.storageDirectory);
//		if (!bi.isDownloaded) {
//			return;
//		}
//		Intent intent;
//		if (!bi.isFixedLayout) {
//			intent = new Intent(context,BookViewActivity.class);
//		}else {
//			intent = new Intent(context,MagazineActivity.class);
//		}
//		intent.putExtra("BOOKCODE",bi.bookCode);//bi.bookCode
//		intent.putExtra("TITLE",bi.title);
//		intent.putExtra("AUTHOR", bi.creator);
//		intent.putExtra("BOOKNAME",bi.fileName);
//		if (bi.isRTL && !bi.isRead) {
//			intent.putExtra("POSITION",(double)1);
//		}else {
//			intent.putExtra("POSITION",bi.position);
//		}
//		intent.putExtra("THEMEINDEX",app.setting.theme);//app.setting.theme
//		intent.putExtra("DOUBLEPAGED",app.setting.doublePaged);//app.setting.doublePaged
//		intent.putExtra("transitionType",app.setting.transitionType);//app.setting.transitionType
//		intent.putExtra("GLOBALPAGINATION",app.setting.globalPagination);//app.setting.globalPagination
//		intent.putExtra("RTL",bi.isRTL);
//		intent.putExtra("VERTICALWRITING",bi.isVerticalWriting);
//
//		intent.putExtra("SPREAD", bi.spread);
//		intent.putExtra("ORIENTATION", bi.orientation);
//
//		context.startActivity(intent);
	}
}*/
