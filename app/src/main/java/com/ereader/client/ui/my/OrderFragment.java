package com.ereader.client.ui.my;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.ereader.client.R;
import com.ereader.client.entities.Order;
import com.ereader.client.entities.OrderList;
import com.ereader.client.entities.json.OrderData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.adapter.BookAdapter;
import com.ereader.client.ui.adapter.OrderAdapter;
import com.ereader.client.ui.bookstore.BookDetailActivity;
import com.ereader.client.ui.pay.PayActivity;
import com.ereader.client.ui.view.PullToRefreshView;
import com.ereader.client.ui.view.PullToRefreshView.OnFooterRefreshListener;
import com.ereader.client.ui.view.PullToRefreshView.OnHeaderRefreshListener;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;
import com.glview.widget.Toast;

@SuppressLint("ValidFragment")
public class OrderFragment extends Fragment implements OnClickListener,
OnHeaderRefreshListener, OnFooterRefreshListener{
	private View view;
	private Context mContext;
	private AppController controller;

	private ListView lv_order;
	private PullToRefreshView pull_refresh_order;
	private List<OrderList> mOrderList = new ArrayList<OrderList>();
	private OrderAdapter adapter;
	private String mOrderType = "";

	public static final int REFRESH_DOWN_OK = 1; // 向下刷新
	public static final int REFRESH_UP_OK = 2;  //向上拉
	public static final int CANCEL= 3;  //取消订单
	public static final int PAY_OK = 4;  //支付订单
	private Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_DOWN_OK:
				OrderData orderData = (OrderData)controller.getContext().getBusinessData("OrderListResp"+mOrderType);
				mOrderList.clear();
				mOrderList.addAll(orderData.getData());
				//ToastUtil.showToast(mContext, "刷新成功！", ToastUtil.LENGTH_LONG);
				pull_refresh_order.onHeaderRefreshComplete();
				adapter.notifyDataSetChanged();
				break;
			case REFRESH_UP_OK:
				adapter.notifyDataSetChanged();
				pull_refresh_order.onFooterRefreshComplete();
				break;
				case CANCEL:
					int position = Integer.valueOf(msg.obj.toString());
					mOrderList.remove(position);
					adapter.notifyDataSetChanged();
				break;
				case  11:
					IntentUtil.intent(mContext, PayActivity.class);
					break;
			default:
				break;
			}
		};
	};

	@SuppressLint("ValidFragment")
	public OrderFragment(String type){
		this.mOrderType = type;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.my_order_fragment, container, false);
		controller = AppController.getController(getActivity());
		mContext = getActivity();
		findView();
		initView();
		return view;
	}
	private void findView() {
		lv_order= (ListView)view.findViewById(R.id.lv_order);
		pull_refresh_order = (PullToRefreshView)view.findViewById(R.id.pull_refresh_order);
	}
	private void initView() {
		pull_refresh_order.setOnHeaderRefreshListener(this);
		pull_refresh_order.setOnFooterRefreshListener(this);
		adapter = new OrderAdapter(mContext, mOrderList,mhandler);
		lv_order.setAdapter(adapter);
		lv_order.setOnItemClickListener(orderItemListener);
	}

	@Override
	public void onResume() {
		super.onResume();
		getOrderList();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 0){
			String orderId = data.getExtras().getString("orderId");
		}
	}

	public void getOrderList() {
		//ProgressDialogUtil.showProgressDialog(mContext, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.getOrderList(mhandler,mOrderType);
				//ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}

	private OnItemClickListener orderItemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			IntentUtil.intent(mContext, BookDetailActivity.class);
		}
	};
	
	
	
	
	@Override
	public void onClick(View v) {
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {

		mhandler.sendEmptyMessageDelayed(REFRESH_UP_OK, 3000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		getOrderList();
		//mhandler.sendEmptyMessageDelayed(REFRESH_DOWN_OK, 3000);
	}
}
