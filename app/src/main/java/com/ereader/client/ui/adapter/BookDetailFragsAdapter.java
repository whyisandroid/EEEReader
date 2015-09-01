package com.ereader.client.ui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ereader.client.entities.Book;
import com.ereader.client.ui.bookstore.BookDetailFragment;
import com.ereader.client.ui.bookstore.BookSPFragment;

public class BookDetailFragsAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragments;

	public BookDetailFragsAdapter(FragmentManager fm,int length,Book book) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		fragments.add(new BookDetailFragment(book.getExtra().getContents())); 
		fragments.add(new BookDetailFragment(book.getInfo().getDescription()));
		fragments.add(new BookDetailFragment(book.getExtra().getAuthor_info()));
		fragments.add(new BookSPFragment(book));
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
