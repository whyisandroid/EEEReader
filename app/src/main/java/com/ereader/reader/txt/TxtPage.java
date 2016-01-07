package com.ereader.reader.txt;

import com.ereader.reader.read.Page;

import java.util.ArrayList;
import java.util.List;

public class TxtPage extends Page {
	
	public TxtChapter chapter;

	public List<TxtLine> lines = new ArrayList<TxtLine>();
	
	public int offset;
	
}
