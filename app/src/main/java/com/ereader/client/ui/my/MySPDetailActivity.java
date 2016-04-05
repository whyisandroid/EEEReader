package com.ereader.client.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.SpComment;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.ProgressDialogUtil;

public class MySPDetailActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView tv_sp_name;
	private TextView tv_book_content;
	private TextView tv_book_date;
	private RatingBar rbar_sp_star;

	private Handler mHandler = new Handler(){
			
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					break;
				default:
					break;
				}
			};
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_sp_detail_layout);
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
		tv_sp_name = (TextView)findViewById(R.id.tv_sp_name);
		tv_book_content = (TextView)findViewById(R.id.tv_book_content);
		tv_book_date = (TextView)findViewById(R.id.tv_book_date);
		rbar_sp_star = (RatingBar)findViewById(R.id.rbar_sp_star);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("书评");
		SpComment sp = (SpComment)getIntent().getExtras().getSerializable("sp");
		tv_sp_name.setText(sp.getTitle());
		tv_book_content.setText(sp.getContent());
		tv_book_date.setText(sp.getCreated_at());
		rbar_sp_star.setRating(Float.valueOf(sp.getScore()));
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
