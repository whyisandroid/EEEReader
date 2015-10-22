package com.ereader.common.util;


import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class ToastUtil {
    public static int LENGTH_LONG = 1500;
    public static int LENGTH_SHORT = Toast.LENGTH_SHORT;

    private static Toast mToast = null;

    public static void showToast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
        }
        setToastGravity();
        mToast.show();
    }

    public static void showToast(Context context, int msgId, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msgId, duration);
        } else {
            mToast.setText(msgId);
        }

        setToastGravity();
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 方法描述：设置吐司的位置
     *
     * @author: ghf
     * @time: 2014-11-6 下午4:12:25
     */
    public static void setToastGravity() {
        if (mToast != null) {
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
    }
}
