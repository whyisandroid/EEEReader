package com.ereader.client.ui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.ui.my.MessageFragment;
import com.ereader.client.ui.my.MessageFriendsFragment;

public class MessageFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public MessageFragsAdapter(FragmentManager fm,int length) {
		super(fm);
		fragments = new ArrayList<Fragment>();
			fragments.add(new MessageFriendsFragment()); 
			fragments.add(new MessageFragment()); 
			fragments.add(new MessageFragment()); 
			fragments.add(new MessageFragment()); 
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
