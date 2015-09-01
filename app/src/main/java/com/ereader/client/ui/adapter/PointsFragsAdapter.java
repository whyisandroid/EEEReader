package com.ereader.client.ui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.ui.my.PointsFragment;

public class PointsFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public PointsFragsAdapter(FragmentManager fm,int length) {
		super(fm);
		fragments = new ArrayList<Fragment>();
			fragments.add(new PointsFragment()); 
			fragments.add(new PointsFragment()); 
			fragments.add(new PointsFragment()); 
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
