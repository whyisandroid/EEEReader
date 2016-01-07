package com.ereader.reader.txt;

import com.ereader.reader.read.Chapter;

import java.util.ArrayList;
import java.util.List;

public class TxtChapter extends Chapter {

	private static final long serialVersionUID = 1L;
	
	public int offset;
	public int length;
	
	public TxtBlock block;
	
	public transient List<TxtPage> pages = new ArrayList<TxtPage>();
	public transient TxtPage currentPage;
	public transient TxtChapter realChapter = this;
	
	public int getCurrentPageIndex() {
		return currentPage != null ? pages.indexOf(currentPage) : -1;
	}

	@Override
	public String toString() {
		return String.format("name:%s, offset:%d, length:%d", title, offset, length);
	}
}
