package com.ereader.client.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.entities.Category;
import com.ereader.client.ui.my.MesFragment;
import com.ereader.client.ui.my.MessageFragment;
import com.ereader.client.ui.my.MessageFriendApplyFragment;
import com.ereader.client.ui.my.MessageSystemFragment;

public class MessageFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<MesFragment> fragments;

	public MessageFragsAdapter(FragmentManager fm,List<Category> mListTitle) {
		super(fm);
		fragments = new ArrayList<MesFragment>();
		fragments.add(new MessageFriendApplyFragment());
		fragments.add(new MessageFragment());
		fragments.add(new MessageSystemFragment());
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public MesFragment getItem(int position) {
		return fragments.get(position);
	}
}
