package com.ereader.common.constant;

import android.os.Environment;

/**
 * Created by ghf on 15/10/21.
 */
public class Constant {

    public final static String OUTPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EEEReader/";//

    public final static String DBNAME = "bookshelf.db";

    public final static String DBNAME_DOWNLOAD= "download.db";

    public final static String DOWNLOAD = OUTPATH + "Downloads/";

    public final static String SUFFIX_EPUB= ".epub";

}
