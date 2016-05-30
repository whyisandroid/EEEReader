package com.ereader.client.ui.pay;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.AddBuy;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.MainFragmentActivity;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/11/13 15:25
 ***************************************/
public class PaySuccessActivity extends BaseActivity implements View.OnClickListener {
    private AppController controller;
    private Button pay_success_bt_buy;
    private Button pay_success_bt_read;
    private TextView tv_success_info;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
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
        setContentView(R.layout.pay_success_layout);
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
        tv_success_info = (TextView)findViewById(R.id.tv_success_info);
        pay_success_bt_buy = (Button) findViewById(R.id.pay_success_bt_buy);
        pay_success_bt_read = (Button) findViewById(R.id.pay_success_bt_read);
    }

    /**
     * 方法描述：初始化 View
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {
        ((TextView) findViewById(R.id.tv_main_top_title)).setText("加入购物车");
        pay_success_bt_buy.setOnClickListener(this);
        pay_success_bt_read.setOnClickListener(this);
        String payFriendID = getIntent().getExtras().getString("payFriendID");
        if(!TextUtils.isEmpty(payFriendID)){
            tv_success_info.setText("您已经成功购买！");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.pay_success_bt_buy:
                IntentUtil.intent(this, MainFragmentActivity.class);
                break;
            case R.id.pay_success_bt_read:

                break;
            default:
                break;
        }
    }
}
