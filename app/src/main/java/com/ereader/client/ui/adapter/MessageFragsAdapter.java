package com.ereader.client.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.entities.Category;
import com.ereader.client.ui.my.MessageFragment;
import com.ereader.client.ui.my.MessageFriendsFragment;

public class MessageFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public MessageFragsAdapter(FragmentManager fm,List<Category> mListTitle) {
		super(fm);
		fragments = new ArrayList<Fragment>();
			fragments.add(new MessageFriendsFragment(mListTitle.get(0)));
			fragments.add(new MessageFragment(mListTitle.get(1)));
			fragments.add(new MessageFragment(mListTitle.get(2)));
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
