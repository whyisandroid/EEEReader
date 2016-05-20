package com.ereader.reader.read.epub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;

import com.ereader.reader.Constant;
import com.ereader.reader.model.StoreBook;
import com.ereader.reader.read.Chapter;
import com.ereader.reader.read.cache.LocalCache;
import com.ereader.reader.read.txt.TxtBlock;
import com.ereader.reader.read.txt.TxtChapter;
import com.ereader.reader.read.txt.TxtPage;
import com.ereader.reader.read.txt.TxtReader;
import com.ereader.reader.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

/**
 * TODO 需要优化。现在排版和渲染都是采用简单的txt文本输出，没有处理html样式
 * 
 * @author lijing.lj
 */
public class EPubReader extends TxtReader {
	
	final static String TAG = Constant.TAG;
	
	nl.siegmann.epublib.domain.Book mEPubBook;
	
	public EPubReader(Context context) {
		super(context);
	}
	
	@Override
	public boolean loadBook(StoreBook storeBook) throws IOException {
		mBookLoaded = false;
		mStoreBook = storeBook;
		EpubReader reader = new EpubReader();
		mEPubBook = reader.readEpub(new FileInputStream(storeBook.file));
		mBookLoaded = mEPubBook != null;
		if (mBookLoaded) {
			if (mEPubBook.getTitle() != null && mEPubBook.getTitle().length() > 0) {
				storeBook.name = mEPubBook.getTitle();
			}
			if (mEPubBook != null && !"error".equals(storeBook.cover) && (storeBook.cover == null || !new File(storeBook.cover).exists())) {
				saveCover(storeBook, mEPubBook);
			}
			loadChapters();
			loadBuffer();
			mCurrentChapterString = mCurrentBlockString;
			loadCurrentChapter();
			loadNextChapter();
		}
		return mBookLoaded;
	}
	
	private void saveCover(StoreBook storeBook, nl.siegmann.epublib.domain.Book epubBook) {
		FileOutputStream fos = null;
		try {
			if (epubBook.getCoverImage() != null) {
				Bitmap bitmap = BitmapFactory.decodeStream(epubBook.getCoverImage().getInputStream());
				if (bitmap != null) {
					String path = LocalCache.instance(mContext).getCachePath(FileUtils.getCoverCacheFile(storeBook));
					File file = new File(path);
					file.getParentFile().mkdirs();
					fos = new FileOutputStream(file);
					bitmap.compress(CompressFormat.PNG, 100, fos);
					storeBook.cover = path;
				} else {
					Log.w(TAG, "saveCover fail");
				}
			} else {
				Resource r = epubBook.getResources().getById("cover");
				if (r != null) {
					Bitmap bitmap = BitmapFactory.decodeStream(r.getInputStream());
					if (bitmap != null) {
						String path = LocalCache.instance(mContext).getCachePath(FileUtils.getCoverCacheFile(storeBook));
						File file = new File(path);
						file.getParentFile().mkdirs();
						fos = new FileOutputStream(file);
						bitmap.compress(CompressFormat.PNG, 100, fos);
						storeBook.cover = path;
					} else {
						Log.w(TAG, "saveCover fail");
					}
				} else {
					Log.w(TAG, "saveCover fail");
				}
			}
		} catch (Exception e) {
			Log.w(TAG, "saveCover error", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
		if (storeBook.cover == null) {
			storeBook.cover = "error";
		}
	}
	
	@Override
	protected String loadBuffer(TxtBlock block) {
		EPubChapter chapter = (EPubChapter) block.chapters.get(0);
		String content = "";
		try {
			content = Html.fromHtml(new String(chapter.resource.getData(), chapter.resource.getInputEncoding())).toString();
		} catch (Throwable tr) {
			Log.w(TAG, "loadBuffer", tr);
		}
		chapter.offset = 0;
		chapter.length = content.length();
		block.stringLength = content.length();
		block.length = content.length();
		return content;
	}
	
	private void loadChapters() throws IOException {
		List<TOCReference> tocReferences = mEPubBook.getTableOfContents().getTocReferences();
		if (tocReferences != null) {
			mBlocks.clear();
			for (TOCReference tocReference : tocReferences) {
				EPubBlock block = new EPubBlock();
				EPubChapter chapter = new EPubChapter();
				chapter.block = block;
				chapter.header = false;
				chapter.title = tocReference.getTitle();
				chapter.name = tocReference.getTitle();
				chapter.resource = tocReference.getResource();
				block.chapters.add(chapter);
				mBlocks.add(block);
			}
			mHasChapter = true;
			for (TxtBlock block : mBlocks) {
				if (mCurrentBlock == null) {
					mCurrentBlock = block;
				}
				if (getCurrentOffset() >= calculateOffset(block, block.chapters.get(0), null)) {
					mCurrentBlock = block;
				} else {
					break;
				}
			}
			mCurrentChapter = mCurrentBlock.chapters.get(0);
		}
	}

	@Override
	public Chapter getCurrentChapter() {
		return mCurrentChapter;
	}

	@Override
	protected double calculateProgress(TxtPage page) {
		return calculateOffset(page.chapter.block, page.chapter, page);
	}
	
	@Override
	public float getCurrentProgress() {
		return (float) getCurrentOffset();
	}
	
	@Override
	protected double progressToOffset(float progress) {
		return progress;
	}
	
	/**
	 * @TODO EPub阅读进度以0——1保存，Txt是以0——总长度保存
	 */
	@Override
	protected double calculateOffset(TxtBlock block, TxtChapter chapter,
			TxtPage page) {
		double d = mBlocks.indexOf(block);
		if (d >= 0) {
			d /= mBlocks.size();
			if (page != null) {
				double p = chapter.pages.indexOf(page);
				if (p >= 0) {
					p /= chapter.pages.size();
					p /= mBlocks.size();
					d += p;
				}
			}
			return d;
		}
		return 0;
	}
}
