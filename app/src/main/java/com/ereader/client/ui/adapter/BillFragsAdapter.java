package com.ereader.client.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ereader.client.entities.Category;
import com.ereader.client.ui.bookstore.BookFragment;
import com.ereader.client.ui.pay.BillFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BillFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public BillFragsAdapter(FragmentManager fm,List<Category> mList) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < mList.size(); i++) {
			fragments.add(new BillFragment(mList.get(i)));
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
