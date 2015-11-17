package com.ereader.client.ui.buycar;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.AddBuy;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/11/13 15:25
 ***************************************/
public class AddCarSuccessActivity extends BaseActivity implements View.OnClickListener {
    private AppController controller;
    private TextView addcar_tv_name;
    private TextView addcar_tv_coust;
    private TextView addcar_tv_count;
    private TextView addcar_tv_count_money;
    private Button addcar_bt_pay;
    private Button addcar_bt_goto;

    private Handler mHandler = new Handler() {
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
        setContentView(R.layout.add_car_layout);
        controller = AppController.getController(this);
        findView();
        initView();
    }

    /**
     *
     * 方法描述：FindView
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void findView() {

        addcar_tv_name = (TextView) findViewById(R.id.addcar_tv_name);
        addcar_tv_coust = (TextView) findViewById(R.id.addcar_tv_coust);
        addcar_tv_count = (TextView) findViewById(R.id.addcar_tv_count);
        addcar_tv_count_money = (TextView) findViewById(R.id.addcar_tv_count_money);
        addcar_bt_pay = (Button) findViewById(R.id.addcar_bt_pay);
        addcar_bt_goto = (Button) findViewById(R.id.addcar_bt_goto);
    }

    /**
     *
     * 方法描述：初始化 View
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {
        ((TextView) findViewById(R.id.tv_main_top_title)).setText("加入购物车");
        String name = getIntent().getExtras().getString("name");
        String price = getIntent().getExtras().getString("coust");
        addcar_tv_name.setText(name);
        addcar_tv_coust.setText("¥ "+price);

        AddBuy buy = (AddBuy)controller.getContext().getBusinessData("AddBuyResp");
        String countHtml = "购物车已有"+"<font color = \"#43a8d7\">"+buy.getTotal_product_count()+"</font>" +"件商品";
        addcar_tv_count.setText(Html.fromHtml(countHtml));
        String priceHtml = "应付金额："+"<font color = \"#43a8d7\">"+buy.getTotal_price()+"</font>";
        addcar_tv_count_money.setText(Html.fromHtml(priceHtml));

        addcar_bt_pay.setOnClickListener(this);
        addcar_bt_goto.setOnClickListener(this);
    }

    private void getList() {
        ProgressDialogUtil.showProgressDialog(this, "", false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.featuredList(mHandler);
                ProgressDialogUtil.closeProgressDialog();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addcar_bt_pay:
                IntentUtil.intent(this, BuyCarActivity.class);
                break;
            case R.id.addcar_bt_goto:
                this.finish();
                break;
            default:
                break;
        }
    }
}
