package com.ereader.client.ui.more;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Article;
import com.ereader.client.entities.json.ArticleData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.NoticeAdapter;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

public class NoticeActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private ListView lv_more_notice;
	private List<Article> mList = new ArrayList<Article>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_notice_layout);
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
		lv_more_notice = (ListView)findViewById(R.id.lv_more_notice);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		String id = getIntent().getExtras().getString("id");
		String title = "";
		if("10".equals(id)){
			title = "公告";
		}else if("20".equals(id)){
			title = "购物指南";
		}else{
			title = "支付方式";
		}
		((TextView) findViewById(R.id.tv_main_top_title)).setText(title);
		
		ArticleData data = (ArticleData)controller.getContext().getBusinessData("ArticleResp");
		mList.clear();
		mList = data.getData();
		NoticeAdapter adapter = new NoticeAdapter(this,data.getData());
		lv_more_notice.setAdapter(adapter);
		lv_more_notice.setOnItemClickListener(noticeItemListener);
	}
	
	private OnItemClickListener noticeItemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2,
				long arg3) {
			
				ProgressDialogUtil.showProgressDialog(NoticeActivity.this, "通信中…", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.getArticleDetail(mList.get(arg2).getArticle_id());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
		}
	};

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
