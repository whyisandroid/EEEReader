package com.ereader.client.ui.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Order;
import com.ereader.client.entities.json.WalletData;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.my.FriendsActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

public class PayActivity extends BaseActivity implements OnClickListener {
    private AppController controller;
    private TextView tv_pay_money;
    private TextView tv_pay_all_money;// 实际付款
    private CheckBox ck_pay_point;
    private CheckBox ck_pay_friend;
    private EditText et_pay_point;
    private EditText et_pay_friend;
    private TextView tv_pay_point;
    private TextView tv_pay_point_sum;
    private Button main_top_right;
    private ImageButton main_top_left;
    private Button bt_pay_go; //支付
    private String money;
    private RelativeLayout rl_pay_point;
    private RelativeLayout rl_pay_friend;

    private Order order;
    private String pointPay = "0"; //积分折扣的金额
    private String payFriendID = "";// 送朋友的 ID


    public static final int SUCCESS = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                   // ToastUtil.showToast(PayActivity.this, "购买成功", ToastUtil.LENGTH_LONG);
                    IntentUtil.intent(PayActivity.this, PaySuccessActivity.class);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_layout);
        controller = AppController.getController(this);
        findView();
        initView();
    }

    /**
     * 方法描述：FindView
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void findView() {
        tv_pay_money = (TextView) findViewById(R.id.tv_pay_money);
        tv_pay_all_money = (TextView) findViewById(R.id.tv_pay_all_money);
        ck_pay_point = (CheckBox) findViewById(R.id.ck_pay_point);
        ck_pay_friend = (CheckBox) findViewById(R.id.ck_pay_friend);
        et_pay_point = (EditText) findViewById(R.id.et_pay_point);
        et_pay_friend = (EditText) findViewById(R.id.et_pay_friend);
        tv_pay_point = (TextView) findViewById(R.id.tv_pay_point);
        tv_pay_point_sum = (TextView) findViewById(R.id.tv_pay_point_sum);
        main_top_right = (Button) findViewById(R.id.main_top_right);
        main_top_left = (ImageButton) findViewById(R.id.main_top_left);
        bt_pay_go = (Button) findViewById(R.id.bt_pay_go);
        rl_pay_point = (RelativeLayout) findViewById(R.id.rl_pay_point);
        rl_pay_friend = (RelativeLayout) findViewById(R.id.rl_pay_friend);
    }

    /**
     * 方法描述：初始化 View
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {
        final WalletData wallet = (WalletData) controller.getContext().getBusinessData("WalletResp");
        ((TextView) findViewById(R.id.tv_main_top_title)).setText("支付");
        main_top_right.setOnClickListener(this);
        main_top_left.setOnClickListener(this);
        bt_pay_go.setOnClickListener(this);
        main_top_right.setText("充值");
        order = (Order) controller.getContext().getBusinessData("OrderResp");
        money = order.getPay_total();
        tv_pay_all_money.setText("¥ " + money);
        tv_pay_point.setText("(可用" + wallet.getPoint() + "点)");
        tv_pay_point_sum.setText("-¥ 0.00");

        ck_pay_friend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_pay_friend.setVisibility(View.VISIBLE);
                    et_pay_friend.setFocusable(true);
                    et_pay_friend.setFocusableInTouchMode(true);
                    et_pay_friend.requestFocus();
                } else {
                    et_pay_friend.setVisibility(View.GONE);
                }
            }
        });

        et_pay_friend.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(PayActivity.this, FriendsActivity.class);
                 FriendsActivity.mFriendsSend = 2;
                 startActivityForResult(intent, 0);
             }
        });

        ck_pay_point.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    point(et_pay_point.getText().toString());
                    rl_pay_point.setVisibility(View.VISIBLE);
                } else {
                    tv_pay_all_money.setText("¥ " + money);
                    rl_pay_point.setVisibility(View.GONE);
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
                ToastUtil.showToast(PayActivity.this, "没有这么多积分", ToastUtil.LENGTH_LONG);
                et_pay_point.setText(wallet.getPoint());
                pointPay = StringUtil.div(wallet.getPoint(), wallet.getP2e_exchange_rate(), 2);
            } else {
                pointPay = StringUtil.div(s.toString(), wallet.getP2e_exchange_rate(), 2);

                if(Double.valueOf(StringUtil.subtractionMoney(order.getPay_total(),pointPay)) < 0){
                    ToastUtil.showToast(PayActivity.this, "所填积分已经超过购买书本价格！", ToastUtil.LENGTH_LONG);
                    pointPay = order.getPay_total();
                   String point = StringUtil.mul(order.getPay_total(),"100");
                    et_pay_point.setText(point.split(".")[0]);
                }
            }
        }
        tv_pay_point_sum.setText("-¥" + pointPay);
        tv_pay_all_money.setText("¥ " + StringUtil.subtractionMoney(order.getPay_total(), pointPay));
    }

    @Override
    protected void onResume() {
        super.onResume();
        WalletData wallet = (WalletData) controller.getContext().getBusinessData("WalletResp");
        tv_pay_money.setText("¥ " + wallet.getEcoin());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1){
            et_pay_friend.setText(data.getExtras().getString("friendName"));
            et_pay_friend.setTag(data.getExtras().getString("friendId"));
        }
    }

    String point = "0";
    String payMoney = "0";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.main_top_left:
                onBackPressed();
                break;
            case R.id.main_top_right:
                IntentUtil.intent(PayActivity.this, RechargeActivity.class);
                break;
            case R.id.bt_pay_go:
                if (ck_pay_point.isChecked()&&!TextUtils.isEmpty(et_pay_point.getText().toString())) {
                    point = et_pay_point.getText().toString();
                } else {
                    point = "0";
                }
                WalletData wallet = (WalletData) controller.getContext().getBusinessData("WalletResp");
                String pointPay = StringUtil.div(point, wallet.getP2e_exchange_rate(), 2);
                if(Double.valueOf(pointPay) >= Double.valueOf(order.getPay_total())){
                    payMoney = "0";
                }else{
                    payMoney = StringUtil.subtractionMoney(order.getPay_total(),pointPay);
                }
                if(ck_pay_friend.isChecked()){
                    payFriendID = TextUtils.isEmpty(et_pay_friend.getText().toString())?"":et_pay_friend.getTag().toString();
                }else {
                    payFriendID = "";
                }

                ProgressDialogUtil.showProgressDialog(this, "", false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        controller.pay(mHandler, order.getOrder_id(), payMoney, point, payFriendID);
                        ProgressDialogUtil.closeProgressDialog();
                    }
                }).start();
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BuyCarActivity.isFresh = false;
    }
}
