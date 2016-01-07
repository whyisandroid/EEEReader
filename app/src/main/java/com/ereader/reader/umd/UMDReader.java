package com.ereader.reader.umd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ereader.reader.Constant;
import com.ereader.reader.model.StoreBook;
import com.ereader.reader.read.Chapter;
import com.ereader.reader.read.cache.LocalCache;
import com.ereader.reader.read.txt.TxtChapter;
import com.ereader.reader.read.txt.TxtReader;
import com.ereader.reader.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UMDReader extends TxtReader {
	
	final static String TAG = Constant.TAG;
	
	private UMD mUMD;
	
	public UMDReader(Context context) {
		super(context);
	}
	
	@Override
	public boolean loadBook(StoreBook storeBook) throws IOException {
		mBookLoaded = false;
		mStoreBook = storeBook;
		UMD umd = null;
		Log.d(TAG, "loadUmd begin");
		mBookFile = new File(storeBook.file);
		try {
			if (storeBook.fileSign == mBookFile.length()) {
				umd = loadCache(storeBook);
			}
			if (umd == null) {
				umd = new UMDDecoder().decode(new File(storeBook.file));
				if (umd != null) {
					saveCache(storeBook, umd);
				}
			}
		} catch (Exception e) {
			Log.w(TAG, "loadBook", e);
		}
		if (umd != null && !"error".equals(storeBook.cover) && (storeBook.cover == null || !new File(storeBook.cover).exists())) {
			saveCover(storeBook, umd);
		}
		mUMD = umd;
		Log.d(TAG, "loadUmd end");
		mBookLoaded = mUMD != null;
		if (mUMD != null) {
			if (mUMD.getTitle() != null && mUMD.getTitle().length() > 0) {
				storeBook.name = mUMD.getTitle();
			}
			storeBook.fileSign = mBookFile.length();
			
			mCurrentBlock = mUMD.getBlock();
			mBlocks.clear();
			mBlocks.add(mCurrentBlock);
			mCurrentBlockString = mUMD.getContent();
			mTotalLength = mCurrentBlock.length;
			mTotalStringLength = mCurrentBlock.stringLength;
			loadChapters();
		}
		return mBookLoaded;
	}
	
	private void loadChapters() {
		if (mBookLoaded) {
			for (TxtChapter chapter : mCurrentBlock.chapters) {
				if (getCurrentOffset() > chapter.offset) {
					mCurrentChapter = chapter;
				}
			}
			if (mCurrentChapter == null) {
				mCurrentChapter = mCurrentBlock.chapters.get(0);
			}
			mHasChapter = true;
			mCurrentChapterString = mCurrentBlockString.substring(mCurrentChapter.offset, mCurrentChapter.offset + mCurrentChapter.length);
		}
	}
	
	@Override
	public List<Chapter> getChapters() {
		if (!mBookLoaded) {
			return null;
		}
		List<Chapter> list = new ArrayList<Chapter>();
		list.addAll(mCurrentBlock.chapters);
		return list;
	}
	
	private UMD loadCache(StoreBook storeBook) {
		try {
			return (UMD) LocalCache.instance(mContext).readData(FileUtils.getBookCacheFile(storeBook));
		} catch (Exception e) {
		}
		return null;
	}
	
	private void saveCache(StoreBook storeBook, UMD umd) {
		try {
			LocalCache.instance(mContext).writeData(FileUtils.getBookCacheFile(storeBook), umd);
		} catch (Exception e) {
		}
	}
	
	private void saveCover(StoreBook storeBook, UMD umd) {
		FileOutputStream fos = null;
		try {
			if (umd.getCovers() != null) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(umd.getCovers(), 0, umd.getCovers().length);
				if (bitmap != null) {
					String path = LocalCache.instance(mContext).getCachePath(FileUtils.getCoverCacheFile(storeBook));
					File file = new File(path);
					file.getParentFile().mkdirs();
					fos = new FileOutputStream(file);
					bitmap.compress(CompressFormat.PNG, 100, fos);
					storeBook.cover = path;
				} else {
					storeBook.cover = "error";
					Log.w(TAG, "saveCover fail");
				}
			} else {
				storeBook.cover = "error";
				Log.w(TAG, "saveCover fail");
			}
		} catch (Exception e) {
			storeBook.cover = "error";
			Log.w(TAG, "saveCover error", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
