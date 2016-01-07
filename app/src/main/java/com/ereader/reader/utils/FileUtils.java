package com.ereader.reader.utils;

import android.content.Context;
import android.os.Environment;

import com.ereader.reader.model.StoreBook;
import com.ereader.reader.model.BookFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {
	
	public static final List<String> FILE_END = new ArrayList<String>();
	static {
		for (MimeType e : MimeType.values()) {
			FILE_END.add(e.name());
		}
	}
	
	public static List<BookFile> getFiles(String path) {
		List<BookFile> books = new ArrayList<BookFile>();
		File[] files = new File(path).listFiles();
		if (files == null) {
			return books;
		}
		for (File file : files) {
			if (file.isHidden() || !file.canRead()) continue;
			
			if (file.isFile()) {
				int dot = file.getName().lastIndexOf(".");
				if (dot > 0) {
					String fileEnd = file.getName().substring(dot + 1, file.getName().length()).toLowerCase();
					if (FILE_END.contains(fileEnd)) {
						books.add(new BookFile(file.getName(), file.getPath(), fileEnd, true));
					}
				}
				
			} else {
				books.add(new BookFile(file.getName(), file.getAbsolutePath(), false));
			}
		}
		Collections.sort(books);
		return books;
	}
	
	public static void delete(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f != null) {
						delete(f);
					}
				}
			}
		}
		file.delete();
	}
	
	public static boolean accept(String type) {
		return type != null && FILE_END.contains(type.toLowerCase());
	}
	
	public static String getCoverCacheFile(StoreBook storeBook) {
		return getBookCacheDir(storeBook) + File.separatorChar + "cover";
	}
	
	public static String getBookCacheFile(StoreBook storeBook) {
		return getBookCacheDir(storeBook) + File.separatorChar + "storeBook";
	}
	
	public static String getBookCacheDir(StoreBook storeBook) {
		return storeBook.type + File.separatorChar + MD5.GetMD5Code(storeBook.file);
	}
	
	public static String getPresetBookPath(Context c, String file) {
		File dir;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			dir = new File(Environment.getExternalStorageDirectory(), "MReader/books/");
		} else {
			dir = c.getDir("books", Context.MODE_PRIVATE);
		}
		dir.mkdirs();
		String s = new File(dir, file).getAbsolutePath();
		return s;
	}
}
