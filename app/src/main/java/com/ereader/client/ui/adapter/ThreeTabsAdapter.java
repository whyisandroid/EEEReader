package com.ereader.client.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.ui.view.TabsAdapter;

import java.util.List;

public class ThreeTabsAdapter implements TabsAdapter{
	private LayoutInflater inflater;
	public List<String> mlist;

	public ThreeTabsAdapter(Context mContext, List<String> mlist) {
		inflater=LayoutInflater.from(mContext);
		this.mlist = mlist;
	}
	
	@Override
	public View getView(int position) {
		TextView layout = (TextView)inflater.inflate(R.layout.tab_three, null);
		layout.setText(mlist.get(position));
		return layout;
	}
}
