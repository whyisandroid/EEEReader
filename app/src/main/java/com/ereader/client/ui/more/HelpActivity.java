package com.ereader.client.ui.more;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.json.ArticleData;
import com.ereader.client.entities.json.ArticleList;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.HelpAdapter;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private RelativeLayout rl_help_info;
	private ListView lv_help;
	private List<ArticleList> dataList = new ArrayList<ArticleList>();
	private HelpAdapter adapter;

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					dataList.clear();
					dataList.addAll((List<ArticleList>)controller.getContext().getBusinessData("ArticleResp"));
					adapter.notifyDataSetChanged();
					break;
				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help__layout);
		controller = AppController.getController(this);
		findView();
		initView();

		ProgressDialogUtil.showProgressDialog(this, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getArticle(mHandler);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}

	/**
	 * 
	 * 方法描述：FindView
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		rl_help_info = (RelativeLayout) findViewById(R.id.rl_help_info);
		lv_help = (ListView)findViewById(R.id.lv_help);

		adapter = new HelpAdapter(this,dataList);
		lv_help.setAdapter(adapter);
		lv_help.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("ArticleList", dataList.get(position));
				IntentUtil.intent(HelpActivity.this, bundle, NoticeActivity.class, false);
			}
		});
	}

	/**
	 * 
	 * 方法描述：初始化 View
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("帮助中心");
		rl_help_info.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_help_info:
				/*ProgressDialogUtil.showProgressDialog(this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						PageRq page = new PageRq();
						controller.getNotice(page);
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();*/

				IntentUtil.intent(HelpActivity.this, Notice2Activity.class);
				break;
		default:
			break;
		}
	}
}
