package com.ereader.client.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.entities.Category;
import com.ereader.client.ui.my.OrderFragment;

public class OrderFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public OrderFragsAdapter(FragmentManager fm,List<Category> mListTitle) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		for (int i = 0;i<mListTitle.size();i++)
			fragments.add(new OrderFragment(mListTitle.get(i).getCategory_id()));
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
