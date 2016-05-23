package com.ereader.reader.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.ereader.client.R;
import com.ereader.client.ui.share.ShareActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.reader.Constant;
import com.ereader.reader.db.BookDBHelper;
import com.ereader.reader.exception.BookException;
import com.ereader.reader.model.StoreBook;
import com.ereader.reader.read.BookPageManager;
import com.ereader.reader.utils.FileUtils;
import com.ereader.reader.utils.MimeType;
import com.ereader.reader.view.BookReadView;
import com.glview.view.View;
import com.glview.view.View.OnClickListener;
import com.glview.widget.ImageButton;
import com.glview.widget.ImageView;
import com.glview.widget.TextView;
import com.glview.widget.Toast;

import java.io.File;

public class ReaderActivity extends BaseActivity implements OnClickListener{
	
	final static String TAG = Constant.TAG;
	
	private ProgressDialog mLoadingDialog;

	private BookReadView mReadView;
	BookPageManager mBookPageManager;

	private ImageView back;
	private ImageButton bookMark;
//	private ImageButton setting_reading;

	private TextView read_share;
	private TextView read_note;
	StoreBook storeBook = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setGLContentView(R.layout.activity_reader);
		init();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		init();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (mBookPageManager == null || !mBookPageManager.isBookLoaded()) {
			return false;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	private void init() {
		showLoading();
		Intent intent = getIntent();

		MimeType type = null;
		try {
			storeBook = (StoreBook) intent.getSerializableExtra("storeBook");
		} catch (Exception e) {}
		if (storeBook == null) {
			String file = intent.getStringExtra("file");
			if (file == null) {
				Uri data = intent.getData();
				if (data != null) {
					file = data.getPath();
					type = MimeType.valueOfMimeType(intent.getType());
					Log.d(TAG, "open file:" + file);
					Log.d(TAG, "mime type:" + type);
				}
			}
			if (file != null) {
				storeBook = BookDBHelper.get(getApplicationContext()).queryBook(file);
				if (storeBook == null) {
					File f = new File(file);
					if (f.exists() && f.canRead() && f.isFile()) {
						String fileName = f.getName();
						int dot = fileName.lastIndexOf(".");
						if (type == null) {
							if (dot > 0) {
								String fileEnd = fileName.substring(dot + 1).toLowerCase();
								if (FileUtils.accept(fileEnd)) {
									storeBook = new StoreBook();
									storeBook.file = file;
									storeBook.type = fileEnd;
									storeBook.name = fileName.substring(0, dot);
								}
							}
						} else if (FileUtils.accept(type.name())) {
							storeBook = new StoreBook();
							storeBook.file = file;
							storeBook.type = type.name();
							storeBook.name = dot > 0 ? fileName.substring(0, dot) : fileName;
						}
					}
				}
			}
		}
		if (storeBook != null) {
			mBookPageManager.openBook(storeBook);
			new AsyncTask<String, String, Boolean>() {
				@Override
				protected Boolean doInBackground(String... args) {
					try {
						boolean r = mBookPageManager.openBookInternal();
						if (!r) {
							Toast.showShortToast(ReaderActivity.this, R.string.error_load_book);
						}
						return r;
					} catch (BookException e) {
						Log.d(TAG, "openBook:", e);
						Toast.showShortToast(ReaderActivity.this, e.getErrorCode().getMessage());
					} catch (Exception e) {
						Log.d(TAG, "openBook:", e);
						Toast.showShortToast(ReaderActivity.this, R.string.error_load_book);
					}
					return false;
				}
				@Override
				protected void onPostExecute(Boolean result) {
					cancelLoading();
					if (result) {
						mReadView.postInvalidate();
					} else {
						finish();
					}
				};
			}.execute();
		} else {
			Toast.showShortToast(ReaderActivity.this, R.string.error_load_book);
			finish();
		}
	}
	
	@Override
	public void onAttached(View content) {
		super.onAttached(content);
		mReadView = (BookReadView) content.findViewById(R.id.book_read_view);
		mBookPageManager = new BookPageManager(this, mReadView);
		mReadView.setBootPageManager(mBookPageManager);
		back=(ImageView)content.findViewById(R.id.back);
		back.setOnClickListener(this);
		bookMark=(ImageButton)content.findViewById(R.id.bookmark);
		bookMark.setOnClickListener(this);

		read_share=(TextView)content.findViewById(R.id.read_share);
		read_note=(TextView)content.findViewById(R.id.read_note);
		read_share.setOnClickListener(this);
		read_note.setOnClickListener(this);

//		setting_reading=(ImageButton)content.findViewById(R.id.setting_reading);
//		setting_reading.setOnClickListener(this);
//		.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (mBookPageManager != null) {
			mBookPageManager.onPause();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mBookPageManager != null) {
			mBookPageManager.onResume();
		}
	}
	
	@Override
	protected void onStop() {
		cancelLoading();
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void showLoading() {
		cancelLoading();
		mLoadingDialog = new ProgressDialog(this);
		mLoadingDialog.setCancelable(false);
		mLoadingDialog.show();
	}
	
	private void cancelLoading() {
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			try {
    			mLoadingDialog.dismiss();
			} catch(Throwable tr) {}
			mLoadingDialog = null;
		}
	}

	@Override
	public void onClick(View v) {
		if( v == back ){//书架／返回
			ReaderActivity.this.finish();
		}else if( v == bookMark ){//书签
			Toast.showToast(this,"TODO：还是记个百分比，字体大小～",Toast.LENGTH_SHORT);
		} else if( v ==read_note ){
			IntentUtil.intent(ReaderActivity.this,NoteActivity.class,false);

		}else if( v ==read_share ){
			String title = storeBook.name;
			String textToShare = "快来阅读《"+title+"》,来自"+ getResources().getString(R.string.app_name);
			Log.e("share.product_id",storeBook.toString()+"");
			ShareActivity.share(ReaderActivity.this,title,textToShare,
					"http://www.rreadeg.com/index.php?s=/Home/Book/share/id/"+storeBook.product_id+".html",storeBook.cover,true);
		}
	}
}
