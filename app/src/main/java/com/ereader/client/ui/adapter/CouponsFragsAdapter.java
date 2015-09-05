package com.ereader.client.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.entities.Category;
import com.ereader.client.ui.my.CouponsFragment;

public class CouponsFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public CouponsFragsAdapter(FragmentManager fm,List<Category> mList) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		fragments.add(new CouponsFragment(mList.get(1)));
		fragments.add(new CouponsFragment(mList.get(2)));
		fragments.add(new CouponsFragment(mList.get(3)));
		fragments.add(new CouponsFragment(mList.get(4)));
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
