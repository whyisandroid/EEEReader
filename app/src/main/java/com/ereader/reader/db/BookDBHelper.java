/**
 * < CoolReader Database operator.>
 *  Copyright (C) <2009>  <Wang XinFeng,ACC http://androidos.cc/dev>
 *
 *   This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package com.ereader.reader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ereader.reader.Constant;
import com.ereader.reader.model.StoreBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Database operator
 * 
 * @author lijing.lj
 */
public class BookDBHelper extends SQLiteOpenHelper {
	
	private final static String TAG = Constant.TAG;
	
	public final static String DB_NAME = "book.db";
	public final static int DB_VERSION = 1;
	
	private final static String TABLE_SETTINGS_TABLE = "settings";
	private final static String TABLE_SETTINGS_KEY = "key";
	private final static String TABLE_SETTINGS_VALUE = "value";
	
	private final static String TABLE_BOOK_TABLE = "books";
	private final static String TABLE_BOOK_BOOK_ID = "book_id";
	private final static String TABLE_BOOK_BOOK_TITLE = "book_title";
	private final static String TABLE_BOOK_BOOK_FILE = "book_file";
	private final static String TABLE_BOOK_BOOK_PRESET_FILE = "book_file_preset";
	private final static String TABLE_BOOK_BOOK_TYPE = "book_type";
	private final static String TABLE_BOOK_BOOK_COVER = "book_cover";
	private final static String TABLE_BOOK_READ_POSITION = "read_position";
	private final static String TABLE_BOOK_FILE_SIGN = "file_sign";
	private final static String TABLE_BOOK_PRODUCT_ID = "product_id";
	private final static String TABLE_BOOK_ADD_TIME = "add_time";
	private final static String TABLE_BOOK_READ_TIME = "read_time";
	
	private SQLiteDatabase mDatabase = null;
	
	private static BookDBHelper sInstance;
	
	public static BookDBHelper get(Context c) {
		if (sInstance == null) {
			sInstance = new BookDBHelper(c);
		}
		return sInstance;
	}

	private BookDBHelper(Context c) {
		super(c, DB_NAME, null, DB_VERSION);
		getDatabase();
	}
	
	private SQLiteDatabase getDatabase() {
		if (mDatabase == null) {
			mDatabase = getWritableDatabase();
		}
		return mDatabase;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "start create table");
		db.execSQL(
				"CREATE TABLE IF NOT EXISTS " + TABLE_BOOK_TABLE + "(" +
					TABLE_BOOK_BOOK_ID + " INTEGER PRIMARY KEY," +
					TABLE_BOOK_BOOK_TITLE + " TEXT NOT NULL," +
					TABLE_BOOK_BOOK_FILE + " TEXT UNIQUE NOT NULL, " +
					TABLE_BOOK_BOOK_PRESET_FILE + " TEXT, " +
					TABLE_BOOK_BOOK_TYPE + " TEXT NOT NULL," + 
					TABLE_BOOK_BOOK_COVER + " TEXT," +
					TABLE_BOOK_READ_POSITION + " DOUBLE NOT NULL," + 
					TABLE_BOOK_FILE_SIGN + " INTEGER NOT NULL," +
					TABLE_BOOK_PRODUCT_ID+" INTEGER NOT NULL," +
					TABLE_BOOK_ADD_TIME + " INTEGER NOT NULL," +
					TABLE_BOOK_READ_TIME + " INTEGER NOT NULL" + ")");
		
