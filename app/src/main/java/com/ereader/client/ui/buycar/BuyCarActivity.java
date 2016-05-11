package com.ereader.client.ui.buycar;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.PayCar;
import com.ereader.client.entities.PayCarList;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.entities.json.WalletData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.BuyCarAdapter;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.pay.PayActivity;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.Json_U;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

// 购物车
public class BuyCarActivity extends BaseActivity implements OnClickListener {
	private AppController controller;
	private TextView tv_buy_money;
	private ListView lv_buy_car;
	private List<Book> mList = new ArrayList<Book>();
	private BuyCarAdapter adapter;
	private Button bt_buy_go;
	private CheckBox rb_car_all;
	private int buyNum = 0;
	private String money = "0";
	private TextView tv_buycar_delete;

	private EditText et_pay_point;
	private CheckBox ck_pay_point;
	private TextView tv_pay_point_sum;
	private LinearLayout ll_pay_point;

	private String pointPay;


	public static final int  ORDER_SUCCESS = 4 ;

	public static boolean isFresh = true;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case -1:
					mList.clear();
					adapter.notifyDataSetChanged();
					// 更改金额 和结算数量
					checkMoney();
					EReaderApplication.getInstance().saveBuyCar(null);
					break;
			case 0:
				mList.clear();
				mList.addAll((List<Book>) controller.getContext()
						.getBusinessData("BuyCarResp"));
				adapter.notifyDataSetChanged();
				// 更改金额 和结算数量
				checkMoney();
				saveData();
				break;
			case 1:
				int position = Integer.valueOf(msg.obj.toString());
				mList.get(position).setSelect(true);
				adapter.notifyDataSetChanged();
				checkMoney();
				BookOnlyResp resp1 = EReaderApplication.getInstance().getBuyCar();
				resp1.getData().get(position).setSelect(true);
				EReaderApplication.getInstance().saveBuyCar(resp1);
				break;
			case 2:
				int position2 = Integer.valueOf(msg.obj.toString());
				mList.get(position2).setSelect(false);
				adapter.notifyDataSetChanged();
				checkMoney();
				BookOnlyResp resp2 = EReaderApplication.getInstance().getBuyCar();
				resp2.getData().get(position2).setSelect(false);
				EReaderApplication.getInstance().saveBuyCar(resp2);
				break;
			case 3:
				BookOnlyResp resp3 = EReaderApplication.getInstance().getBuyCar();
				int size = mList.size();
				for (int i = size-1; i >=0; i--) {
					if (mList.get(i).isSelect()) {
						mList.remove(i);
						resp3.getData().remove(i);
					}
				}
				EReaderApplication.getInstance().saveBuyCar(resp3);
				checkMoney();
				adapter.notifyDataSetChanged();
				break;
				case ORDER_SUCCESS:
					IntentUtil.intent(BuyCarActivity.this, PayActivity.class);
					break;
				case  11: //获取 point 成功
					WalletData wallet = (WalletData) controller.getContext().getBusinessData("WalletResp");
					ck_pay_point.setText("积分("+wallet.getPoint()+"点)");
					break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_car_layout);
		controller = AppController.getController(this);
		findView();
		initView();
		getPoint();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isFresh){
			getCar();
		}else{
			isFresh = true;
		}
	}

	private void getCar() {
		if (!EReaderApplication.getInstance().isLogin()) {
			IntentUtil.intent(BuyCarActivity.this, LoginActivity.class);
			this.finish();
		} else {
			ProgressDialogUtil.showProgressDialog(this, "", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					controller.buyCar(mHandler);
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
		}
	}

	private void  getPoint(){
		ProgressDialogUtil.showProgressDialog(BuyCarActivity.this, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.wallet(mHandler);
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
		lv_buy_car = (ListView) findViewById(R.id.lv_buy_car);
		bt_buy_go = (Button) findViewById(R.id.bt_buy_go);
		tv_buy_money = (TextView) findViewById(R.id.tv_buy_money);
		tv_buycar_delete = (TextView) findViewById(R.id.tv_buycar_delete);
		rb_car_all = (CheckBox) findViewById(R.id.rb_car_all);

		tv_pay_point_sum = (TextView) findViewById(R.id.tv_pay_point_sum);
		ck_pay_point = (CheckBox) findViewById(R.id.ck_pay_point);
		et_pay_point = (EditText) findViewById(R.id.et_pay_point);
		ll_pay_point = (LinearLayout) findViewById(R.id.ll_pay_point);
	}

	/**
	 * 
	 * 方法描述：初始化 View
	 * 
	 * @author: why
	 * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		((TextView) findViewById(R.id.tv_main_top_title)).setText("购物车");
		bt_buy_go.setOnClickListener(this);
		BookOnlyResp resp = (BookOnlyResp) EReaderApplication.getInstance()
				.getBuyCar();
		if (resp != null) {
			mList.clear();
			mList.addAll(resp.getData());

			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i).isSelect()) {
					buyNum++;
					money = StringUtil.addMoney(money, mList.get(i).getPrice());
				}
			}
		}
		tv_buy_money.setText("¥ " + money);
		bt_buy_go.setText("结算（" + buyNum + ")");
		adapter = new BuyCarAdapter(this, mList, mHandler);
		lv_buy_car.setAdapter(adapter);
		rb_car_all.setChecked(true);
		rb_car_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mList.size() == 0) {
					ToastUtil.showToast(BuyCarActivity.this, "没有可选择商品", ToastUtil.LENGTH_LONG);
					return;
				}
				if (isChecked) {
					money = "0";
					buyNum = 0;
					for (int i = 0; i < mList.size(); i++) {
						mList.get(i).setSelect(true);
					}
				} else {
					money = "0";
					buyNum = 0;
					for (int i = 0; i < mList.size(); i++) {
						mList.get(i).setSelect(false);
					}
				}
				checkMoney();
				saveData();
				adapter.notifyDataSetChanged();
			}
		});
		tv_buycar_delete.setOnClickListener(this);



		ck_pay_point.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					ll_pay_point.setVisibility(View.VISIBLE);
				} else {
					et_pay_point.setText("");
					ll_pay_point.setVisibility(View.INVISIBLE);
				}
			}
		});


		et_pay_point.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().startsWith("0")){
					et_pay_point.setText("");
					return;
				}
				point(s.toString());
			}
		});
	}


	// 处理分数
	private void point(String s){
		WalletData wallet = (WalletData) controller.getContext().getBusinessData("WalletResp");
		if(TextUtils.isEmpty(s)){
			pointPay = "0.00";
		}else {
			if (Double.valueOf(StringUtil.subtractionMoney(wallet.getPoint(), s.toString())) < 0) {
				ToastUtil.showToast(BuyCarActivity.this, "没有这么多积分", ToastUtil.LENGTH_LONG);
				et_pay_point.setText(wallet.getPoint());
				pointPay = StringUtil.div(wallet.getPoint(), wallet.getP2e_exchange_rate(), 2);
			} else {
				pointPay = StringUtil.div(s.toString(), wallet.getP2e_exchange_rate(), 2);

				if(Double.valueOf(StringUtil.subtractionMoney(money,pointPay)) < 0){
					ToastUtil.showToast(BuyCarActivity.this, "所填积分已经超过购买书本价格！", ToastUtil.LENGTH_LONG);
					pointPay = "0";
					//String point = StringUtil.mul(order.getPay_total(),"100");
					et_pay_point.setText("");
				}
			}
		}
		tv_pay_point_sum.setText("-¥" + pointPay);
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_buy_go:
			boolean flag = false;
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i).isSelect()) {
					flag = true;
				}
			}

			if(!flag){
				ToastUtil.showToast(BuyCarActivity.this, "没有可支付商品", ToastUtil.LENGTH_LONG);
				return;
			}

			// 生成订单
			try {
				final String  orderData = getOrderMessage();
				ProgressDialogUtil.showProgressDialog(this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.getOrderId(mHandler, orderData,et_pay_point.getText().toString());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			break;
		case R.id.tv_buycar_delete:
			StringBuffer deleteID = new StringBuffer();
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i).isSelect()) {
					deleteID.append(mList.get(i).getProduct_id()).append(",");
				}
			}
			if(TextUtils.isEmpty(deleteID.toString())){
				ToastUtil.showToast(BuyCarActivity.this, "请选择要删除的书", ToastUtil.LENGTH_LONG);
			}else{
				// 
				deleteCar(deleteID.toString());
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @return
	 */
	private String getOrderMessage() throws BusinessException{
		PayCarList pList = new PayCarList();
		for (int i = 0 ; i < mList.size(); i++){
			Book book = mList.get(i);
			if(book.isSelect()){
				pList.getmPayCarList().add(new PayCar(book.getInfo().getProduct_id()));
			}
		}
		String jsonData = Json_U.objToJsonStr(pList);
		return jsonData.substring(15,jsonData.length()-1);
	}


	private void deleteCar(final String id) {
		ProgressDialogUtil.showProgressDialog(this, "", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				controller.deleteBuyCar(mHandler,id);
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}

	/**
	 * 
	 */
	private void checkMoney() {
		money = "0";
		buyNum = 0;
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).isSelect()) {
				buyNum++;
				money = StringUtil.addMoney(money, mList.get(i).getPrice());
			}
		}
		tv_buy_money.setText("¥ " + money);
		bt_buy_go.setText("结算（" + buyNum + ")");
	}

	// 改变 data
	private void saveData() {
		BookOnlyResp resp = (BookOnlyResp) EReaderApplication.getInstance()
				.getBuyCar();
		for (int i = 0; i < resp.getData().size(); i++) {
				resp.getData().get(i).setSelect(true);
		}
		EReaderApplication.getInstance().saveBuyCar(resp);
	}
}
