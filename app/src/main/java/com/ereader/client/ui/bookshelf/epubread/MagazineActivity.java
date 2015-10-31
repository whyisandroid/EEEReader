package com.ereader.client.ui.bookshelf.epubread;

import java.io.File;
import java.io.FileOutputStream;

import com.ereader.client.R;
import com.skytree.epub.Book;
import com.skytree.epub.CacheListener;
import com.skytree.epub.ClickListener;
import com.skytree.epub.FixedControl;
import com.skytree.epub.KeyListener;
import com.skytree.epub.MediaOverlayListener;
import com.skytree.epub.PageInformation;
import com.skytree.epub.PageMovedListener;
import com.skytree.epub.PageTransition;
import com.skytree.epub.Parallel;
import com.skytree.epub.Setting;
import com.skytree.epub.SkyProvider;
import com.skytree.epub.State;
import com.skytree.epub.StateListener;
import com.ereader.client.ui.bookshelf.epubread.SkyLayout;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class MagazineActivity extends Activity {
	RelativeLayout ePubView,topView;
	FixedControl fv;
	View pieBack;

	SkyLayout mediaBox;
 	ImageButton playAndPauseButton;
 	ImageButton stopButton;
 	ImageButton prevButton;
 	ImageButton nextButton;
 	
 	ImageButton actionButton;

	
	Parallel currentParallel;
	boolean autoStartPlayingWhenNewPagesLoaded = true;  
	boolean autoMovePageWhenParallesFinished = true;	
	boolean isAutoPlaying = true;						
	int bookCode;
	double pagePositionInBook;
	
 	SkySetting setting;
 	SkyDatabase sd;
 	boolean isRotationLocked;
	
	private void debug(String msg) {
		Log.w("EPub",msg);
	}
	
	
    public void onCreate(Bundle savedInstanceState) {
    	sd = new SkyDatabase(this);
		setting = sd.fetchSetting();
    	
        String fileName = new String();
		Bundle bundle = getIntent().getExtras();
		fileName = bundle.getString("BOOKNAME");
		bookCode = bundle.getInt("BOOKCODE");
		pagePositionInBook = bundle.getDouble("POSITION");
		
		int spread = bundle.getInt("SPREAD");
		int orientation = bundle.getInt("ORIENTATION");		
		
		super.onCreate(savedInstanceState); 		
		
		ePubView = new RelativeLayout(this);
		LayoutParams rlp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		ePubView.setLayoutParams(rlp);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); // width,height
//		int maxCachesForCaptures = 10; // for low spec device, set this value below 25, default 25
//		fv = new FixedControl(this,spread,orientation,maxCachesForCaptures); //
//		fv = new FixedControl(this,spread,orientation);
//		fv = new FixedControl(this,Book.SpreadAuto,orientation); 
//		fv = new FixedControl(this,Book.SpreadNone,orientation);
		fv = new FixedControl(this,Book.SpreadBoth,orientation);
		
		Bitmap pagesStack = 	BitmapFactory.decodeFile(SkySetting.getStorageDirectory()+"/images/PagesStack.png");
		Bitmap pagesCenter = 	BitmapFactory.decodeFile(SkySetting.getStorageDirectory()+"/images/PagesCenter.png");		
		fv.setPagesCenterImage(pagesCenter);
//		fv.setPagesStackImage(pagesStack);
		CacheDelegate ch = new CacheDelegate();
		fv.setCacheListener(ch);
		fv.setStateListener(new StateDelegate());
		fv.setBaseDirectory(SkySetting.getStorageDirectory() + "/books");
        fv.setBookName(fileName);
        
        // SkyProvider is the default Epub File Handler inside SkyEpub SDK since 5.0
		SkyProvider skyProvider = new SkyProvider();
		skyProvider.setKeyListener(new KeyDelegate());
		fv.setContentProvider(skyProvider);		
		SkyProvider skyProviderForCache = new SkyProvider();
		skyProviderForCache.setKeyListener(new KeyDelegate());
		fv.setContentProviderForCache(skyProviderForCache);

//		fv.setContentProvider(new EpubProvider());
//		fv.setContentProviderForCache(new EpubProvider());
        fv.setLayoutParams(params);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.width =  LayoutParams.FILL_PARENT;	// 400;
        params.height = LayoutParams.FILL_PARENT;	// 600;
        ClickDelegate cd = new ClickDelegate();
        fv.setClickListener(cd);
        PageMovedDelegate pd = new PageMovedDelegate();
        fv.setPageMovedListener(pd);
        fv.setTimeForRendering(500); 
        fv.setCurlQuality(0.75f);
        fv.setAutoScroll(false);
        fv.setNavigationAreaWidthRatio(0.05f);
        fv.setMediaOverlayListener(new MediaOverlayDelegate());
        fv.setSequenceBasedForMediaOverlay(false); 
//        fv.setMaxCachesForLoad(15);	// set max number of pages to be cached in memory for fast page transition. 

        // If you want to get the license key for commercial or non commercial use, please don't hesitate to email us. (skytree21@gmail.com). 
		// Without the proper license key, watermark message(eg.'unlicensed') may be shown in background. 
		fv.setLicenseKey("0000-0000-0000-0000");
		int transitionType = bundle.getInt("transitionType");
		if (transitionType==0) {
			fv.setPageTransition(PageTransition.None);
		}else if (transitionType==1) {
			fv.setPageTransition(PageTransition.Slide);
		}else if (transitionType==2) {
			fv.setPageTransition(PageTransition.Curl);
		}
		
		// in nexus 5, enforce the sdk to use software layer to render epub to avoid some display issues. 
		String modelName = SkyUtility.getModelName();
		if (modelName.toLowerCase().contains("hammerhead") || modelName.toLowerCase().contains("nexus 5")) {
			fv.useSoftwareLayer();
		}
		
		// set the minimum speed for pageTransition 
		// to avoid the conflict between dragging object inside page and swiping for turning page.		
		fv.setSwipeSpeedForPageTransition(0.6f);
		
		// set custom script for all pages in this epub.
		// and you can call it by using "executeJavascript"
		String script = "function beep() {"+
		    "var sound = new Audio('data:audio/wav;base64,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qJrx4KOQjahZxWbcZgztj2c49nKmkId44S71j0c8eV9yDK6uPRzx5X18eDvjvQ6yKo9ZSS6l//8elePK/Lf//IInrOF/FvDoADYAGBMGb7FtErm5MXMlmPAJQVgWta7Zx2go+8xJ0UiCb8LHHdftWyLJE0QIAIsI+UbXu67dZMjmgDGCGl1H+vpF4NSDckSIkk7Vd+sxEhBQMRU8j/12UIRhzSaUdQ+rQU5kGeFxm+hb1oh6pWWmv3uvmReDl0UnvtapVaIzo1jZbf/pD6ElLqSX+rUmOQNpJFa/r+sa4e/pBlAABoAAAAA3CUgShLdGIxsY7AUABPRrgCABdDuQ5GC7DqPQCgbbJUAoRSUj+NIEig0YfyWUho1VBBBA//uQZB4ABZx5zfMakeAAAAmwAAAAF5F3P0w9GtAAACfAAAAAwLhMDmAYWMgVEG1U0FIGCBgXBXAtfMH10000EEEEEECUBYln03TTTdNBDZopopYvrTTdNa325mImNg3TTPV9q3pmY0xoO6bv3r00y+IDGid/9aaaZTGMuj9mpu9Mpio1dXrr5HERTZSmqU36A3CumzN/9Robv/Xx4v9ijkSRSNLQhAWumap82WRSBUqXStV/YcS+XVLnSS+WLDroqArFkMEsAS+eWmrUzrO0oEmE40RlMZ5+ODIkAyKAGUwZ3mVKmcamcJnMW26MRPgUw6j+LkhyHGVGYjSUUKNpuJUQoOIAyDvEyG8S5yfK6dhZc0Tx1KI/gviKL6qvvFs1+bWtaz58uUNnryq6kt5RzOCkPWlVqVX2a/EEBUdU1KrXLf40GoiiFXK///qpoiDXrOgqDR38JB0bw7SoL+ZB9o1RCkQjQ2CBYZKd/+VJxZRRZlqSkKiws0WFxUyCwsKiMy7hUVFhIaCrNQsKkTIsLivwKKigsj8XYlwt/WKi2N4d//uQRCSAAjURNIHpMZBGYiaQPSYyAAABLAAAAAAAACWAAAAApUF/Mg+0aohSIRobBAsMlO//Kk4soosy1JSFRYWaLC4qZBYWFRGZdwqKiwkNBVmoWFSJkWFxX4FFRQWR+LsS4W/rFRb/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////VEFHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAU291bmRib3kuZGUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMjAwNGh0dHA6Ly93d3cuc291bmRib3kuZGUAAAAAAAAAACU=');"  
		    +"sound.play(); }";

		fv.setCustomScript(script);
		
		// set the background color of each page (only for the case that the background color of page is transparent) 
		fv.changeBackgroundColor(Color.WHITE);
		// set the color of window which the content page does not hide. 
		fv.changeWindowColor(Color.LTGRAY);
		
		isRotationLocked = setting.lockRotation;
		
        ePubView.addView(fv);

        this.makeMediaBox();
        int startPageIndex = (int)pagePositionInBook;
        fv.setStartPageIndex(startPageIndex); 
        setContentView(ePubView);
        fv.setImmersiveMode(true);
        SkyUtility.makeFullscreen(this);
        
		topView = new RelativeLayout(this);
		LayoutParams tlp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		topView.setLayoutParams(tlp);
		topView.setVisibility(View.INVISIBLE);
		topView.setVisibility(View.GONE);
		topView.setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        return true;
		    }
		});
		
		ePubView.addView(topView);	
		
				
		this.makeIndicator();
    } 
    
    public void startCaching() {
    	// if you need to capture each page inside epub, call fv.startCaching.  
//    	fv.startCaching();
    }
    
	class KeyDelegate implements KeyListener {
		@Override
		public String getKeyForEncryptedData(String uuidForContent, String contentName, String uuidForEpub) {
			// TODO Auto-generated method stub
			return "test";
		}

		@Override
		public Book getBook() {
			// TODO Auto-generated method stub
			return fv.getBook();
		}		
	}

 
	private OnClickListener listener=new OnClickListener(){
		public void onClick(View arg) {			
			if (arg.getId() == 8080) {
				playPrev();
			} else if (arg.getId() == 8081) {
				playAndPause();				
			} else if (arg.getId() == 8082) {
				stopPlaying();		
			} else if (arg.getId() == 8083) {
				playNext();
			} else if (arg.getId() == 8084) {
				finish();				
			} 		    
		}
	};
	

	
	class MediaOverlayDelegate implements MediaOverlayListener {
		@Override
		public void onParallelStarted(Parallel parallel) {
			// TODO Auto-generated method stub
			fv.changeElementColor("#FFFF00",parallel.hash,parallel.pageIndex);
			currentParallel = parallel;			
		}

		@Override
		public void onParallelEnded(Parallel parallel) {
			// TODO Auto-generated method stub
			fv.restoreElementColor();			
		}

		@Override
		public void onParallelsEnded() {
			// TODO Auto-generated method stub
		    fv.restoreElementColor();
		    if (autoStartPlayingWhenNewPagesLoaded) isAutoPlaying = true;
		    if (autoMovePageWhenParallesFinished) {
		        fv.gotoNextPage();
		    }
		}		
	}
	
	void playAndPause() {
		if (fv.isPlayingPaused()) {
	        if (!fv.isPlayingStarted()) {
	            fv.playFirstParallel();	        	
	            if (autoStartPlayingWhenNewPagesLoaded) isAutoPlaying = true;
	        }else {
	            fv.resumePlayingParallel();	        	
	            if (autoStartPlayingWhenNewPagesLoaded) isAutoPlaying = true;
	        }        
	    
	    }else {	        
	        fv.pausePlayingParallel();
	        if (autoStartPlayingWhenNewPagesLoaded) isAutoPlaying = false;
	    }
		this.changePlayAndPauseButton();
	}
	
	void stopPlaying() {
	    fv.stopPlayingParallel();
	    fv.restoreElementColor();
	    if (autoStartPlayingWhenNewPagesLoaded) isAutoPlaying = false;
	    this.changePlayAndPauseButton();
	}
	
	void playPrev() {
	    fv.restoreElementColor();
	    if (currentParallel.parallelIndex==0) {
	        if (autoMovePageWhenParallesFinished) fv.gotoPrevPage();
	    }else {
	        fv.playPrevParallel();
	    }
	}

	void playNext() {
	    fv.restoreElementColor();
		fv.playNextParallel();
	}
	
	public void onStop() {
		super.onStop();
//		debug("onStop");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		sd.updatePosition(bookCode, pagePositionInBook);
		sd.updateSetting(setting);
		
		fv.stopPlayingParallel();
		fv.restoreElementColor();
		fv.stopCaching();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		fv.playFirstParallel();
		new Handler().postDelayed(
				new Runnable() { 
					public void run() {
						startCaching();
					}
				}
		,3000);
	}

	public void onDestory() {
		super.onDestroy();
		debug("onDestory");		
	}
	
	@Override
	 public void onBackPressed() {
		finish();
		return;
	 }

	
	public void makeMediaBox() {
		mediaBox = new SkyLayout(this);
		setFrame(mediaBox, 100, 200, ps(270),ps(50));
		
		int bs = ps(32);
		int sb = 15;
		prevButton = this.makeImageButton(9898, R.drawable.prev2x, bs,bs);
		setLocation(prevButton,ps(10),ps(5));
		prevButton.setId(8080);
		prevButton.setOnClickListener(listener);
		playAndPauseButton = this.makeImageButton(9898, R.drawable.pause2x, bs,bs);
		setLocation(playAndPauseButton,ps(sb)+bs+ps(10),ps(5));
		playAndPauseButton.setId(8081);
		playAndPauseButton.setOnClickListener(listener);
		stopButton = this.makeImageButton(9898, R.drawable.stop2x, bs,bs);
		setLocation(stopButton,(ps(sb)+bs)*2,ps(5));
		stopButton.setId(8082);
		stopButton.setOnClickListener(listener);
		nextButton = this.makeImageButton(9898, R.drawable.next2x, bs,bs);
		setLocation(nextButton,(ps(sb)+bs)*3,ps(5));
		nextButton.setId(8083);
		nextButton.setOnClickListener(listener);
		
		mediaBox.setVisibility(View.INVISIBLE);
		mediaBox.setVisibility(View.GONE);		
		
		mediaBox.addView(prevButton);
		mediaBox.addView(playAndPauseButton);
		mediaBox.addView(stopButton);
		mediaBox.addView(nextButton);
		
		this.ePubView.addView(mediaBox);		
	}

	
	public boolean isPortrait() {
		int orientation = getResources().getConfiguration().orientation;
		if (orientation==Configuration.ORIENTATION_PORTRAIT) return true;
		else return false;	
	}
	
	public void hideMediaBox() {
		if (mediaBox!=null) {
			mediaBox.setVisibility(View.INVISIBLE);
			mediaBox.setVisibility(View.GONE);
		}
	}
	
	public void showMediaBox() {
		if (this.isPortrait()) {
			setLocation(mediaBox,ps(50), ps(120));
		}else {
			setLocation(mediaBox, ps(110), ps(20));
		}
		mediaBox.setVisibility(View.VISIBLE);
	}
	
	private void changePlayAndPauseButton() {
		Drawable icon;
		int imageId;
		if (!fv.isPlayingStarted() || fv.isPlayingPaused()) {
			imageId = R.drawable.play2x;
		}else {
			imageId = R.drawable.pause2x;	
		}
		
		int bs = ps(32);
		icon = getResources().getDrawable(imageId);
		icon.setBounds(0,0,bs,bs);
		Bitmap iconBitmap = ((BitmapDrawable)icon).getBitmap();
		Bitmap bitmapResized = Bitmap.createScaledBitmap(iconBitmap, bs, bs, false);
		playAndPauseButton.setImageBitmap(bitmapResized);
	}

	
	class PageMovedDelegate implements PageMovedListener {
		public void onPageMoved(PageInformation pi) {
			pagePositionInBook = pi.pageIndex;
			String msg = String.format("pn:%d/tn:%d ps:%f",pi.pageIndex,pi.numberOfPagesInChapter,pi.pagePositionInBook);
//			Log.w("EPub",msg);
//			Log.w("EPub"," "+fv.getPageIndex()+"/"+fv.getPageCount());			
	        if (fv.isMediaOverlayAvailable()) {
	        	showMediaBox();
	            if (isAutoPlaying) {
	                fv.playFirstParallel();
	            }
	        }else {
	        	hideMediaBox();
	        }
	        
	        fv.setUserInteractionEnabled(false);
	        new Handler().postDelayed(
	        		new Runnable() { 
	        			public void run() {
	        				fv.setUserInteractionEnabled(true);		
	        			}
	        		}
	        ,100);

		}
		
		public void onChapterLoaded(int chapterIndex) {
			// do nothing in FixedLayout. 
		}
	}
	
	public void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
		int childCount = viewGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = viewGroup.getChildAt(i);
			view.setEnabled(enabled);
			if (view instanceof ViewGroup) {
				enableDisableViewGroup((ViewGroup) view, enabled);
			}
		}
	}
	
	public void setFrame(View view,int dx, int dy, int width, int height) {
		LayoutParams param = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT); // width,height
		param.leftMargin = dx;
		param.topMargin =  dy;
		param.width = width;
		param.height = height;
		view.setLayoutParams(param);	
	}
	
	public void setLocation(View view,int px, int py) {
		LayoutParams param = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT); // width,height
		param.leftMargin = px;
		param.topMargin =  py;
		view.setLayoutParams(param);
	}
	
	public int getDensityDPI() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int density = metrics.densityDpi;
		return density;
	}
	
	public int getPS(float dip) {
		float densityDPI = this.getDensityDPI();
		int px = (int)(dip*(densityDPI/240));
		return px;		
//		int px = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));
//		return px;
	}
	
	public int getPXFromLeft(float dip) {
		int ps = this.getPS(dip);
		return ps;		
	}
	
	public int getPXFromRight(float dip) {
		int ps = this.getPS(dip);
		int ms = this.getWidth()-ps;
		return ms;
	}
	
	public int getPYFromTop(float dip) {
		int ps = this.getPS(dip);
		return ps;
	}
	
	public int getPYFromBottom(float dip) {
		int ps = this.getPS(dip);
		int ms = this.getHeight()-ps;
		return ms;
	}
	
	public int pxl(float dp) {
		return this.getPXFromLeft(dp);		
	}
	
	public int pxr(float dp) {
		return this.getPXFromRight(dp);
	}
	
	public int pyt(float dp) {
		return this.getPYFromTop(dp);
	}
	
	public int pyb(float dp) {
		return this.getPYFromBottom(dp);
	}
	
	public int ps(float dp) {
		return this.getPS(dp);
	}
	
	public int pw(float sdp) {
		int ps = this.getPS(sdp*2);
		int ms = this.getWidth()-ps;
		return ms;
	}
	
	public int cx(float dp) {
		int ps = this.getPS(dp);
		int ms = this.getWidth()/2-ps/2;
		return ms;		
	}
	
	public int getWidth() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		return width;		
	}
	
	public int getHeight() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int height = metrics.heightPixels;
		return height;			
	}
	
	public ImageButton makeImageButton(int id,String imageName,int width, int height) {
		LayoutParams param = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT); // width,height
		Drawable icon;
		ImageButton button = new ImageButton(this);	
		button.setId(id);
		button.setOnClickListener(listener);
		button.setBackgroundColor(Color.TRANSPARENT);
		icon = this.getDrawableFromAssets(imageName);
		icon.setBounds(0,0,width,height);
		Bitmap iconBitmap = ((BitmapDrawable)icon).getBitmap();
		Bitmap bitmapResized = Bitmap.createScaledBitmap(iconBitmap, width, height, false);
		button.setImageBitmap(bitmapResized);
		button.setVisibility(View.VISIBLE);
		param.width = 		width;		
		param.height = 		height;
		button.setLayoutParams(param);		
		button.setOnTouchListener(new ImageButtonHighlighterOnTouchListener(button));
		
		
		return button;		
	}
	
	public ImageButton makeImageButton(int id,int resId,int width, int height) {
		LayoutParams param = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT); // width,height
		Drawable icon;
		ImageButton button = new ImageButton(this);
		button.setAdjustViewBounds(true);
		button.setId(id);
		button.setOnClickListener(listener);
		button.setBackgroundColor(Color.TRANSPARENT);
		icon = getResources().getDrawable(resId);
		icon.setBounds(0,0,width,height);
		
		Bitmap iconBitmap = ((BitmapDrawable)icon).getBitmap();
		Bitmap bitmapResized = Bitmap.createScaledBitmap(iconBitmap, width, height, false);
		button.setImageBitmap(bitmapResized);
		button.setVisibility(View.VISIBLE);
		param.width = 		(int)(width);		
		param.height = 		(int)(height);
		button.setLayoutParams(param);		
		button.setOnTouchListener(new ImageButtonHighlighterOnTouchListener(button));
		return button;		
	}

	
	Drawable getDrawableFromAssets(String name) {
		try {
//			InputStream ims = getResources().getAssets().open(name);
//			Drawable d = Drawable.createFromStream(ims, null);
			Drawable d = Drawable.createFromStream(getAssets().open(name), null);
			return d;
		}catch(Exception e) {
			return null;
		}	
	}
	
	class ImageButtonHighlighterOnTouchListener implements OnTouchListener {
		  final ImageButton button;

		  public ImageButtonHighlighterOnTouchListener(final ImageButton button) {
		    super();
		    this.button = button;
		  }
		  
		  @Override
		  public boolean onTouch(final View view, final MotionEvent motionEvent) {
			  if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				  button.setColorFilter(Color.argb(155, 220, 220, 220));
			  } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				  button.setColorFilter(Color.argb(0, 185, 185, 185));
			  }
		    return false;
		  }
	}
	
	public void showToast(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.show();
	}
	
 	ProgressBar progressBar;
	
	public void makeIndicator() {
		progressBar = new ProgressBar(this, null,android.R.attr.progressBarStyleSmallInverse);		
		ePubView.addView(progressBar);		
		this.hideIndicator();
	}
	
	public void showIndicator() {
		LayoutParams params =
			    (LayoutParams)progressBar.getLayoutParams();
		params.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
		progressBar.setLayoutParams(params);
		progressBar.setVisibility(View.VISIBLE);
	}
	
	public void hideIndicator() {
		if (progressBar!=null) {
			progressBar.setVisibility(View.INVISIBLE);
			progressBar.setVisibility(View.GONE);
		}
	}
	
	// whenever the state of engine is changed, skyepub will invoke this listener. 
	class StateDelegate implements StateListener {
		public void onStateChanged(State state) {
			if (state==State.LOADING) {
				showIndicator();
			}else if (state==State.ROTATING) {
				showIndicator();				
			}else if (state==State.BUSY) {				
				showIndicator();
			}else if (state==State.NORMAL) {				
				hideIndicator();
			}
		}		
	}
	
	// this function returns the folder in which thumbnail images are saved. 
	// in this example, the external sd card will be used as storage for thumbnails.  
	private String getCacheFolder() {
		String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
		return dir;		
	}
	
	// for the thumbnail images, this returns the unique file name for given pageIndex and bookCode.  
	private String getFilePath(int pageIndex) {
		String prefix = this.getCacheFolder();
		String name = String.format("sb%d-cache%d.jpg",this.bookCode,pageIndex);
	    String filePath = prefix+"/"+name;
		
		return filePath;
	}

	// saves the bitmap which is passed from skyepub sdk. 
	// the bitmap which is passed from onCached is the original size. you may resize it smaller for your sake. 
	private void saveBitmap(Bitmap bitmap,String filePath) {
		try {		    
			if (bitmap==null) return;
			FileOutputStream out = new FileOutputStream(filePath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);			
			out.flush();
			out.close();
			
		} catch (Exception e) {
		       e.printStackTrace();
		}		
	}
	
	// CacheListener is used for generating the thumbnail image for each page. 
	class CacheDelegate implements CacheListener {
		// when caching process is started, this is called with the number of pages which should be cached. 
		@Override
		public void onCachingStarted(int numberOfUncached) {
			showToast("onCachingStarted");
		}

		// when caching process is finished, this is called with the number of pages which are already processed. 
		@Override
		public void onCachingFinished(int numberOfCached) {
			showToast("onCachingFinished");
		}

		// whenever each page is cached, this is called with captured bitmap for each page and pageIndex. 
		@Override
		public void onCached(int pageIndex, Bitmap bitmap, double progress) {
			// TODO Auto-generated method stub
			showToast("onCached for "+pageIndex);
			String filePath = getFilePath(pageIndex);
			saveBitmap(bitmap,filePath);
			// bitmap should be recycled after it is processed by user. 
			if (!bitmap.isRecycled()) bitmap.recycle();			
		}
		
		// if the thumbnail file already exists, you should return true to avoid necessary capturing page.  
		@Override
		public boolean cacheExist(int pageIndex) {
			// TODO Auto-generated method stub
			String filePath = getFilePath(pageIndex);
		    File file = new File(filePath);
		    if (file.exists()) return true;		    
			return false;
		}		
	}
	
	class ClickDelegate implements ClickListener {
		@Override
		public void onClick(int x,int y) {
			Setting.debug("Click detected at "+x+":"+y);
			// when you need to call a function in your custom script which is defined by "fv.setCustomScript"
			// you can use executeJavascript(script) 
			// or executeJavascript(script,true or false); true is for left page only and false is for right page only while landscape mode.  
			// fv.executeJavascript("beep()");
		}
		
		public void onImageClicked(int x,int y,String src) {}
		public void onLinkClicked(int x,int y,String href) {}
		@Override
		public boolean ignoreLink(int x,int y,String href) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public void onLinkForLinearNoClicked(int x, int y, String href) {}
		@Override
		public void onIFrameClicked(int x, int y, String src) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onVideoClicked(int x, int y, String src) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onAudioClicked(int x, int y, String src) {
			// TODO Auto-generated method stub
			
		}
	}
}
