package com.ereader.reader.read.txt;

public class TxtLine {
	
	public final String line;
	
	public  LineType type;
	
	public final float width;
	
	public TxtLine(String line, LineType type, float width) {
		this.line = line;
		this.type = type;
		this.width = width;
	}

	public static enum LineType {
		TITLE_TOP, TITLE, TITLE_BOTTOM, NORMAL, PARAGRAPH
	}

}
