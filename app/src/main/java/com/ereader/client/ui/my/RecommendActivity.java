package com.ereader.client.ui.my;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.CollectionAdapter;
import com.ereader.client.ui.adapter.RecommendAdapter;
// 推荐
public class RecommendActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private ListView lv_my_recommend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_recommend_layout);
		controller = AppController.getController(this);
		findView();
		initView();
	}
	/**
	 * 
	  * 方法描述：FindView
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		lv_my_recommend = (ListView)findViewById(R.id.lv_my_recommend);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("我的推荐");
		List<String> mList = new ArrayList<String>();
		RecommendAdapter adapter = new RecommendAdapter(this, mList);
		lv_my_recommend.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case  R.id.textView1:
			break;
		default:
			break;
		}
	}
}
