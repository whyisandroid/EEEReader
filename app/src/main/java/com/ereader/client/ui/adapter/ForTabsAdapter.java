package com.ereader.client.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.ui.view.TabsAdapter;

public class ForTabsAdapter implements TabsAdapter{
	private LayoutInflater inflater;
	public List<String> mlist;
	
	public ForTabsAdapter(Context mContext, List<String> mlist) {
		inflater=LayoutInflater.from(mContext);
		this.mlist = mlist;
	}
	
	@Override
	public View getView(int position) {
		TextView layout = (TextView)inflater.inflate(R.layout.tab_for, null);
		layout.setText(mlist.get(position));
		return layout;
	}
}
