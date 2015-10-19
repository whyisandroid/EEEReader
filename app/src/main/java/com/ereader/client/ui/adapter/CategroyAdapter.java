package com.ereader.client.ui.adapter;

import java.util.List;

import com.ereader.client.R;
import com.ereader.client.entities.Category;
import com.ereader.client.entities.SubCategory;
import com.ereader.client.ui.bookstore.BookActivity;
import com.ereader.common.util.IntentUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategroyAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<SubCategory> sList;
	
	public CategroyAdapter(Context mContext,List<SubCategory> sList) {
		this.mContext = mContext;
		this.sList = sList;
		inflater=LayoutInflater.from(mContext);
	}
	@Override
	public int getGroupCount() {
		return sList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return sList.get(groupPosition).getSub() == null?0:sList.get(groupPosition).getSub().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return sList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return sList.get(groupPosition).getSub().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		 	SubCategory sub = sList.get(groupPosition);
			convertView =inflater.inflate(R.layout.book_categroy_grop, null);
			((TextView)convertView.findViewById(R.id.tv_category_grop)).setText(sub.getName());
			ImageView iv_categroy_grop = (ImageView)convertView.findViewById(R.id.iv_categroy_grop);
			if(isExpanded){
				iv_categroy_grop.setImageResource(R.drawable.b10_04);
			}else{
				iv_categroy_grop.setImageResource(R.drawable.b10_03);
			}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition,final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView =inflater.inflate(R.layout.book_categroy_child, null);
		final List<Category> cList = sList.get(groupPosition).getSub();
		((TextView)convertView.findViewById(R.id.tv_category_child)).setText(cList.get(childPosition).getName());
		((TextView)convertView.findViewById(R.id.tv_category_child)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("title", cList.get(childPosition).getName());
				bundle.putString("categroyItem_id", cList.get(childPosition).getCategory_id());
				IntentUtil.intent(mContext, bundle, BookActivity.class, false);
			}
		});
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}
