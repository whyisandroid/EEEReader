package com.ereader.client.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.StringUtil;
import com.ereader.common.util.ToastUtil;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private AppController controller;
    private Button bt_login;
    private TextView tv_login_register;
    private TextView tv_login_findpwd;
    private EditText et_login_username;
    private EditText et_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        controller = AppController.getController(this);
        findView();
        initView();
        // test
    }

    /**
     * 方法描述：FindView
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void findView() {
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_login_register = (TextView) findViewById(R.id.tv_login_register);
        tv_login_findpwd = (TextView) findViewById(R.id.tv_login_findpwd);

        et_login_username = (EditText) findViewById(R.id.et_login_username);
        et_login_password = (EditText) findViewById(R.id.et_login_password);

    }


    /**
     * 方法描述：初始化 View
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {
        ((TextView) findViewById(R.id.tv_main_top_title)).setText("登录");
        bt_login.setOnClickListener(this);
        tv_login_findpwd.setOnClickListener(this);
        tv_login_register.setOnClickListener(this);

        et_login_username.setText("admin");
        et_login_password.setText("123321");

        //test
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_login:
                if (invaild()) {
                    login();
                }
                break;
            case R.id.tv_login_findpwd:
                IntentUtil.intent(this, FindPwdActivity.class);
                break;
            case R.id.tv_login_register:
                IntentUtil.intent(this, RegisterActivity.class);
                break;
            default:
                break;
        }
    }


    /**
     * 方法描述：TODO
     *
     * @return
     * @author: why
     * @time: 2014-10-21 上午11:17:11
     */
    private boolean invaild() {
        String account = et_login_username.getText().toString().trim();
        String password = et_login_password.getText().toString().trim();
        String accountValidate = StringUtil.accountName(account);
        if (!TextUtils.isEmpty(accountValidate)) {
            ToastUtil.showToast(this, accountValidate, ToastUtil.LENGTH_LONG);
            return false;
        } else {
            controller.getContext().addBusinessData("user.account", account);
        }
        String passwordValidate = StringUtil.pwd(password);
        if (!TextUtils.isEmpty(passwordValidate)) {
            ToastUtil.showToast(this, passwordValidate, ToastUtil.LENGTH_LONG);
            return false;
        } else {
            controller.getContext().addBusinessData("user.password", password);
        }
        return true;
    }

    /**
     * 方法描述：TODO
     *
     * @author: why
     * @time: 2014-10-21 上午11:17:14
     */
    private void login() {
        ProgressDialogUtil.showProgressDialog(this, "", false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.login();
                ProgressDialogUtil.closeProgressDialog();
            }
        }).start();
    }
}
