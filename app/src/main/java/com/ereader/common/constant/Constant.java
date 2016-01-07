package com.ereader.common.constant;

import android.os.Environment;

/**
 * Created by ghf on 15/10/21.
 */
public class Constant {

    public final static String ROOT_OUTPATH = Environment.getExternalStorageDirectory().getAbsolutePath();//
    public final static String FOLDER_NAME = "EEEReader";

    public final static String OUTPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EEEReader/";//

    public final static String DBNAME = "bookshelf.db";

    public final static String DBNAME_DOWNLOAD= "download.db";

    public final static String DOWNLOAD = OUTPATH + "Downloads/";

    public final static String BOOKS = OUTPATH + "books/";

    public final static String SUFFIX_EPUB= ".epub";

    public final static String ASSET_PATH = "books/";

}
