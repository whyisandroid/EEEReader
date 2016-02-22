package com.ereader.client.ui.more;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Notice;
import com.ereader.client.entities.Page;
import com.ereader.client.entities.json.NoticeData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.BookAdapter;
import com.ereader.client.ui.adapter.NoticeAdapter;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.common.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

public class NoticeRealActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private ListView lv_more_notice;

	private PullToRefreshView pull_refresh_book;
	private List<Notice> mList = new ArrayList<Notice>();
	private BookAdapter adapter;
	private Page page;

	public static final int BOOK = 0; // 更新页面数据
	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	public static final int REFRESH_ERROR = 3; // 刷新失败
	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case BOOK:
					// 更新页面数据
					NoticeData noticeData = (NoticeData) controller.getContext().getBusinessData("NoticeResp");
					for (int i = 0; i < noticeData.getData().size(); i++) {
						boolean flag = true;

						for (int j = 0; j < mList.size(); j++) {
							if(noticeData.getData().get(i).getId().equals(mList.get(j).getId())) {
								flag = false;
							}
						}
						if(flag){
							mList.add(noticeData.getData().get(i));
						}
					}
					page = noticeData.getPage();
					adapter.notifyDataSetChanged();
					pull_refresh_book.onHeaderRefreshComplete();
					pull_refresh_book.onFooterRefreshComplete();
					break;
				case REFRESH_DOWN_OK:
					pull_refresh_book.onHeaderRefreshComplete();
					break;
				case REFRESH_UP_OK:
					adapter.notifyDataSetChanged();
					pull_refresh_book.onFooterRefreshComplete();
					break;
				case REFRESH_ERROR:
					pull_refresh_book.onHeaderRefreshComplete();
					pull_refresh_book.onFooterRefreshComplete();
					break;

				default:
					break;
			}
		}

		;
	};

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
		((TextView) findViewById(R.id.tv_main_top_title)).setText("公告");
		NoticeAdapter adapter = new NoticeAdapter(this,null);
		lv_more_notice.setAdapter(adapter);
		lv_more_notice.setOnItemClickListener(noticeItemListener);
	}
	
	private OnItemClickListener noticeItemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2,
				long arg3) {
			
				ProgressDialogUtil.showProgressDialog(NoticeRealActivity.this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.getNoticeDetail(mList.get(arg2).getId());
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
