package com.ereader.client.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.entities.Category;
import com.ereader.client.entities.DisCategory;
import com.ereader.client.ui.bookstore.BookFragment;

public class BookFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public BookFragsAdapter(FragmentManager fm,List<Category> list) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < list.size(); i++) {
			fragments.add(new BookFragment(list.get(i))); 
		}
	}
	public BookFragsAdapter(FragmentManager fm,List<DisCategory> list,int category) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < list.size(); i++) {
			fragments.add(new BookFragment(list.get(i),category)); 
		}
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
}
