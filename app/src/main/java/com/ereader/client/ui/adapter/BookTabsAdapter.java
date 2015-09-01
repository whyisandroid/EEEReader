package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ereader.client.R;
import com.ereader.client.entities.Category;
import com.ereader.client.entities.DisCategory;
import com.ereader.client.ui.view.TabsAdapter;

public class BookTabsAdapter implements TabsAdapter{
	private LayoutInflater inflater;
	private List<Category> mlist;
	private List<DisCategory> mDislist;
	private int categroy = 0;
	
	public BookTabsAdapter(Context mContext,List<Category> mlist) {
		inflater=LayoutInflater.from(mContext);
		this.mlist = mlist;
	}
	public BookTabsAdapter(Context mContext,List<DisCategory> mDislist,int categroy) {
		inflater=LayoutInflater.from(mContext);
		this.mDislist = mDislist;
		this.categroy = categroy;
	}
	
	@Override
	public View getView(int position) {
		Button layout = (Button)inflater.inflate(R.layout.book_store_new_tab, null);
		if(categroy == 0){
			layout.setText(mlist.get(position).getName());
		}else{
			layout.setText(mDislist.get(position).getName());
		}
		return layout;
	}
}
