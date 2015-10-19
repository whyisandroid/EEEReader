package com.ereader.client.ui.fragment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BookPagerAdapter;
import com.ereader.client.ui.adapter.BookShelfAdapter;
import com.ereader.client.ui.bookshelf.Read;
import com.ereader.client.ui.bookshelf.ReadActivity;
import com.ereader.client.ui.bookshelf.SearchBuyActivity;
import com.ereader.client.ui.bookshelf.read.EpubNavigator;
import com.ereader.client.ui.bookshelf.read.LocalBook;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.view.LoopViewPager;
import com.ereader.client.ui.view.PointView;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ToastUtil;

public class BookshelfFragment extends Fragment {
	private static final String TAG = "BookshelfFragment";

	private View view;
	private Context mContext;
	private AppController controller;
	private Button main_top_right;
	private GridView gridv_book;
	private LoopViewPager viewpager;
	private LinearLayout pointlayout;

	private ArrayList<HashMap<String, Object>> listItem = null;
	private HashMap<String, Object> map = null;
	private Map<String, Integer[]> map2;// 存放本地推荐目录的小封面图片引用
	private SharedPreferences sp;
	private LocalBook localbook;
	private boolean isInit = false;
	private PointView pointView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.book_shelf_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		initRead();
		return view;
	}

	private void initRead() {
		if (!isInit) {
			new AsyncSetApprove().execute("");
		}

		// 读取名为"mark"的sharedpreferences
		sp = mContext.getSharedPreferences("mark", mContext.MODE_PRIVATE);
		localbook = new LocalBook(mContext, "localbook");
		map2 = new HashMap<String, Integer[]>();
		String[] bookids = getResources().getStringArray(R.array.bookid);
		for (int i = 0; i < bookids.length; i++) {
			map2.put(bookids[i], new Integer[] { R.drawable.book0 + i });
		}

	}

	private void findView() {
		main_top_right = (Button) view.findViewById(R.id.main_top_right);
		gridv_book = (GridView) view.findViewById(R.id.gridv_book);
		viewpager= (LoopViewPager)view.findViewById(R.id.viewpager);
		pointlayout= (LinearLayout)view.findViewById(R.id.pointlayout);
	}

	private void initView() {
		((TextView) view.findViewById(R.id.tv_main_top_title)).setText("书架");
		main_top_right.setText("已购");
		main_top_right.setOnClickListener(rightListener);
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");

		BookShelfAdapter adapter = new BookShelfAdapter(mContext, list);
		gridv_book.setAdapter(adapter);
		gridv_book.setOnItemClickListener(gridItemListener);
		
		
		List<String> listPager = new ArrayList<String>();
		listPager.add("");
		listPager.add("");
		listPager.add("");
		
		BookPagerAdapter pageAdapter = new BookPagerAdapter(mContext, listPager);
		viewpager.setAdapter(pageAdapter);
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(viewpagerListener);
		
		pointView = new PointView(getActivity(), listPager.size());
		pointlayout.removeAllViews();
		pointlayout.addView(pointView);
		pointView.setPosition(0);
		pointlayout.postInvalidate();
		
	}


	@Override
	public void onResume() {
		super.onResume();


	}

	private  OnPageChangeListener viewpagerListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {

			// TODO Auto-generated method stub
			int size = viewpager.getAdapter().getCount();
			pointView.setPosition(position % size);
			pointView.postInvalidate();
		
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};

	private OnItemClickListener gridItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ToastUtil.showToast(mContext, position + "", ToastUtil.LENGTH_LONG);
			Intent it = new Intent();

			it.setClass(mContext, ReadActivity.class);
			getResources().openRawResource(R.raw.book0);

			String path = (String) listItem.get(0).get("path");
			//it.putExtra("aaa", path);getString(R.string.bpath)
			it.putExtra(getString(R.string.bpath), path);
			startActivity(it);

		}
	};

	private OnClickListener rightListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (EReaderApplication.getInstance().isLogin()){
				IntentUtil.intent(mContext, SearchBuyActivity.class);
			}else{
				IntentUtil.intent(mContext, LoginActivity.class);
			}

		}
	};

	class AsyncSetApprove extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			if (!isInit) {
				File path = mContext.getFilesDir();
				String[] strings = getResources()
						.getStringArray(R.array.bookid);// 获取assets目录下的文件列表
				for (int i = 0; i < strings.length; i++) {
					try {
						FileOutputStream out = new FileOutputStream(path + "/"
								+ strings[i]);
						BufferedInputStream bufferedIn = new BufferedInputStream(
								getResources().openRawResource(R.raw.book0 + i));
						BufferedOutputStream bufferedOut = new BufferedOutputStream(
								out);
						byte[] data = new byte[2048];
						int length = 0;
						while ((length = bufferedIn.read(data)) != -1) {
							bufferedOut.write(data, 0, length);
						}
						// 将缓冲区中的数据全部写出
						bufferedOut.flush();
						// 关闭流
						bufferedIn.close();
						bufferedOut.close();
						sp.edit().putBoolean("isInit", true).commit();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				ArrayList<HashMap<String, String>> insertList = new ArrayList<HashMap<String, String>>();
				File[] f1 = path.listFiles();
				int len = f1.length;
				for (int i = 0; i < len; i++) {
					if (f1[i].isFile()) {
						if (f1[i].toString().contains(".txt")) {
							HashMap<String, String> insertMap = new HashMap<String, String>();
							insertMap.put("parent", f1[i].getParent());
							insertMap.put("path", f1[i].toString());
							insertList.add(insertMap);
						}
					}
				}
				SQLiteDatabase db = localbook.getWritableDatabase();
				db.delete("localbook", "type='" + 2 + "'", null);

				for (int i = 0; i < insertList.size(); i++) {
					try {
						if (insertList.get(i) != null) {
							String s = insertList.get(i).get("parent");
							String s1 = insertList.get(i).get("path");
							String sql1 = "insert into " + "localbook"
									+ " (parent,path" + ", type"
									+ ",now,ready) values('" + s + "','" + s1
									+ "',2,0,null" + ");";
							db.execSQL(sql1);
						}
					} catch (SQLException e) {
						Log.e(TAG, "setApprove SQLException", e);
					} catch (Exception e) {
						Log.e(TAG, "setApprove Exception", e);
					}
				}
				db.close();
			}
			isInit = true;
			sp.edit().putBoolean("isInit", true).commit();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			local();
			super.onPostExecute(result);
		}
	}

	/**
	 * 获取SD卡根目录
	 * 
	 * @return
	 */
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();
	}

	/**
	 * 本地书库载入
	 */
	public void local() {
		SQLiteDatabase db = localbook.getReadableDatabase();
		String col[] = { "path" };
		Cursor cur = db.query("localbook", col, "type=1", null, null, null,
				null);
		Cursor cur1 = db.query("localbook", col, "type=2", null, null, null,
				null);
		Integer num = cur.getCount();
		Integer num1 = cur1.getCount();
		ArrayList<String> arraylist = new ArrayList<String>();
		while (cur1.moveToNext()) {
			String s = cur1.getString(cur1.getColumnIndex("path"));
			arraylist.add(s);
		}
		while (cur.moveToNext()) {
			String s = cur.getString(cur.getColumnIndex("path"));
			arraylist.add(s);
		}
		db.close();
		cur.close();
		cur1.close();
		if (listItem == null)
			listItem = new ArrayList<HashMap<String, Object>>();
		listItem.clear();
		String[] bookids = getResources().getStringArray(R.array.bookid);
		String[] booknames = getResources().getStringArray(R.array.bookname);
		String[] bookauthors = getResources()
				.getStringArray(R.array.bookauthor);
		Map<String, String[]> maps = new HashMap<String, String[]>();
		for (int i = 0; i < bookids.length; i++) {
			String[] value = new String[2];
			value[0] = booknames[i];
			value[1] = bookauthors[i];
			maps.put(bookids[i], value);
		}
		for (int i = 0; i < num + num1; i++) {
			if (i < num1) {
				File file1 = new File(arraylist.get(i));
				String m = file1.getName().substring(0,
						file1.getName().length() - 4);
				if (m.length() > 8) {
					m = m.substring(0, 8) + "...";
				}
				String id = arraylist.get(i).substring(
						arraylist.get(i).lastIndexOf("/") + 1);
				String[] array = maps.get(id);
				String auther = array != null && array[1] == null ? "未知"
						: array[1];
				String name = array[0] == null ? m : array[0];
				map = new HashMap<String, Object>();

				if (i == 0) {
					map.put("itemback", R.drawable.itemback);
				} else if ((i % 2) == 0) {
					map.put("itemback", R.drawable.itemback);
				}
				map.put("ItemImage",
						map2 != null ? map2.get(file1.getName())[0]
								: R.drawable.cover);
				map.put("BookName", "");
				map.put("ItemTitle", name == null ? m : name);
				map.put("ItemTitle1", "作者：" + auther);
				map.put("LastImage", "推荐书目");
				map.put("path", file1.getPath());
				map.put("com", 0 + file1.getName());// 单独用于排序
				listItem.add(map);
			} else {
				map = new HashMap<String, Object>();

				File file1 = new File(arraylist.get(i));
				String m = file1.getName().substring(0,
						file1.getName().length() - 4);
				if (m.length() > 8) {
					m = m.substring(0, 8) + "...";
				}
				if (i == 0) {
					map.put("itemback", R.drawable.itemback);
				} else if ((i % 2) == 0) {
					map.put("itemback", R.drawable.itemback);
				}
				map.put("ItemImage", R.drawable.cover);
				map.put("BookName", m);
				map.put("ItemTitle", m);
				map.put("ItemTitle1", "作者：未知");
				map.put("LastImage", "本地导入");
				map.put("path", file1.getPath());
				map.put("com", "1");
				listItem.add(map);
			}
		}
	}
}