		db.execSQL(
				"CREATE TABLE IF NOT EXISTS " + TABLE_SETTINGS_TABLE + "(" +
					TABLE_SETTINGS_KEY + " TEXT PRIMARY KEY," +
					TABLE_SETTINGS_VALUE + " TEXT NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	/** save the storeBook info to database */
	public int insertBook(StoreBook storeBook) {
		Log.d(TAG, "insert the storeBook into database");
		if (null == queryBookById(storeBook.book_id)) {
			ContentValues values = new ContentValues();
			values.put(TABLE_BOOK_BOOK_ID, storeBook.book_id);
			values.put(TABLE_BOOK_BOOK_FILE, storeBook.file);
			values.put(TABLE_BOOK_BOOK_PRESET_FILE, storeBook.presetFile);
			values.put(TABLE_BOOK_BOOK_TITLE, storeBook.name);
			values.put(TABLE_BOOK_BOOK_TYPE, storeBook.type);
			values.put(TABLE_BOOK_BOOK_COVER, storeBook.cover);
			values.put(TABLE_BOOK_READ_POSITION, storeBook.readPosition);
			values.put(TABLE_BOOK_FILE_SIGN, storeBook.fileSign);
			values.put(TABLE_BOOK_PRODUCT_ID, storeBook.product_id);
			long time = System.currentTimeMillis();
			values.put(TABLE_BOOK_ADD_TIME, time);
			values.put(TABLE_BOOK_READ_TIME, time);
			storeBook.book_id = (int) getDatabase().insert(TABLE_BOOK_TABLE, null, values);
			return storeBook.book_id;
		}else{
			updateReadTime(storeBook);//更新阅读时间
		}
		return -1;
	}
	
	public StoreBook queryBook(String file) {
		Log.d(TAG, "query the book form database");
		Log.d(TAG, "query the book file:" + file);
		String[] col = new String[] {TABLE_BOOK_BOOK_ID, TABLE_BOOK_BOOK_FILE, TABLE_BOOK_BOOK_PRESET_FILE, TABLE_BOOK_BOOK_TITLE, TABLE_BOOK_BOOK_TYPE, TABLE_BOOK_BOOK_COVER, TABLE_BOOK_READ_POSITION, TABLE_BOOK_FILE_SIGN,TABLE_BOOK_PRODUCT_ID};
		Cursor cur = getDatabase().query(TABLE_BOOK_TABLE, col, TABLE_BOOK_BOOK_FILE + "=?", new String[]{file}, null, null, null);
		if (cur.moveToFirst()) {
			StoreBook storeBook = new StoreBook();
			storeBook.book_id = cur.getInt(0);
			storeBook.file = cur.getString(1);
			storeBook.presetFile = cur.getString(2);
			storeBook.name = cur.getString(3);
			storeBook.type = cur.getString(4);
			storeBook.cover = cur.getString(5);
			storeBook.readPosition = cur.getDouble(6);
			storeBook.fileSign = cur.getLong(7);
			storeBook.product_id=cur.getString(8);
			cur.close();
			return storeBook;
		}
		return null;
	}
	public StoreBook queryBookById(int book_id) {
		Log.d(TAG, "query the book form database");
		Log.d(TAG, "query the book file:" + book_id);
		String[] col = new String[] {TABLE_BOOK_BOOK_ID, TABLE_BOOK_BOOK_FILE, TABLE_BOOK_BOOK_PRESET_FILE, TABLE_BOOK_BOOK_TITLE, TABLE_BOOK_BOOK_TYPE, TABLE_BOOK_BOOK_COVER, TABLE_BOOK_READ_POSITION, TABLE_BOOK_FILE_SIGN,TABLE_BOOK_PRODUCT_ID};
		Cursor cur = getDatabase().query(TABLE_BOOK_TABLE, col, TABLE_BOOK_BOOK_ID + "=?", new String[]{book_id+""}, null, null, null);
		if (cur.moveToFirst()) {
			StoreBook storeBook = new StoreBook();
			storeBook.book_id = cur.getInt(0);
			storeBook.file = cur.getString(1);
			storeBook.presetFile = cur.getString(2);
			storeBook.name = cur.getString(3);
			storeBook.type = cur.getString(4);
			storeBook.cover = cur.getString(5);
			storeBook.readPosition = cur.getDouble(6);
			storeBook.fileSign = cur.getLong(7);
			storeBook.product_id=cur.getString(8);
			cur.close();
			return storeBook;
		}
		return null;
	}
	public int updateBook(StoreBook storeBook) {
		ContentValues values = new ContentValues();
		values.put(TABLE_BOOK_BOOK_FILE, storeBook.file);
		values.put(TABLE_BOOK_BOOK_PRESET_FILE, storeBook.presetFile);
		values.put(TABLE_BOOK_BOOK_TITLE, storeBook.name);
		values.put(TABLE_BOOK_BOOK_TYPE, storeBook.type);
		values.put(TABLE_BOOK_BOOK_COVER, storeBook.cover);
		values.put(TABLE_BOOK_READ_POSITION, storeBook.readPosition);
		values.put(TABLE_BOOK_FILE_SIGN, storeBook.fileSign);
		values.put(TABLE_BOOK_PRODUCT_ID, storeBook.product_id);
		return getDatabase().update(TABLE_BOOK_TABLE, values, TABLE_BOOK_BOOK_ID + "=?", new String[]{String.valueOf(storeBook.book_id)});
	}
	
	public int updateReadTime(StoreBook storeBook) {
		ContentValues values = new ContentValues();
		values.put(TABLE_BOOK_READ_TIME, System.currentTimeMillis());
		return getDatabase().update(TABLE_BOOK_TABLE, values, TABLE_BOOK_BOOK_ID + "=?", new String[]{String.valueOf(storeBook.book_id)});
	}
	
	public List<StoreBook> queryAllBooks() {
		List<StoreBook> list = new ArrayList<StoreBook>();
		Log.d(TAG, "query all books form database");
		String[] col = new String[] {TABLE_BOOK_BOOK_ID, TABLE_BOOK_BOOK_FILE, TABLE_BOOK_BOOK_PRESET_FILE, TABLE_BOOK_BOOK_TITLE, TABLE_BOOK_BOOK_TYPE, TABLE_BOOK_BOOK_COVER, TABLE_BOOK_READ_POSITION, TABLE_BOOK_FILE_SIGN,TABLE_BOOK_PRODUCT_ID};
		Cursor cur = getDatabase().query(TABLE_BOOK_TABLE, col, null, null, null, null, TABLE_BOOK_READ_TIME + " desc");
		while (cur.moveToNext()) {
			StoreBook storeBook = new StoreBook();
			storeBook.book_id = cur.getInt(0);
			storeBook.file = cur.getString(1);
			storeBook.presetFile = cur.getString(2);
			storeBook.name = cur.getString(3);
			storeBook.type = cur.getString(4);
			storeBook.cover = cur.getString(5);
			storeBook.readPosition = cur.getDouble(6);
			storeBook.fileSign = cur.getLong(7);
			storeBook.product_id=cur.getString(8);
			list.add(storeBook);
		}
		cur.close();
		return list;
	}
	
	public List<StoreBook> queryAllPresetBooks() {
		List<StoreBook> list = new ArrayList<StoreBook>();
		Log.d(TAG, "query all preset books form database");
		String[] col = new String[] {TABLE_BOOK_BOOK_ID, TABLE_BOOK_BOOK_FILE, TABLE_BOOK_BOOK_PRESET_FILE, TABLE_BOOK_BOOK_TITLE, TABLE_BOOK_BOOK_TYPE, TABLE_BOOK_BOOK_COVER, TABLE_BOOK_READ_POSITION, TABLE_BOOK_FILE_SIGN,TABLE_BOOK_PRODUCT_ID};
		Cursor cur = getDatabase().query(TABLE_BOOK_TABLE, col, TABLE_BOOK_BOOK_PRESET_FILE + " is not null", null, null, null, TABLE_BOOK_READ_TIME + " desc");
		while (cur.moveToNext()) {
			StoreBook storeBook = new StoreBook();
			storeBook.book_id = cur.getInt(0);
			storeBook.file = cur.getString(1);
			storeBook.presetFile = cur.getString(2);
			storeBook.name = cur.getString(3);
			storeBook.type = cur.getString(4);
			storeBook.cover = cur.getString(5);
			storeBook.readPosition = cur.getDouble(6);
			storeBook.fileSign = cur.getLong(7);
			storeBook.product_id=cur.getString(8);
			list.add(storeBook);
		}
		cur.close();
		return list;
	}
	
	public int deleteBook(StoreBook storeBook) {
		return deleteBook(storeBook.book_id);
	}
	
	public int deleteBook(int book_id) {
		return getDatabase().delete(TABLE_BOOK_TABLE, TABLE_BOOK_BOOK_ID + "=?", new String[]{String.valueOf(book_id)});
	}
	
	public String getSettingsValue(String key) {
		String value = null;
		String[] col = new String[] {TABLE_SETTINGS_VALUE};
		Cursor cur = getDatabase().query(TABLE_SETTINGS_TABLE, col, TABLE_SETTINGS_KEY + "=?", new String[]{key}, null, null, null);
		if (cur.moveToNext()) {
			value = cur.getString(0);
		}
		cur.close();
		return value;
	}
	
	public void addOrUpdateSettingsValue(String key, String value) {
		String oldValue = getSettingsValue(key);
		if (oldValue != null) {
			if (!oldValue.equals(value)) {
				ContentValues values = new ContentValues();
				values.put(TABLE_SETTINGS_VALUE, value);
				getDatabase().update(TABLE_SETTINGS_TABLE, values, TABLE_SETTINGS_KEY + "=?", new String[]{key}); 
			}
		} else {
			ContentValues values = new ContentValues();
			values.put(TABLE_SETTINGS_KEY, key);
			values.put(TABLE_SETTINGS_VALUE, value);
			getDatabase().insert(TABLE_SETTINGS_TABLE, null, values);
		}
	}

}