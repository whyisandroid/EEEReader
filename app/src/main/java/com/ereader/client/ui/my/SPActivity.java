package com.ereader.client.ui.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

// 写书评
public class SPActivity extends BaseActivity implements OnClickListener {
    private AppController controller;
    private Button bt_sp_submint;
    private TextView tv_sp_name;
    private RatingBar rbar_sp_star;
    private EditText et_sp_title;
    private EditText et_sp_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_sp_layout);
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
        bt_sp_submint = (Button) findViewById(R.id.bt_sp_submint);
        tv_sp_name = (TextView) findViewById(R.id.tv_sp_name);
        rbar_sp_star = (RatingBar) findViewById(R.id.rbar_sp_star);
        et_sp_title = (EditText)findViewById(R.id.et_sp_title);
        et_sp_comment = (EditText)findViewById(R.id.et_sp_comment);
    }


    /**
     * 方法描述：初始化 View
     *
     * @author: why  评论
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {
        ((TextView) findViewById(R.id.tv_main_top_title)).setText("写书评");
        String name = getIntent().getExtras().getString("name");
        tv_sp_name.setText(name);
        bt_sp_submint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_sp_submint:

                final String title = et_sp_title.getText().toString();
                if(TextUtils.isEmpty(title)){
                    ToastUtil.showLongToast(SPActivity.this,"标题不能为空");
                    return;
                }
                if(title.length()>= 50){
                    ToastUtil.showLongToast(SPActivity.this,"标题最多50个字符");
                    return;
                }


                final String comment = et_sp_comment.getText().toString();
                if(TextUtils.isEmpty(comment)){
                    ToastUtil.showLongToast(SPActivity.this,"内容不能为空");
                    return;
                }
                if(comment.length()>= 1500){
                    ToastUtil.showLongToast(SPActivity.this,"内容最多1500个字符");
                    return;
                }




                ProgressDialogUtil.showProgressDialog(SPActivity.this, "", false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        controller.addComment(rbar_sp_star.getRating(),getIntent().getExtras().getString("orderId"), getIntent().getExtras().getString("id"), title, comment);
                        ProgressDialogUtil.closeProgressDialog();
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
