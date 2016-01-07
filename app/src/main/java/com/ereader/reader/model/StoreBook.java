package com.ereader.reader.model;

import com.ereader.client.R;
import com.ereader.client.service.download.DownloadInfo;
import com.ereader.reader.utils.MimeType;

import java.io.Serializable;

public class StoreBook extends DownloadInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public final static String PRESET_PREFIX = "preset://";
	
//	public int book_id;
//	public String name;
	public String file;
	public String presetFile;
	public String type;
	public String cover;
	
	public double readPosition;
	
	public long fileSign;

	public StoreBook(){
	}

	public int getIconRes()
	{
		if( type.equalsIgnoreCase(MimeType.txt.name())) return R.drawable.cover_txt;
		else if( type.equalsIgnoreCase(MimeType.epub.name())) return R.drawable.cover_epub;
		else if( type.equalsIgnoreCase("ebk")) return R.drawable.cover_ebk;
		else if( type.equalsIgnoreCase("pdf")) return R.drawable.cover_pdf;
		else if( type.equalsIgnoreCase(MimeType.umd.name())) return R.drawable.cover_umd;
		else if( type.equalsIgnoreCase("new")) return R.drawable.cover_net;
		else return R.drawable.cover_default_new;
	}
	
	public boolean isPreset() {
		return presetFile != null && presetFile.startsWith(PRESET_PREFIX);
	}
	
	public String getPresetFile() {
		return isPreset() ? presetFile.replace(PRESET_PREFIX, "") : null;
	}
	
	@Override
	public String toString() {
		return String.format("book(name=%s; file=%s, type=%s)", name, file, type);
	}

}
