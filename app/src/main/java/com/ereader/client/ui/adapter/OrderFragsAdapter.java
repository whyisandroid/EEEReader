package com.ereader.client.ui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.ui.my.OrderFragment;

public class OrderFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public OrderFragsAdapter(FragmentManager fm,int length) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < length; i++) {
			fragments.add(new OrderFragment()); 
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
