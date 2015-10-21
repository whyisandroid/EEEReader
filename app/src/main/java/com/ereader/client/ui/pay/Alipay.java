package com.ereader.client.ui.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ereader.client.entities.RechargeOrder;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.pay.alipay.PayResult;
import com.ereader.client.ui.pay.alipay.SignUtils;
import com.ereader.common.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/10/13 21:24
 ***************************************/
public class Alipay {
    private Context mContext;
    // 商户PID
    public static final String PARTNER = "2088911378227176";
    // 商户收款账号
    public static final String SELLER = "2976417620@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMrJs8JONAf8n9yG" +
            "EKeFgIoFlKPgfHxLwv4xj63rcIrXeOiS28MgYotKL/Hc2P6vSf3I4ZtD0rWW9jPe" +
            "wyP8nkqxn6Ew4Oa4dzheYahzXiSJk5kaYbIYGZK+LFc4NgXD1BUZQ1lebEiT1GKA" +
            "b8ZWh6muox1BT1ryVgy91nGHSrUXAgMBAAECgYEAxQntl2wjVGtb7Yt6nTuz5Oh9" +
            "QCp86s02n4UKpaQyC0ZInjssO9G4zrz8DxdW5UjvLR/GTvY8w+L2QPo/RDVOm2xo" +
            "zPZdYQTHkG0QEGMYtSWu0wkV/eiLbAB1ruOPv9Yoihfn55Q9O85fcSHAGlS713GD" +
            "wM4XHLvgiFjttNKbIxkCQQDk/4bsL63Q8Z9729Bhk+eMu8ZviGZCZDpfqz74UfjY" +
            "kbSh398zj3daHI9S6m0q7uKYSkTT/jlJqhZATcyNUFRlAkEA4rL/+c2krzBhh1j6" +
            "KOrovEug/p3qFySsYxSa7ZNNyvCOtFmwsDVH4t2OXzebJ2YnssyJUaFg8LhT9hou" +
            "84WVywJAXApMS3/6Z8HF7T1zatTMu2b5ofh7yXGJeMxWee5Qi5M8u9dEWe/u87wI" +
            "6zWn/RqY3sbzFRXYJsCDQwNCFQZVdQJACcBPWGxrEAoRt+Ow5v+rwSWzqCAcyKAQ" +
            "NjbVrzBt4TPVv5Y4DmxMSkrja98Vcm1neb5ojbVWWSRkoCpiKKFvXwJAeIYqeVIj" +
            "807atXJ+52z0s8M/ySD1eMjUdh38dGbGQeI/93BzwJZo9XcfSjTX76Koeyj45bND" +
            "KAmobzyKqz9NhQ==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private RechargeOrder mOrder;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "充值成功",
                                Toast.LENGTH_SHORT).show();
                        AppController.getController().getCurrentActivity().finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG:
                    if ("true".equals(msg.obj.toString())) {
                    } else {
                        Toast.makeText(mContext, "未安装支付宝应用", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public Alipay(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay() {
        check();
        /*if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mContext)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    dialoginterface.dismiss();
                                }
                            }).show();
            return;
        }*/
        // 订单
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        LogUtil.Log("pay", payInfo);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */
    public void check() {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask((Activity) mContext);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();
    }

    public static boolean checkPay(Context mContext) {
        // 构造PayTask 对象
        PayTask payTask = new PayTask((Activity) mContext);
        // 调用查询接口，获取查询结果
        boolean isExist = payTask.checkAccountIfExist();
        return isExist;
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask((Activity) mContext);
        String version = payTask.getVersion();
        Toast.makeText(mContext, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + mOrder.getOut_trade_no() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + mOrder.getTotal_fee() + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + mOrder.getNotify_url()
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=" + "\"" + mOrder.getNotify_url() + "\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public RechargeOrder getmOrder() {
        return mOrder;
    }

    public void setmOrder(RechargeOrder mOrder) {
        this.mOrder = mOrder;
    }
}
