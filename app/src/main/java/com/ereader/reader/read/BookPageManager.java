package com.ereader.reader.read;

import android.content.Context;
import android.util.Log;

import com.ereader.reader.Constant;
import com.ereader.reader.db.BookDBHelper;
import com.ereader.reader.exception.BookException;
import com.ereader.reader.exception.ErrorCode;
import com.ereader.reader.model.StoreBook;
import com.ereader.reader.read.Reader.InvalidateListener;
import com.ereader.reader.read.settings.ReadSettings;
import com.ereader.reader.read.settings.SettingsObserver;
import com.ereader.reader.read.settings.Theme;
import com.ereader.reader.utils.IOUtils;
import com.ereader.reader.view.BookReadView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BookPageManager implements SettingsObserver, InvalidateListener {
	
	final static String TAG = Constant.TAG;
	
	StoreBook mStoreBook;
	File mBookFile;
	long mBookLength;
	long mCurrentOffset;
	String mCharset;
	
	final BookDBHelper mDbHelper;
	final Context mContext;
	
	Reader mReader;
	
	final BookReadView mView;
	
	int mWidth = -1, mHeight = -1;

	public BookPageManager(Context context, BookReadView v) {
		mContext = context;
		mDbHelper = BookDBHelper.get(context);
		mView = v;
	}
	
	public StoreBook getBook() {
		return mStoreBook;
	}
	
	public void openBook(StoreBook storeBook) {
		if (mStoreBook != null && !mStoreBook.equals(storeBook)) {
			destroy();
		}
		ReadSettings.addSettingsObserver(this);
		mStoreBook = storeBook;
		mReader = Reader.createReader(mContext, mStoreBook);
	}
	
	public boolean openBookInternal() throws BookException {
		StoreBook storeBook = mStoreBook;
		if (storeBook == null) {
			throw new BookException(ErrorCode.FILE_NOT_EXIST);
		}
		File file = new File(storeBook.file);
		if (storeBook.isPreset() && !file.exists()) {
			try {
				file.getParentFile().mkdirs();
				IOUtils.copy(mContext.getAssets().open(storeBook.getPresetFile()), new FileOutputStream(file));
			} catch (IOException e) {
				Log.d(TAG, "copy preset storeBook", e);
			}
		}
		if (!file.exists()) {
			throw new BookException(ErrorCode.FILE_NOT_EXIST);
		}
		try {
			mReader.setInvalidateListener(this);
			if (mWidth > 0 && mHeight > 0) {
				mReader.setSize(mWidth, mHeight);
			}
			if (mReader.loadBook(mStoreBook)) {
				if (mDbHelper.insertBook(mStoreBook) == -1) {
					mDbHelper.updateReadTime(mStoreBook);
				}
				return true;
			}
		} catch (Exception e) {
			throw new BookException(ErrorCode.BOOK_LOAD_FAIL, "openBookInternal failed:" + file, e);
		}
		return false;
	}
	
	public boolean isBookLoaded() {
		return mReader != null && mReader.isBookLoaded();
	}
	
	public void saveReadPosition() {
		if (mReader != null && mReader.isBookLoaded() && mStoreBook != null) {
			mStoreBook.readPosition = mReader.getCurrentOffset();
			mDbHelper.updateBook(mStoreBook);
		}
	}
	
	public void destroy() {
		ReadSettings.removeSettingsObserver(this);
		mStoreBook = null;
		if (mReader != null) {
			mReader.destroy();
		}
		mReader = null;
	}
	
	public void setSize(int w, int h) {
		mWidth = w;
		mHeight = h;
		if (mReader != null) {
			mReader.setSize(w, h);
		}
	}
	
	public void shift(boolean forward) {
		if (mReader != null) {
			if (forward) {
				mReader.switchToNextPage();
			} else {
				mReader.switchToPreviousPage();
			}
		}
	}
	
	public boolean hasPreviousPage() {
		return mReader != null && mReader.hasPreviousPage();
	}
	
	public boolean hasNextPage() {
		return mReader != null && mReader.hasNextPage();
	}
	
	public BitmapPage getCurrentPage() {
		return mReader != null ? mReader.getCurrentPage() : null;
	}
	
	public BitmapPage getPreviousPage() {
		return mReader != null ? mReader.getPreviousPage() : null;
	}
	
	public BitmapPage getNextPage() {
		return mReader != null ? mReader.getNextPage() : null;
	}
	
	public void invalidate() {
		if (mReader != null) {
			mReader.invalidatePages();
		}
	}
	
	public List<Chapter> getChapters() {
		return mReader != null ? mReader.getChapters() : null;
	}
	
	public Chapter getCurrentChapter() {
		return mReader != null ? mReader.getCurrentChapter() : null;
	}
	
	public void seekToChapter(Chapter chapter) {
		if (mReader != null) {
			mReader.seekToChapter(chapter);
			mView.invalidate();
		}
	}
	
	public void seekTo(float progress) {
		if (mReader != null) {
			mReader.seekTo(progress);
			mView.invalidate();
		}
	}
	
	public float getCurrentProgress() {
		return mReader != null ? mReader.getCurrentProgress() : 0;
	}
	
	public void seekToPreviousChapter() {
		if (mReader != null) {
			mReader.seekToPreviousChapter();
			mView.invalidate();
		}
	}
	
	public void seekToNextChapter() {
		if (mReader != null) {
			mReader.seekToNextChapter();
			mView.invalidate();
		}
	}
	
	public void reset() {
	}

	@Override
	public void onChange(String key, Object oldValue, Object newValue) {
		if (mReader != null) {
			if (ReadSettings.FONT_SIZE.equals(key)) {
				mReader.setFontSize((Integer) newValue);
			} else if (ReadSettings.THEME.equals(key)) {
				mReader.setTheme((Theme) newValue);
			}
			mView.invalidate();
		}
	}
	
	@Override
	public void onInvalidate() {
		invalidate();
		mView.postInvalidate();
	}
	
	public void onPause() {
		saveReadPosition();
		if (mReader != null) {
			mReader.onPause();
		}
	}
	
	public void onResume() {
		if (mReader != null) {
			mReader.onResume();
		}
	}
}
