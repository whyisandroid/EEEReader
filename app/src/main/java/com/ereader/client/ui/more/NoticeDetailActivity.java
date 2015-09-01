package com.ereader.client.ui.more;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Article;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;

public class NoticeDetailActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView tv_notice_title;
	private TextView tv_notice_detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_notice_detail_layout);
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
		tv_notice_title = (TextView)findViewById(R.id.tv_notice_title);
		tv_notice_detail = (TextView)findViewById(R.id.tv_notice_detail);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		Article article = (Article)controller.getContext().getBusinessData("ArticleDetailResp");
		String title = "";
		if("10".equals(article.getParent_id())){
			title = "公告";
		}else if("20".equals(article.getParent_id())){
			title = "购物指南";
		}else{
			title = "支付方式";
		}
		((TextView) findViewById(R.id.tv_main_top_title)).setText(title);
		tv_notice_title.setText(article.getTitle());
		tv_notice_detail.setText(article.getContent());
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
